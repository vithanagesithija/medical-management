package guii;

import code.Patient;
import code.PatientDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class patientdetails extends JFrame {
    private JTextField idField, nameField, ageField, phoneField, emailField, addressField;
    private JButton addButton, removeButton, updateButton, viewButton;

    public patientdetails() {
        setTitle("Patient Management System");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Patient Details"));

        idField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        addressField = new JTextField();

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);

        addButton = new JButton("Add Patient");
        removeButton = new JButton("Remove Patient");
        updateButton = new JButton("Update Patient");
        viewButton = new JButton("View Patients");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(viewButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePatient();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePatient();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new patientview().setVisible(true);
                dispose();
            }
        });
    }

    private void addPatient() {
        Patient patient = new Patient(idField.getText(), nameField.getText(), Integer.parseInt(ageField.getText()),
                phoneField.getText(), emailField.getText(), addressField.getText());
        if (PatientDAO.addPatient(patient)) {
            JOptionPane.showMessageDialog(this, "Patient added successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePatient() {
        String patientId = idField.getText();
        if (PatientDAO.deletePatient(patientId)) {
            JOptionPane.showMessageDialog(this, "Patient removed successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to remove patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePatient() {
        Patient patient = new Patient(idField.getText(), nameField.getText(), Integer.parseInt(ageField.getText()),
                phoneField.getText(), emailField.getText(), addressField.getText());
        if (PatientDAO.updatePatient(patient)) {
            JOptionPane.showMessageDialog(this, "Patient updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new patientdetails().setVisible(true));
    }
}
