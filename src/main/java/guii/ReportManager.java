package guii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportManager extends JFrame {
    private JButton patientDetailsButton, monthlyReportButton, doctorScheduleButton, pharmacyReportButton;

    public ReportManager() {
        setTitle("Monthly Report Manager");
        setSize(700, 500);  // Set size to 700x500
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(153, 153, 153)); // Set background color to (153, 153, 153)

        // Header label
        JLabel headerLabel = new JLabel("Monthly Report Manager", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 22));
        headerLabel.setForeground(new Color(255, 255, 255));  // Set header text color to black
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0, 0, 0)); // Blue color for header
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Align buttons horizontally
        buttonPanel.setBackground(new Color(153, 153, 153));  // Set background color for the button panel
        add(buttonPanel, BorderLayout.CENTER);

        // Create buttons
        patientDetailsButton = new JButton("Patient Details");
        monthlyReportButton = new JButton("Monthly Report");
        doctorScheduleButton = new JButton("Doctor Schedule");
        pharmacyReportButton = new JButton("Pharmacy Inventory");

        // Set button sizes and styles
        patientDetailsButton.setPreferredSize(new Dimension(200, 50)); // Increase button size
        monthlyReportButton.setPreferredSize(new Dimension(200, 50)); // Increase button size
        doctorScheduleButton.setPreferredSize(new Dimension(200, 50)); // Increase button size
        pharmacyReportButton.setPreferredSize(new Dimension(200, 50)); // Increase button size

        patientDetailsButton.setBackground(new Color(0, 0, 0));
        patientDetailsButton.setForeground(Color.WHITE);
        patientDetailsButton.setFont(new Font("Arial", Font.BOLD, 14));

        monthlyReportButton.setBackground(new Color(0, 0, 0));
        monthlyReportButton.setForeground(Color.WHITE);
        monthlyReportButton.setFont(new Font("Arial", Font.BOLD, 14));

        doctorScheduleButton.setBackground(new Color(0, 0, 0));
        doctorScheduleButton.setForeground(Color.WHITE);
        doctorScheduleButton.setFont(new Font("Arial", Font.BOLD, 14));

        pharmacyReportButton.setBackground(new Color(0, 0, 0));
        pharmacyReportButton.setForeground(Color.WHITE);
        pharmacyReportButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Add buttons to button panel
        buttonPanel.add(patientDetailsButton);
        buttonPanel.add(monthlyReportButton);
        buttonPanel.add(doctorScheduleButton);
        buttonPanel.add(pharmacyReportButton);

        // Action listeners for buttons
        patientDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new patientview().setVisible(true);
            }
        });

        monthlyReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the ViewAppointment window
                new ViewAppointment().setVisible(true);
                // Close the current ReportManager window
            }
        });

        doctorScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorView().setVisible(true);
            }
        });

        pharmacyReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PharmacyInventoryView().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReportManager().setVisible(true));
    }
}
