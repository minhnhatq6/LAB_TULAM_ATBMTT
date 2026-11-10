package week_01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class frm_LichSu extends JFrame {
    private JTable tbl_history;
    private JLabel lbl_status;

    public frm_LichSu() {
        initComponents();
        setLocationRelativeTo(null);
        tbl_history.setModel(HistoryDao.emptyModel());
        loadData();
    }

    private void initComponents() {
        setTitle("Lịch sử thao tác (SQL Server)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLayout(null);

        JLabel lblTitle = new JLabel("LỊCH SỬ THAO TÁC - BẢNG DBO.HISTORY");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBounds(20, 10, 750, 34);
        add(lblTitle);

        tbl_history = new JTable();
        JScrollPane scrollTable = new JScrollPane(tbl_history);
        scrollTable.setBounds(20, 50, 745, 240);
        add(scrollTable);

        JButton btn_refresh = new JButton("Tải lại");
        btn_refresh.setBounds(20, 305, 110, 30);
        add(btn_refresh);

        lbl_status = new JLabel("Đang tải...");
        lbl_status.setBounds(145, 305, 620, 30);
        add(lbl_status);

        btn_refresh.addActionListener((ActionEvent evt) -> {
            loadData();
        });
    }

    private void loadData() {
        try {
            tbl_history.setModel(HistoryDao.loadLatest(50));
            lbl_status.setText("Đã tải " + tbl_history.getRowCount() + " dòng mới nhất từ dbo.history.");
        } catch (java.sql.SQLException ex) {
            lbl_status.setText("Không đọc được lịch sử: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frm_LichSu().setVisible(true));
    }
}