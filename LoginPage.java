package Employee_Management;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LoginPage extends JFrame implements ActionListener {
    private JFrame f;
    private JLabel l1, l2;
    private JTextField t1;
    private JPasswordField t2;
    private JButton b1, b2, b3;

    public LoginPage() {
        this("");
    }

    public LoginPage(String initialUsername) {
        f = new JFrame("Login - Employee Management System");
        f.setBackground(Color.WHITE);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        l1 = new JLabel("Username:");
        l1.setBounds(40, 30, 100, 30);
        l1.setFont(labelFont);
        f.add(l1);

        t1 = new JTextField(initialUsername != null ? initialUsername : "");
        t1.setBounds(140, 30, 180, 30);
        t1.setFont(inputFont);
        f.add(t1);

        l2 = new JLabel("Password:");
        l2.setBounds(40, 80, 100, 30);
        l2.setFont(labelFont);
        f.add(l2);

        t2 = new JPasswordField();
        t2.setBounds(140, 80, 180, 30);
        t2.setFont(inputFont);
        f.add(t2);

        // Lock icon
        ImageIcon i1 = ResourceUtil.loadIcon("Employee_Management/Icon/lock.jpeg");
        Image i2 = i1.getImage() != null ? i1.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH) : null;
        ImageIcon i3 = i2 != null ? new ImageIcon(i2) : i1;
        JLabel l3 = new JLabel(i3);
        l3.setBounds(340, 20, 140, 140);
        f.add(l3);

        // Login Button
        b1 = new JButton("Login");
        b1.setBackground(new Color(0, 102, 204));
        b1.setForeground(Color.WHITE);
        b1.setBounds(40, 140, 130, 35);
        b1.setFont(labelFont);
        b1.setFocusPainted(false);
        b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b1.addActionListener(this);
        f.add(b1);

        // Create Account Button
        b3 = new JButton("Create Account");
        b3.setBackground(new Color(40, 167, 69));
        b3.setForeground(Color.WHITE);
        b3.setBounds(180, 140, 140, 35);
        b3.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b3.setFocusPainted(false);
        b3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b3.addActionListener(this);
        f.add(b3);

        // Close Button
        b2 = new JButton("Close");
        b2.setBackground(new Color(108, 117, 125));
        b2.setForeground(Color.WHITE);
        b2.setBounds(40, 190, 280, 30);
        b2.setFont(labelFont);
        b2.setFocusPainted(false);
        b2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b2.addActionListener(this);
        f.add(b2);

        // Trigger login when user hits Enter on password field
        t2.addActionListener(e -> handleLogin());

        f.setSize(510, 280);
        f.setLocationRelativeTo(null); // Center on screen
        f.setResizable(false);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ee) {
        if (ee.getSource() == b1) {
            handleLogin();
        } else if (ee.getSource() == b3) {
            f.dispose();
            new SignupPage(t1.getText().trim());
        } else if (ee.getSource() == b2) {
            f.dispose();
            System.exit(0);
        }
    }

    private void handleLogin() {
        String name = t1.getText().trim();
        String pass = new String(t2.getPassword());

        if (name.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(f,
                    "Please enter both username and password.",
                    "Input Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ConnectionClass obj = new ConnectionClass();
            if (obj.con == null) {
                JOptionPane.showMessageDialog(f,
                        "Unable to connect to database! Please make sure MySQL is running.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Step 1: Check if username exists
            String checkUserQuery = "SELECT * FROM logindata WHERE username = ?";
            try (PreparedStatement pstmt = obj.prepareStatement(checkUserQuery)) {
                pstmt.setString(1, name);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (!rs.next()) {
                        // Username NOT found in database
                        int choice = JOptionPane.showConfirmDialog(f,
                                "Username '" + name + "' is not registered.\nWould you like to create a new account now?",
                                "Account Not Found",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);

                        if (choice == JOptionPane.YES_OPTION) {
                            f.dispose();
                            new SignupPage(name);
                        } else {
                            t1.requestFocus();
                        }
                        return;
                    }

                    // Step 2: Username exists, verify password
                    String storedPassword = rs.getString("password");
                    if (storedPassword.equals(pass)) {
                        JOptionPane.showMessageDialog(f,
                                "Login Successful! Welcome, " + name + ".",
                                "Login Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        f.dispose();
                        new HomePage().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(f,
                                "Incorrect password for username '" + name + "'. Please try again.",
                                "Authentication Error",
                                JOptionPane.ERROR_MESSAGE);
                        t2.setText("");
                        t2.requestFocus();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(f,
                    "An unexpected error occurred: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
