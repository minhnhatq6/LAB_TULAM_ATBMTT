package week_02;

import javax.swing.*;

public class frm_RC4 extends frm_DES {
    public frm_RC4() {
        super();
        setTitle("RC4 Cipher");
        lblTitle.setText("MẬT MÃ DÒNG RC4");
        txt_key.setText("Key");
        lblHint.setText("1 - 256 byte; bản mã hiển thị dạng HEX");
    }

    @Override
    protected void setupActions() {
        btn_encrypt.addActionListener(e -> runCrypt(true));
        btn_decrypt.addActionListener(e -> runCrypt(false));
        btn_save.addActionListener(e -> {
            String path = FileTextIO.saveText(this, txt_ciphertext, "rc4_ciphertext.txt");
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
                String out = RC4Cipher.encrypt(src, txt_key.getText());
                txt_ciphertext.setText(out);
                lbl_status.setText("<html>Đã mã hóa RC4. " + HistoryDao.tryInsert("RC4", "ENCRYPT", null, src.length(), out.length()) + "</html>");
            } else {
                String out = RC4Cipher.decrypt(src, txt_key.getText());
                txt_plaintext.setText(out);
                lbl_status.setText("<html>Đã giải mã RC4. " + HistoryDao.tryInsert("RC4", "DECRYPT", null, src.length(), out.length()) + "</html>");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}