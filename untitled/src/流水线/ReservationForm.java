package 流水线;


import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 继承JFrame可以使得定义的类成为一个窗口，可以修改窗口的属性和方法

/**
 * 预约表的界面设置
 */
public class ReservationForm extends JFrame {
    public static JButton[][] btn; // 按钮
    public int mat[][] = new int[20][20]; // 预约表
    Container container = null;
    ActionListener listener = null; // 监听网格中的按钮点击情况

    ReservationForm(int M, int N) {
        JFrame app = new JFrame("预约表");  // 创建窗口，定义窗口名字
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //用户单击窗口的关闭按钮时程序关闭
        container = app.getContentPane(); // JFrame不能直接添加组件，所有组件需要添加到ContentPane中
        container.setLayout(new GridLayout(M + 1, N + 1)); // 设置布局，布局为网格布局，每个方格是个按钮
        app.setLocation(400, 200); // 设置窗口所在位置，零点是显示器左上角
        listener = new ActionListener() { // 初始化监听器
            public void actionPerformed(ActionEvent event) { // 事件处理方法
                JButton temp = (JButton) event.getSource(); // 获取事件源，这里是一个按钮
                if (temp.getActionCommand() != "") { // 获取被点击按钮位于网格的位置
                    if (temp.getText() == "") // 获取按钮的名字
                        temp.setText("1"); // 空按钮设置名字为1
                    else if (temp.getText() == "1") // 取消设置为1
                        temp.setText("");
                    else if (temp.getText() == "确认") // 点击确认
                    {
                        setReservationForm(M, N);
                        Answer answer = new Answer(M, N, mat);
                    }
                }
            }
        };
        buttonHelp(M,N);
        app.setSize(1000, 400);
        app.setVisible(true);  // 设置可见
    }

    // 为网格设置按钮，并为按钮绑定事件、设置属性
    private void buttonHelp(int M,int N){
        // 为每个网格设置按钮，并绑定监听器
        btn = new JButton[M + 1][N + 1];
        for (int i = 0; i < M + 1; i++)
            for (int j = 0; j < N + 1; j++) {
                int num = i * (N + 1) + j;
                btn[i][j] = new JButton(""); // 按钮名字为空
                btn[i][j].setContentAreaFilled(false); // 设置成透明按钮
                btn[i][j].setActionCommand("" + num); // 被点击后响应消息为位置
                btn[i][j].addActionListener(listener);
                container.add(btn[i][j]);
            }
        btn[0][0].setText("确认");
        btn[0][0].setContentAreaFilled(true);
        for (int i = 1; i < M + 1; i++) {
            btn[i][0].setText("S" + i);
            btn[i][0].setActionCommand("");
        }
        for (int i = 1; i < N + 1; i++) {
            btn[0][i].setText("t" + i);
            btn[0][i].setActionCommand("");
        }
    }

    // 根据按钮点击请情况设置预约表,并打印
    private void setReservationForm(int M, int N) {
        String btnName = null;
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                btnName = btn[i][j].getText();
                if (btnName == "1")
                    mat[i][j] = 1;
                else
                    mat[i][j] = 0;
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }


}
