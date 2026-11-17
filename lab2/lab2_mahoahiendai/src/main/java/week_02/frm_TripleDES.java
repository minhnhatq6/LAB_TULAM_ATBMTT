package week_02;

import javax.swing.*;

public class frm_TripleDES extends frm_DES {
    public frm_TripleDES() {
        super();
        setTitle("TripleDES Cipher");
        lblTitle.setText("MẬT MÃ TRIPLEDES (3DES)");
        txt_key.setText("123456789012345678901234");
        lblHint.setText("Đúng 24 ký tự (24 byte)");
    }

    @Override
    protected void setupActions() {
        btn_encrypt.addActionListener(e -> runCrypt(true));
        btn_decrypt.addActionListener(e -> runCrypt(false));
        btn_save.addActionListener(e -> {
            String path = FileTextIO.saveText(this, txt_ciphertext, "tripledes_ciphertext.txt");
            if (path != null) lbl_status.setText("Đã lưu tệp: " + path);
        });
        btn_open.addActionListener(e -> {
            String path = FileTextIO.openText(this, txt_ciphertext);
            if (path != null) lbl_status.setText("Đã mở tệp: " + path);
        });
    }

    private void runCrypt(boolean encrypt) {
        String src = encrypt ? txt_plaintext.getText() : txt_ciphertext.getText();
        if (src.isEmpty()) { JOptionPane.showMessageDialog(this, "Hãy nhập dữ liệu trước."); return; }
        try {
            if (encrypt) {
                String out = TripleDESCipher.encrypt(src, txt_key.getText());
                txt_ciphertext.setText(out);
                lbl_status.setText("<html>Đã mã hóa TripleDES. " + HistoryDao.tryInsert("TripleDES", "ENCRYPT", null, src.length(), out.length()) + "</html>");
            } else {
                String out = TripleDESCipher.decrypt(src, txt_key.getText());
                txt_plaintext.setText(out);
                lbl_status.setText("<html>Đã giải mã TripleDES. " + HistoryDao.tryInsert("TripleDES", "DECRYPT", null, src.length(), out.length()) + "</html>");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Thao tác thất bại:\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}