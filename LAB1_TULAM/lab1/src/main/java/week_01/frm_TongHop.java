package week_01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class frm_TongHop extends JFrame {
    private JComboBox<String> cbo_algorithm;
    private JLabel lbl_hint, lbl_status;
    private JTextArea txt_plaintext, txt_ciphertext;
    private JTextField txt_key;

    public frm_TongHop() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Chương trình tổng hợp - 6 thuật toán");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(640, 500);
        setLayout(null);

        JLabel lblTitle = new JLabel("CHƯƠNG TRÌNH TỔNG HỢP 6 THUẬT TOÁN CỔ ĐIỂN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(20, 10, 600, 34);
        add(lblTitle);

        JLabel lblAlgo = new JLabel("Thuật toán:");
        lblAlgo.setBounds(20, 50, 80, 22);
        add(lblAlgo);

        cbo_algorithm = new JComboBox<>(CipherTongHop.ALGORITHMS);
        cbo_algorithm.setBounds(100, 50, 180, 28);
        add(cbo_algorithm);

        lbl_hint = new JLabel("Khóa mẫu: 3");
        lbl_hint.setBounds(300, 50, 300, 22);
        add(lbl_hint);

        JLabel lblPlain = new JLabel("Plaintext:");
        lblPlain.setBounds(20, 85, 80, 22);
        add(lblPlain);

        txt_plaintext = new JTextArea();
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setRows(3);
        JScrollPane scrollPlain = new JScrollPane(txt_plaintext);
        scrollPlain.setBounds(20, 110, 580, 65);
        add(scrollPlain);

        JLabel lblKey = new JLabel("Key:");
        lblKey.setBounds(20, 185, 60, 22);
        add(lblKey);

        txt_key = new JTextField("3");
        txt_key.setBounds(80, 185, 200, 28);
        add(txt_key);

        JLabel lblCipher = new JLabel("Ciphertext:");
        lblCipher.setBounds(20, 220, 80, 22);
        add(lblCipher);

        txt_ciphertext = new JTextArea();
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setRows(3);
        JScrollPane scrollCipher = new JScrollPane(txt_ciphertext);
        scrollCipher.setBounds(20, 245, 580, 65);
        add(scrollCipher);

        JButton btn_encrypt = new JButton("Encrypt");
        btn_encrypt.setBounds(20, 325, 100, 30);
        add(btn_encrypt);

        JButton btn_decrypt = new JButton("Decrypt");
        btn_decrypt.setBounds(130, 325, 100, 30);
        add(btn_decrypt);

        JButton btn_open = new JButton("Open File");
        btn_open.setBounds(240, 325, 110, 30);
        add(btn_open);

        JButton btn_save = new JButton("Save File");
        btn_save.setBounds(360, 325, 110, 30);
        add(btn_save);

        JButton btn_sql = new JButton("Kết nối SQL Server");
        btn_sql.setBounds(20, 370, 160, 30);
        add(btn_sql);

        JButton btn_history = new JButton("Xem lịch sử");
        btn_history.setBounds(190, 370, 130, 30);
        add(btn_history);

        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setBounds(20, 420, 580, 25);
        add(lbl_status);

        cbo_algorithm.addActionListener((ActionEvent evt) -> {
            String algorithm = (String) cbo_algorithm.getSelectedItem();
            txt_key.setText(CipherTongHop.sampleKey(algorithm));
            lbl_hint.setText("Khóa mẫu: " + CipherTongHop.sampleKey(algorithm));
        });

        btn_encrypt.addActionListener((ActionEvent evt) -> {
            String algorithm = (String) cbo_algorithm.getSelectedItem();
            String input = txt_plaintext.getText();
            try {
                String result = CipherTongHop.encrypt(algorithm, input, txt_key.getText());
                txt_ciphertext.setText(result);
                String saved = HistoryDao.tryInsert(algorithm, "ENCRYPT", input, result);
                lbl_status.setText("Đã mã hóa " + algorithm + ". " + saved);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_decrypt.addActionListener((ActionEvent evt) -> {
            String algorithm = (String) cbo_algorithm.getSelectedItem();
            String input = txt_ciphertext.getText();
            try {
                String result = CipherTongHop.decrypt(algorithm, input, txt_key.getText());
                txt_plaintext.setText(result);
                String saved = HistoryDao.tryInsert(algorithm, "DECRYPT", input, result);
                lbl_status.setText("Đã giải mã " + algorithm + ". " + saved);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_open.addActionListener((ActionEvent evt) -> {
            String path = FileTextIO.openText(this, txt_ciphertext);
            if (path != null) lbl_status.setText("Đã mở tệp: " + path);
        });

        btn_save.addActionListener((ActionEvent evt) -> {
            String path = FileTextIO.saveText(this, txt_ciphertext, "ciphertext.txt");
            if (path != null) lbl_status.setText("Đã lưu tệp: " + path);
        });

        btn_sql.addActionListener((ActionEvent evt) -> {
            new frm_KetNoiSQL().setVisible(true);
        });

        btn_history.addActionListener((ActionEvent evt) -> {
            new frm_LichSu().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frm_TongHop().setVisible(true));
    }
}