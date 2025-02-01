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
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"ID", "Name", "Age", "Phone", "Email", "Address"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        patientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientTable);

        loadPatientData(tableModel);

        // Back button setup
        backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel();
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
