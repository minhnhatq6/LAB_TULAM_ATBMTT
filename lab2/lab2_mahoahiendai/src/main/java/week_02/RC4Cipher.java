package week_02;

/**
 * Thuật toán RC4 (Rivest Cipher 4 - Mã dòng / Stream Cipher):
 * - Sinh ra một dòng khóa giả ngẫu nhiên (Keystream) có độ dài bằng đúng dữ liệu, sau đó thực hiện phép XOR từng byte[cite: 95].
 * - Tính chất đối xứng của phép XOR: (P ^ K) ^ K = P, do đó hàm mã hóa và giải mã dùng chung một logic duy nhất[cite: 95].
 */
public final class RC4Cipher {
    private RC4Cipher() {}

    /** Thực thi thuật toán RC4 gồm 2 giai đoạn: KSA (trộn mảng trạng thái) và PRGA (sinh dòng khóa XOR)[cite: 95]. */
    public static byte[] apply(byte[] data, byte[] key) {
        if (key.length < 1 || key.length > 256) {
            throw new IllegalArgumentException("Khóa RC4 phải từ 1 đến 256 byte."); //[cite: 95]
        }
        // --- GIAI ĐOẠN 1: KSA (Key-Scheduling Algorithm) ---
        // Khởi tạo mảng hoán vị S gồm 256 phần tử từ 0 đến 255[cite: 95]
        int[] s = new int[256];
        for (int i = 0; i < 256; i++) s[i] = i;

        // Xáo trộn mảng S dựa trên các byte của khóa key[cite: 95]
        int j = 0;
        for (int i = 0; i < 256; i++) {
            // & 0xFF dùng để chuyển byte có dấu (-128..127) trong Java thành số nguyên không dấu (0..255)[cite: 95]
            j = (j + s[i] + (key[i % key.length] & 0xFF)) & 0xFF;
            // Hoán đổi giá trị S[i] và S[j][cite: 95]
            int t = s[i]; s[i] = s[j]; s[j] = t;
        }

        // --- GIAI ĐOẠN 2: PRGA (Pseudo-Random Generation Algorithm) ---
        byte[] out = new byte[data.length];
        int i = 0; j = 0;
        for (int k = 0; k < data.length; k++) {
            i = (i + 1) & 0xFF; // Tăng chỉ số i theo modulo 256[cite: 95]
            j = (j + s[i]) & 0xFF; // Cập nhật chỉ số j theo S[i][cite: 95]
            // Tiếp tục hoán đổi S[i] và S[j] để làm biến đổi trạng thái liên tục[cite: 95]
            int t = s[i]; s[i] = s[j]; s[j] = t;
            
            // Lấy 1 byte dòng khóa tại vị trí (S[i] + S[j]) mod 256 và XOR (^) với byte dữ liệu thứ k[cite: 95]
            out[k] = (byte) (data[k] ^ s[(s[i] + s[j]) & 0xFF]);
        }
        return out;
    }

    public static String encrypt(String plaintext, String key) {
        CipherUtil.requireKey(key); //[cite: 96]
        // Chuyển kết quả mảng byte sau khi XOR sang chuỗi thập lục phân (HEX) để tránh lỗi ký tự điều khiển không in được[cite: 95, 96]
        return CipherUtil.hex(apply(CipherUtil.utf8(plaintext), CipherUtil.utf8(key)));
    }

    public static String decrypt(String hexCiphertext, String key) {
        CipherUtil.requireKey(key); //[cite: 96]
        // Đổi chuỗi HEX về mảng byte rồi gọi lại đúng hàm apply() để XOR lần 2 khôi phục bản rõ[cite: 95, 96]
        return CipherUtil.text(apply(CipherUtil.fromHex(hexCiphertext), CipherUtil.utf8(key)));
    }
}