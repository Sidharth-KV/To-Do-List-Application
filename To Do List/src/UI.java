import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class UI extends JFrame {
    private JPanel taskPanel; // Panel to hold tasks with checkboxes
    private JTextField taskInputField; // Input field for new tasks
    private ArrayList<JCheckBox> taskCheckBoxes; // arrayList to store task checkboxes

    // JDBC Variables
    private final String DB_URL = "jdbc:mysql://localhost:3306/todo_list_app";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Skibidi_Sigma";

    // Constructor
    UI() {
        // Set frame properties
        this.setTitle("To-Do List App with MySQL");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 600);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(new Color(0, 0, 69));

        //app icon
        ImageIcon img = new ImageIcon("logo.png");
        this.setIconImage(img.getImage());

        // Create and configure title label
        JLabel titleLabel = new JLabel("To-Do List App", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel, BorderLayout.NORTH);

        // Initialize the panel to hold tasks
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        taskPanel.setBackground(new Color(32,12,45));

        //scroll pane functionality
        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        this.add(scrollPane, BorderLayout.CENTER);

        // list array for tasks
        taskCheckBoxes = new ArrayList<>();

        //input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setBackground(new Color(0, 0, 69));

        // create and configure the task input field
        taskInputField = new JTextField(25);
        taskInputField.setFont(new Font("Arial", Font.PLAIN, 18));

        // buttons
        JButton addButton = new JButton("Add Task");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Add components to the input panel
        inputPanel.add(taskInputField);
        inputPanel.add(addButton);

        // Add input panel to the bottom of the frame
        this.add(inputPanel, BorderLayout.SOUTH);

        // Load existing tasks from the database
        loadTasksFromDatabase();

        // Add functionality to the "Add Task" button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskText = taskInputField.getText().trim();
                if (!taskText.isEmpty()) {
                    addTaskToDatabase(taskText); // Add the task to the database
                    taskInputField.setText(""); // Clear the input field
                }
            }
        });

        // Set the frame visible
        this.setVisible(true);
    }

    // Method to load tasks from the database
    private void loadTasksFromDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT id, task FROM tasks WHERE completed = FALSE";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String task = rs.getString("task");
                int id = rs.getInt("id");
                addTaskToUI(task, id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading tasks from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to add a new task to the UI
    private void addTaskToUI(String taskText, int taskId) {
        JCheckBox taskCheckBox = new JCheckBox(taskText);
        taskCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
        taskPanel.add(taskCheckBox); // Add the checkbox to the task panel
        taskCheckBoxes.add(taskCheckBox); // Add to the list of checkboxes

        // Add action listener to detect when the checkbox is selected
        taskCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (taskCheckBox.isSelected()) {
                    // Start a timer to remove the task after 3 seconds
                    Timer timer = new Timer(3000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            taskPanel.remove(taskCheckBox); // Remove from the panel
                            taskCheckBoxes.remove(taskCheckBox); // Remove from the list
                            taskPanel.revalidate(); // Refresh the panel
                            taskPanel.repaint(); // Repaint the panel
                            markTaskAsCompleted(taskId); // Update task in the database
                        }
                    });
                    timer.setRepeats(false); // Execute the timer action only once
                    timer.start();
                }
            }
        });

        taskPanel.revalidate(); // Refresh the task panel
        taskPanel.repaint(); // Repaint the task panel
    }

    // Method to add a new task to the database
    private void addTaskToDatabase(String taskText) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO tasks (task) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, taskText);
            stmt.executeUpdate();

            // Get the generated task ID and add it to the UI
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int taskId = rs.getInt(1);
                addTaskToUI(taskText, taskId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding task to database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to mark a task as completed in the database
    private void markTaskAsCompleted(int taskId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE tasks SET completed = TRUE WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, taskId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating task status.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method
    public static void main(String[] args) {
        new UI();
    }
}

