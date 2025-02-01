package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.*;


public class PatientManagementUI extends JFrame {
    private JTextField idField, nameField, ageField, phoneField, emailField, addressField;
    private JButton addButton, updateButton, removeButton,backButton;

    public PatientManagementUI() {
        setTitle("Patient Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 5, 5));

        // Labels and Text Fields
        add(new JLabel("ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Age:"));
        ageField = new JTextField();
        add(ageField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        // Buttons
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");

        add(addButton);
        add(updateButton);
        add(removeButton);

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

        setVisible(true);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
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
        } else {
            JOptionPane.showMessageDialog(this, "Error removing patient!");
        }
    }

    public static void main(String[] args) {
        new PatientManagementUI();
    }
}
