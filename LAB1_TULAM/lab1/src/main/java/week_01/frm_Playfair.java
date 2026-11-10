package week_01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class frm_Playfair extends JFrame {
    private JTextArea txt_plaintext;
    private JTextArea txt_ciphertext;
    private JTextArea txt_matrix;
    private JTextField txt_key;
    private JLabel lbl_status;

    public frm_Playfair() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Playfair Cipher");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(630, 560);
        setLayout(null);

        JLabel lblTitle = new JLabel("MẬT MÃ PLAYFAIR");
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

        txt_key = new JTextField("KEYWORD");
        txt_key.setBounds(80, 155, 120, 28);
        add(txt_key);

        JLabel lblKeyHint = new JLabel("Chỉ gồm chữ A-Z, ví dụ KEYWORD");
        lblKeyHint.setBounds(210, 155, 380, 22);
        add(lblKeyHint);

        JLabel lblMatrix = new JLabel("Key Matrix:");
        lblMatrix.setBounds(20, 195, 100, 22);
        add(lblMatrix);

        txt_matrix = new JTextArea();
        txt_matrix.setFont(new Font("Monospaced", Font.BOLD, 14));
        txt_matrix.setEditable(false);
        JScrollPane scrollMatrix = new JScrollPane(txt_matrix);
        scrollMatrix.setBounds(20, 220, 170, 110);
        add(scrollMatrix);

        JButton btn_matrix = new JButton("Generate Key Matrix");
        btn_matrix.setBounds(200, 220, 170, 30);
        add(btn_matrix);

        JLabel lblCipher = new JLabel("Ciphertext:");
        lblCipher.setBounds(20, 340, 100, 22);
        add(lblCipher);

        txt_ciphertext = new JTextArea();
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setRows(3);
        JScrollPane scrollCipher = new JScrollPane(txt_ciphertext);
        scrollCipher.setBounds(20, 365, 570, 65);
        add(scrollCipher);

        JButton btn_encrypt = new JButton("Encrypt");
        btn_encrypt.setBounds(20, 440, 95, 30);
        add(btn_encrypt);

        JButton btn_decrypt = new JButton("Decrypt");
        btn_decrypt.setBounds(125, 440, 95, 30);
        add(btn_decrypt);

        JButton btn_open = new JButton("Open Ciphertext File");
        btn_open.setBounds(230, 440, 170, 30);
        add(btn_open);

        JButton btn_save = new JButton("Save Ciphertext File");
        btn_save.setBounds(410, 440, 170, 30);
        add(btn_save);

        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setBounds(20, 485, 570, 22);
        add(lbl_status);

        btn_matrix.addActionListener((ActionEvent evt) -> {
            try {
                txt_matrix.setText(PlayfairCipher.getKeyMatrix(txt_key.getText()));
                txt_matrix.setCaretPosition(0);
                lbl_status.setText("Đã tạo ma trận khóa 5x5 (I và J dùng chung ô).");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Khóa không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_encrypt.addActionListener((ActionEvent evt) -> {
            String plaintext = txt_plaintext.getText();
            if (plaintext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
                return;
            }
            try {
                String key = txt_key.getText().trim();
                txt_ciphertext.setText(PlayfairCipher.encrypt(plaintext, key));
                lbl_status.setText("Đã mã hóa Playfair (bỏ dấu cách, J -> I, chèn X).");
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
                String key = txt_key.getText().trim();
                txt_plaintext.setText(PlayfairCipher.decrypt(ciphertext, key));
                lbl_status.setText("Đã giải mã Playfair (còn ký tự đệm X).");
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
            String path = FileTextIO.saveText(this, txt_ciphertext, "playfair_ciphertext.txt");
            if (path != null) {
                lbl_status.setText("Đã lưu tệp: " + path);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frm_Playfair().setVisible(true));
    }
}