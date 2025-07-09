package Java_Compiler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Introduction {
    public void Create_Introduction(){
        JFrame frame = new JFrame("开发者信息");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        String Introduce = "开发人：网络空间安全四班的代码菠萝哥——蔡佳豪\n"
                +"开发原因：因为感兴趣，且想试试经过谢大选修摧残后，自己的开发能力能达到什么水平吧\n"
                +"开发过程：2025.1.12日前我会将开发过程，思路，遇到的问题以及如何解决的全部打包放在我的csdn账号上（名称：香袋Plus）\n"
                +"开发感悟：当你没有参考文章可以借鉴时，就是考验你的时候了。"
                +"操作指南（点击最下方的按钮）";
        JButton button = new JButton("操作指南");
        button.setBounds(50,150,80,24);
        JTextArea textArea = new JTextArea(Introduce);
        textArea.setEditable(false); // 设置文本区域不可编辑

        frame.add(textArea,BorderLayout.CENTER);
        frame.add(button,BorderLayout.SOUTH);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "莫急，待完善！！", "温馨提醒", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        frame.setVisible(true);

    }
}
