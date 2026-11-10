package week_01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class frm_Transposition extends JFrame {
    private JTextArea txt_plaintext;
    private JTextArea txt_ciphertext;
    private JTextField txt_key;
    private JLabel lbl_status;

    public frm_Transposition() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Transposition Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(630, 430);
        setLayout(null);

        JLabel lblTitle = new JLabel("MẬT MÃ HOÁN VỊ CỘT (TRANSPOSITION)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(20, 10, 570, 34);
        add(lblTitle);

        JLabel lblPlain = new JLabel("Plaintext:");
        lblPlain.setBounds(20, 50, 100, 22);
        add(lblPlain);

        txt_plaintext = new JTextArea();
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setRows(3);
        JScrollPane scrollPlain = new JScrollPane(txt_plaintext);
        scrollPlain.setBounds(20, 75, 570, 70);
        add(scrollPlain);

        JLabel lblKey = new JLabel("Key:");
        lblKey.setBounds(20, 155, 60, 22);
        add(lblKey);

        txt_key = new JTextField("2,3,1");
        txt_key.setBounds(80, 155, 120, 28);
        add(txt_key);

        JLabel lblKeyHint = new JLabel("Thứ tự đọc cột, cách nhau dấu phẩy, ví dụ 2,3,1");
        lblKeyHint.setBounds(210, 155, 380, 22);
        add(lblKeyHint);

        JLabel lblCipher = new JLabel("Ciphertext:");
        lblCipher.setBounds(20, 190, 100, 22);
        add(lblCipher);

        txt_ciphertext = new JTextArea();
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setRows(3);
        JScrollPane scrollCipher = new JScrollPane(txt_ciphertext);
        scrollCipher.setBounds(20, 215, 570, 70);
        add(scrollCipher);

        JButton btn_encrypt = new JButton("Encrypt");
        btn_encrypt.setBounds(20, 300, 95, 30);
        add(btn_encrypt);

        JButton btn_decrypt = new JButton("Decrypt");
        btn_decrypt.setBounds(125, 300, 95, 30);
        add(btn_decrypt);

        JButton btn_open = new JButton("Open Ciphertext File");
        btn_open.setBounds(230, 300, 170, 30);
        add(btn_open);

        JButton btn_save = new JButton("Save Ciphertext File");
        btn_save.setBounds(410, 300, 170, 30);
        add(btn_save);

        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setBounds(20, 345, 570, 22);
        add(lbl_status);

        btn_encrypt.addActionListener((ActionEvent evt) -> {
            String plaintext = txt_plaintext.getText();
            if (plaintext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
                return;
            }
            try {
                int[] key = TranspositionCipher.parseKey(txt_key.getText());
                txt_ciphertext.setText(TranspositionCipher.encrypt(plaintext, key));
                lbl_status.setText("Đã mã hóa với " + key.length + " cột.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_decrypt.addActionListener((ActionEvent evt) -> {
            String ciphertext = txt_ciphertext.getText();
            if (ciphertext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext trước khi giải mã.");
                return;
            }
            try {
                int[] key = TranspositionCipher.parseKey(txt_key.getText());
                txt_plaintext.setText(TranspositionCipher.decrypt(ciphertext, key));
                lbl_status.setText("Đã giải mã với " + key.length + " cột.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_open.addActionListener((ActionEvent evt) -> {
            String path = FileTextIO.openText(this, txt_ciphertext);
            if (path != null) {
                lbl_status.setText("Đã mở tệp: " + path);
            }
        });

        btn_save.addActionListener((ActionEvent evt) -> {
            String path = FileTextIO.saveText(this, txt_ciphertext, "transposition_ciphertext.txt");
            if (path != null) {
                lbl_status.setText("Đã lưu tệp: " + path);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frm_Transposition().setVisible(true));
    }
}