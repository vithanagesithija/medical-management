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
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set overall background color
        getContentPane().setBackground(new Color(153, 153, 153)); // Gray background

        // Create and style the header label
        JLabel headerLabel = new JLabel("Patient Details", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerLabel.setForeground(Color.WHITE); // Black text color
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0,0,0)); // Slightly lighter gray
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"ID", "Name", "Age", "Phone", "Email", "Address"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        patientTable = new JTable(tableModel);
        patientTable.setRowHeight(30);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 14));
        patientTable.setSelectionBackground(new Color(0, 0, 0)); // Black selection color
        patientTable.setSelectionForeground(Color.WHITE);

        // Add alternating row colors for better readability
        patientTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(180, 180, 180) : Color.WHITE);
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(patientTable);
        loadPatientData(tableModel);

        // Back button setup
        backButton = new JButton("Back");
        backButton.setBackground(new Color(0,0,0)); // Light gray button background
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(100, 40)); // Set button size

        // Button Panel (Horizontally aligned)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(153, 153, 153)); // Match background
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
