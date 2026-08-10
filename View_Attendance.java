package Employee_Management;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class View_Attendance extends JFrame implements ActionListener {

    JTable table;
    DefaultTableModel model;
    JButton bt1;
    JTextField tf1;
    JPanel p1, p2, p3;
    Font f, f1;
    JLabel l1, l2;

    public View_Attendance() {
        super("Employee Attendance Records");
        setSize(1000, 500);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        f = new Font("Segoe UI", Font.PLAIN, 14);
        f1 = new Font("Segoe UI", Font.BOLD, 18);

        String columns[] = {"Employee ID", "Name", "Email", "First Half", "Second Half", "Date"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setFont(f);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);

        loadAttendanceData();

        JScrollPane js = new JScrollPane(table);

        l1 = new JLabel("Attendance Management System");
        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setFont(f1);
        l1.setForeground(new Color(0, 102, 204));

        l2 = new JLabel("Employee ID:");
        l2.setFont(new Font("Segoe UI", Font.BOLD, 14));

        tf1 = new JTextField();
        tf1.setFont(f);

        bt1 = new JButton("Search Attendance");
        bt1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bt1.setBackground(new Color(0, 102, 204));
        bt1.setForeground(Color.WHITE);
        bt1.setFocusPainted(false);
        bt1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bt1.addActionListener(this);

        p1 = new JPanel(new GridLayout(1, 1, 10, 10));
        p1.add(l1);

        p2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        p2.add(l2);
        tf1.setPreferredSize(new Dimension(180, 30));
        p2.add(tf1);
        p2.add(bt1);

        p3 = new JPanel(new BorderLayout());
        p3.add(p1, BorderLayout.NORTH);
        p3.add(p2, BorderLayout.SOUTH);

        add(p3, BorderLayout.NORTH);
        add(js, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadAttendanceData() {
        model.setRowCount(0);
        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT a.Eid, e.name, e.Email, a.first_half, a.second_half, a.date "
                    + "FROM attendance a JOIN employee e ON a.Eid = e.Eid ORDER BY a.date DESC";

            try (Statement stm = obj.con.createStatement();
                 ResultSet rest = stm.executeQuery(q)) {
                while (rest.next()) {
                    model.addRow(new Object[]{
                        rest.getString("Eid"),
                        rest.getString("name"),
                        rest.getString("Email"),
                        rest.getString("first_half"),
                        rest.getString("second_half"),
                        rest.getString("date")
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String eid = tf1.getText().trim();
        if (e.getSource() == bt1) {
            if (eid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an Employee ID to search.", "Input Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new View_Attendance_single(eid).setVisible(true);
        }
    }

    public static void main(String args[]) {
        new View_Attendance().setVisible(true);
    }
}
