package week_01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class frm_TanCong extends JFrame {
    private JTextArea txt_cipher, txt_result;
    private JTextField txt_known_plain, txt_known_cipher;
    private JLabel lbl_status;

    public frm_TanCong() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Phân tích mật mã cổ điển");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(660, 560);
        setLayout(null);

        JLabel lblTitle = new JLabel("PHÂN TÍCH (TẤN CÔNG) MẬT MÃ CỔ ĐIỂN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(20, 10, 600, 34);
        add(lblTitle);

        JLabel lblCipher = new JLabel("Ciphertext:");
        lblCipher.setBounds(20, 50, 100, 22);
        add(lblCipher);

        txt_cipher = new JTextArea("KHOOR");
        txt_cipher.setLineWrap(true);
        txt_cipher.setRows(2);
        JScrollPane scrollCipher = new JScrollPane(txt_cipher);
        scrollCipher.setBounds(130, 50, 480, 50);
        add(scrollCipher);

        JButton btn_bruteforce = new JButton("Brute Force Caesar");
        btn_bruteforce.setBounds(130, 110, 170, 30);
        add(btn_bruteforce);

        JButton btn_frequency = new JButton("Frequency Analysis");
        btn_frequency.setBounds(315, 110, 170, 30);
        add(btn_frequency);

        JLabel lblPlain = new JLabel("Bản rõ đã biết:");
        lblPlain.setBounds(20, 155, 100, 22);
        add(lblPlain);

        txt_known_plain = new JTextField("HELLO");
        txt_known_plain.setBounds(130, 155, 140, 28);
        add(txt_known_plain);

        JLabel lblKnownCipher = new JLabel("Bản mã:");
        lblKnownCipher.setBounds(290, 155, 60, 22);
        add(lblKnownCipher);

        txt_known_cipher = new JTextField("RIJVS");
        txt_known_cipher.setBounds(360, 155, 140, 28);
        add(txt_known_cipher);

        JButton btn_known = new JButton("Known Plaintext (Vigenere)");
        btn_known.setBounds(130, 195, 230, 30);
        add(btn_known);

        JLabel lblResult = new JLabel("Kết quả:");
        lblResult.setBounds(20, 240, 100, 22);
        add(lblResult);

        txt_result = new JTextArea();
        txt_result.setEditable(false);
        txt_result.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollResult = new JScrollPane(txt_result);
        scrollResult.setBounds(130, 240, 480, 220);
        add(scrollResult);

        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setBounds(130, 475, 480, 25);
        add(lbl_status);

        btn_bruteforce.addActionListener((ActionEvent evt) -> {
            String cipher = txt_cipher.getText().trim();
            if (cipher.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext.");
                return;
            }
            txt_result.setText(AttackDemos.caesarBruteForce(cipher));
            txt_result.setCaretPosition(0);
            lbl_status.setText("Đã thử đủ 26 khóa Caesar.");
        });

        btn_frequency.addActionListener((ActionEvent evt) -> {
            String cipher = txt_cipher.getText().trim();
            if (cipher.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext.");
                return;
            }
            txt_result.setText(AttackDemos.frequency(cipher));
            txt_result.setCaretPosition(0);
            lbl_status.setText("Đã thống kê tần suất chữ cái.");
        });

        btn_known.addActionListener((ActionEvent evt) -> {
            try {
                String key = AttackDemos.knownPlaintext(txt_known_plain.getText(), txt_known_cipher.getText());
                txt_result.setText("Khóa Vigenère suy ra từng vị trí: " + key
                        + "\nNếu khóa ngắn hơn bản rõ, khóa sẽ lặp lại (KEY KEY ...).");
                lbl_status.setText("Khóa suy ra: " + key);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frm_TanCong().setVisible(true));
    }
}