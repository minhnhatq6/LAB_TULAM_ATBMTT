package week_02;

import javax.swing.*;
import java.awt.*;

public class frm_DES extends JFrame {
    protected JTextArea txt_plaintext, txt_ciphertext;
    protected JTextField txt_key;
    protected JLabel lbl_status, lblTitle, lblHint;
    protected JButton btn_encrypt, btn_decrypt, btn_save, btn_open;

    public frm_DES() {
        setTitle("DES Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(630, 440);
        setLocationRelativeTo(null);
        setLayout(null);

        lblTitle = new JLabel("MẬT MÃ DES");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(20, 10, 570, 34);
        add(lblTitle);

        JLabel l1 = new JLabel("Plaintext:");
        l1.setBounds(20, 50, 100, 22);
        add(l1);

        txt_plaintext = new JTextArea();
        txt_plaintext.setLineWrap(true);
        JScrollPane s1 = new JScrollPane(txt_plaintext);
        s1.setBounds(20, 75, 570, 70);
        add(s1);

        JLabel l2 = new JLabel("Key:");
        l2.setBounds(20, 155, 50, 22);
        add(l2);

        txt_key = new JTextField("12345678");
        txt_key.setBounds(75, 155, 240, 28);
        add(txt_key);

        lblHint = new JLabel("Đúng 8 ký tự không dấu (8 byte)");
        lblHint.setBounds(325, 155, 270, 22);
        add(lblHint);

        JLabel l3 = new JLabel("Ciphertext:");
        l3.setBounds(20, 190, 100, 22);
        add(l3);

        txt_ciphertext = new JTextArea();
        txt_ciphertext.setLineWrap(true);
        JScrollPane s2 = new JScrollPane(txt_ciphertext);
        s2.setBounds(20, 215, 570, 70);
        add(s2);

        btn_encrypt = new JButton("Encrypt"); btn_encrypt.setBounds(20, 300, 100, 30); add(btn_encrypt);
        btn_decrypt = new JButton("Decrypt"); btn_decrypt.setBounds(130, 300, 100, 30); add(btn_decrypt);
        btn_save = new JButton("Save File"); btn_save.setBounds(240, 300, 120, 30); add(btn_save);
        btn_open = new JButton("Open File"); btn_open.setBounds(370, 300, 120, 30); add(btn_open);

        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setBounds(20, 345, 570, 36);
        add(lbl_status);

        setupActions();
    }

    protected void setupActions() {
        btn_encrypt.addActionListener(e -> runCrypt(true));
        btn_decrypt.addActionListener(e -> runCrypt(false));
        btn_save.addActionListener(e -> {
            String path = FileTextIO.saveText(this, txt_ciphertext, "des_ciphertext.txt");
            if (path != null) lbl_status.setText("Đã lưu tệp: " + path);
        });
        btn_open.addActionListener(e -> {
            String path = FileTextIO.openText(this, txt_ciphertext);
            if (path != null) lbl_status.setText("Đã mở tệp: " + path);
        });
    }

    private void runCrypt(boolean encrypt) {
        String src = encrypt ? txt_plaintext.getText() : txt_ciphertext.getText();
        if (src.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập dữ liệu trước.");
            return;
        }
        try {
            if (encrypt) {
                String out = DESCipher.encrypt(src, txt_key.getText());
                txt_ciphertext.setText(out);
                lbl_status.setText("<html>Đã mã hóa DES. " + HistoryDao.tryInsert("DES", "ENCRYPT", null, src.length(), out.length()) + "</html>");
            } else {
                String out = DESCipher.decrypt(src, txt_key.getText());
                txt_plaintext.setText(out);
                lbl_status.setText("<html>Đã giải mã DES. " + HistoryDao.tryInsert("DES", "DECRYPT", null, src.length(), out.length()) + "</html>");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Thao tác thất bại:\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}