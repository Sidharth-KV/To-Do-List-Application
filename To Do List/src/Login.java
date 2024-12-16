import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        this.setSize(600, 600); // Set resolution to 600x600
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(new Color(10, 45, 56));

        // Title label
        JLabel titleLabel = new JLabel("Login Page", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32)); // Larger font for higher resolution
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel, BorderLayout.NORTH);

        // Center panel for inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 15, 15)); // Increased gaps for proportion
        inputPanel.setBackground(new Color(10, 45, 56));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        inputPanel.setPreferredSize(new Dimension(100, 10));// Increased margins

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(25); //input fields

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(25);

        // Add components to input panel
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Add input panel to frame
        this.add(inputPanel, BorderLayout.CENTER);

        // panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Adjusted spacing
        buttonPanel.setBackground(new Color(10, 45, 56));

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18)); 
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                JOptionPane.showMessageDialog(null, "Login clicked: " + username);
            }
        });

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //register functionality
                Register regWin = new Register();
            
            }
        });

        // Forgot Password button
        JButton forgotPasswordButton = new JButton("Forgot Password");
        forgotPasswordButton.setFont(new Font("Arial", Font.BOLD, 18));
        forgotPasswordButton.setPreferredSize(new Dimension(150, 40));
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //forgot password functionality
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
    // Main method to run the application
    public static void main(String[] args) {
        	new Login();
    }
}
