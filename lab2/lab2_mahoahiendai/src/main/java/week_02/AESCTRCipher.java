package week_02;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.*;

/**
 * Thuật toán AES-256 chế độ CTR (Counter Mode):
 * - Biến đổi mã khối AES thành cơ chế mã dòng (Stream Cipher): mã hóa bộ đếm (Nonce || Counter) để tạo ra keystream rồi XOR với bản rõ[cite: 102].
 * - Ưu điểm: Không cần đệm dữ liệu (độ dài bản mã bằng đúng độ dài bản rõ) và có thể tính toán song song nhiều khối cùng lúc[cite: 102].
 */
public final class AESCTRCipher {
    private AESCTRCipher() {}

    public static String newKey() { return CipherUtil.b64(CipherUtil.randomBytes(32)); } // Sinh khóa 256 bit (32 byte)[cite: 102, 103]
    public static String newNonce() { return CipherUtil.b64(CipherUtil.randomBytes(12)); } // Sinh Nonce 96 bit (12 byte)[cite: 102, 103]

    private static byte[] crypt(int mode, byte[] input, String keyB64, String nonceB64) throws Exception {
        byte[] key = CipherUtil.fromB64(keyB64); //[cite: 103]
        byte[] nonce = CipherUtil.fromB64(nonceB64); //[cite: 103]
        if (key.length != 32 || nonce.length != 12) {
            throw new IllegalArgumentException("Cần Key 32 byte và Nonce 12 byte (Base64). Bấm Generate Key / Generate Nonce."); //[cite: 103]
        }
        // Tạo khối đếm (Counter Block) 16 byte = 12 byte Nonce ở đầu + 4 byte bộ đếm ở cuối (khởi tạo giá trị bằng 1)[cite: 102, 103]
        byte[] counterBlock = Arrays.copyOf(nonce, 16);
        counterBlock[15] = 1;

        // Chế độ CTR dùng NoPadding vì phép XOR hoạt động trên từng byte độc lập[cite: 102, 103]
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(mode, new SecretKeySpec(key, "AES"), new IvParameterSpec(counterBlock));
        return cipher.doFinal(input); //[cite: 103]
    }

    public static String encrypt(String plaintext, String keyB64, String nonceB64) throws Exception {
        return CipherUtil.b64(crypt(Cipher.ENCRYPT_MODE, CipherUtil.utf8(plaintext), keyB64, nonceB64)); //[cite: 103]
    }

    public static String decrypt(String ciphertext, String keyB64, String nonceB64) throws Exception {
        return CipherUtil.text(crypt(Cipher.DECRYPT_MODE, CipherUtil.fromB64(ciphertext), keyB64, nonceB64)); //[cite: 103]
    }

    public static String toFile(String nonceB64, String ciphertext) {
        return "NONCE=" + nonceB64.trim() + "\n" + ciphertext.trim() + "\n"; // Lưu Nonce và bản mã, tuyệt đối không lưu khóa[cite: 103]
    }

    public static String[] fromFile(String content) {
        String[] lines = content.trim().split("\\R", 2);
        if (lines.length < 2 || !lines[0].startsWith("NONCE=")) {
            throw new IllegalArgumentException("Tệp không đúng định dạng: dòng 1 phải là NONCE=..., dòng 2 là bản mã."); //[cite: 103]
        }
        return new String[]{lines[0].substring(6).trim(), lines[1].trim()}; //[cite: 103]
    }
}