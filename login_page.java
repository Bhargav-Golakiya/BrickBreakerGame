import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class login_page extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public login_page() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLayout(null);
        getContentPane().setBackground(new Color(92, 64, 51));

        Font pixelFont = new Font("Monospaced", Font.BOLD, 16);

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 183, 77));
        titleLabel.setBounds(150, 20, 100, 30);
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Email");
        usernameLabel.setFont(pixelFont);
        usernameLabel.setForeground(Color.YELLOW);
        usernameLabel.setBounds(50, 70, 300, 20);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(50, 90, 300, 30);
        usernameField.setBackground(Color.DARK_GRAY);
        usernameField.setForeground(Color.WHITE);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(pixelFont);
        passwordLabel.setForeground(Color.YELLOW);
        passwordLabel.setBounds(50, 130, 300, 20);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 150, 300, 30);
        passwordField.setBackground(Color.DARK_GRAY);
        passwordField.setForeground(Color.WHITE);
        add(passwordField);

        JButton loginButton = new JButton("Sign In");
        loginButton.setBounds(50, 200, 300, 40);
        loginButton.setBackground(new Color(255, 138, 101));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(pixelFont);
        loginButton.addActionListener(e -> login());
        add(loginButton);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(50, 250, 300, 40);
        signUpButton.setBackground(new Color(255, 183, 77));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(pixelFont);
        signUpButton.addActionListener(e -> new SignUpPage().setVisible(true));
        add(signUpButton);

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        statusLabel.setBounds(50, 300, 300, 20);
        add(statusLabel);

        setLocationRelativeTo(null);
    }

    public Connection connectDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3307/login_db";
            String user = "root";
            String password = "bhargav";
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed: " + e.getMessage());
            return null;
        }
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both fields.");
            return;
        }

        Connection conn = connectDatabase();
        if (conn != null) {
            try {
                String query = "SELECT * FROM users WHERE email = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    dispose(); // ✅ Close login window
                    Main.startGame(); // ✅ Correct way to start the game
                } else {
                    statusLabel.setText("Invalid username or password.");
                }
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new login_page().setVisible(true));
    }
}
