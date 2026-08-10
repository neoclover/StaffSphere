package Employee_Management;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class View_Employee_Data implements ActionListener {

    JFrame f;
    JLabel id8, id, aid, id1, aid1, id2, aid2, id3, aid3, id4, aid4, id5, aid5, id6, aid6, id7, aid7, id9;
    String emp_id, name, address, phone, email, post, dob, aadhar;
    JButton b1, b2;

    public View_Employee_Data(String Eid) {
        try {
            ConnectionClass obj = new ConnectionClass();
            String s = "SELECT * FROM employee WHERE Eid = ?";
            try (PreparedStatement pstmt = obj.prepareStatement(s)) {
                pstmt.setString(1, Eid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(null, "Employee ID '" + Eid + "' not found!", "Not Found", JOptionPane.WARNING_MESSAGE);
                        new View_Employee();
                        return;
                    } else {
                        emp_id = rs.getString("Eid");
                        name = rs.getString("name");
                        address = rs.getString("Address");
                        dob = rs.getString("Dob");
                        email = rs.getString("Email");
                        phone = rs.getString("Phone");
                        post = rs.getString("Post");
                        aadhar = rs.getString("Aadhar");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        f = new JFrame("Employee Details - " + emp_id);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(600, 600);
        f.setLocationRelativeTo(null); // Center on screen
        f.setLayout(null);

        id9 = new JLabel();
        id9.setBounds(0, 0, 600, 600);
        id9.setLayout(null);
        ImageIcon img = ResourceUtil.loadIcon("Employee_Management/Icon/off.jpg");
        Image scaledBg = img.getImage() != null ? img.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH) : null;
        id9.setIcon(scaledBg != null ? new ImageIcon(scaledBg) : img);

        id8 = new JLabel("Employee Profile");
        id8.setBounds(180, 20, 300, 40);
        id8.setFont(new Font("Segoe UI", Font.BOLD, 26));
        id8.setForeground(new Color(0, 102, 204));
        id9.add(id8);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 15);

        int y = 90, gap = 40;

        // ID
        id = new JLabel("Employee ID:");
        id.setBounds(70, y, 140, 30);
        id.setFont(labelFont);
        id9.add(id);

        aid = new JLabel(emp_id);
        aid.setBounds(220, y, 320, 30);
        aid.setFont(valueFont);
        id9.add(aid);

        // Name
        id1 = new JLabel("Name:");
        id1.setBounds(70, y + gap, 140, 30);
        id1.setFont(labelFont);
        id9.add(id1);

        aid1 = new JLabel(name);
        aid1.setBounds(220, y + gap, 320, 30);
        aid1.setFont(valueFont);
        id9.add(aid1);

        // Address
        id2 = new JLabel("Address:");
        id2.setBounds(70, y + 2 * gap, 140, 30);
        id2.setFont(labelFont);
        id9.add(id2);

        aid2 = new JLabel(address);
        aid2.setBounds(220, y + 2 * gap, 320, 30);
        aid2.setFont(valueFont);
        id9.add(aid2);

        // Phone
        id3 = new JLabel("Phone:");
        id3.setBounds(70, y + 3 * gap, 140, 30);
        id3.setFont(labelFont);
        id9.add(id3);

        aid3 = new JLabel(phone);
        aid3.setBounds(220, y + 3 * gap, 320, 30);
        aid3.setFont(valueFont);
        id9.add(aid3);

        // Email
        id4 = new JLabel("Email:");
        id4.setBounds(70, y + 4 * gap, 140, 30);
        id4.setFont(labelFont);
        id9.add(id4);

        aid4 = new JLabel(email);
        aid4.setBounds(220, y + 4 * gap, 320, 30);
        aid4.setFont(valueFont);
        id9.add(aid4);

        // Post
        id5 = new JLabel("Job Post:");
        id5.setBounds(70, y + 5 * gap, 140, 30);
        id5.setFont(labelFont);
        id9.add(id5);

        aid5 = new JLabel(post);
        aid5.setBounds(220, y + 5 * gap, 320, 30);
        aid5.setFont(valueFont);
        id9.add(aid5);

        // DOB
        id6 = new JLabel("Date of Birth:");
        id6.setBounds(70, y + 6 * gap, 140, 30);
        id6.setFont(labelFont);
        id9.add(id6);

        aid6 = new JLabel(dob);
        aid6.setBounds(220, y + 6 * gap, 320, 30);
        aid6.setFont(valueFont);
        id9.add(aid6);

        // Aadhar
        id7 = new JLabel("Aadhar No:");
        id7.setBounds(70, y + 7 * gap, 140, 30);
        id7.setFont(labelFont);
        id9.add(id7);

        aid7 = new JLabel(aadhar);
        aid7.setBounds(220, y + 7 * gap, 320, 30);
        aid7.setFont(valueFont);
        id9.add(aid7);

        // Buttons
        b1 = new JButton("Print");
        b1.setBackground(new Color(0, 102, 204));
        b1.setForeground(Color.WHITE);
        b1.setFont(labelFont);
        b1.setBounds(150, 480, 120, 35);
        b1.setFocusPainted(false);
        b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b1.addActionListener(this);
        id9.add(b1);

        b2 = new JButton("Close");
        b2.setBackground(new Color(108, 117, 125));
        b2.setForeground(Color.WHITE);
        b2.setFont(labelFont);
        b2.setBounds(300, 480, 120, 35);
        b2.setFocusPainted(false);
        b2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b2.addActionListener(this);
        id9.add(b2);

        f.add(id9);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            JOptionPane.showMessageDialog(f, "Employee Profile Printed Successfully!", "Print Document", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == b2) {
            f.dispose();
        }
    }

    public static void main(String[] args) {
        new View_Employee_Data("E101");
    }
}
