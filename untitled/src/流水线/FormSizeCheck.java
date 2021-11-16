package 流水线;


import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.*;
import java.util.Vector;


/**
 * 定义预约表大小的界面
 */
public class FormSizeCheck extends JFrame {
    public int m, n;
    FormSizeCheck() {
        JFrame app = new JFrame("非线性流水线调度");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(600, 200);
        Container container = app.getContentPane();
        container.setLayout(new FlowLayout());

        JTextField[] t = {
                new JTextField("输入预约表大小："),
                new JTextField("段数（小于15）:"),
                new JTextField(4),     //先输入预约表的行和列
                new JTextField("时间（小于15）:"),
                new JTextField(4)
        };
        t[0].setEditable(false); // 设置不可修改
        t[1].setEditable(false);
        t[3].setEditable(false);

        for (int k = 0; k < 5; k++) {
            container.add(t[k]);
        }

        JButton button = new JButton("确定");
        container.add(button);
        app.setVisible(true);
        app.setLocation(600, 200);
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button) {
                    m = Integer.parseInt(t[2].getText());
                    n = Integer.parseInt(t[4].getText());
                    ReservationForm reservationForm = new ReservationForm(m, n); // 点击确认后弹出预约表表格
                }
            }
        };

        button.addActionListener(listener);
    }


    public static void main(String args[]) {
        FormSizeCheck line = new FormSizeCheck();

    }
}

