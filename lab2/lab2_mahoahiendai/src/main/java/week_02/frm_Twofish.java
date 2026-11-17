package week_02;

import javax.swing.*;
import java.awt.*;

public class frm_Twofish extends JFrame {
    private JTextArea txt_plaintext, txt_ciphertext;
    private JTextField txt_key, txt_iv;
    private JLabel lbl_status;

    public frm_Twofish() {
        setTitle("Twofish Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(630, 480);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("MẬT MÃ TWOFISH (CBC)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(20, 10, 570, 34);
        add(lblTitle);

        JLabel l1 = new JLabel("Plaintext:"); l1.setBounds(20, 50, 100, 22); add(l1);
        txt_plaintext = new JTextArea(); txt_plaintext.setLineWrap(true);
        JScrollPane s1 = new JScrollPane(txt_plaintext); s1.setBounds(20, 75, 570, 70); add(s1);

        JLabel l2 = new JLabel("Key:"); l2.setBounds(20, 155, 60, 22); add(l2);
        txt_key = new JTextField("Twofish@Lab2"); txt_key.setBounds(95, 155, 220, 28); add(txt_key);
        JLabel lHint = new JLabel("Mật khẩu bất kỳ (khóa = SHA-256)"); lHint.setBounds(325, 155, 260, 22); add(lHint);

        JLabel l3 = new JLabel("IV (HEX):"); l3.setBounds(20, 195, 70, 22); add(l3);
        txt_iv = new JTextField(); txt_iv.setBounds(95, 195, 330, 28); add(txt_iv);
        JButton btn_iv = new JButton("Generate IV"); btn_iv.setBounds(440, 195, 130, 28); add(btn_iv);

        JLabel l4 = new JLabel("Ciphertext:"); l4.setBounds(20, 235, 100, 22); add(l4);
        txt_ciphertext = new JTextArea(); txt_ciphertext.setLineWrap(true);
        JScrollPane s2 = new JScrollPane(txt_ciphertext); s2.setBounds(20, 260, 570, 70); add(s2);

        JButton btn_encrypt = new JButton("Encrypt"); btn_encrypt.setBounds(20, 345, 100, 30); add(btn_encrypt);
        JButton btn_decrypt = new JButton("Decrypt"); btn_decrypt.setBounds(130, 345, 100, 30); add(btn_decrypt);
        JButton btn_save = new JButton("Save File"); btn_save.setBounds(240, 345, 120, 30); add(btn_save);
        JButton btn_open = new JButton("Open File"); btn_open.setBounds(370, 345, 120, 30); add(btn_open);

        lbl_status = new JLabel("Sẵn sàng."); lbl_status.setBounds(20, 390, 570, 36); add(lbl_status);

        btn_iv.addActionListener(e -> {
            txt_iv.setText(TwofishCipher.newIv());
            lbl_status.setText("Đã tạo IV ngẫu nhiên 16 byte. Giữ nguyên IV này để giải mã.");
        });

        btn_encrypt.addActionListener(e -> {
            String pt = txt_plaintext.getText();
            if (pt.isEmpty()) { JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext."); return; }
            try {
                String ct = TwofishCipher.encrypt(pt, txt_key.getText(), txt_iv.getText());
                txt_ciphertext.setText(ct);
                lbl_status.setText("<html>Đã mã hóa Twofish. " + HistoryDao.tryInsert("Twofish", "ENCRYPT", null, pt.length(), ct.length()) + "</html>");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_decrypt.addActionListener(e -> {
            String ct = txt_ciphertext.getText();
            if (ct.isEmpty()) { JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext."); return; }
            try {
                String pt = TwofishCipher.decrypt(ct, txt_key.getText(), txt_iv.getText());
                txt_plaintext.setText(pt);
                lbl_status.setText("<html>Đã giải mã Twofish. " + HistoryDao.tryInsert("Twofish", "DECRYPT", null, ct.length(), pt.length()) + "</html>");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai Key hoặc IV.\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_save.addActionListener(e -> {
            if (txt_ciphertext.getText().isBlank()) { JOptionPane.showMessageDialog(this, "Chưa có bản mã để lưu."); return; }
            String content = TwofishCipher.toFile(txt_iv.getText(), txt_ciphertext.getText());
            String path = FileTextIO.saveString(this, content, "twofish_ciphertext.txt");
            if (path != null) lbl_status.setText("Đã lưu IV + bản mã vào: " + path);
        });

        btn_open.addActionListener(e -> {
            String content = FileTextIO.openString(this);
            if (content == null) return;
            try {
                String[] parts = TwofishCipher.fromFile(content);
                txt_iv.setText(parts[0]);
                txt_ciphertext.setText(parts[1]);
                lbl_status.setText("Đã mở tệp, khôi phục IV và bản mã.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Tệp không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}