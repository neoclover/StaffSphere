package Employee_Management;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import javax.swing.*;

public class Add_Employee implements ActionListener {
    JFrame f;
    JTextField tfName, tfDOB, tfId, tfEmail, tfPhone, tfJobPost, tfAadhar, tfAddress;
    JButton btnSubmit, btnCancel;

    public Add_Employee() {
        f = new JFrame("Add Employee - Employee Management System");
        f.setSize(850, 650);
        f.setLocationRelativeTo(null); // Center on screen
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(new BorderLayout());

        // Load background image
        ImageIcon imageIcon = ResourceUtil.loadIcon("Employee_Management/Icon/recruiting-professionals.jpg");
        Image image = imageIcon.getImage() != null ? imageIcon.getImage().getScaledInstance(850, 650, Image.SCALE_SMOOTH) : null;
        ImageIcon scaledIcon = image != null ? new ImageIcon(image) : imageIcon;

        JLabel background = new JLabel(scaledIcon);
        background.setLayout(null);
        f.setContentPane(background);

        // Title
        JLabel heading = new JLabel("Add New Employee");
        heading.setBounds(280, 20, 400, 40);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setForeground(new Color(0, 102, 204));
        background.add(heading);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        int x = 200, yStart = 80, gap = 45, width = 350;

        // Name
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(x, yStart, 150, 28);
        lblName.setFont(labelFont);
        background.add(lblName);
        tfName = new JTextField();
        tfName.setBounds(x + 150, yStart, width, 28);
        tfName.setFont(inputFont);
        background.add(tfName);

        // Address
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(x, yStart + gap, 150, 28);
        lblAddress.setFont(labelFont);
        background.add(lblAddress);
        tfAddress = new JTextField();
        tfAddress.setBounds(x + 150, yStart + gap, width, 28);
        tfAddress.setFont(inputFont);
        background.add(tfAddress);

        // Date of Birth
        JLabel lblDOB = new JLabel("Date of Birth:");
        lblDOB.setBounds(x, yStart + 2 * gap, 150, 28);
        lblDOB.setFont(labelFont);
        background.add(lblDOB);
        tfDOB = new JTextField();
        tfDOB.setBounds(x + 150, yStart + 2 * gap, width, 28);
        tfDOB.setFont(inputFont);
        background.add(tfDOB);

        // Employee ID
        JLabel lblId = new JLabel("Employee ID:");
        lblId.setBounds(x, yStart + 3 * gap, 150, 28);
        lblId.setFont(labelFont);
        background.add(lblId);
        tfId = new JTextField();
        tfId.setBounds(x + 150, yStart + 3 * gap, width, 28);
        tfId.setFont(inputFont);
        background.add(tfId);

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(x, yStart + 4 * gap, 150, 28);
        lblEmail.setFont(labelFont);
        background.add(lblEmail);
        tfEmail = new JTextField();
        tfEmail.setBounds(x + 150, yStart + 4 * gap, width, 28);
        tfEmail.setFont(inputFont);
        background.add(tfEmail);

        // Phone
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(x, yStart + 5 * gap, 150, 28);
        lblPhone.setFont(labelFont);
        background.add(lblPhone);
        tfPhone = new JTextField();
        tfPhone.setBounds(x + 150, yStart + 5 * gap, width, 28);
        tfPhone.setFont(inputFont);
        background.add(tfPhone);

        // Job Post
        JLabel lblJobPost = new JLabel("Job Post:");
        lblJobPost.setBounds(x, yStart + 6 * gap, 150, 28);
        lblJobPost.setFont(labelFont);
        background.add(lblJobPost);
        tfJobPost = new JTextField();
        tfJobPost.setBounds(x + 150, yStart + 6 * gap, width, 28);
        tfJobPost.setFont(inputFont);
        background.add(tfJobPost);

        // Aadhar No
        JLabel lblAadhar = new JLabel("Aadhar No:");
        lblAadhar.setBounds(x, yStart + 7 * gap, 150, 28);
        lblAadhar.setFont(labelFont);
        background.add(lblAadhar);
        tfAadhar = new JTextField();
        tfAadhar.setBounds(x + 150, yStart + 7 * gap, width, 28);
        tfAadhar.setFont(inputFont);
        background.add(tfAadhar);

        // Buttons
        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(x + 80, yStart + 8 * gap + 10, 130, 35);
        btnSubmit.setBackground(new Color(0, 102, 204));
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(this);
        background.add(btnSubmit);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(x + 230, yStart + 8 * gap + 10, 130, 35);
        btnCancel.setBackground(new Color(108, 117, 125));
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(this);
        background.add(btnCancel);

        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            String name = tfName.getText().trim();
            String address = tfAddress.getText().trim();
            String dob = tfDOB.getText().trim();
            String id = tfId.getText().trim();
            String email = tfEmail.getText().trim();
            String phone = tfPhone.getText().trim();
            String jobPost = tfJobPost.getText().trim();
            String aadhar = tfAadhar.getText().trim();

            if (name.isEmpty() || address.isEmpty() || dob.isEmpty() || id.isEmpty()
                    || email.isEmpty() || phone.isEmpty() || jobPost.isEmpty() || aadhar.isEmpty()) {
                JOptionPane.showMessageDialog(f,
                        "Please fill in all the fields!",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                ConnectionClass obj = new ConnectionClass();
                String query = "INSERT INTO employee (Eid, name, Address, Dob, Email, Phone, Post, Aadhar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = obj.prepareStatement(query)) {
                    pstmt.setString(1, id);
                    pstmt.setString(2, name);
                    pstmt.setString(3, address);
                    pstmt.setString(4, dob);
                    pstmt.setString(5, email);
                    pstmt.setString(6, phone);
                    pstmt.setString(7, jobPost);
                    pstmt.setString(8, aadhar);

                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(f,
                            "Employee details added successfully!\nName: " + name + "\nEmployee ID: " + id,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    f.dispose();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(f,
                        "Error adding employee: " + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnCancel) {
            f.dispose();
        }
    }

    public static void main(String[] args) {
        new Add_Employee();
    }
}
