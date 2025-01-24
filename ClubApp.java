import javax.swing.*;
import java.awt.*;

public class ClubApp extends JFrame {
    public ClubApp() {
        setTitle("Club App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the frame

        // Create a CardLayout to switch between panels
        CardLayout cardLayout = new CardLayout();
        getContentPane().setLayout(cardLayout);

        // Create instances of panels
        ClubInfoPanel clubInfoPanel = new ClubInfoPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);
        LoginPanel loginPanel = new LoginPanel(this);

        // Add panels to the frame
        getContentPane().add(clubInfoPanel, "ClubInfo");
        getContentPane().add(registerPanel, "Register");
        getContentPane().add(loginPanel, "Login");

        // Show the Club Information Panel by default
        cardLayout.show(getContentPane(), "ClubInfo");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClubApp app = new ClubApp();
            app.setVisible(true);
        });
    }
}
