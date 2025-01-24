import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ClubInfoPanel extends JPanel {
    private Image backgroundImage;

    public ClubInfoPanel(JFrame frame) {
        // Load background image
        backgroundImage = new ImageIcon("D:\\java project jk\\bg.jpg").getImage();
        setLayout(new BorderLayout());

        // Club Title Section
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setBackground(new Color(30, 144, 255, 180)); // Dodger Blue with transparency
        JLabel titleLabel = new JLabel("Welcome to CLUB APP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32)); // Increased font size
        titleLabel.setForeground(new Color(0, 0, 128)); // Navy Blue color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Center Panel for Images and Description
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                super.paintComponent(g);
            }
        };
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Image Section
        JPanel imagePanel = new JPanel(new GridLayout(2, 2, 20, 20)); // 2 rows, 2 columns with spacing
        imagePanel.setOpaque(false); // Make image panel transparent

        // Add formatted image panels with clickable links
        addFormattedImageWithLink(imagePanel, "D:\\java project jk\\PanelPage1 .jpg", "https://www.aaruush.org/");
        addFormattedImageWithLink(imagePanel, "D:\\java project jk\\PanelPage2.jpg", "https://example2.com");
        addFormattedImageWithLink(imagePanel, "D:\\java project jk\\Panelpage3.jpg", "https://acmsrm.in/");
        addFormattedImageWithLink(imagePanel, "D:\\java project jk\\panelimaage4.jpg", "https://srm-university.blogspot.com/2012/05/srm-milan.html");

        centerPanel.add(imagePanel); // Add imagePanel to centerPanel

        // Club Description Section
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setOpaque(false);
        descriptionPanel.setBackground(new Color(60, 179, 113, 180)); // Medium Sea Green with transparency
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JTextArea descriptionArea = new JTextArea(5, 30);
        descriptionArea.setText("Our club is a pioneering platform designed to bring together students and various clubs under one collaborative community. At CLUB HUB, we aim to foster connections between different clubs, creating a vibrant space where students can discover new interests, expand their skill sets, and build meaningful relationships.");
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(new Color(245, 245, 245, 200)); // Light Gray with transparency
        descriptionArea.setForeground(Color.BLACK);
        descriptionArea.setEditable(false);
        descriptionPanel.add(descriptionArea);
        centerPanel.add(descriptionPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBackground(new Color(220, 20, 60, 180)); // Crimson with transparency
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton registerButton = new JButton("Register");
        styleButton(registerButton);
        registerButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) frame.getContentPane().getLayout();
            layout.show(frame.getContentPane(), "Register");
        });

        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) frame.getContentPane().getLayout();
            layout.show(frame.getContentPane(), "Login");
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Override paintComponent to draw the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    // Method to add a formatted, resized image with a clickable link
    private void addFormattedImageWithLink(JPanel panel, String imagePath, String url) {
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH); // Resize image larger
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setAlignmentX(CENTER_ALIGNMENT);
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5)); // Add border for contrast
            imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Open the URL on mouse click
            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new java.net.URI(url));
                    } catch (IOException | java.net.URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }

                // Add hover effect for image labels
                @Override
                public void mouseEntered(MouseEvent e) {
                    imageLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5)); // Change border color on hover
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    imageLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5)); // Reset border color
                }
            });

            panel.add(imageLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to style buttons
    private void styleButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(120, 40)); // Set button size
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor for buttons
        button.setFocusPainted(false); // Remove focus paint for a cleaner look
    }
}
