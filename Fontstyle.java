package Java_Compiler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fontstyle extends JFrame{
        JTextField sizeTextField;
        JTextField fontFormatTextField;
        JTextPane textArea;
        //创建窗口用于修改textpane参数
        public Fontstyle(JTextPane textArea){
            this.textArea=textArea;
            setTitle("字体设置");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(300,200);
            setLocation(500,300);
            initComponents();
        }

     public void initComponents() {
        // 创建布局管理器
        setLayout(new FlowLayout());

        // 创建字体大小输入框
        JLabel sizeLabel=new JLabel("字体大小：");
        sizeTextField=new JTextField(20);
        add(sizeLabel);
        add(sizeTextField);

        // 创建字体格式输入框
        JLabel fontFormatLabel=new JLabel("字体格式：");
        fontFormatTextField=new JTextField(20);
        add(fontFormatLabel);
        add(fontFormatTextField);

        // 创建应用按钮
        JButton applyButton=new JButton("修改");
        add(applyButton);

        applyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Apply();
            }
        });
    }

    public void Apply(){
        int size=Integer.parseInt(sizeTextField.getText());
        String fontFormatStr=fontFormatTextField.getText();
        int style=Font.PLAIN;

        // 根据用户输入的字体格式字符串设置样式
        if("粗体".equals(fontFormatStr))
        {
            style=Font.BOLD;
        }
        else if ("斜体".equals(fontFormatStr))
        {
            style=Font.ITALIC;
        }
        else if ("粗斜体".equals(fontFormatStr))
        {
            style=Font.BOLD | Font.ITALIC;
        }

        // 创建字体对象，使用用户输入的字体名称
        Font font=new Font(fontFormatStr,style,size);

        // 应用字体到文本区域
        textArea.setFont(font);
    }
}
