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
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set overall background color
        getContentPane().setBackground(new Color(250, 250, 250)); // Light off-white color

        // Create and style the header label
        JLabel headerLabel = new JLabel("Patient Details", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 30));
        headerLabel.setForeground(new Color(0, 102, 204)); // Blue color
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(255, 255, 255)); // White background for the header
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Create the input panel with a light background color
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 15));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        inputPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        idField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        addressField = new JTextField();

        // Add the input fields with labels and enhanced fonts
        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        idLabel.setForeground(new Color(70, 130, 180)); // SteelBlue color

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setForeground(new Color(70, 130, 180)); // SteelBlue color

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        ageLabel.setForeground(new Color(70, 130, 180)); // SteelBlue color

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneLabel.setForeground(new Color(70, 130, 180)); // SteelBlue color

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setForeground(new Color(70, 130, 180)); // SteelBlue color

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        addressLabel.setForeground(new Color(70, 130, 180)); // SteelBlue color

        // Add the labels and input fields to the panel
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

        // Create buttons and set their color and font
        addButton = new JButton("Add Patient");
        removeButton = new JButton("Remove Patient");
        updateButton = new JButton("Update Patient");
        viewButton = new JButton("View Patients");

        // Set button styles
        addButton.setBackground(new Color(70, 130, 180)); // SteelBlue
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));

        removeButton.setBackground(new Color(70, 130, 180));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFont(new Font("Arial", Font.BOLD, 14));

        updateButton.setBackground(new Color(70, 130, 180));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));

        viewButton.setBackground(new Color(70, 130, 180));
        viewButton.setForeground(Color.WHITE);
        viewButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Create a button panel with a subtle background color
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245)); // Light gray background
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(viewButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
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

    // Method to add a patient to the database
    private void addPatient() {
        Patient patient = new Patient(idField.getText(), nameField.getText(), Integer.parseInt(ageField.getText()),
                phoneField.getText(), emailField.getText(), addressField.getText());
        if (PatientDAO.addPatient(patient)) {
            JOptionPane.showMessageDialog(this, "Patient added successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to delete a patient from the database
    private void deletePatient() {
        String patientId = idField.getText();
        if (PatientDAO.deletePatient(patientId)) {
            JOptionPane.showMessageDialog(this, "Patient removed successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to remove patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update patient details
    private void updatePatient() {
        Patient patient = new Patient(idField.getText(), nameField.getText(), Integer.parseInt(ageField.getText()),
                phoneField.getText(), emailField.getText(), addressField.getText());
        if (PatientDAO.updatePatient(patient)) {
            JOptionPane.showMessageDialog(this, "Patient updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update patient.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new patientdetails().setVisible(true));
    }
}
