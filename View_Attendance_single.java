package Employee_Management;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class View_Attendance_single extends JFrame implements ActionListener {
    JTable table;
    DefaultTableModel model;
    JButton bt1;
    Font f;

    public View_Attendance_single(String eid) {
        super("Attendance Records for Employee: " + eid);
        setSize(850, 400);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        f = new Font("Segoe UI", Font.PLAIN, 14);

        String columns[] = {"Employee ID", "Name", "Email", "First Half", "Second Half", "Date"};
        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        table.setFont(f);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);

        int count = 0;
        try {
            ConnectionClass obj = new ConnectionClass();
            String s = "SELECT a.Eid, e.name, e.Email, a.first_half, a.second_half, a.date "
                    + "FROM attendance a JOIN employee e ON a.Eid = e.Eid "
                    + "WHERE a.Eid = ? ORDER BY a.date DESC";

            try (PreparedStatement pstmt = obj.prepareStatement(s)) {
                pstmt.setString(1, eid);
                try (ResultSet rest = pstmt.executeQuery()) {
                    while (rest.next()) {
                        model.addRow(new Object[]{
                            rest.getString("Eid"),
                            rest.getString("name"),
                            rest.getString("Email"),
                            rest.getString("first_half"),
                            rest.getString("second_half"),
                            rest.getString("date")
                        });
                        count++;
                    }
                }
            }

            if (count == 0) {
                JOptionPane.showMessageDialog(this, "No attendance records found for Employee ID: " + eid, "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JScrollPane js = new JScrollPane(table);

        bt1 = new JButton("Close");
        bt1.setBackground(new Color(108, 117, 125));
        bt1.setForeground(Color.WHITE);
        bt1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bt1.setFocusPainted(false);
        bt1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bt1.addActionListener(this);

        add(js, BorderLayout.CENTER);
        add(bt1, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new View_Attendance_single("E101").setVisible(true);
    }
}
