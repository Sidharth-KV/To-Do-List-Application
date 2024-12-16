import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;

public class Register extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private final String DB_URL = "jdbc:mysql://localhost:3306/todo_list_app";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Skibidi_Sigma";

    // Constructor
    Register() {
        this.setTitle("Register Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 600); // Set resolution
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(new Color(10, 45, 56));

        // Title label
        JLabel titleLabel = new JLabel("Register", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel, BorderLayout.NORTH);

        // Center panel for inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2, 15, 15));
        inputPanel.setBackground(new Color(10, 45, 56));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(15);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(15);

        // Add components to input panel
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Add input panel to the frame
        this.add(inputPanel, BorderLayout.CENTER);

        // South panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(10, 45, 56));

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Call register function
                registerUser(username, password);
            }
        });

        // Add the register button to the panel
        buttonPanel.add(registerButton);

        // Add button panel to the frame
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Make frame visible
        this.setVisible(true);
    }

    // Function to register the user in the database
    private void registerUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO users (name, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                this.dispose(); // Close the register window
                new Login(); // Open the login window
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Could not register user", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
