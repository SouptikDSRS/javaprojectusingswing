import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class RegisterPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField nameField;
    private JTextField collegeField;
    private JTextField courseField;
    private JButton registerButton;
    private JButton switchToLoginButton;
    private BufferedImage backgroundImage;

    public RegisterPanel(JFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        Font font = new Font("Arial", Font.BOLD, 16);
        Color labelColor = new Color(0, 100, 0); // Deep green

        Color buttonColor = new Color(0, 102, 204); // Blue

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("D:\\java project jk\\registration-form-2024.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Username Label and Field
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(font);
        usernameLabel.setForeground(labelColor);
        add(usernameLabel, gbc);
        
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(font);
        passwordLabel.setForeground(labelColor);
        add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Email Label and Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(font);
        emailLabel.setForeground(labelColor);
        add(emailLabel, gbc);
        
        emailField = new JTextField(15);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Name Label and Field
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(font);
        nameLabel.setForeground(labelColor);
        add(nameLabel, gbc);
        
        nameField = new JTextField(15);
        gbc.gridx = 1;
        add(nameField, gbc);

        // College Label and Field
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel collegeLabel = new JLabel("College Name:");
        collegeLabel.setFont(font);
        collegeLabel.setForeground(labelColor);
        add(collegeLabel, gbc);
        
        collegeField = new JTextField(15);
        gbc.gridx = 1;
        add(collegeField, gbc);

        // Course Label and Field
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setFont(font);
        courseLabel.setForeground(labelColor);
        add(courseLabel, gbc);
        
        courseField = new JTextField(15);
        gbc.gridx = 1;
        add(courseField, gbc);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.setFont(font);
        registerButton.setForeground(Color.WHITE); // Text color
        registerButton.setBackground(buttonColor); // Button background color
        registerButton.addActionListener(new RegisterAction());
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        // Switch to Login Button
        switchToLoginButton = new JButton("Already have an account? Login");
        switchToLoginButton.setFont(font);
        switchToLoginButton.setForeground(Color.WHITE); // Text color
        switchToLoginButton.setBackground(buttonColor); // Button background color
        switchToLoginButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "Login");
        });
        gbc.gridy = 7;
        add(switchToLoginButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();
            String name = nameField.getText();
            String college = collegeField.getText();
            String course = courseField.getText();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || name.isEmpty() || college.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(RegisterPanel.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (registerUser(username, password, email, name, college, course)) {
                    JOptionPane.showMessageDialog(RegisterPanel.this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(RegisterPanel.this, "Registration failed. Username may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private boolean registerUser(String username, String password, String email, String name, String college, String course) {
            String insertQuery = "INSERT INTO users (username, password, email, name, college, course) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, email);
                pstmt.setString(4, name);
                pstmt.setString(5, college);
                pstmt.setString(6, course);
        
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Registration successful!");
                    return true;
                } else {
                    System.out.println("Registration failed: no rows affected.");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        
    }
}
