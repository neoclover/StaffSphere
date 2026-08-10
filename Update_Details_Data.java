package Employee_Management;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Update_Details_Data extends JFrame implements ActionListener {

    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l13, l12;
    JButton bt1, bt2;
    JPanel p1, p2, p3;
    JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7;
    Font f, f1;
    Choice ch;

    public Update_Details_Data() {
        super("Update Employee Details");
        setSize(950, 700);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        f = new Font("Segoe UI", Font.BOLD, 24);
        f1 = new Font("Segoe UI", Font.BOLD, 15);

        ch = new Choice();
        ch.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        loadEmployeeIDs();

        // Labels
        l1 = new JLabel("Update Employee Details");
        l2 = new JLabel("Name");
        l3 = new JLabel("Address");
        l4 = new JLabel("Date of Birth");
        l5 = new JLabel("Email");
        l6 = new JLabel("Phone");
        l7 = new JLabel("Job Post");
        l8 = new JLabel("Aadhar");
        l12 = new JLabel("Select Emp ID");

        JLabel[] labels = {l2, l3, l4, l5, l6, l7, l8, l12};
        for (JLabel label : labels) {
            label.setFont(f1);
        }

        // TextFields
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();
        tf6 = new JTextField();
        tf7 = new JTextField();

        Dimension fieldSize = new Dimension(230, 30);
        JTextField[] fields = {tf1, tf2, tf3, tf4, tf5, tf6, tf7};
        for (JTextField tf : fields) {
            tf.setPreferredSize(fieldSize);
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        // Buttons
        bt1 = new JButton("Update Data");
        bt2 = new JButton("Back");
        bt1.setFont(f1);
        bt2.setFont(f1);
        bt1.setBackground(new Color(0, 102, 204));
        bt1.setForeground(Color.WHITE);
        bt1.setFocusPainted(false);
        bt1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt2.setBackground(new Color(108, 117, 125));
        bt2.setForeground(Color.WHITE);
        bt2.setFocusPainted(false);
        bt2.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bt1.addActionListener(this);
        bt2.addActionListener(this);

        // Panel p1 for heading
        p1 = new JPanel(new GridLayout(1, 1));
        l1.setFont(f);
        l1.setForeground(new Color(0, 102, 204));
        l1.setHorizontalAlignment(JLabel.CENTER);
        p1.add(l1);

        // Panel p2 for form
        p2 = new JPanel(new GridLayout(9, 2, 10, 12));
        Border padding = BorderFactory.createEmptyBorder(0, 20, 0, 0);

        p2.add(l12);
        p2.add(ch);

        addRow(p2, l2, tf1, padding);
        addRow(p2, l3, tf2, padding);
        addRow(p2, l4, tf3, padding);
        addRow(p2, l5, tf4, padding);
        addRow(p2, l6, tf5, padding);
        addRow(p2, l7, tf6, padding);
        addRow(p2, l8, tf7, padding);

        p2.add(bt1);
        p2.add(bt2);

        // Panel p3 for image
        p3 = new JPanel(new GridLayout(1, 1));
        ImageIcon img = ResourceUtil.loadIcon("Employee_Management/Icon/update.jpg");
        Image img1 = img.getImage() != null ? img.getImage().getScaledInstance(380, 650, Image.SCALE_SMOOTH) : null;
        l13 = new JLabel(img1 != null ? new ImageIcon(img1) : img);
        p3.add(l13);

        setLayout(new BorderLayout(10, 10));
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.CENTER);
        add(p3, BorderLayout.WEST);

        // Populate fields when Employee ID is selected
        ch.addItemListener(e -> populateEmployeeData());

        // Populate initial selection if available
        if (ch.getItemCount() > 0) {
            populateEmployeeData();
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
                    ch.add(rest.getString("Eid"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateEmployeeData() {
        String eid = ch.getSelectedItem();
        if (eid == null || eid.isEmpty()) return;

        try {
            ConnectionClass obj = new ConnectionClass();
            String q1 = "SELECT * FROM employee WHERE Eid = ?";
            try (PreparedStatement pstmt = obj.prepareStatement(q1)) {
                pstmt.setString(1, eid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        tf1.setText(rs.getString("name"));
                        tf2.setText(rs.getString("Address"));
                        tf3.setText(rs.getString("Dob"));
                        tf4.setText(rs.getString("Email"));
                        tf5.setText(rs.getString("Phone"));
                        tf6.setText(rs.getString("Post"));
                        tf7.setText(rs.getString("Aadhar"));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addRow(JPanel panel, JLabel label, JTextField textField, Border padding) {
        panel.add(label);
        JPanel tfPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tfPanel.setBorder(padding);
        tfPanel.add(textField);
        panel.add(tfPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            String id = ch.getSelectedItem();
            if (id == null || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No Employee selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String name = tf1.getText().trim();
            String address = tf2.getText().trim();
            String dob = tf3.getText().trim();
            String email = tf4.getText().trim();
            String phone = tf5.getText().trim();
            String post = tf6.getText().trim();
            String aadhar = tf7.getText().trim();

            try {
                ConnectionClass obj = new ConnectionClass();
                String q = "UPDATE employee SET name=?, Address=?, Dob=?, Email=?, Phone=?, Post=?, Aadhar=? WHERE Eid=?";
                try (PreparedStatement pstmt = obj.prepareStatement(q)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, address);
                    pstmt.setString(3, dob);
                    pstmt.setString(4, email);
                    pstmt.setString(5, phone);
                    pstmt.setString(6, post);
                    pstmt.setString(7, aadhar);
                    pstmt.setString(8, id);

                    int rows = pstmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Employee details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Update failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ee) {
                ee.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating employee: " + ee.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == bt2) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new Update_Details_Data();
    }
}
