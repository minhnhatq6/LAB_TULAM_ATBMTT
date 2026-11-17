package week_02;

import javax.crypto.Cipher;
import javax.crypto.spec.*;

/**
 * Thuật toán TripleDES (DESede - Encrypt-Decrypt-Encrypt):
 * - Chạy thuật toán DES 3 lần liên tiếp trên mỗi khối 8 byte: Mã hóa bằng K1 -> Giải mã bằng K2 -> Mã hóa bằng K3[cite: 85].
 * - Khóa dài 24 byte (192 bit) = K1 (8 byte) || K2 (8 byte) || K3 (8 byte) để tăng độ an toàn chống vét cạn so với DES đơn[cite: 85].
 */
public final class TripleDESCipher {
    private static final String TRANSFORMATION = "DESede/CBC/PKCS5Padding"; // DESede là tên chuẩn của 3DES trong Java Cryptography[cite: 85]
    private TripleDESCipher() {}

    /** Kiểm tra khóa bắt buộc phải đủ 24 byte (tương ứng 3 khóa DES con)[cite: 85]. */
    private static SecretKeySpec key(String key) {
        CipherUtil.requireKey(key); //[cite: 85]
        byte[] k = CipherUtil.utf8(key); //[cite: 85]
        if (k.length != 24) {
            throw new IllegalArgumentException("Khóa DESede phải đúng 24 byte (gõ 24 ký tự không dấu). Hiện có " + k.length + " byte."); //[cite: 85]
        }
        return new SecretKeySpec(k, "DESede"); //[cite: 85]
    }

    public static String encrypt(String plaintext, String key) throws Exception {
        // Kích thước khối của 3DES vẫn là 64 bit nên IV ngẫu nhiên vẫn dài 8 byte[cite: 85]
        byte[] iv = CipherUtil.randomBytes(8);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key(key), new IvParameterSpec(iv)); //[cite: 85]
        // Ghép 8 byte IV vào đầu bản mã và chuyển sang Base64[cite: 85]
        return CipherUtil.b64(CipherUtil.concat(iv, cipher.doFinal(CipherUtil.utf8(plaintext))));
    }

    public static String decrypt(String ciphertext, String key) throws Exception {
        byte[] all = CipherUtil.fromB64(ciphertext); //[cite: 85]
        if (all.length < 16) throw new IllegalArgumentException("Bản mã quá ngắn hoặc bị hỏng."); //[cite: 85]
        // Tách 8 byte IV ở đầu và phần dữ liệu mã hóa phía sau[cite: 86]
        byte[] iv = java.util.Arrays.copyOfRange(all, 0, 8);
        byte[] data = java.util.Arrays.copyOfRange(all, 8, all.length);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key(key), new IvParameterSpec(iv)); //[cite: 86]
        return CipherUtil.text(cipher.doFinal(data)); //[cite: 86]
    }
}