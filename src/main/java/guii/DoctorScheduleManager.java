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
    private JPanel mainPanel;

    public DoctorScheduleManager() {
        // Initialize components
        idField = new JTextField();
        nameField = new JTextField();
        specializationField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        addButton = new JButton("Add Doctor");
        updateButton = new JButton("Update Doctor");
        removeButton = new JButton("Remove Doctor");
        viewButton = new JButton("View Doctor");

        // Create and set up the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2, 10, 10));
        mainPanel.add(new JLabel("ID:"));
        mainPanel.add(idField);
        mainPanel.add(new JLabel("Name:"));
        mainPanel.add(nameField);
        mainPanel.add(new JLabel("Specialization:"));
        mainPanel.add(specializationField);
        mainPanel.add(new JLabel("Phone:"));
        mainPanel.add(phoneField);
        mainPanel.add(new JLabel("Email:"));
        mainPanel.add(emailField);
        mainPanel.add(addButton);
        mainPanel.add(updateButton);
        mainPanel.add(removeButton);
        mainPanel.add(viewButton);

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
                // Leave empty as requested
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
