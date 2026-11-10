package week_01;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class frm_Menu extends JFrame {

    // Bảng màu giao diện hiện đại (Modern Dark Theme)
    private static final Color BG_DARK = new Color(15, 23, 42);         // #0F172A (Nền chính)
    private static final Color CARD_BG = new Color(30, 41, 59);         // #1E293B (Thẻ container)
    private static final Color BORDER_COLOR = new Color(51, 65, 85);    // #334155 (Viền)
    private static final Color TEXT_PRIMARY = new Color(248, 250, 252); // #F8FAFC (Chữ chính)
    private static final Color TEXT_MUTED = new Color(148, 163, 184);   // #94A3B8 (Chữ phụ)
    private static final Color ACCENT_CYAN = new Color(56, 189, 248);   // #38BDF8 (Điểm nhấn)

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("LAB 1 - Mã hóa đối xứng cổ điển");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 520);
        setResizable(false);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout(0, 16));

        // ================= HEADER =================
        JPanel pnlHeader = new JPanel();
        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        pnlHeader.setBorder(new EmptyBorder(24, 30, 5, 30));

        JLabel lblBadge = new JLabel("AN TOÀN & BẢO MẬT THÔNG TIN • LAB 01");
        lblBadge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblBadge.setForeground(ACCENT_CYAN);
        lblBadge.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitle = new JLabel("MÃ HÓA ĐỐI XỨNG CỔ ĐIỂN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(TEXT_PRIMARY);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("Chọn một mô-đun để mở cửa sổ thực hành độc lập tương ứng:");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(TEXT_MUTED);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        pnlHeader.add(lblBadge);
        pnlHeader.add(Box.createRigidArea(new Dimension(0, 4)));
        pnlHeader.add(lblTitle);
        pnlHeader.add(Box.createRigidArea(new Dimension(0, 6)));
        pnlHeader.add(lblSub);
        add(pnlHeader, BorderLayout.NORTH);

        // ================= BODY =================
        JPanel pnlBody = new JPanel();
        pnlBody.setOpaque(false);
        pnlBody.setLayout(new BoxLayout(pnlBody, BoxLayout.Y_AXIS));
        pnlBody.setBorder(new EmptyBorder(0, 30, 0, 30));

        // Nhóm 1: Sáu thuật toán cơ bản
        JLabel lblGroup1 = new JLabel("6 THUẬT TOÁN CƠ BẢN (BÀI 1.1 – 1.6)");
        lblGroup1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblGroup1.setForeground(TEXT_MUTED);
        pnlBody.add(lblGroup1);
        pnlBody.add(Box.createRigidArea(new Dimension(0, 8)));

        JPanel pnlBasic = new JPanel(new GridLayout(2, 3, 12, 12));
        pnlBasic.setOpaque(false);

        ModernButton btn1 = new ModernButton("1.1  Caesar", new Color(37, 99, 235), new Color(29, 78, 216));
        ModernButton btn2 = new ModernButton("1.2  Vigenère", new Color(37, 99, 235), new Color(29, 78, 216));
        ModernButton btn3 = new ModernButton("1.3  Rail Fence", new Color(37, 99, 235), new Color(29, 78, 216));
        ModernButton btn4 = new ModernButton("1.4  Playfair", new Color(37, 99, 235), new Color(29, 78, 216));
        ModernButton btn5 = new ModernButton("1.5  Transposition", new Color(37, 99, 235), new Color(29, 78, 216));
        ModernButton btn6 = new ModernButton("1.6  Autokey", new Color(37, 99, 235), new Color(29, 78, 216));

        pnlBasic.add(btn1);
        pnlBasic.add(btn2);
        pnlBasic.add(btn3);
        pnlBasic.add(btn4);
        pnlBasic.add(btn5);
        pnlBasic.add(btn6);
        pnlBody.add(pnlBasic);

        pnlBody.add(Box.createRigidArea(new Dimension(0, 18)));

        // Nhóm 2: Nâng cao & Cơ sở dữ liệu
        JLabel lblGroup2 = new JLabel("CÔNG CỤ NÂNG CAO & HỆ THỐNG");
        lblGroup2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblGroup2.setForeground(TEXT_MUTED);
        pnlBody.add(lblGroup2);
        pnlBody.add(Box.createRigidArea(new Dimension(0, 8)));

        JPanel pnlAdvanced = new JPanel(new GridLayout(1, 3, 12, 12));
        pnlAdvanced.setOpaque(false);

        ModernButton btn7 = new ModernButton("⚡ Nâng cao 01 - Tổng hợp", new Color(13, 148, 136), new Color(15, 118, 110));
        ModernButton btn8 = new ModernButton("🛡 Nâng cao 02 - Tấn công", new Color(147, 51, 234), new Color(126, 34, 206));
        ModernButton btn9 = new ModernButton("🗄 Kết nối SQL Server", new Color(217, 119, 6), new Color(180, 83, 9));

        pnlAdvanced.add(btn7);
        pnlAdvanced.add(btn8);
        pnlAdvanced.add(btn9);
        pnlBody.add(pnlAdvanced);

        add(pnlBody, BorderLayout.CENTER);

        // ================= FOOTER =================
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setOpaque(false);
        pnlFooter.setBorder(new EmptyBorder(0, 30, 20, 30));

        JLabel lblNote = new JLabel("ⓘ  Mỗi form mở ra là một cửa sổ riêng (DISPOSE). Đóng form con không tắt ứng dụng.");
        lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNote.setForeground(TEXT_MUTED);
        pnlFooter.add(lblNote, BorderLayout.WEST);

        add(pnlFooter, BorderLayout.SOUTH);

        // ================= SỰ KIỆN NÚT BẤM =================
        btn1.addActionListener((ActionEvent e) -> new frm_Caesar().setVisible(true));
        btn2.addActionListener((ActionEvent e) -> new frm_Vigenere().setVisible(true));
        btn3.addActionListener((ActionEvent e) -> new frm_RailFence().setVisible(true));
        btn4.addActionListener((ActionEvent e) -> new frm_Playfair().setVisible(true));
        btn5.addActionListener((ActionEvent e) -> new frm_Transposition().setVisible(true));
        btn6.addActionListener((ActionEvent e) -> new frm_Autokey().setVisible(true));
        btn7.addActionListener((ActionEvent e) -> new frm_TongHop().setVisible(true));
        btn8.addActionListener((ActionEvent e) -> new frm_TanCong().setVisible(true));
        btn9.addActionListener((ActionEvent e) -> new frm_KetNoiSQL().setVisible(true));
    }

    // ================= CUSTOM BUTTON CLASS =================
    private static class ModernButton extends JButton {
        private final Color normalColor;
        private final Color hoverColor;
        private boolean isHovered = false;

        public ModernButton(String text, Color normalColor, Color hoverColor) {
            super(text);
            this.normalColor = normalColor;
            this.hoverColor = hoverColor;
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(200, 42));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Đổ màu nền bo góc
            g2.setColor(isHovered ? hoverColor : normalColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

            // Vẽ viền nhẹ
            g2.setColor(BORDER_COLOR);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        // Áp dụng Look & Feel của hệ thống trước khi khởi động
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new frm_Menu().setVisible(true));
    }
}