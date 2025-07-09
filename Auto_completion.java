package Java_Compiler;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.util.Arrays;
import java.util.Objects;

public class Auto_completion {
    JTextPane textPane;
    JTextArea textArea;
    public Auto_completion(JTextPane textPane,JTextArea textArea) {
        this.textPane = textPane;
        this.textArea = textArea;
    }

    //提取空格后面的字符串查询法
    //目前没有考虑=，-等一系列的运算符号后面的查询方法
    public String Gettext() throws BadLocationException {
        int caretPosition = textPane.getCaretPosition();
        String text0 = textPane.getText(0,caretPosition);
        int end = text0.lastIndexOf(" ");
        return text0.substring(end+1);
    }

    String[] word={
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "if", "goto", "implements", "import", "instanceof", "int",
            "interface", "long", "native", "new", "package", "private",
            "protected", "public", "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while",
            "true", "false", "null","Object", "String", "Math", "System", "Runtime",
            "File", "InputStream", "OutputStream", "Reader", "Writer",
            "BufferedReader", "BufferedWriter", "FileReader", "FileWriter", "PrintWriter",
            "List", "Set", "Map", "ArrayList", "LinkedList", "HashSet",
            "LinkedHashSet", "TreeSet", "HashMap", "LinkedHashMap", "TreeMap",
            "Calendar", "Date", "Scanner",
            "URL", "URLConnection", "HttpURLConnection",
            "Buffer", "ByteBuffer", "CharBuffer", "FileChannel",
            "DatagramChannel", "SocketChannel", "ServerSocketChannel",
            "Connection", "Statement", "PreparedStatement", "ResultSet",
            "Frame", "Window", "Panel", "Button", "Label", "TextField",
            "JFrame", "JPanel", "JButton", "JLabel", "JTextField"
    };


    public void checkTree() throws BadLocationException {
        Arrays.sort(word);
        String key= Gettext();
        //自动检索的结果
        String advice1 = "";
        String advice2 = "";
        String advice3 = "";

        // 找到第一个匹配的字符串的索引
        for (int i = 0; i < word.length; i++) {
            if (word[i].startsWith(key)) {
                advice1 = word[i];
                if(word[i+1].startsWith(key)&&i+1<word.length)
                {
                    advice2 = word[i+1];
                    if(word[i+2].startsWith(key)&&i+2<word.length)
                    {
                        advice3 = word[i+2];
                    }
                    else
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
                break;
            }
        }

        if(Objects.equals(key, "{"))advice1="}";
        else if(Objects.equals(key, "("))advice1=")";
        else if(Objects.equals(key, "'"))advice1="'";
        else if(Objects.equals(key, "["))advice1="]";
        else if(Objects.equals(key, "\""))advice1="\"";
        else if(Objects.equals(key, "."))advice1=".";

        if(advice1==null)advice1=";";
        else if(advice2==null)advice2=";";
        else if(advice3==null)advice2=";";

        String text1=advice1+"\n"+advice2+"\n"+advice3;
       // System.out.println("找到了"+advice1+" "+advice2+" "+advice3);
        textArea.setText(text1);
    }



}
