package guii;

import code.DatabaseConnection;
import code.Appointment;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AppointmentManager extends JFrame {
    private JTextField appointmentIdField;
    private JTextField patientIdField;
    private JTextField doctorIdField;
    private JTextField appointmentDateField;
    private JTextField appointmentTimeField;
    private JButton addButton;
    private JButton updateButton;
    private JButton removeButton;
    private JButton viewButton;
    public JPanel mainPanel;

    public AppointmentManager() {
        // Set basic JFrame properties
        setTitle("Appointment Manager");
        setSize(700, 500);  // Updated size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create mainPanel and build the UI
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(153, 153, 153));  // Set background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Initialize components with increased width
        appointmentIdField = new JTextField(30);
        patientIdField = new JTextField(30);
        doctorIdField = new JTextField(30);
        appointmentDateField = new JTextField(30);
        appointmentTimeField = new JTextField(30);
        addButton = new JButton("Add Appointment");
        updateButton = new JButton("Update Appointment");
        removeButton = new JButton("Remove Appointment");
        viewButton = new JButton("View Appointments");

        // Set button colors (0,0,0 color)
        addButton.setBackground(new Color(0, 0, 0));
        addButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(0, 0, 0));
        updateButton.setForeground(Color.WHITE);
        removeButton.setBackground(new Color(0, 0, 0));
        removeButton.setForeground(Color.WHITE);
        viewButton.setBackground(new Color(0, 0, 0));
        viewButton.setForeground(Color.WHITE);

        // Header Label
        JLabel headerLabel = new JLabel("Appointment Details", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 0, 0));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(headerLabel, gbc);

        // Add Input Fields
        addInputField("Appointment ID:", appointmentIdField, 1, gbc);
        addInputField("Patient ID:", patientIdField, 2, gbc);
        addInputField("Doctor ID:", doctorIdField, 3, gbc);
        addInputField("Appointment Date:(YYYY-MM-DD)", appointmentDateField, 4, gbc);
        addInputField("Appointment Time:(HH:MM)", appointmentTimeField, 5, gbc);

        // Add Buttons
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 6;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(153, 153, 153)); // Background color for button panel
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Align buttons horizontally
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewButton);
        mainPanel.add(buttonPanel, gbc);

        // Button Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAppointment();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAppointment();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAppointment();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to ViewAppointment window
                new ViewAppointment().setVisible(true);
                dispose(); // Close AppointmentManager window
            }
        });

        add(mainPanel);
    }

    private void addInputField(String label, JTextField field, int row, GridBagConstraints gbc) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        mainPanel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        mainPanel.add(field, gbc);
    }

    private void addAppointment() {
        String appointmentId = appointmentIdField.getText();
        String patientId = patientIdField.getText();
        String doctorId = doctorIdField.getText();
        String date = appointmentDateField.getText();
        String time = appointmentTimeField.getText();

        if (doesDoctorExist(doctorId) && doesPatientExist(patientId)) {
            Appointment appointment = new Appointment(appointmentId, patientId, doctorId, date, time);
            insertAppointmentIntoDatabase(appointment);

            // Fetch patient and doctor emails
            String patientEmail = getPatientEmail(patientId);
            String doctorEmail = getDoctorEmail(doctorId);

            // Send emails
            if (patientEmail != null) {
                String patientSubject = "Appointment Confirmation";
                String patientBody = "Dear Patient,\n\nYour appointment is scheduled on " + date + " at " + time + ".\n\nThank you.";
                EmailSender.sendEmail(patientEmail, patientSubject, patientBody);
            }

            if (doctorEmail != null) {
                String doctorSubject = "New Appointment Scheduled";
                String doctorBody = "Dear Doctor,\n\nYou have a new appointment scheduled on " + date + " at " + time + " with Patient ID: " + patientId + ".\n\nThank you.";
                EmailSender.sendEmail(doctorEmail, doctorSubject, doctorBody);
            }

            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Doctor or Patient does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean doesDoctorExist(String doctorId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM doctors WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, doctorId);
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error checking doctor: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean doesPatientExist(String patientId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM patients WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, patientId);
                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error checking patient: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void insertAppointmentIntoDatabase(Appointment appointment) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO appointments (appointment_id, patient_id, doctor_id, appointment_date, appointment_time) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, appointment.getAppointmentId());
                statement.setString(2, appointment.getPatientId());
                statement.setString(3, appointment.getDoctorId());
                statement.setString(4, appointment.getAppointmentDate());
                statement.setString(5, appointment.getAppointmentTime());
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Appointment added successfully.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error inserting appointment: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAppointment() {
        String appointmentId = appointmentIdField.getText();
        String patientId = patientIdField.getText();
        String doctorId = doctorIdField.getText();
        String date = appointmentDateField.getText();
        String time = appointmentTimeField.getText();

        if (doesDoctorExist(doctorId) && doesPatientExist(patientId)) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_date = ?, appointment_time = ? WHERE appointment_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, patientId);
                    statement.setString(2, doctorId);
                    statement.setString(3, date);
                    statement.setString(4, time);
                    statement.setString(5, appointmentId);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Appointment updated successfully.");
                    clearForm();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error updating appointment: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Doctor or Patient does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeAppointment() {
        String appointmentId = appointmentIdField.getText();
        if (!appointmentId.isEmpty()) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "DELETE FROM appointments WHERE appointment_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, appointmentId);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Appointment removed successfully.");
                    clearForm();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error removing appointment: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Appointment ID is required for removal.", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearForm() {
        appointmentIdField.setText("");
        patientIdField.setText("");
        doctorIdField.setText("");
        appointmentDateField.setText("");
        appointmentTimeField.setText("");
    }

    private String getPatientEmail(String patientId) {
        String email = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT email FROM patients WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, patientId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    email = resultSet.getString("email");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching patient email: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return email;
    }

    private String getDoctorEmail(String doctorId) {
        String email = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT email FROM doctors WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, doctorId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    email = resultSet.getString("email");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching doctor email: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return email;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppointmentManager().setVisible(true));
    }
}
