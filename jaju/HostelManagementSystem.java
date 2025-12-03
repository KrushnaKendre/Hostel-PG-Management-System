import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Tenant Model
class Tenant {
    String name, room, phone;

    Tenant(String name, String room, String phone) {
        this.name = name;
        this.room = room;
        this.phone = phone;
    }
}

public class HostelManagementSystem extends JFrame implements ActionListener {

    CardLayout card;
    JPanel mainPanel;

    ArrayList<Tenant> tenants = new ArrayList<>();

    // Fields for Add Tenant Form
    JTextField nameField, roomField, phoneField;
    JTextArea viewArea;

    public HostelManagementSystem() {

        setTitle("Hostel / PG Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        card = new CardLayout();
        mainPanel = new JPanel(card);

        // Add all screens
        mainPanel.add(loginScreen(), "login");
        mainPanel.add(menuScreen(), "menu");
        mainPanel.add(addTenantScreen(), "add");
        mainPanel.add(viewTenantScreen(), "view");
        mainPanel.add(deleteTenantScreen(), "delete");

        add(mainPanel);
        setVisible(true);
    }

    // ------------------ Gradient Panel ---------------------
    class GradientPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, new Color(70, 90, 255),
                    getWidth(), getHeight(), new Color(140, 0, 255));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // ------------------ Login Screen -----------------------
    JPanel loginScreen() {
        JPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Hostel / PG Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBounds(270, 60, 450, 40);
        panel.add(title);

        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(300, 180, 100, 30);
        userLbl.setForeground(Color.WHITE);
        panel.add(userLbl);

        JTextField userField = new JTextField();
        userField.setBounds(400, 180, 200, 30);
        panel.add(userField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(300, 230, 100, 30);
        passLbl.setForeground(Color.WHITE);
        panel.add(passLbl);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(400, 230, 200, 30);
        panel.add(passField);

        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn);
        loginBtn.setBounds(400, 290, 200, 40);
        panel.add(loginBtn);

        loginBtn.addActionListener(e -> {
            card.show(mainPanel, "menu");
        });

        return panel;
    }

    // ------------------ Menu Screen -----------------------
    JPanel menuScreen() {
        JPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Hostel / PG Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBounds(290, 40, 400, 40);
        panel.add(title);

        JButton addBtn = new JButton("Add Tenant");
        JButton viewBtn = new JButton("View Tenants");
        JButton deleteBtn = new JButton("Delete Tenant");
        JButton exitBtn = new JButton("Exit");

        styleButton(addBtn);
        styleButton(viewBtn);
        styleButton(deleteBtn);
        styleButton(exitBtn);

        addBtn.setBounds(330, 150, 250, 50);
        viewBtn.setBounds(330, 220, 250, 50);
        deleteBtn.setBounds(330, 290, 250, 50);
        exitBtn.setBounds(330, 360, 250, 50);

        panel.add(addBtn);
        panel.add(viewBtn);
        panel.add(deleteBtn);
        panel.add(exitBtn);

        addBtn.addActionListener(e -> card.show(mainPanel, "add"));
        viewBtn.addActionListener(e -> {
            updateView();
            card.show(mainPanel, "view");
        });
        deleteBtn.addActionListener(e -> card.show(mainPanel, "delete"));
        exitBtn.addActionListener(e -> System.exit(0));

        return panel;
    }

    // ------------------ Add Tenant Screen -----------------------
    JPanel addTenantScreen() {
        JPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Add Tenant Details");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(330, 40, 400, 40);
        panel.add(title);

        JLabel nameLbl = new JLabel("Name:");
        JLabel roomLbl = new JLabel("Room No.:");
        JLabel phoneLbl = new JLabel("Phone:");

        nameLbl.setForeground(Color.WHITE);
        roomLbl.setForeground(Color.WHITE);
        phoneLbl.setForeground(Color.WHITE);

        nameLbl.setBounds(250, 150, 120, 30);
        roomLbl.setBounds(250, 200, 120, 30);
        phoneLbl.setBounds(250, 250, 120, 30);

        panel.add(nameLbl);
        panel.add(roomLbl);
        panel.add(phoneLbl);

        nameField = new JTextField();
        roomField = new JTextField();
        phoneField = new JTextField();

        nameField.setBounds(350, 150, 250, 30);
        roomField.setBounds(350, 200, 250, 30);
        phoneField.setBounds(350, 250, 250, 30);

        panel.add(nameField);
        panel.add(roomField);
        panel.add(phoneField);

        JButton add = new JButton("Save Tenant");
        JButton back = new JButton("Back");

        styleButton(add);
        styleButton(back);

        add.setBounds(350, 320, 250, 45);
        back.setBounds(350, 380, 250, 45);

        panel.add(add);
        panel.add(back);

        add.addActionListener(this);
        back.addActionListener(e -> card.show(mainPanel, "menu"));

        return panel;
    }

    // ------------------ View Tenant Screen -----------------------
    JPanel viewTenantScreen() {
        JPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Tenant List");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(360, 30, 300, 40);
        panel.add(title);

        viewArea = new JTextArea();
        viewArea.setEditable(false);
        viewArea.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JScrollPane scroll = new JScrollPane(viewArea);
        scroll.setBounds(180, 100, 550, 350);
        panel.add(scroll);

        JButton back = new JButton("Back");
        styleButton(back);
        back.setBounds(350, 470, 200, 40);

        back.addActionListener(e -> card.show(mainPanel, "menu"));
        panel.add(back);

        return panel;
    }

    // ------------------ Delete Tenant Screen -----------------------
    JPanel deleteTenantScreen() {
        JPanel panel = new GradientPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Delete Tenant");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(350, 40, 300, 40);
        panel.add(title);

        JLabel nameLbl = new JLabel("Enter Tenant Name:");
        nameLbl.setForeground(Color.WHITE);
        nameLbl.setBounds(260, 200, 200, 30);
        panel.add(nameLbl);

        JTextField deleteField = new JTextField();
        deleteField.setBounds(420, 200, 250, 30);
        panel.add(deleteField);

        JButton deleteBtn = new JButton("Delete");
        JButton back = new JButton("Back");

        styleButton(deleteBtn);
        styleButton(back);

        deleteBtn.setBounds(300, 280, 180, 45);
        back.setBounds(500, 280, 180, 45);

        panel.add(deleteBtn);
        panel.add(back);

        deleteBtn.addActionListener(e -> {
            String name = deleteField.getText();
            boolean removed = tenants.removeIf(t -> t.name.equalsIgnoreCase(name));

            if (removed)
                JOptionPane.showMessageDialog(this, "Tenant removed successfully!");
            else
                JOptionPane.showMessageDialog(this, "Tenant not found.");

            deleteField.setText("");
        });

        back.addActionListener(e -> card.show(mainPanel, "menu"));

        return panel;
    }

    // ------------------ Button Styling -----------------------
    void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(new Color(255, 255, 255));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // ------------------ Add Tenant Action -----------------------
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String room = roomField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty() || room.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        tenants.add(new Tenant(name, room, phone));
        JOptionPane.showMessageDialog(this, "Tenant added successfully!");

        nameField.setText("");
        roomField.setText("");
        phoneField.setText("");
    }

    // ------------------ Update Tenant List -----------------------
    void updateView() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name\t\tRoom\t\tPhone\n");
        sb.append("---------------------------------------------\n");

        for (Tenant t : tenants) {
            sb.append(t.name).append("\t\t")
              .append(t.room).append("\t\t")
              .append(t.phone).append("\n");
        }

        viewArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        new HostelManagementSystem();
    }
}
