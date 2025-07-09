package Java_Compiler;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Error_mark {
    public void mark(JTextPane textPane, String errorMessage) throws BadLocationException {

        int[] markline =new int[100];//设置最大错误行数为100
        Pattern pattern1 = Pattern.compile("file\\.java:(\\d+)"); // 修正正则表达式
        Matcher matcher1 = pattern1.matcher(errorMessage);

        int count = 0;
        while (matcher1.find()) {
            int number = Integer.parseInt(matcher1.group(1));
            if(number>0) {
                markline[count] = number;
                count++;
            }
        }

        if (count == 0) {
            System.out.println("错误行数输出为空");
        }

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet errorSet = new SimpleAttributeSet();
        StyleConstants.setForeground(errorSet, Color.WHITE);
        StyleConstants.setBackground(errorSet, Color.RED);

        int currentLine = 1; // 当前行号
        int start = 0; // 错误行的起始位置
        int end = 0; // 错误行的结束位置
        int op=0;

        while(count!=0)
        {
            for (int i = 0; i < doc.getLength(); i++) {
                char ch = doc.getText(i, 1).charAt(0);
                if (ch == '\n') {
                    if (currentLine == markline[op]) { // 找到第一个错误行
                        start = end;
                        end = i;
                        doc.setCharacterAttributes(start, end - start, errorSet, false);
                        markline[op] = -1; // 标记为已处理
                        op++;
                    }
                    end = i + 1;
                    currentLine++;
                }
            }
            count--;
        }

        // 检查是否有未处理的错误行
        for (int line : markline) {
            if (line > 0) {
                System.out.println("未处理的错误行: " + line);
            }
        }

    }
}