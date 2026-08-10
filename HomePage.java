package Employee_Management;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import javax.swing.*;

public class HomePage extends JFrame implements ActionListener {
    Font f, f1, f2;

    public HomePage() {
        super("Employee Management System - Home");
        setSize(1280, 720);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f = new Font("Segoe UI", Font.BOLD, 18);
        f2 = new Font("Segoe UI", Font.BOLD, 32);
        f1 = new Font("Segoe UI", Font.BOLD, 16);

        // Background image
        ImageIcon bgIcon = ResourceUtil.loadIcon("Employee_Management/Icon/Preview-Image-6.jpg");
        Image img = bgIcon.getImage() != null ? bgIcon.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH) : null;
        ImageIcon scaledIcon = img != null ? new ImageIcon(img) : bgIcon;

        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, 1280, 720);
        background.setLayout(null);
        setContentPane(background);

        // Header Banner
        JLabel banner = new JLabel("Employee Management System", SwingConstants.CENTER);
        banner.setBounds(200, 30, 880, 50);
        banner.setFont(f2);
        banner.setForeground(new Color(255, 255, 255));
        background.add(banner);

        // Menu Bar
        JMenuBar m1 = new JMenuBar();
        m1.setBackground(new Color(30, 30, 30));

        JMenu men1 = new JMenu("Profile");
        JMenuItem ment1 = new JMenuItem("Complete Profile");
        JMenuItem ment2 = new JMenuItem("View Profile");

        JMenu men2 = new JMenu("Manage");
        JMenuItem ment3 = new JMenuItem("Update Details");

        JMenu men3 = new JMenu("Attendance");
        JMenuItem ment4 = new JMenuItem("Take Attendance");
        JMenuItem ment5 = new JMenuItem("View Attendance");

        JMenu men7 = new JMenu("Delete");
        JMenuItem ment12 = new JMenuItem("Delete Employee");

        JMenu men6 = new JMenu("Session");
        JMenuItem ment10 = new JMenuItem("Logout");
        JMenuItem ment11 = new JMenuItem("Exit");

        // Add items
        men1.add(ment1);
        men1.add(ment2);
        men2.add(ment3);
        men3.add(ment4);
        men3.add(ment5);
        men7.add(ment12);
        men6.add(ment10);
        men6.add(ment11);

        m1.add(men1);
        m1.add(men2);
        m1.add(men3);
        m1.add(men7);
        m1.add(men6);

        // Menu Styling
        JMenu[] menus = {men1, men2, men3, men7, men6};
        for (JMenu menu : menus) {
            menu.setForeground(Color.WHITE);
            menu.setFont(f1);
        }
        men6.setForeground(new Color(255, 99, 71)); // Red accent for session

        JMenuItem[] items = {ment1, ment2, ment3, ment4, ment5, ment10, ment11, ment12};
        for (JMenuItem item : items) {
            item.setBackground(new Color(45, 45, 45));
            item.setForeground(Color.WHITE);
            item.setFont(f1);
            item.addActionListener(this);
        }

        setJMenuBar(m1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comnd = e.getActionCommand();

        if (comnd.equals("Complete Profile")) {
            new Add_Employee();
        } else if (comnd.equals("View Profile")) {
            new View_Employee();
        } else if (comnd.equals("Update Details")) {
            new Update_Details_Data().setVisible(true);
        } else if (comnd.equals("Take Attendance")) {
            new Employee_Attendance().setVisible(true);
        } else if (comnd.equals("View Attendance")) {
            new View_Attendance().setVisible(true);
        } else if (comnd.equals("Delete Employee")) {
            deleteEmployeeFlow();
        } else if (comnd.equals("Logout")) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                this.dispose();
                new LoginPage();
            }
        } else if (comnd.equals("Exit")) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Do you really want to exit the application?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void deleteEmployeeFlow() {
        String empId = JOptionPane.showInputDialog(this,
                "Enter Employee ID to delete:",
                "Delete Employee",
                JOptionPane.QUESTION_MESSAGE);

        if (empId != null && !empId.trim().isEmpty()) {
            empId = empId.trim();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to permanently delete Employee ID '" + empId + "'?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    ConnectionClass obj = new ConnectionClass();
                    String delQuery = "DELETE FROM employee WHERE Eid = ?";
                    try (PreparedStatement pstmt = obj.prepareStatement(delQuery)) {
                        pstmt.setString(1, empId);
                        int affected = pstmt.executeUpdate();
                        if (affected > 0) {
                            // Also clean up attendance records for this employee
                            try (PreparedStatement attPstmt = obj.prepareStatement("DELETE FROM attendance WHERE Eid = ?")) {
                                attPstmt.setString(1, empId);
                                attPstmt.executeUpdate();
                            }
                            JOptionPane.showMessageDialog(this,
                                    "Employee ID '" + empId + "' deleted successfully.",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "No employee found with ID '" + empId + "'.",
                                    "Not Found",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Error deleting employee: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
