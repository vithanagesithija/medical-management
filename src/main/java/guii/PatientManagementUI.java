package gui;

import code.Patient;
import code.PatientDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;


public class PatientManagementUI extends JFrame {
    private JTextField idField, nameField, ageField, phoneField, emailField, addressField;
    private JButton addButton, updateButton, removeButton;
    private JTable patientTable;
    private DefaultTableModel tableModel;

    public PatientManagementUI() {
        setTitle("Patient Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Table to display patient details
        String[] columnNames = {"ID", "Name", "Age", "Phone", "Email", "Address"};
        tableModel = new DefaultTableModel(columnNames, 0);
        patientTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(patientTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Panel for input fields and buttons
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        // Add buttons
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.NORTH);

        // Button Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePatient();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePatient();
            }
        });

        // Load initial patient data
        loadPatients();

        setVisible(true);
    }

    // Method to load patient data into the table
    private void loadPatients() {
        // Clear the existing rows
        tableModel.setRowCount(0);

        // Get the list of patients from the database
        List<Patient> patients = PatientDAO.getAllPatients();

        // Add each patient to the table
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

    private void addPatient() {
        String id = idField.getText();
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();

        // Create a new Patient object
        Patient patient = new Patient(id, name, age, phone, email, address);

        // Call the addPatient method from PatientDAO
        boolean success = PatientDAO.addPatient(patient);

        if (success) {
            JOptionPane.showMessageDialog(this, "Patient Added!");
            loadPatients();  // Reload the patient list after adding
        } else {
            JOptionPane.showMessageDialog(this, "Error adding patient!");
        }
    }

    private void updatePatient() {
        String id = idField.getText();
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();

        // Create a new Patient object
        Patient patient = new Patient(id, name, age, phone, email, address);

        // Call the updatePatient method from PatientDAO
        boolean success = PatientDAO.updatePatient(patient);

        if (success) {
            JOptionPane.showMessageDialog(this, "Patient Updated!");
            loadPatients();  // Reload the patient list after updating
        } else {
            JOptionPane.showMessageDialog(this, "Error updating patient!");
        }
    }

    private void removePatient() {
        String id = idField.getText();

        // Call the deletePatient method from PatientDAO
        boolean success = PatientDAO.deletePatient(id);

        if (success) {
            JOptionPane.showMessageDialog(this, "Patient Removed!");
            loadPatients();  // Reload the patient list after removing
        } else {
            JOptionPane.showMessageDialog(this, "Error removing patient!");
        }
    }

    public static void main(String[] args) {
        new PatientManagementUI();
    }
}
