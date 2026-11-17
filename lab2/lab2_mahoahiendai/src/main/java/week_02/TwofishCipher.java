package week_02;

import java.security.MessageDigest;
import java.util.Arrays;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.TwofishEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.*;

/**
 * Thuật toán Twofish (sử dụng thư viện BouncyCastle):
 * - Là mã khối đối xứng có kích thước khối 128 bit (16 byte), hỗ trợ khóa lên tới 256 bit (32 byte)[cite: 98].
 * - Mật khẩu người dùng nhập vào được băm qua SHA-256 để luôn thu được khóa chuẩn 32 byte[cite: 98, 99].
 */
public final class TwofishCipher {
    private TwofishCipher() {}

    /** Sinh vector IV ngẫu nhiên 16 byte (tương ứng kích thước 1 khối Twofish) và xuất dạng 32 ký tự HEX[cite: 98, 99]. */
    public static String newIv() { return CipherUtil.hex(CipherUtil.randomBytes(16)); }

    private static byte[] run(boolean encrypt, byte[] input, String key, String ivHex) throws Exception {
        CipherUtil.requireKey(key); //[cite: 99]
        byte[] iv = CipherUtil.fromHex(ivHex); // Chuyển chuỗi HEX của IV về mảng byte[cite: 99]
        if (iv.length != 16) {
            throw new IllegalArgumentException("IV phải đúng 16 byte = 32 ký tự HEX. Bấm Generate IV để tạo."); //[cite: 99]
        }
        // Dùng hàm băm SHA-256 chuyển chuỗi mật khẩu bất kỳ thành mảng khóa 32 byte (256 bit)[cite: 98, 99]
        byte[] k = MessageDigest.getInstance("SHA-256").digest(CipherUtil.utf8(key));

        // Khởi tạo bộ mã hóa Twofish chạy ở chế độ CBC và tự động đệm PKCS7 (tương tự PKCS5 cho khối 16 byte)[cite: 98, 99]
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(CBCBlockCipher.newInstance(new TwofishEngine()));
        // Nạp chế độ (mã hóa/giải mã) kèm khóa K và vector khởi tạo IV[cite: 99]
        cipher.init(encrypt, new ParametersWithIV(new KeyParameter(k), iv));

        // Cấp phát mảng đầu ra đủ lớn và xử lý toàn bộ dữ liệu đầu vào[cite: 99]
        byte[] out = new byte[cipher.getOutputSize(input.length)];
        int len = cipher.processBytes(input, 0, input.length, out, 0);
        len += cipher.doFinal(out, len); // Hoàn tất khối cuối cùng và xử lý phần đệm[cite: 99]
        return Arrays.copyOf(out, len); // Cắt bỏ phần dư thừa của mảng đệm[cite: 99]
    }

    public static String encrypt(String plaintext, String key, String ivHex) throws Exception {
        return CipherUtil.b64(run(true, CipherUtil.utf8(plaintext), key, ivHex)); //[cite: 99]
    }

    public static String decrypt(String ciphertext, String key, String ivHex) throws Exception {
        return CipherUtil.text(run(false, CipherUtil.fromB64(ciphertext), key, ivHex)); //[cite: 99]
    }

    /** Định dạng xuất file: lưu IV ở dòng 1 và bản mã ở dòng 2 (không lưu khóa bí mật)[cite: 98, 99]. */
    public static String toFile(String ivHex, String ciphertext) {
        return "IV=" + ivHex.trim() + "\n" + ciphertext.trim() + "\n"; //[cite: 99]
    }

    /** Đọc lại file đã lưu để tách lấy IV và bản mã[cite: 99]. */
    public static String[] fromFile(String content) {
        String[] lines = content.trim().split("\\R", 2);
        if (lines.length < 2 || !lines[0].startsWith("IV=")) {
            throw new IllegalArgumentException("Tệp không đúng định dạng: dòng 1 phải là IV=..., dòng 2 là bản mã."); //[cite: 99]
        }
        return new String[]{lines[0].substring(3).trim(), lines[1].trim()}; //[cite: 99]
    }
}