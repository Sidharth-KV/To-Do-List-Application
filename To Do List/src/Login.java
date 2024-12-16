import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends JFrame {

    private JTextField usernameField; // Field for username
    private JPasswordField passwordField; // Field for password

    
    //JDBC vars
    private final String DB_URL = "jdbc:mysql://localhost:3306/todo_list_app";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Skibidi_Sigma";

    
    
    // Constructor
    Login() {
        // Frame settings
        this.setTitle("Login Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 600); // Set resolution
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(new Color(10, 45, 56));

        // Title label
        JLabel titleLabel = new JLabel("Login Page", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32)); // Larger font for higher resolution
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel, BorderLayout.NORTH);

        // Center panel for inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2, 15, 15)); // Increased gaps for proportion
        inputPanel.setBackground(new Color(10, 45, 56));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        inputPanel.setPreferredSize(new Dimension(100, 10));// Increased margins

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Larger font
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(10);// Increased size for input fields
        
        
        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(10);

        // Add components to input panel
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Add input panel to the frame
        this.add(inputPanel, FlowLayout.CENTER);
        
        // South panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Adjusted spacing
        buttonPanel.setBackground(new Color(10, 45, 56));

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18)); // Larger font
        loginButton.setPreferredSize(new Dimension(150, 40)); // Larger button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if(loginUser(username, password))
                {
                	dispose();
                	new UI();
                }
                
                
                
            }
        });
        
        

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder for register functionality
                Register newWindow = new Register();
                dispose();
            
            }
        });

        // Forgot Password button
        JButton forgotPasswordButton = new JButton("Forgot Password");
        forgotPasswordButton.setFont(new Font("Arial", Font.BOLD, 18));
        forgotPasswordButton.setPreferredSize(new Dimension(150, 40));
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder for forgot password functionality
                JOptionPane.showMessageDialog(null, "Forgot Password clicked");
            }
        });

        // Add buttons to the panel
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(forgotPasswordButton);

        // Add button panel to the frame
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Make frame visible
        this.setVisible(true);  
    }
    
    private boolean loginUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE name = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while connecting to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Main method to run the application
    public static void main(String[] args) {
        	new Login();
    }
}
