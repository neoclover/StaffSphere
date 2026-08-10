package Employee_Management;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View_Employee implements ActionListener {
    JFrame f;
    JTextField t;
    JLabel l2;
    JButton b, b1;

    public View_Employee() {
        f = new JFrame("View Employee");
        f.setSize(500, 270);
        f.setLocationRelativeTo(null); // Center on screen
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel() {
            Image bg = ResourceUtil.loadIcon("Employee_Management/Icon/view.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) {
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panel.setLayout(null);

        l2 = new JLabel("Employee ID:");
        l2.setBounds(40, 60, 180, 30);
        l2.setFont(new Font("Segoe UI", Font.BOLD, 22));
        l2.setForeground(Color.WHITE);
        panel.add(l2);

        t = new JTextField();
        t.setBounds(220, 60, 230, 32);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        panel.add(t);

        b = new JButton("Search");
        b.setBounds(140, 140, 110, 35);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBackground(new Color(0, 102, 204));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addActionListener(this);
        panel.add(b);

        b1 = new JButton("Cancel");
        b1.setBounds(270, 140, 110, 35);
        b1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b1.setBackground(new Color(108, 117, 125));
        b1.setForeground(Color.WHITE);
        b1.setFocusPainted(false);
        b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b1.addActionListener(this);
        panel.add(b1);

        t.addActionListener(e -> searchEmployee());

        f.setContentPane(panel);
        f.setVisible(true);
    }

    private void searchEmployee() {
        String empId = t.getText().trim();
        if (empId.isEmpty()) {
            JOptionPane.showMessageDialog(f, "Please enter an Employee ID.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        f.dispose();
        new View_Employee_Data(empId);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b) {
            searchEmployee();
        } else if (e.getSource() == b1) {
            f.dispose();
        }
    }

    public static void main(String[] args) {
        new View_Employee();
    }
}
