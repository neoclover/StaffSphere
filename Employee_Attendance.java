package Employee_Management;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.*;

public class Employee_Attendance extends JFrame implements ActionListener {

    JLabel l1, l2, l3, l4, l5;
    JTextField tf1, tf2;
    JButton bt1, bt2;
    Choice c1, c2, c3;
    Font f;
    JPanel p;

    public Employee_Attendance() {
        super("Take Employee Attendance");
        setSize(480, 340);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        f = new Font("Segoe UI", Font.BOLD, 14);

        l1 = new JLabel("Select Employee ID:");
        l4 = new JLabel("Name:");
        l5 = new JLabel("Email:");
        l2 = new JLabel("First Half:");
        l3 = new JLabel("Second Half:");

        JLabel[] labels = {l1, l4, l5, l2, l3};
        for (JLabel label : labels) {
            label.setFont(f);
        }

        tf1 = new JTextField();
        tf2 = new JTextField();
        tf1.setEditable(false);
        tf2.setEditable(false);
        tf1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf2.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        c2 = new Choice();
        c2.add("Present");
        c2.add("Absent");

        c3 = new Choice();
        c3.add("Present");
        c3.add("Absent");

        c2.setFont(f);
        c3.setFont(f);

        bt1 = new JButton("Submit");
        bt2 = new JButton("Close");
        bt1.setBackground(new Color(0, 102, 204));
        bt1.setForeground(Color.WHITE);
        bt1.setFont(f);
        bt1.setFocusPainted(false);
        bt1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt2.setBackground(new Color(108, 117, 125));
        bt2.setForeground(Color.WHITE);
        bt2.setFont(f);
        bt2.setFocusPainted(false);
        bt2.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt1.addActionListener(this);
        bt2.addActionListener(this);

        c1 = new Choice();
        c1.setFont(f);

        loadEmployeeIDs();

        // Layout
        p = new JPanel();
        p.setLayout(new GridLayout(6, 2, 12, 12));
        p.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        p.add(l1); p.add(c1);
        p.add(l4); p.add(tf1);
        p.add(l5); p.add(tf2);
        p.add(l2); p.add(c2);
        p.add(l3); p.add(c3);
        p.add(bt1); p.add(bt2);
        add(p);

        c1.addItemListener(e -> populateEmployeeDetails());

        if (c1.getItemCount() > 0) {
            populateEmployeeDetails();
        }

        setVisible(true);
    }

    private void loadEmployeeIDs() {
        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT Eid FROM employee";
            try (Statement stm = obj.con.createStatement();
                 ResultSet rest = stm.executeQuery(q)) {
                while (rest.next()) {
                    c1.add(rest.getString("Eid"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateEmployeeDetails() {
        String eid = c1.getSelectedItem();
        if (eid == null || eid.isEmpty()) return;

        try {
            ConnectionClass obj = new ConnectionClass();
            String q2 = "SELECT name, Email FROM employee WHERE Eid = ?";
            try (PreparedStatement pstmt = obj.prepareStatement(q2)) {
                pstmt.setString(1, eid);
                try (ResultSet rest1 = pstmt.executeQuery()) {
                    if (rest1.next()) {
                        tf1.setText(rest1.getString("name"));
                        tf2.setText(rest1.getString("Email"));
                    }
                }
            }
        } catch (Exception exx) {
            exx.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == bt1) {
            String ch_eid = c1.getSelectedItem();
            if (ch_eid == null || ch_eid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No Employee selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String ch_f = c2.getSelectedItem();
            String ch_l = c3.getSelectedItem();
            String name = tf1.getText();
            String email = tf2.getText();

            String dt = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

            try {
                ConnectionClass obj = new ConnectionClass();
                String q1 = "INSERT INTO attendance(Eid, name, email, first_half, second_half, date) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = obj.prepareStatement(q1)) {
                    pstmt.setString(1, ch_eid);
                    pstmt.setString(2, name);
                    pstmt.setString(3, email);
                    pstmt.setString(4, ch_f);
                    pstmt.setString(5, ch_l);
                    pstmt.setString(6, dt);

                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Attendance recorded successfully for " + name + " (" + dt + ")");
                    this.dispose();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving attendance: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ev.getSource() == bt2) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new Employee_Attendance();
    }
}
