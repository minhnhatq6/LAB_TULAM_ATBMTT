package week_02;

import javax.swing.*;
import java.awt.*;

public class frm_AES_CTR extends JFrame {
    private JTextArea txt_plaintext, txt_ciphertext;
    private JTextField txt_key, txt_nonce;
    private JLabel lbl_status;

    public frm_AES_CTR() {
        setTitle("AES-CTR Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 480);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("BÀI NÂNG CAO 01 - AES-256 CHẾ ĐỘ CTR");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(20, 10, 580, 34);
        add(lblTitle);

        JLabel l1 = new JLabel("Plaintext:"); l1.setBounds(20, 50, 100, 22); add(l1);
        txt_plaintext = new JTextArea(); txt_plaintext.setLineWrap(true);
        JScrollPane s1 = new JScrollPane(txt_plaintext); s1.setBounds(20, 75, 590, 70); add(s1);

        JLabel l2 = new JLabel("Key (Base64):"); l2.setBounds(20, 155, 100, 22); add(l2);
        txt_key = new JTextField(); txt_key.setBounds(125, 155, 330, 28); add(txt_key);
        JButton btn_genkey = new JButton("Generate Key"); btn_genkey.setBounds(465, 155, 145, 28); add(btn_genkey);

        JLabel l3 = new JLabel("Nonce (Base64):"); l3.setBounds(20, 195, 105, 22); add(l3);
        txt_nonce = new JTextField(); txt_nonce.setBounds(125, 195, 330, 28); add(txt_nonce);
        JButton btn_gennonce = new JButton("Generate Nonce"); btn_gennonce.setBounds(465, 195, 145, 28); add(btn_gennonce);

        JLabel l4 = new JLabel("Ciphertext:"); l4.setBounds(20, 235, 100, 22); add(l4);
        txt_ciphertext = new JTextArea(); txt_ciphertext.setLineWrap(true);
        JScrollPane s2 = new JScrollPane(txt_ciphertext); s2.setBounds(20, 260, 590, 70); add(s2);

        JButton btn_encrypt = new JButton("Encrypt"); btn_encrypt.setBounds(20, 345, 100, 30); add(btn_encrypt);
        JButton btn_decrypt = new JButton("Decrypt"); btn_decrypt.setBounds(130, 345, 100, 30); add(btn_decrypt);
        JButton btn_save = new JButton("Save File"); btn_save.setBounds(240, 345, 120, 30); add(btn_save);
        JButton btn_open = new JButton("Open File"); btn_open.setBounds(370, 345, 120, 30); add(btn_open);

        lbl_status = new JLabel("Sẵn sàng."); lbl_status.setBounds(20, 390, 590, 36); add(lbl_status);

        btn_genkey.addActionListener(e -> {
            txt_key.setText(AESCTRCipher.newKey());
            lbl_status.setText("Đã tạo khóa ngẫu nhiên 256 bit.");
        });

        btn_gennonce.addActionListener(e -> {
            txt_nonce.setText(AESCTRCipher.newNonce());
            lbl_status.setText("Đã tạo nonce ngẫu nhiên 96 bit.");
        });

        btn_encrypt.addActionListener(e -> {
            String pt = txt_plaintext.getText();
            if (pt.isEmpty()) { JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext."); return; }
            try {
                String ct = AESCTRCipher.encrypt(pt, txt_key.getText(), txt_nonce.getText());
                txt_ciphertext.setText(ct);
                lbl_status.setText("<html>Đã mã hóa AES-CTR. " + HistoryDao.tryInsert("AES-CTR", "ENCRYPT", null, pt.length(), ct.length()) + "</html>");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_decrypt.addActionListener(e -> {
            String ct = txt_ciphertext.getText();
            if (ct.isEmpty()) { JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext."); return; }
            try {
                String pt = AESCTRCipher.decrypt(ct, txt_key.getText(), txt_nonce.getText());
                txt_plaintext.setText(pt);
                lbl_status.setText("<html>Đã giải mã AES-CTR. " + HistoryDao.tryInsert("AES-CTR", "DECRYPT", null, ct.length(), pt.length()) + "</html>");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_save.addActionListener(e -> {
            if (txt_ciphertext.getText().isBlank()) { JOptionPane.showMessageDialog(this, "Chưa có bản mã để lưu."); return; }
            String content = AESCTRCipher.toFile(txt_nonce.getText(), txt_ciphertext.getText());
            String path = FileTextIO.saveString(this, content, "aes_ctr_ciphertext.txt");
            if (path != null) lbl_status.setText("Đã lưu nonce + bản mã vào: " + path);
        });

        btn_open.addActionListener(e -> {
            String content = FileTextIO.openString(this);
            if (content == null) return;
            try {
                String[] parts = AESCTRCipher.fromFile(content);
                txt_nonce.setText(parts[0]);
                txt_ciphertext.setText(parts[1]);
                lbl_status.setText("Đã mở tệp. Nhập đúng Key rồi bấm Decrypt.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Tệp không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}