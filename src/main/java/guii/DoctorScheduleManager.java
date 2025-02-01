package guii;

import code.DatabaseConnection;
import code.Doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorScheduleManager {
    private JTextField idField;
    private JTextField nameField;
    private JTextField specializationField;
    private JTextField phoneField;
    private JTextField emailField;
    private JButton addButton;
    private JButton updateButton;
    private JButton removeButton;
    private JButton viewButton;
    public JPanel mainPanel;

    public DoctorScheduleManager() {
        // Initialize components
        idField = new JTextField(20);
        nameField = new JTextField(20);
        specializationField = new JTextField(20);
        phoneField = new JTextField(20);
        emailField = new JTextField(20);
        addButton = new JButton("Add Doctor");
        updateButton = new JButton("Update Doctor");
        removeButton = new JButton("Remove Doctor");
        viewButton = new JButton("View Doctor");

        // Set colors for buttons
        addButton.setBackground(new Color(50, 205, 50)); // Green
        addButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(70, 130, 180)); // SteelBlue
        updateButton.setForeground(Color.WHITE);
        removeButton.setBackground(new Color(220, 20, 60)); // Crimson
        removeButton.setForeground(Color.WHITE);
        viewButton.setBackground(new Color(255, 165, 0)); // Orange
        viewButton.setForeground(Color.WHITE);

        // Set font for labels and fields to bold
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.BOLD, 12);

        // Create and set up the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add header label
        JLabel headerLabel = new JLabel("Doctor Details");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 102, 204)); // Blue
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(headerLabel, gbc);

        // Add the input fields with labels
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Specialization:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(specializationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(emailField, gbc);

        // Add buttons
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(addButton, gbc);
        gbc.gridy = 7;
        mainPanel.add(updateButton, gbc);
        gbc.gridy = 8;
        mainPanel.add(removeButton, gbc);
        gbc.gridy = 9;
        mainPanel.add(viewButton, gbc);

        // Set label font to bold
        for (Component component : mainPanel.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setFont(boldFont);
            }
        }

        // Set text field font to bold
        idField.setFont(fieldFont);
        nameField.setFont(fieldFont);
        specializationField.setFont(fieldFont);
        phoneField.setFont(fieldFont);
        emailField.setFont(fieldFont);

        // Button Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDoctor();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDoctor();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeDoctor();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to DoctorView when viewButton is clicked
                new DoctorView();  // Open DoctorView class
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(viewButton);
                currentFrame.dispose(); // Close DoctorScheduleManager
            }
        });
    }

    private void addDoctor() {
        String id = idField.getText();
        String name = nameField.getText();
        String specialization = specializationField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (!id.isEmpty() && !name.isEmpty() && !specialization.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            Doctor doctor = new Doctor(id, name, specialization, phone, email);
            insertDoctorIntoDatabase(doctor);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(null, "All fields are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void insertDoctorIntoDatabase(Doctor doctor) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO doctors (id, name, specialization, phone, email) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, doctor.getDocID());
                statement.setString(2, doctor.getName());
                statement.setString(3, doctor.getSpecialization());
                statement.setString(4, doctor.getPhone());
                statement.setString(5, doctor.getEmail());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting doctor into database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDoctor() {
        String id = idField.getText();

        if (!id.isEmpty()) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "UPDATE doctors SET name = ?, specialization = ?, phone = ?, email = ? WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, nameField.getText());
                    statement.setString(2, specializationField.getText());
                    statement.setString(3, phoneField.getText());
                    statement.setString(4, emailField.getText());
                    statement.setString(5, id);
                    statement.executeUpdate();
                    clearForm();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error updating doctor in database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID field is required for updating.", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removeDoctor() {
        String id = idField.getText();

        if (!id.isEmpty()) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "DELETE FROM doctors WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, id);
                    statement.executeUpdate();
                    clearForm();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error removing doctor from database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID field is required for removal.", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        specializationField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Doctor Schedule Manager");
        frame.setContentPane(new DoctorScheduleManager().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
