package Java_Compiler;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Connect_cmd {

    public void run(JTextArea cli,JTextPane textPane) {
        try {
            // 编译 Java 文件
            String[] cmd1 = {"cmd", "/c", "javac", "D:\\idea java\\untitled\\src\\Java_Compiler\\file.java"};
            Process process1 = Runtime.getRuntime().exec(cmd1);

            BufferedReader error = new BufferedReader(new InputStreamReader(process1.getErrorStream(), Charset.forName("GBK")));
            String error_line;
            String error_text="";
            while ((error_line = error.readLine()) != null) {
                String finalLine = error_line;
                error_text = error_text + finalLine;
                SwingUtilities.invokeLater(() -> cli.append(finalLine + "\n"));
            }
            Error_mark g = new Error_mark();
            System.out.println(error_text);
            if(error_text.length()>2)
            {
                g.mark(textPane,error_text);
            }


            int exitCode1 = process1.waitFor();
            if (exitCode1 == 0) {
                System.out.println("编译成功");
                JOptionPane.showMessageDialog(null, "编译成功", "提示", JOptionPane.INFORMATION_MESSAGE);

                cli.setEditable(true);
                // 运行编译后的 Java 文件
                String[] cmd2 = {"cmd", "/c", "java", "D:\\idea java\\untitled\\src\\Java_Compiler\\file.java"};
                Process process2 = Runtime.getRuntime().exec(cmd2);

                Document document = cli.getDocument();
                document.addDocumentListener(new DocumentListener() {
                    public void insertUpdate(DocumentEvent e) {
                        //优化部分1

                            int pos = 0;
                            String pnt = cli.getText().substring(49);
                            while ((pos = pnt.indexOf("\n", pos)) != -1) {
                                pnt = pnt.replaceAll("\n", " ");
                                try {
                                    String ans = cli.getText().substring(48).replaceAll("\n", "");
                                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(process2.getOutputStream(),Charset.forName("GBK")));
                                    writer.println(ans);
                                    writer.flush();
                                } catch (Exception e1) {
                                    System.out.println("输入数据错误" + e1.getMessage());
                                }
                            }
                    }

                    public void removeUpdate(DocumentEvent e) {

                    }

                    public void changedUpdate(DocumentEvent e) {

                    }
                });

                new Thread(() -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process2.getInputStream(),Charset.forName("GBK")))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String finalLine = line;
                            SwingUtilities.invokeLater(() -> cli.append(finalLine + "\n"));
                        }
                        cli.setEditable(false);
                    } catch (IOException e) {
                        System.out.println("输入数据错误" + e.getMessage());
                    }
                }).start();



                //优化部分2
                // 等待 Java 程序结束
                /*
                int exitCode2 = process2.waitFor();
                if (exitCode2 == 0) {
                    System.out.println("运行成功");
                } else {
                    System.out.println("运行失败");
                }
                 */

            } else {
                System.out.println("编译失败");
                JOptionPane.showMessageDialog(null, "编译失败", "提示", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | InterruptedException e) {
            cli.append("执行时发生的问题：" + e.getMessage());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }
}
