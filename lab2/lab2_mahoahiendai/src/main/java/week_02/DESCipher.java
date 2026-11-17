package week_02;

import javax.crypto.Cipher;
import javax.crypto.spec.*;

/**
 * Thuật toán DES (Data Encryption Standard):
 * - Kích thước khối (Block size): 64 bit (8 byte)[cite: 80, 81].
 * - Độ dài khóa: 64 bit (8 byte), trong đó 56 bit dùng để mã hóa và 8 bit kiểm tra chẵn lẻ[cite: 80, 81].
 * - Chế độ hoạt động CBC (Cipher Block Chaining): mỗi khối bản rõ được XOR với khối bản mã trước đó trước khi mã hóa[cite: 80].
 * - Đệm PKCS5Padding: tự động thêm từ 1 đến 8 byte vào cuối để tổng độ dài dữ liệu chia hết cho 8 byte[cite: 80].
 */
public final class DESCipher {
    private static final String TRANSFORMATION = "DES/CBC/PKCS5Padding"; // Khai báo thuật toán / chế độ / kiểu đệm[cite: 81]
    private DESCipher() {}

    /** Kiểm tra và chuyển đổi chuỗi khóa đầu vào thành đối tượng SecretKeySpec 8 byte[cite: 81]. */
    private static SecretKeySpec key(String key) {
        CipherUtil.requireKey(key); // Đảm bảo khóa không rỗng[cite: 70, 81]
        byte[] k = CipherUtil.utf8(key); // Chuyển chuỗi khóa sang mảng byte UTF-8[cite: 69, 81]
        if (k.length != 8) {
            throw new IllegalArgumentException("Khóa DES phải đúng 8 byte (gõ 8 ký tự không dấu). Hiện có " + k.length + " byte."); //[cite: 81]
        }
        return new SecretKeySpec(k, "DES"); // Tạo khóa chuẩn DES[cite: 81]
    }

    public static String encrypt(String plaintext, String key) throws Exception {
        // Sinh vector khởi tạo (IV) ngẫu nhiên 8 byte cho khối đầu tiên, giúp cùng 1 bản rõ mã hóa nhiều lần cho ra kết quả khác nhau[cite: 80, 81]
        byte[] iv = CipherUtil.randomBytes(8);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // Khởi tạo bộ mã hóa với chế độ ENCRYPT_MODE, khóa K và vector IV[cite: 81]
        cipher.init(Cipher.ENCRYPT_MODE, key(key), new IvParameterSpec(iv));
        // Thực hiện mã hóa mảng byte UTF-8 của bản rõ[cite: 81]
        byte[] encrypted = cipher.doFinal(CipherUtil.utf8(plaintext));
        // Ghép IV vào đầu bản mã (IV || Ciphertext) rồi mã hóa Base64 để hiển thị dạng văn bản[cite: 80, 81]
        return CipherUtil.b64(CipherUtil.concat(iv, encrypted));
    }

    public static String decrypt(String ciphertext, String key) throws Exception {
        // Giải mã chuỗi Base64 về mảng byte gốc[cite: 80, 81]
        byte[] all = CipherUtil.fromB64(ciphertext);
        // Độ dài tối thiểu phải gồm 8 byte IV + ít nhất 1 khối mã 8 byte = 16 byte[cite: 81]
        if (all.length < 16) throw new IllegalArgumentException("Bản mã quá ngắn hoặc bị hỏng.");
        // Tách 8 byte đầu tiên làm IV, phần còn lại từ byte thứ 8 trở đi là dữ liệu đã mã hóa[cite: 80, 81]
        byte[] iv = java.util.Arrays.copyOfRange(all, 0, 8);
        byte[] data = java.util.Arrays.copyOfRange(all, 8, all.length);
        
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // Khởi tạo bộ giải mã với đúng khóa K và IV đã tách ra[cite: 81]
        cipher.init(Cipher.DECRYPT_MODE, key(key), new IvParameterSpec(iv));
        // Giải mã, tự động gỡ bỏ phần đệm PKCS5 và chuyển mảng byte về chuỗi tiếng Việt UTF-8[cite: 80, 81]
        return CipherUtil.text(cipher.doFinal(data));
    }
}