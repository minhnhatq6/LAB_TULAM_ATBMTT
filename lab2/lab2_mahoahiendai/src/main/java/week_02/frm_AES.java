package week_02;

import javax.swing.*;
import java.awt.*;

public class frm_AES extends JFrame {
    private String currentUser = null;
    private JTextField txt_username, txt_ticket, txt_key;
    private JPasswordField txt_password;
    private JTextArea txt_plaintext, txt_ciphertext;
    private JLabel lbl_status;

    public frm_AES() {
        setTitle("AES Cipher - Register / Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(630, 620);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("AES-GCM - ĐĂNG KÝ / ĐĂNG NHẬP (SQL SERVER)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(20, 10, 580, 34);
        add(lblTitle);

        addLabel("Username:", 55);
        txt_username = new JTextField("sinhvien01"); txt_username.setBounds(110, 55, 220, 28); add(txt_username);

        addLabel("Password:", 90);
        txt_password = new JPasswordField(); txt_password.setBounds(110, 90, 220, 28); add(txt_password);
        JLabel lPassHint = new JLabel("(ít nhất 8 ký tự)"); lPassHint.setBounds(340, 90, 200, 22); add(lPassHint);

        JButton btn_sql = new JButton("Kết nối SQL Server"); btn_sql.setBounds(110, 130, 160, 30); add(btn_sql);
        JButton btn_register = new JButton("Register"); btn_register.setBounds(280, 130, 100, 30); add(btn_register);
        JButton btn_login = new JButton("Login"); btn_login.setBounds(390, 130, 100, 30); add(btn_login);

        addLabel("Ticket:", 175);
        txt_ticket = new JTextField(); txt_ticket.setEditable(false); txt_ticket.setBounds(110, 175, 420, 28); add(txt_ticket);

        addLabel("Plaintext:", 215);
        txt_plaintext = new JTextArea(); txt_plaintext.setLineWrap(true);
        JScrollPane s1 = new JScrollPane(txt_plaintext); s1.setBounds(20, 240, 570, 70); add(s1);

        addLabel("Key AES:", 320);
        txt_key = new JTextField(); txt_key.setBounds(110, 320, 260, 28); add(txt_key);
        JLabel lKeyHint = new JLabel("Mật khẩu để tạo khóa AES-256"); lKeyHint.setBounds(380, 320, 220, 22); add(lKeyHint);

        addLabel("Ciphertext:", 360);
        txt_ciphertext = new JTextArea(); txt_ciphertext.setLineWrap(true);
        JScrollPane s2 = new JScrollPane(txt_ciphertext); s2.setBounds(20, 385, 570, 70); add(s2);

        JButton btn_encrypt = new JButton("Encrypt"); btn_encrypt.setBounds(20, 470, 100, 30); add(btn_encrypt);
        JButton btn_decrypt = new JButton("Decrypt"); btn_decrypt.setBounds(130, 470, 100, 30); add(btn_decrypt);
        JButton btn_save = new JButton("Save File"); btn_save.setBounds(240, 470, 120, 30); add(btn_save);
        JButton btn_open = new JButton("Open File"); btn_open.setBounds(370, 470, 120, 30); add(btn_open);

        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setBounds(20, 515, 570, 40);
        add(lbl_status);

        btn_sql.addActionListener(e -> new frm_KetNoiSQL().setVisible(true));

        btn_register.addActionListener(e -> {
            try {
                String user = txt_username.getText().trim();
                UserDao.register(user, txt_password.getPassword());
                HistoryDao.tryInsert("AES", "REGISTER", user, 0, 0);
                lbl_status.setText("Register thành công: " + user + ". Bấm Login để đăng nhập.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi Register", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_login.addActionListener(e -> {
            try {
                String user = txt_username.getText().trim();
                if (UserDao.login(user, txt_password.getPassword())) {
                    currentUser = user;
                    txt_ticket.setText(UserDao.newTicket());
                    HistoryDao.tryInsert("AES", "LOGIN", user, 0, 0);
                    lbl_status.setText("Login thành công: " + user + ". Có thể dùng Encrypt/Decrypt.");
                } else {
                    currentUser = null;
                    txt_ticket.setText("");
                    JOptionPane.showMessageDialog(this, "Sai username hoặc mật khẩu.", "Login thất bại", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi SQL Server: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_encrypt.addActionListener(e -> {
            if (currentUser == null) { JOptionPane.showMessageDialog(this, "Hãy Login trước khi dùng AES."); return; }
            String pt = txt_plaintext.getText();
            if (pt.isEmpty()) { JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext."); return; }
            try {
                String ct = AESCipher.encrypt(pt, txt_key.getText());
                txt_ciphertext.setText(ct);
                lbl_status.setText("<html>Đã mã hóa AES. " + HistoryDao.tryInsert("AES", "ENCRYPT", currentUser, pt.length(), ct.length()) + "</html>");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Mã hóa thất bại:\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_decrypt.addActionListener(e -> {
            if (currentUser == null) { JOptionPane.showMessageDialog(this, "Hãy Login trước khi dùng AES."); return; }
            String ct = txt_ciphertext.getText();
            if (ct.isEmpty()) { JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext."); return; }
            try {
                String pt = AESCipher.decrypt(ct, txt_key.getText());
                txt_plaintext.setText(pt);
                lbl_status.setText("<html>Đã giải mã AES. " + HistoryDao.tryInsert("AES", "DECRYPT", currentUser, ct.length(), pt.length()) + "</html>");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai Key hoặc bản mã bị sửa.\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_save.addActionListener(e -> {
            String path = FileTextIO.saveText(this, txt_ciphertext, "aes_ciphertext.txt");
            if (path != null) lbl_status.setText("Đã lưu tệp: " + path);
        });
        btn_open.addActionListener(e -> {
            String path = FileTextIO.openText(this, txt_ciphertext);
            if (path != null) lbl_status.setText("Đã mở tệp: " + path);
        });
    }

    private void addLabel(String text, int y) {
        JLabel l = new JLabel(text); l.setBounds(20, y, 90, 22); add(l);
    }
}