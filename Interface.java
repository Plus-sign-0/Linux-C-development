package Java_Compiler;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


class MyFrame extends JFrame {

    JTextPane text_input = new JTextPane();
    JTextArea Auto = new JTextArea();//单词一般不会超过20个字符吧？

    public MyFrame() {

        setTitle("Saber限定款-java编译器"); // 编译器的名字
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 添加界面关闭，最小化，最大化功能
        setSize(2400, 1500); // 设置界面大小
        Font font1 = new Font("text", Font.PLAIN, 16);//设置字体
        Font font2 = new Font("cli", Font.PLAIN, 12);
        setLayout(null); // 布置管理器

        text_input.setFont(font1); // 设置默认字体
        text_input.setForeground(Color.WHITE); // 设置字体颜色为白色
        text_input.setCaretColor(Color.WHITE); // 设置光标颜色为白色
        text_input.setBackground(new Color(31, 31, 31)); // 设置文本区域背景为黑色
        text_input.setBounds(180, 0, 850, 858);//设置输入文本框大小

        JScrollPane scrollPane = new JScrollPane(text_input); // 创建滚动面板
        scrollPane.setBounds(180, 0, 850, 858); // 设置滚动面板的位置和大小
        add(scrollPane); // 将滚动面板添加到窗口中

        JMenuBar menuBar = new JMenuBar();//添加菜单
        JMenu Setting=new JMenu(" 设置 ");//添加设置
        JMenu Run=new JMenu(" 运行 ");//添加运行

        //设置里添加具体的设置项目
        JMenuItem Font=new JMenuItem("字体（Font）");
        JMenuItem Experience=new JMenuItem("更好的体验（Better Experience）");
        JMenuItem developer=new JMenuItem("开发者信息（Developer Information）");

        //添加高亮设置按钮在图片的下方
        JButton Color_add = new JButton("一键高亮设置");
        Color_add.setBounds(1217,265,120,36);

        // 创建一个JPanel用于放置图片
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(1030, 0,500, 450);

        // 创建一个JLabel用于显示图片
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("D:\\idea java\\untitled\\src\\Java_Compiler\\photo.jpg")); // 替换为您的图片路径
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // 设置图片居中显示

        // 将JLabel添加到JPanel中
        imagePanel.add(imageLabel);

        //设置CLI界面的文本框
        JTextArea cli = new JTextArea();
        cli.setForeground(Color.WHITE);
        cli.setCaretColor(Color.WHITE);
        cli.setFont(font2);
        cli.setBackground(new Color(31, 31, 31));
        cli.setBounds(1065, 320, 400, 480);
        cli.append("Java文件路径为D:\\idea java\\untitled\\src\\Java_Compiler\n");
        cli.setEditable(false);
        JScrollPane cli_scrollPane = new JScrollPane(cli);
        cli_scrollPane.setBounds(1065, 320, 400, 480);
        add(cli_scrollPane);

        //设置补全代码的文本框（自动显示的）

        Auto.setEditable(false);
        Auto.setForeground(Color.WHITE);
        Auto.setCaretColor(Color.WHITE);
        Auto.setFont(font1);
        Auto.setBackground(new Color(31, 31, 31));
        Auto.setBounds(1065,250,150,67);
        add(Auto);

        //添加到JFrame中
        add(imagePanel);
        add(Color_add);
        Setting.add(Font);
        Setting.add(Experience);
        Setting.add(developer);
        menuBar.add(Setting);
        menuBar.add(Run);
        setShortcut(text_input,Auto);
        setJMenuBar(menuBar);

        //添加字体按钮的监听器
        Font.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Fontstyle ans = new Fontstyle(text_input);
                ans.setVisible(true);
            }
        });

        //添加开发者信息按钮的监听器
        developer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Introduction me = new Introduction();
                me.Create_Introduction();
            }
        });

        //添加一键高亮设置按钮的监听器
        Color_add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                update_color(text_input);
            }
        });

        //添加运行按钮的监听器
        Run.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                cli.setText("Java文件路径为D:\\idea java\\untitled\\src\\Java_Compiler\n");

                String cmd_text =text_input.getText();
                Create_file b = new Create_file(cmd_text);
                b.connect();

                Connect_cmd q = new Connect_cmd();
                q.run(cli,text_input);
            }
        });

        addWindowListener(new WindowAdapter() {

        });


        //添加输入文本的pnt监听器
        //采用延时执行优化卡顿问题
        Document pnt = text_input.getDocument();
        pnt.addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                Timer timer =new Timer(400,ev->
                {
                    update_color(text_input);
                    Auto_completion y = new Auto_completion(text_input,Auto);
                    try {
                        y.checkTree();
                    } catch (BadLocationException ex) {
                        throw new RuntimeException(ex);
                    }

                });
                timer.setRepeats(false);
                timer.start();
            }

            public void removeUpdate(DocumentEvent e) {
                Timer timer =new Timer(400,ev->
                {
                    update_color(text_input);
                    Auto_completion y = new Auto_completion(text_input,Auto);
                    try {
                        y.checkTree();
                    } catch (BadLocationException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }

            public void changedUpdate(DocumentEvent e) {
               //更换字体格式不变化
                //否则会陷入死循环
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                String Path = "D:\\idea java\\untitled\\src\\Java_Compiler\\file.java";
                File file = new File(Path);
                if (file.exists()) {
                    file.delete();
                }
            }
        });

        setVisible(true); // 设置可视化
    }

    public void update_color(JTextPane text)
    {
        Add_color m = new Add_color(text);
        m.markKeywords();
    }

    public void setShortcut(JTextPane text, JTextArea area) {
        // 创建Action来执行复制和粘贴第一行内容
        Action copyAndPasteFirstLineAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int caretPosition = text.getCaretPosition();
                    String textBeforeCaret = text.getText(0, caretPosition);
                    String textAfterCaret = text.getText(caretPosition, text.getDocument().getLength() - caretPosition);

                    String firstLine = area.getText().split("\n")[0];

                    int lastSpaceIndex = textBeforeCaret.lastIndexOf(' ');
                    if (lastSpaceIndex != -1) {
                        textBeforeCaret = textBeforeCaret.substring(0, lastSpaceIndex + 1);
                    }

                    text.setText(textBeforeCaret + firstLine + textAfterCaret);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(text, "Error processing text: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        // 创建Action来执行复制和粘贴第二行内容
        Action copyAndPasteSecondLineAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int caretPosition = text.getCaretPosition();
                    String textBeforeCaret = text.getText(0, caretPosition);
                    String textAfterCaret = text.getText(caretPosition, text.getDocument().getLength() - caretPosition);

                    String[] lines = area.getText().split("\n");
                    if (lines.length > 1) {
                        String secondLine = lines[1];

                        int lastSpaceIndex = textBeforeCaret.lastIndexOf(' ');
                        if (lastSpaceIndex != -1) {
                            textBeforeCaret = textBeforeCaret.substring(0, lastSpaceIndex + 1);
                        }

                        text.setText(textBeforeCaret + secondLine + textAfterCaret);
                    }
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(text, "Error processing text: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        // 创建Action来执行复制和粘贴第三行内容
        Action copyAndPasteThirdLineAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int caretPosition = text.getCaretPosition();
                    String textBeforeCaret = text.getText(0, caretPosition);
                    String textAfterCaret = text.getText(caretPosition, text.getDocument().getLength() - caretPosition);

                    String[] lines = area.getText().split("\n");
                    if (lines.length > 2) {
                        String thirdLine = lines[2];

                        int lastSpaceIndex = textBeforeCaret.lastIndexOf(' ');
                        if (lastSpaceIndex != -1) {
                            textBeforeCaret = textBeforeCaret.substring(0, lastSpaceIndex + 1);
                        }

                        text.setText(textBeforeCaret + thirdLine + textAfterCaret);
                    }
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(text, "Error processing text: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        // 设置快捷键Ctrl+1
        KeyStroke keyStroke1 = KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.CTRL_DOWN_MASK);
        text.getActionMap().put("copyAndPasteFirstLine", copyAndPasteFirstLineAction);
        text.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke1, "copyAndPasteFirstLine");

        // 设置快捷键Ctrl+2
        KeyStroke keyStroke2 = KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.CTRL_DOWN_MASK);
        text.getActionMap().put("copyAndPasteSecondLine", copyAndPasteSecondLineAction);
        text.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke2, "copyAndPasteSecondLine");

        // 设置快捷键Ctrl+3
        KeyStroke keyStroke3 = KeyStroke.getKeyStroke(KeyEvent.VK_3, KeyEvent.CTRL_DOWN_MASK);
        text.getActionMap().put("copyAndPasteThirdLine", copyAndPasteThirdLineAction);
        text.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke3, "copyAndPasteThirdLine");
    }

}

    public class Interface {
        public static void main(String[] args) {
            new MyFrame();
        }
    }





/*

编译样例01(输出)：
package Java_Compiler;

 public class file{
     public static void main(String[] args) {
         int a=10;
         System.out.print("This number is"+a);
     }
 }

编译样例02（输入输出）：

package Java_Compiler;
 import java.util.Scanner;

 public class file {
     public static void main(String[] args) {
         int a,b;
         Scanner in =new Scanner(System.in);
         a=in.nextInt();
         System.out.println(a+9);
         b=in.nextInt();
         System.out.println(b+100);
     }
 }
编译样例03（方法调用）：
package Java_Compiler;

 public class file{
     public static void main(String[] args) {
         int a=10,b=6;
         int c=sum(a,b);
         System.out.println("The end is"+c);
     }
     public static int sum(int a,int b){
         return a+b;
     }
 }

编译样例04（类的调用）：
 package Java_Compiler;

 public class file{
     public static void main(String[] args) {
     computing c = new computing();
     c.or();
     c.add();
     c.sub();
     }
 }
 class computing
 {
     int num=100;
     public void add()
     {
         System.out.println((num+1));
     }

     public void sub()
     {
         System.out.println((num-1));
     }

     public void or()
     {
         System.out.println("This number is not my necessity");
     }
 }


*/
