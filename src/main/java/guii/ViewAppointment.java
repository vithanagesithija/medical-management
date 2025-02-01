package guii;

import code.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAppointment extends JFrame {
    private JTable appointmentTable;
    private JScrollPane scrollPane;
    private JButton backButton;

    public ViewAppointment() {
        // Set the title and size of the window
        setTitle("View Appointments");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color
        getContentPane().setBackground(new Color(153, 153, 153)); // Dark gray

        // Create and style the header label
        JLabel headerLabel = new JLabel("Appointments", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 26));
        headerLabel.setForeground(new Color(255,255,255)); // Black text
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0,0,0)); // Light gray background
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Create a table to show appointments
        String[] columns = {"Appointment ID", "Patient ID", "Doctor ID", "Date", "Time"};
        Object[][] data = fetchAppointmentData();
        appointmentTable = new JTable(data, columns);
        appointmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        appointmentTable.setRowHeight(30);
        appointmentTable.setSelectionBackground(new Color(200, 200, 200)); // Light gray
        appointmentTable.setSelectionForeground(Color.BLACK);
        appointmentTable.setGridColor(Color.BLACK); // Black grid lines

        // Scroll Pane to make the table scrollable
        scrollPane = new JScrollPane(appointmentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBackground(new Color(200, 200, 200)); // Light gray background
        backButton.setForeground(new Color(0, 0, 0)); // Black text
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(120, 40)); // Set button size

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open AppointmentManager window
                AppointmentManager appointmentManager = new AppointmentManager();
                appointmentManager.setVisible(true);

                // Close current window
                dispose();
            }
        });

        // Panel for button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0,0,0)); // Match background
        buttonPanel.add(backButton); // Center button
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Fetch data from the database to show in the table
    private Object[][] fetchAppointmentData() {
        String query = "SELECT * FROM appointments";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery()) {

            // Count the rows in the result set
            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            // Prepare the data array
            Object[][] data = new Object[rowCount][5];
            int rowIndex = 0;
            while (resultSet.next()) {
                data[rowIndex][0] = resultSet.getString("appointment_id");
                data[rowIndex][1] = resultSet.getString("patient_id");
                data[rowIndex][2] = resultSet.getString("doctor_id");
                data[rowIndex][3] = resultSet.getString("appointment_date");
                data[rowIndex][4] = resultSet.getString("appointment_time");
                rowIndex++;
            }
            return data;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching appointment data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return new Object[0][0]; // Return empty data on error
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewAppointment().setVisible(true));
    }
}
