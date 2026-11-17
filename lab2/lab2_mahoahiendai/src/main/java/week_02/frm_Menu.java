package week_02;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class frm_Menu extends JFrame {
    private static final Color BG_DARK = new Color(15, 23, 42);
    private static final Color BORDER_COLOR = new Color(51, 65, 85);
    private static final Color TEXT_PRIMARY = new Color(248, 250, 252);
    private static final Color TEXT_MUTED = new Color(148, 163, 184);
    private static final Color ACCENT_CYAN = new Color(56, 189, 248);

    public frm_Menu() {
        setTitle("LAB 2 - Mã hóa đối xứng hiện đại");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout(0, 16));

        // HEADER
        JPanel pnlHeader = new JPanel();
        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        pnlHeader.setBorder(new EmptyBorder(24, 30, 5, 30));

        JLabel lblBadge = new JLabel("AN TOÀN & BẢO MẬT THÔNG TIN • LAB 02");
        lblBadge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblBadge.setForeground(ACCENT_CYAN);

        JLabel lblTitle = new JLabel("MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(TEXT_PRIMARY);

        JLabel lblSub = new JLabel("Chọn một bài thực hành để mở cửa sổ tương ứng:");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(TEXT_MUTED);

        pnlHeader.add(lblBadge);
        pnlHeader.add(Box.createRigidArea(new Dimension(0, 4)));
        pnlHeader.add(lblTitle);
        pnlHeader.add(Box.createRigidArea(new Dimension(0, 6)));
        pnlHeader.add(lblSub);
        add(pnlHeader, BorderLayout.NORTH);

        // BODY
        JPanel pnlBody = new JPanel();
        pnlBody.setOpaque(false);
        pnlBody.setLayout(new BoxLayout(pnlBody, BoxLayout.Y_AXIS));
        pnlBody.setBorder(new EmptyBorder(0, 30, 0, 30));

        JLabel lblGroup1 = new JLabel("CÁC THUẬT TOÁN MÃ KHỐI & MÃ DÒNG (2.1 – 2.6)");
        lblGroup1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblGroup1.setForeground(TEXT_MUTED);
        pnlBody.add(lblGroup1);
        pnlBody.add(Box.createRigidArea(new Dimension(0, 8)));

        JPanel pnlGrid = new JPanel(new GridLayout(3, 3, 12, 12));
        pnlGrid.setOpaque(false);

        ModernButton btnDes = new ModernButton("2.1  DES", new Color(37, 99, 235), new Color(29, 78, 216));
        ModernButton btn3Des = new ModernButton("2.2  TripleDES", new Color(37, 99, 235), new Color(29, 78, 216));
        ModernButton btnAes = new ModernButton("2.3  AES + Login", new Color(13, 148, 136), new Color(15, 118, 110));
        ModernButton btnRc4 = new ModernButton("2.4  RC4", new Color(37, 99, 235), new Color(29, 78, 216));
        ModernButton btnTwofish = new ModernButton("2.5  Twofish", new Color(37, 99, 235), new Color(29, 78, 216));
        ModernButton btnAesCtr = new ModernButton("2.6  Nâng cao - AES-CTR", new Color(147, 51, 234), new Color(126, 34, 206));
        ModernButton btnSql = new ModernButton("🗄 Kết nối SQL Server", new Color(217, 119, 6), new Color(180, 83, 9));
        ModernButton btnHistory = new ModernButton("📋 Xem lịch sử", new Color(71, 85, 105), new Color(51, 65, 85));

        pnlGrid.add(btnDes);
        pnlGrid.add(btn3Des);
        pnlGrid.add(btnAes);
        pnlGrid.add(btnRc4);
        pnlGrid.add(btnTwofish);
        pnlGrid.add(btnAesCtr);
        pnlGrid.add(btnSql);
        pnlGrid.add(btnHistory);
        pnlBody.add(pnlGrid);

        add(pnlBody, BorderLayout.CENTER);

        // FOOTER
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setOpaque(false);
        pnlFooter.setBorder(new EmptyBorder(0, 30, 20, 30));

        JLabel lblNote = new JLabel("ⓘ  Bài nâng cao 02 (AES-CBC) viết bằng Python: chạy tệp aes_cbc_gui.py.");
        lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNote.setForeground(TEXT_MUTED);
        pnlFooter.add(lblNote, BorderLayout.WEST);
        add(pnlFooter, BorderLayout.SOUTH);

        // EVENTS
        btnDes.addActionListener(e -> new frm_DES().setVisible(true));
        btn3Des.addActionListener(e -> new frm_TripleDES().setVisible(true));
        btnAes.addActionListener(e -> new frm_AES().setVisible(true));
        btnRc4.addActionListener(e -> new frm_RC4().setVisible(true));
        btnTwofish.addActionListener(e -> new frm_Twofish().setVisible(true));
        btnAesCtr.addActionListener(e -> new frm_AES_CTR().setVisible(true));
        btnSql.addActionListener(e -> new frm_KetNoiSQL().setVisible(true));
        btnHistory.addActionListener(e -> new frm_LichSu().setVisible(true));
    }

    private static class ModernButton extends JButton {
        private final Color normalColor, hoverColor;
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
            setPreferredSize(new Dimension(200, 44));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                @Override public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isHovered ? hoverColor : normalColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2.setColor(BORDER_COLOR);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frm_Menu().setVisible(true));
    }
}