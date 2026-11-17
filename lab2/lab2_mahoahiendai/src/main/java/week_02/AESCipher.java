package week_02;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.*;

/**
 * Thuật toán AES-256 chế độ GCM (Galois/Counter Mode):
 * - Là chuẩn mã hóa hiện đại (Authenticated Encryption): vừa bảo mật dữ liệu, vừa tạo thẻ xác thực (Auth Tag 128 bit) để phát hiện bản mã bị chỉnh sửa[cite: 88, 89].
 * - Sử dụng hàm dẫn xuất khóa PBKDF2-HMAC-SHA256 (120.000 vòng lặp) để biến mật khẩu người dùng thành khóa 256 bit (32 byte)[cite: 70, 88, 89].
 */
public final class AESCipher {
    private static final int ITERATIONS = 120_000; // Số vòng lặp PBKDF2 làm chậm quá trình dò mật khẩu brute-force[cite: 70, 88, 89]
    private AESCipher() {}

    public static String encrypt(String plaintext, String password) throws Exception {
        CipherUtil.requireKey(password); //[cite: 89]
        // 1. Sinh chuỗi muối (salt) ngẫu nhiên 16 byte để chống tấn công bảng cầu vồng (Rainbow Table)[cite: 88, 89]
        byte[] salt = CipherUtil.randomBytes(16);
        // 2. Sinh số sử dụng một lần (nonce) 12 byte (kích thước chuẩn tối ưu cho AES-GCM)[cite: 88, 89]
        byte[] nonce = CipherUtil.randomBytes(12);
        // 3. Băm mật khẩu cùng với salt qua 120.000 vòng để tạo khóa AES 256 bit[cite: 88, 89]
        byte[] key = CipherUtil.pbkdf2(password.toCharArray(), salt, ITERATIONS, 256);

        // Chế độ GCM hoạt động như mã dòng nên không cần đệm (NoPadding)[cite: 89]
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        // Khởi tạo với độ dài thẻ xác thực (Tag) là 128 bit (16 byte)[cite: 88, 89]
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, nonce));
        // Kết quả doFinal() tự động gắn thêm 16 byte Authentication Tag ở cuối bản mã[cite: 88, 89]
        byte[] encrypted = cipher.doFinal(CipherUtil.utf8(plaintext));

        // Đóng gói theo cấu trúc: Base64(salt [16B] || nonce [12B] || ciphertext + tag)[cite: 88, 89]
        return CipherUtil.b64(CipherUtil.concat(CipherUtil.concat(salt, nonce), encrypted));
    }

    public static String decrypt(String ciphertext, String password) throws Exception {
        CipherUtil.requireKey(password); //[cite: 89]
        byte[] all = CipherUtil.fromB64(ciphertext); //[cite: 89]
        // Tổng độ dài tối thiểu = 16B (salt) + 12B (nonce) + 16B (GCM tag) = 44 byte[cite: 88, 89]
        if (all.length < 44) throw new IllegalArgumentException("Bản mã AES quá ngắn hoặc bị hỏng.");

        // Tách lần lượt salt (16B đầu), nonce (12B tiếp theo) và phần bản mã kèm tag (phần còn lại)[cite: 89]
        byte[] salt = Arrays.copyOfRange(all, 0, 16);
        byte[] nonce = Arrays.copyOfRange(all, 16, 28);
        byte[] data = Arrays.copyOfRange(all, 28, all.length);

        // Tái tạo lại đúng khóa AES 256 bit từ mật khẩu và salt vừa tách[cite: 89]
        byte[] key = CipherUtil.pbkdf2(password.toCharArray(), salt, ITERATIONS, 256);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, nonce));
        
        // Nếu sai mật khẩu hoặc bản mã bị sửa dù chỉ 1 bit, hàm doFinal sẽ ném ngoại lệ AEADBadTagException[cite: 88, 89]
        return CipherUtil.text(cipher.doFinal(data));
    }
}