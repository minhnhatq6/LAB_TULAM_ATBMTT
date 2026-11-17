package week_02;

import javax.swing.*;
import java.awt.*;

public class frm_KetNoiSQL extends JFrame {
    private JTextField txt_server, txt_port, txt_database, txt_user;
    private JPasswordField txt_password;
    private JLabel lbl_status;

    public frm_KetNoiSQL() {
        setTitle("Kết nối Microsoft SQL Server");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(480, 410);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("KẾT NỐI MICROSOFT SQL SERVER");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(20, 15, 430, 34);
        add(lblTitle);

        addLabel("Server:", 60);
        txt_server = addTextField("127.0.0.1", 60, 320);

        addLabel("Port:", 100);
        txt_port = addTextField("14333", 100, 120);

        addLabel("Database:", 140);
        txt_database = addTextField("LAB2_MaHoaHienDai", 140, 320);

        addLabel("User:", 180);
        txt_user = addTextField("lab2_user", 180, 320);

        addLabel("Password:", 220);
        txt_password = new JPasswordField();
        txt_password.setBounds(110, 220, 320, 28);
        add(txt_password);

        JButton btn_test = new JButton("Test Connection");
        btn_test.setBounds(110, 265, 150, 30);
        add(btn_test);

        JButton btn_history = new JButton("Xem lịch sử");
        btn_history.setBounds(275, 265, 130, 30);
        add(btn_history);

        lbl_status = new JLabel("Chưa kết nối.");
        lbl_status.setBounds(20, 310, 430, 45);
        add(lbl_status);

        btn_test.addActionListener(e -> {
            try {
                int port = Integer.parseInt(txt_port.getText().trim());
                DbConfig.set(txt_server.getText().trim(), port, txt_database.getText().trim(),
                        txt_user.getText().trim(), txt_password.getPassword());
                lbl_status.setText("<html>" + DbConfig.test() + "</html>");
            } catch (Exception ex) {
                DbConfig.clear();
                lbl_status.setText("<html>Kết nối thất bại: " + ex.getMessage() + "</html>");
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Không kết nối được SQL Server", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_history.addActionListener(e -> new frm_LichSu().setVisible(true));
    }

    private void addLabel(String text, int y) {
        JLabel l = new JLabel(text);
        l.setBounds(20, y, 85, 22);
        add(l);
    }

    private JTextField addTextField(String text, int y, int w) {
        JTextField tf = new JTextField(text);
        tf.setBounds(110, y, w, 28);
        add(tf);
        return tf;
    }
}