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
        // Set the title and size of the window
        setTitle("Patient Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color
        getContentPane().setBackground(new Color(153, 153, 153));

        // Create and style the header label
        JLabel headerLabel = new JLabel("Patient Details", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE); // Black text
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0,0,0)); // Light gray background
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Create the input panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        inputPanel.setBackground(new Color(153, 153, 153));

        idField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        addressField = new JTextField();

        JLabel idLabel = new JLabel("ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel addressLabel = new JLabel("Address:");

        JLabel[] labels = {idLabel, nameLabel, ageLabel, phoneLabel, emailLabel, addressLabel};
        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setForeground(Color.BLACK); // Black text
        }

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);

        add(inputPanel, BorderLayout.CENTER);

        // Create buttons and set styles
        addButton = new JButton("Add Patient");
        removeButton = new JButton("Remove Patient");
        updateButton = new JButton("Update Patient");
        viewButton = new JButton("View Patients");

        JButton[] buttons = {addButton, removeButton, updateButton, viewButton};
        for (JButton button : buttons) {
            button.setBackground(new Color(0,0,0)); // Light gray
            button.setForeground(Color.WHITE); // Black text
            button.setFont(new Font("Arial", Font.BOLD, 14));
        }

        // Create button panel and align horizontally
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonPanel.setBackground(new Color(153,153,153));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(viewButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> addPatient());
        removeButton.addActionListener(e -> deletePatient());
        updateButton.addActionListener(e -> updatePatient());
        viewButton.addActionListener(e -> {
            new patientview().setVisible(true);
            dispose();
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
