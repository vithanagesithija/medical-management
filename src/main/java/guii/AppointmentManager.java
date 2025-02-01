package guii;

import code.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentManager extends JFrame {
    private JTextField patientIdField, doctorIdField, dateField, timeField;
    private JButton addButton, viewButton;

    public AppointmentManager() {
        setTitle("Appointment Manager");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background

        // Header Label
        JLabel headerLabel = new JLabel("Manage Appointments", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 25));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0, 102, 204)); // Blue header background
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Input Fields Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(new Color(240, 248, 255));

        inputPanel.add(createStyledLabel("Patient ID:"));
        patientIdField = createStyledTextField();
        inputPanel.add(patientIdField);

        inputPanel.add(createStyledLabel("Doctor ID:"));
        doctorIdField = createStyledTextField();
        inputPanel.add(doctorIdField);

        inputPanel.add(createStyledLabel("Date (YYYY-MM-DD):"));
        dateField = createStyledTextField();
        inputPanel.add(dateField);

        inputPanel.add(createStyledLabel("Time (HH:MM):"));
        timeField = createStyledTextField();
        inputPanel.add(timeField);

        add(inputPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(240, 248, 255));

        addButton = createStyledButton("Add Appointment", new Color(0, 128, 255));
        viewButton = createStyledButton("View Appointments", new Color(30, 144, 255));

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Button - Store Data in MySQL
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAppointment();
            }
        });

        // View Button - Show Appointments
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAppointments();
            }
        });
    }

    // Method to Store Data in Database
    private void addAppointment() {
        String patientId = patientIdField.getText();
        String doctorId = doctorIdField.getText();
        String date = dateField.getText();
        String time = timeField.getText();

        if (patientId.isEmpty() || doctorId.isEmpty() || date.isEmpty() || time.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, patientId);
            stmt.setString(2, doctorId);
            stmt.setString(3, date);
            stmt.setString(4, time);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Appointment Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to View Appointments
    private void viewAppointments() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM appointments";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            StringBuilder appointments = new StringBuilder("Appointments:\n");
            while (rs.next()) {
                appointments.append("ID: ").append(rs.getInt("appointment_id"))
                        .append(" | Patient: ").append(rs.getString("patient_id"))
                        .append(" | Doctor: ").append(rs.getString("doctor_id"))
                        .append(" | Date: ").append(rs.getString("appointment_date"))
                        .append(" | Time: ").append(rs.getString("appointment_time"))
                        .append("\n");
            }

            AppointmentView appointmentView = new AppointmentView();
            appointmentView.setAppointments(appointments.toString());
            appointmentView.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        patientIdField.setText("");
        doctorIdField.setText("");
        dateField.setText("");
        timeField.setText("");
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        return textField;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppointmentManager().setVisible(true));
    }
}
