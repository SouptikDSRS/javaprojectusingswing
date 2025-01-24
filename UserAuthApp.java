import javax.swing.*;
import java.awt.*;

public class UserAuthApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Authentication");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new CardLayout());

            // Add panels
            frame.add(new LoginPanel(frame), "Login");
            frame.add(new RegisterPanel(frame), "Register");

            frame.setVisible(true);
        });
    }
}
