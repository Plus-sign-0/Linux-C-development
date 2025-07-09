package Java_Compiler;

import javax.swing.JTextPane;
import javax.swing.text.*;
import java.awt.*;

public class Add_color {
    JTextPane text_input;
    StyledDocument doc;

    public Add_color(JTextPane textInput) {
        this.text_input = textInput;
        this.doc = text_input.getStyledDocument();
        // 创建并设置默认样式
        Style defaultStyle = text_input.addStyle("Default", null);
        StyleConstants.setForeground(defaultStyle, Color.WHITE); // 设置默认前景色为黑色
        doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);

        // 创建并设置关键字样式
        Style keywordStyle1 = text_input.addStyle("Keyword1", defaultStyle);
        StyleConstants.setForeground(keywordStyle1, Color.ORANGE);

        Style keywordStyle2 = text_input.addStyle("Keyword2", defaultStyle);
        StyleConstants.setForeground(keywordStyle2, new Color(0, 191, 255));

        Style keywordStyle3 = text_input.addStyle("Keyword3", defaultStyle);
        StyleConstants.setForeground(keywordStyle3,new Color(144, 238, 144));

        Style keywordStyle4 = text_input.addStyle("Keyword4", defaultStyle);
        StyleConstants.setForeground(keywordStyle4,new Color(160,70,220));

        Style keywordStyle5 = text_input.addStyle("Keyword5", defaultStyle);
        StyleConstants.setForeground(keywordStyle5,new Color(220,220,220));
    }

    public void markKeywords() {

        String text = text_input.getText();

        System.out.println(text);

        String[] keywords1 = {
                "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
                "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
                "finally", "float", "for", "if", "goto", "implements", "import", "instanceof", "int",
                "interface", "long", "native", "new", "package", "private",
                "protected", "public", "return", "short", "static", "strictfp", "super", "switch",
                "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while",
                "true", "false", "null"
        };

        String[] keywords2 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0","print","printf","println","main"};

        String[] keywords4 = {
                // java.lang
                "Object", "String", "Math", "System", "Runtime",

                // java.io
                "File", "InputStream", "OutputStream", "Reader", "Writer",
                "BufferedReader", "BufferedWriter", "FileReader", "FileWriter", "PrintWriter",

                // java.util
                "List", "Set", "Map", "ArrayList", "LinkedList", "HashSet",
                "LinkedHashSet", "TreeSet", "HashMap", "LinkedHashMap", "TreeMap",
                "Calendar", "Date", "Scanner",

                // java.net
                "URL", "URLConnection", "HttpURLConnection",

                // java.nio (New I/O)
                "Buffer", "ByteBuffer", "CharBuffer", "FileChannel",
                "DatagramChannel", "SocketChannel", "ServerSocketChannel",

                // java.sql
                "Connection", "Statement", "PreparedStatement", "ResultSet",

                // java.awt and javax.swing (Graphical User Interface)
                "Frame", "Window", "Panel", "Button", "Label", "TextField",
                "JFrame", "JPanel", "JButton", "JLabel", "JTextField"
        };

        // 高亮关键字
        for (String keyword : keywords1) {
            int pos = 0;
            while ((pos = text.indexOf(keyword, pos)) != -1) {
                int end = pos + keyword.length();
                if (pos == 0 || text.charAt(pos - 1) != '\n' && end < text.length() && text.charAt(end) != '\n') {
                    doc.setCharacterAttributes(pos, keyword.length(), text_input.getStyle("Keyword1"), false);
                }
                pos += keyword.length(); // 移动到关键字之后，继续查找
            }
        }

        for (String keyword : keywords2) {
            int pos = 0;
            while ((pos = text.indexOf(keyword, pos)) != -1) {
                int end = pos + keyword.length();
                if (pos == 0 || text.charAt(pos - 1) != '\n' && end < text.length() && text.charAt(end) != '\n') {
                    doc.setCharacterAttributes(pos, keyword.length(), text_input.getStyle("Keyword2"), false);
                }
                pos += keyword.length(); // 移动到关键字之后，继续查找
            }
        }

        for (String keyword : keywords4) {
            int pos = 0;
            while ((pos = text.indexOf(keyword, pos)) != -1) {
                int end = pos + keyword.length();
                if (pos == 0 || text.charAt(pos - 1) != '\n' && end < text.length() && text.charAt(end) != '\n') {
                    doc.setCharacterAttributes(pos, keyword.length(), text_input.getStyle("Keyword4"), false);
                }
                pos += keyword.length(); // 移动到关键字之后，继续查找
            }
        }

        // 高亮双引号内的内容
        int pos = 0;
        while ((pos = text.indexOf("\"", pos)) != -1) {
            int start = pos;
            pos++; // 跳过第一个双引号
            while (pos < text.length() && text.charAt(pos) != '\"') {
                if (text.charAt(pos) == '\\' && pos + 1 < text.length() && text.charAt(pos + 1) == '\"') {
                    pos += 2; // 跳过转义符和双引号
                    continue;
                }
                pos++;
            }
            if (pos < text.length()) {
                doc.setCharacterAttributes(start, pos - start + 1, text_input.getStyle("Keyword3"), false);
            }
            pos++;
        }

        // 高亮单引号内的内容

        pos = 0;
        while ((pos = text.indexOf("'", pos)) != -1) {
            int start = pos;
            pos++; // 跳过第一个单引号
            while (pos < text.length() && text.charAt(pos) != '\'') {
                pos++;
            }
            if (pos < text.length()) {
                doc.setCharacterAttributes(start, pos - start + 1, text_input.getStyle("Keyword3"), false);
            }
            pos++;
        }

        // 高亮注释
        pos = 0;
        while ((pos = text.indexOf("//", pos)) != -1) {
            int start = pos;
            pos += 2; // 跳过"//"
            while (pos < text.length() && text.charAt(pos) != '\n') {
                pos++;
            }
            if (pos < text.length()) {
                doc.setCharacterAttributes(start, pos - start, text_input.getStyle("Keyword5"), false);
            }
        }
    }

    public void extractText() {
        String text = text_input.getText();
        System.out.println(text);
    }

}