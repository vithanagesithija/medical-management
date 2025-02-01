package guii;

import code.Patient;
import code.PatientDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class patientview extends JFrame {
    private JTable patientTable;
    private JButton backButton;

    public patientview() {
        setTitle("Patient Details");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set overall background color
        getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background

        // Create and style the header label
        JLabel headerLabel = new JLabel("Patient Details", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        headerLabel.setForeground(new Color(0, 102, 204)); // Blue color
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(255, 255, 255)); // White background for the header
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"ID", "Name", "Age", "Phone", "Email", "Address"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        patientTable = new JTable(tableModel);
        patientTable.setRowHeight(30);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 16));
        patientTable.setSelectionBackground(new Color(0, 102, 204)); // Blue selection color
        patientTable.setSelectionForeground(Color.WHITE);

        // Add alternating row colors for better readability
        patientTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(new Color(240, 248, 255)); // Light blue for even rows
                } else {
                    c.setBackground(Color.WHITE); // White for odd rows
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(patientTable);
        loadPatientData(tableModel);

        // Back button setup
        backButton = new JButton("Back");
        backButton.setBackground(new Color(70, 130, 180)); // SteelBlue background
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        buttonPanel.add(backButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
                new patientdetails().setVisible(true); // Open PatientDetails GUI
            }
        });
    }

    private void loadPatientData(DefaultTableModel tableModel) {
        List<Patient> patients = PatientDAO.getAllPatients();
        for (Patient patient : patients) {
            tableModel.addRow(new Object[]{
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getPhone(),
                    patient.getEmail(),
                    patient.getAddress()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new patientview().setVisible(true));
    }
}
