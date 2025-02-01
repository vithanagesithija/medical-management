package guii;

import code.DatabaseConnection;
import code.Doctor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DoctorScheduleManager extends JFrame {
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
        setTitle("Doctor Schedule Manager");
        setSize(700, 500);  // Changed the size to 700x500
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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
        addButton.setBackground(new Color(0, 0, 0));
        addButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(0, 0, 0));
        updateButton.setForeground(Color.WHITE);
        removeButton.setBackground(new Color(0, 0, 0));
        removeButton.setForeground(Color.WHITE);
        viewButton.setBackground(new Color(0, 0, 0));
        viewButton.setForeground(Color.WHITE);

        // Set fonts for labels and fields
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.BOLD, 12);

        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(153, 153, 153));  // Set background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Header label
        JLabel headerLabel = new JLabel("Doctor Details");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 0, 0));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(headerLabel, gbc);

        // Input fields
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

        // Buttons panel for horizontal alignment
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(153, 153, 153));  // Background color for the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewButton);

        // Add button panel to the main panel
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(buttonPanel, gbc);

        // Set fonts
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setFont(boldFont);
            }
        }
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
                new DoctorView().setVisible(true);
                dispose();
            }
        });

        add(mainPanel);
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
            JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Error inserting doctor: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Error updating doctor: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "ID is required for updating.", "Input Error", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Error removing doctor: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "ID is required for removal.", "Input Error", JOptionPane.WARNING_MESSAGE);
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
        SwingUtilities.invokeLater(() -> new DoctorScheduleManager().setVisible(true));
    }
}
