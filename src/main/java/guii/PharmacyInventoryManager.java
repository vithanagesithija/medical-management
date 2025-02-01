package guii;

import code.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PharmacyInventoryManager extends JFrame {
    private JTextField nameField, quantityField, priceField;
    private JButton addButton, updateButton, removeButton, viewButton;

    public PharmacyInventoryManager() {
        setTitle("Manage Pharmacy Inventory");
        setSize(700, 500); // Set size to 700x500
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set the background color of the window
        getContentPane().setBackground(new Color(153, 153, 153));

        // Header Label
        JLabel headerLabel = new JLabel("Pharmacy Inventory Manager", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0, 0, 0));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Input Fields Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        inputPanel.add(new JLabel("Medicine Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        add(inputPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Horizontal alignment
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");
        viewButton = new JButton("View");

        // Set button styles
        setButtonStyle(addButton, new Color(0, 0, 0));  // Black background
        setButtonStyle(updateButton, new Color(0, 0, 0));
        setButtonStyle(removeButton, new Color(0, 0, 0));
        setButtonStyle(viewButton, new Color(0, 0, 0));

        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners for Buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeItem();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to InventoryView class
                PharmacyInventoryView inventoryView = new PharmacyInventoryView();
                inventoryView.setVisible(true);
                setVisible(false); // Hide the current window
            }
        });
    }

    // Method to set button styles
    private void setButtonStyle(JButton button, Color bgColor) {
        button.setBackground(bgColor); // Black background
        button.setForeground(Color.WHITE); // White text
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 50)); // Set button size
    }

    // Method to Add Item to Inventory
    private void addItem() {
        String name = nameField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();

        if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity = Integer.parseInt(quantityText);
        double price = Double.parseDouble(priceText);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO inventory (name, quantity, price) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, price);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Item Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to Update Item in Inventory
    private void updateItem() {
        String name = nameField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();

        if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity = Integer.parseInt(quantityText);
        double price = Double.parseDouble(priceText);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE inventory SET quantity = ?, price = ? WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, quantity);
            stmt.setDouble(2, price);
            stmt.setString(3, name);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Item Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to Remove Item from Inventory
    private void removeItem() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the medicine name to remove!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM inventory WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Item Removed Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clear Input Fields
    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PharmacyInventoryManager().setVisible(true));
    }
}
