package guii;

import code.DatabaseConnection;
import code.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorView {
    private JFrame frame;
    private JTable doctorTable;
    private JScrollPane scrollPane;
    private JButton backButton;
    private JPanel buttonPanel;

    public DoctorView() {
        frame = new JFrame("Doctor Details");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null); // Center the window

        // Set the background color
        frame.getContentPane().setBackground(new Color(153, 153, 153));

        // Create the table with column names
        String[] columnNames = {"ID", "Name", "Specialization", "Phone", "Email"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        doctorTable = new JTable(tableModel);
        scrollPane = new JScrollPane(doctorTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for buttons to align them horizontally
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(153, 153, 153)); // Match background color
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Create a Back button to navigate to DoctorScheduleManager
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(0, 0, 0)); // Black background
        backButton.setForeground(Color.WHITE); // White text
        buttonPanel.add(backButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add header label for "Doctor Details"
        JLabel headerLabel = new JLabel("Doctor Details");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 0, 0)); // Blue color
        frame.add(headerLabel, BorderLayout.NORTH);

        // Load doctors data from the database
        loadDoctorsData();

        // Back button action listener
        backButton.addActionListener(e -> {
            // Close current frame
            frame.dispose();

            // Open DoctorScheduleManager
            SwingUtilities.invokeLater(() -> {
                JFrame scheduleManagerFrame = new JFrame("Doctor Schedule Manager");
                scheduleManagerFrame.setContentPane(new DoctorScheduleManager().mainPanel);
                scheduleManagerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                scheduleManagerFrame.pack();
                scheduleManagerFrame.setVisible(true);
            });
        });

        frame.setVisible(true);
    }

    private void loadDoctorsData() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT id, name, specialization, phone, email FROM doctors";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                DefaultTableModel model = (DefaultTableModel) doctorTable.getModel();
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String specialization = resultSet.getString("specialization");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    model.addRow(new Object[]{id, name, specialization, phone, email});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error fetching doctor data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DoctorView());  // Create and display the DoctorView frame
    }

    public void setVisible(boolean b) {
    }
}
