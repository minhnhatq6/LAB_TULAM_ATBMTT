package week_01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class frm_KetNoiSQL extends JFrame {
    private JTextField txt_server, txt_port, txt_database, txt_user;
    private JPasswordField txt_password;
    private JLabel lbl_status;

    public frm_KetNoiSQL() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Kết nối Microsoft SQL Server");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(480, 420);
        setLayout(null);

        JLabel lblTitle = new JLabel("KẾT NỐI MICROSOFT SQL SERVER");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(20, 15, 430, 34);
        add(lblTitle);

        JLabel lbl1 = new JLabel("Server:");
        lbl1.setBounds(20, 60, 90, 22);
        add(lbl1);
        txt_server = new JTextField("127.0.0.1");
        txt_server.setBounds(110, 60, 330, 28);
        add(txt_server);

        JLabel lbl2 = new JLabel("Port:");
        lbl2.setBounds(20, 100, 90, 22);
        add(lbl2);
        txt_port = new JTextField("14333");
        txt_port.setBounds(110, 100, 130, 28);
        add(txt_port);

        JLabel lbl3 = new JLabel("Database:");
        lbl3.setBounds(20, 140, 90, 22);
        add(lbl3);
        txt_database = new JTextField("LAB1_MaHoaCoDien");
        txt_database.setBounds(110, 140, 330, 28);
        add(txt_database);

        JLabel lbl4 = new JLabel("User:");
        lbl4.setBounds(20, 180, 90, 22);
        add(lbl4);
        txt_user = new JTextField("lab1_user");
        txt_user.setBounds(110, 180, 330, 28);
        add(txt_user);

        JLabel lbl5 = new JLabel("Password:");
        lbl5.setBounds(20, 220, 90, 22);
        add(lbl5);
        txt_password = new JPasswordField();
        txt_password.setBounds(110, 220, 330, 28);
        add(txt_password);

        JButton btn_test = new JButton("Test Connection");
        btn_test.setBounds(110, 265, 150, 30);
        add(btn_test);

        JButton btn_history = new JButton("Xem lịch sử");
        btn_history.setBounds(280, 265, 130, 30);
        add(btn_history);

        lbl_status = new JLabel("Chưa kết nối.");
        lbl_status.setBounds(20, 310, 430, 50);
        add(lbl_status);

        btn_test.addActionListener((ActionEvent evt) -> {
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

        btn_history.addActionListener((ActionEvent evt) -> {
            new frm_LichSu().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frm_KetNoiSQL().setVisible(true));
    }
}