package guii;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppointmentView extends JFrame {
    private JTextArea appointmentListArea;
    private JButton backButton;

    public AppointmentView() {
        setTitle("Appointments");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background

        // Header
        JLabel headerLabel = new JLabel("Appointments", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0, 102, 204));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Appointment List Area
        appointmentListArea = new JTextArea();
        appointmentListArea.setFont(new Font("Arial", Font.PLAIN, 14));
        appointmentListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(appointmentListArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(255, 69, 0)); // Orange-red color
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // Back Button Action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
                new AppointmentManager().setVisible(true); // Open Appointment Manager
            }
        });

        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to update appointment data
    public void setAppointments(String appointments) {
        appointmentListArea.setText(appointments);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppointmentView().setVisible(true));
    }
}
