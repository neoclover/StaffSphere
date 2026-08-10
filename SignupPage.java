package Employee_Management;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class SignupPage extends JFrame implements ActionListener {
    private JTextField tfUsername;
    private JPasswordField tfPassword, tfConfirmPassword;
    private JButton btnSignup, btnBack;

    public SignupPage() {
        this("");
    }

    public SignupPage(String initialUsername) {
        super("Create Account - Employee Management System");
        setSize(480, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Styling elements
        Font headerFont = new Font("Segoe UI", Font.BOLD, 22);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        Color primaryColor = new Color(0, 102, 204);
        Color backgroundColor = new Color(245, 247, 250);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(backgroundColor);

        // Header Title
        JLabel lblTitle = new JLabel("Create New Account", SwingConstants.CENTER);
        lblTitle.setBounds(40, 20, 400, 35);
        lblTitle.setFont(headerFont);
        lblTitle.setForeground(primaryColor);
        mainPanel.add(lblTitle);

        // Username Label & Field
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 75, 130, 25);
        lblUsername.setFont(labelFont);
        mainPanel.add(lblUsername);

        tfUsername = new JTextField(initialUsername != null ? initialUsername : "");
        tfUsername.setBounds(180, 75, 230, 30);
        tfUsername.setFont(inputFont);
        mainPanel.add(tfUsername);

        // Password Label & Field
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 125, 130, 25);
        lblPassword.setFont(labelFont);
        mainPanel.add(lblPassword);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(180, 125, 230, 30);
        tfPassword.setFont(inputFont);
        mainPanel.add(tfPassword);

        // Confirm Password Label & Field
        JLabel lblConfirmPass = new JLabel("Confirm Password:");
        lblConfirmPass.setBounds(50, 175, 130, 25);
        lblConfirmPass.setFont(labelFont);
        mainPanel.add(lblConfirmPass);

        tfConfirmPassword = new JPasswordField();
        tfConfirmPassword.setBounds(180, 175, 230, 30);
        tfConfirmPassword.setFont(inputFont);
        mainPanel.add(tfConfirmPassword);

        // Buttons
        btnSignup = new JButton("Create Account");
        btnSignup.setBounds(50, 240, 170, 38);
        btnSignup.setFont(labelFont);
        btnSignup.setBackground(primaryColor);
        btnSignup.setForeground(Color.WHITE);
        btnSignup.setFocusPainted(false);
        btnSignup.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSignup.addActionListener(this);
        mainPanel.add(btnSignup);

        btnBack = new JButton("Back to Login");
        btnBack.setBounds(240, 240, 170, 38);
        btnBack.setFont(labelFont);
        btnBack.setBackground(new Color(108, 117, 125));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(this);
        mainPanel.add(btnBack);

        // Enter key action
        tfConfirmPassword.addActionListener(e -> registerUser());

        setContentPane(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSignup) {
            registerUser();
        } else if (e.getSource() == btnBack) {
            this.dispose();
            new LoginPage(tfUsername.getText().trim());
        }
    }

    private void registerUser() {
        String username = tfUsername.getText().trim();
        String password = new String(tfPassword.getPassword());
        String confirmPassword = new String(tfConfirmPassword.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields!",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this,
                    "Username must be at least 3 characters long.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match. Please re-enter passwords.",
                    "Password Mismatch",
                    JOptionPane.ERROR_MESSAGE);
            tfConfirmPassword.setText("");
            tfConfirmPassword.requestFocus();
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 4 characters long.",
                    "Weak Password",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ConnectionClass obj = new ConnectionClass();
            if (obj.con == null) {
                JOptionPane.showMessageDialog(this,
                        "Database connection failed! Please ensure MySQL is running.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if username already exists
            String checkQuery = "SELECT username FROM logindata WHERE username = ?";
            try (PreparedStatement checkPstmt = obj.prepareStatement(checkQuery)) {
                checkPstmt.setString(1, username);
                try (ResultSet rs = checkPstmt.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this,
                                "Username '" + username + "' already exists.\nPlease choose a different username or log in.",
                                "User Exists",
                                JOptionPane.WARNING_MESSAGE);
                        tfUsername.requestFocus();
                        return;
                    }
                }
            }

            // Insert new user
            String insertQuery = "INSERT INTO logindata (username, password) VALUES (?, ?)";
            try (PreparedStatement insertPstmt = obj.prepareStatement(insertQuery)) {
                insertPstmt.setString(1, username);
                insertPstmt.setString(2, password);

                int rowsInserted = insertPstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Account created successfully!\nYou can now log in with your credentials.",
                            "Account Created",
                            JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new LoginPage(username);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Account creation failed. Please try again.",
                            "Registration Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error during account creation: " + ex.getMessage(),
                    "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new SignupPage();
    }
}
