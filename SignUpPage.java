import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignUpPage extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public SignUpPage() {
        setTitle("Sign Up");
        setSize(400, 350);
        setLayout(null);
        getContentPane().setBackground(new Color(92, 64, 51));

        Font pixelFont = new Font("Monospaced", Font.BOLD, 16);

        JLabel titleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 183, 77));
        titleLabel.setBounds(150, 20, 100, 30);
        add(titleLabel);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(pixelFont);
        emailLabel.setForeground(Color.YELLOW);
        emailLabel.setBounds(50, 70, 300, 20);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(50, 90, 300, 30);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(pixelFont);
        passwordLabel.setForeground(Color.YELLOW);
        passwordLabel.setBounds(50, 130, 300, 20);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 150, 300, 30);
        add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(50, 200, 300, 40);
        registerButton.addActionListener(e -> registerUser());
        add(registerButton);

        setLocationRelativeTo(null);
    }

    private void registerUser() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        Connection conn = new login_page().connectDatabase();
        if (conn != null) {
            try {
                String query = "INSERT INTO users (email, password) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                stmt.setString(2, password);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "User Registered Successfully!");
                conn.close();
                dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
}
