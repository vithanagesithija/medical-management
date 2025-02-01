package guii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel imageLabel;

    public LoginGUI() {
        setTitle("Login - Healthcare System");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for form
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(153, 153, 153));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Load Image
        ImageIcon icon = new ImageIcon("src/main/java/org/example/download.jpeg"); // Ensure 'logo.png' is in the project folder
        Image image = icon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        imageLabel = new JLabel(new ImageIcon(image));

        // Labels and Fields
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(15);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(150, 40));

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(imageLabel, gbc);

        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);

        gbc.gridy = 2;
        panel.add(usernameField, gbc);

        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);

        gbc.gridy = 4;
        panel.add(passwordField, gbc);

        gbc.gridy = 5;
        panel.add(loginButton, gbc);

        add(panel, BorderLayout.CENTER);

        // Add action listener
        loginButton.addActionListener(new LoginAction());
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Hardcoded validation for demo (Replace with database validation)
            if (username.equals("admin") && password.equals("1234")) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                dispose(); // Close login window
                new HomeGUI().setVisible(true); // Open Home GUI
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}

