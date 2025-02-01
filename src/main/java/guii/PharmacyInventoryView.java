package guii;

import code.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PharmacyInventoryView extends JFrame {
    private JTable inventoryTable;
    private JScrollPane scrollPane;
    private JButton backButton;
    private DefaultTableModel tableModel;

    public PharmacyInventoryView() {
        setTitle("View Inventory");
        setSize(700, 500);  // Set size to 700x500
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(153, 153, 153)); // Set background color to (153, 153, 153)

        // Header Label
        JLabel headerLabel = new JLabel("Pharmacy Inventory", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 22));
        headerLabel.setForeground(new Color(255, 255, 255));  // Set header text color to black
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0, 0, 0));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);

        // Table to display inventory data
        String[] columnNames = {"ID", "Name", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        inventoryTable = new JTable(tableModel);
        inventoryTable.setFont(new Font("Arial", Font.PLAIN, 14));
        inventoryTable.setRowHeight(25);
        inventoryTable.setSelectionBackground(new Color(0, 0, 0));
        inventoryTable.setSelectionForeground(Color.WHITE);
        inventoryTable.setShowGrid(true);
        inventoryTable.setGridColor(Color.LIGHT_GRAY);

        // Make the table scrollable
        scrollPane = new JScrollPane(inventoryTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Align buttons horizontally
        buttonPanel.setBackground(new Color(153, 153, 153));  // Set background color for the button panel

        // Back Button
        backButton = new JButton("Back");
        backButton.setBackground(new Color(0, 0, 0));  // Set button background color
        backButton.setForeground(Color.WHITE);  // Set button text color to white
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listener for Back Button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate back to PharmacyInventoryManager
                PharmacyInventoryManager manager = new PharmacyInventoryManager();
                manager.setVisible(true);
                setVisible(false); // Hide the current window
            }
        });

        // Load Inventory Data
        loadInventory();
    }

    // Load Inventory Data from Database
    private void loadInventory() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM inventory";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Clear any existing data in the table
            tableModel.setRowCount(0);

            // Add rows to the table
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                };
                tableModel.addRow(row);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PharmacyInventoryView().setVisible(true));
    }
}
