import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton switchToRegisterButton;
    private JButton homeButton; // New Home button
    private BufferedImage backgroundImage;
    private JPanel formPanel;

    public LoginPanel(JFrame frame) {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("D:\\java project jk\\360_F_119115529_mEnw3lGpLdlDkfLgRcVSbFRuVl6sMDty.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        // Create a panel for the form
        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false); // Make the form panel transparent
        formPanel.setBackground(new Color(255, 255, 255, 180)); // White background with transparency

        GridBagConstraints gbc = new GridBagConstraints();

        // Username Label and Field
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginAction());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);

        // Switch to Registration Button
        switchToRegisterButton = new JButton("Don't have an account? Register");
        switchToRegisterButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "Register");
        });
        gbc.gridy = 3;
        formPanel.add(switchToRegisterButton, gbc);

        // Home Button to return to the ClubInfo Panel
        homeButton = new JButton("Home");
        homeButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "ClubInfo");
        });
        gbc.gridy = 4;
        formPanel.add(homeButton, gbc);

        // Add the form panel to the center of the main panel
        add(formPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginPanel.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (loginUser(username, password)) {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Open URL after successful login
                    try {
                        Desktop.getDesktop().browse(new java.net.URI("https://www.aaruush.org/events/srm-run-aaruush24/register"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(LoginPanel.this, "Unable to open the website.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Login failed. Check your username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private boolean loginUser(String username, String password) {
            String selectQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password); // Direct password check, without hashing
        
                ResultSet rs = pstmt.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        private String hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashedBytes = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte b : hashedBytes) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
