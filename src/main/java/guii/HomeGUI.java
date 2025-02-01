package guii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeGUI extends JFrame {
    private JButton patientDetailsButton, doctorScheduleButton, pharmacyInventoryButton;
    private JButton reportManagerButton, appointmentManagerButton; // Renamed this button
    private JPanel buttonPanel;

    public HomeGUI() {
        // Set the title and size of the window
        setTitle("Healthcare Management System");
        setSize(700, 500); // Set the size to 700x500
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set the background color of the window
        getContentPane().setBackground(new Color(153, 153, 153));

        // Header label
        JLabel headerLabel = new JLabel("Healthcare Management System", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0, 0, 0)); // Blue color
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Button panel for horizontal alignment
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(153, 153, 153)); // Match background color
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Center and add space between buttons
        add(buttonPanel, BorderLayout.CENTER);

        // Create buttons for each functionality
        patientDetailsButton = new JButton("Patient Details");
        doctorScheduleButton = new JButton("Doctor Schedule");
        pharmacyInventoryButton = new JButton("Pharmacy Inventory");
        reportManagerButton = new JButton("Report Manager");
        appointmentManagerButton = new JButton("Appointment Manager");

        // Set button styles
        setButtonStyle(patientDetailsButton);
        setButtonStyle(doctorScheduleButton);
        setButtonStyle(pharmacyInventoryButton);
        setButtonStyle(reportManagerButton);
        setButtonStyle(appointmentManagerButton);

        // Increase button sizes
        setButtonSize(patientDetailsButton);
        setButtonSize(doctorScheduleButton);
        setButtonSize(pharmacyInventoryButton);
        setButtonSize(reportManagerButton);
        setButtonSize(appointmentManagerButton);

        // Add buttons to the button panel
        buttonPanel.add(patientDetailsButton);
        buttonPanel.add(doctorScheduleButton);
        buttonPanel.add(pharmacyInventoryButton);
        buttonPanel.add(reportManagerButton);
        buttonPanel.add(appointmentManagerButton);

        // Action listeners for each button
        patientDetailsButton.addActionListener(e -> new patientdetails().setVisible(true));
        doctorScheduleButton.addActionListener(e -> {
            try {
                new DoctorScheduleManager().setVisible(true); // Open Doctor Schedule Manager
            } catch (Exception ex) {
                ex.printStackTrace(); // Log any errors
            }
        });
        pharmacyInventoryButton.addActionListener(e -> new PharmacyInventoryManager().setVisible(true));
        reportManagerButton.addActionListener(e -> new ReportManager().setVisible(true));
        appointmentManagerButton.addActionListener(e -> {
            try {
                new AppointmentManager().setVisible(true); // Open Appointment Manager

            } catch (Exception ex) {
                ex.printStackTrace(); // Log any errors
            }
        });
    }

    // Method to set button styles
    private void setButtonStyle(JButton button) {
        button.setBackground(new Color(0, 0, 0)); // Black background
        button.setForeground(Color.WHITE); // White text
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    // Method to increase button sizes
    private void setButtonSize(JButton button) {
        button.setPreferredSize(new Dimension(250, 50)); // Set preferred size (Width x Height)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeGUI().setVisible(true));
    }
}
