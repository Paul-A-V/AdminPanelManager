/*package com.mycompany.adminpanelmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TVSeriesAdminPanel extends JFrame implements ActionListener {
    // Declare buttons for add, update, and delete TV series actions
    private JButton addButton, updateButton, deleteButton;
    // Declare database connection object
    private Connection conn;

    // Constructor
    public TVSeriesAdminPanel() {
        super("TV Series Admin Panel"); // Set the title of the window
        initializeGUI(); // Initialize the graphical user interface
        connectToDatabase(); // Establish a connection to the database
    }

    // Method to initialize the GUI
    private void initializeGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
        setLayout(new FlowLayout()); // Set the layout of the frame to FlowLayout

        // Initialize buttons with their respective labels
        addButton = new JButton("Add TV Series");
        updateButton = new JButton("Update TV Series");
        deleteButton = new JButton("Delete TV Series");

        // Register action listeners for each button
        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);

        // Add buttons to the frame
        add(addButton);
        add(updateButton);
        add(deleteButton);

        pack(); // Pack the components within the window
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true); // Make the window visible
    }

    // Method to establish a connection to the database
    private void connectToDatabase() {
        try {
            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/videos4free";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(url, username, password); // Establish the connection
        } catch (SQLException e) {
            e.printStackTrace(); // Print the stack trace for debugging
            JOptionPane.showMessageDialog(null, "Failed to connect to the database"); // Show an error message
            System.exit(1); // Exit the application with an error status
        }
    }

    // Override the actionPerformed method to handle button click events
    @Override
    public void actionPerformed(ActionEvent e) {
        // Open the corresponding dialog based on which button was clicked
        if (e.getSource() == addButton) {
            // Open a new window or dialog for adding a TV series
            AddTVSeriesDialog addTVSeriesDialog = new AddTVSeriesDialog(this, conn);
            addTVSeriesDialog.setVisible(true);
        } else if (e.getSource() == updateButton) {
            // Open a new window or dialog for updating a TV series
            UpdateTVSeriesDialog updateTVSeriesDialog = new UpdateTVSeriesDialog(this, conn);
            updateTVSeriesDialog.setVisible(true);
        } else if (e.getSource() == deleteButton) {
            // Open a new window or dialog for deleting a TV series
            DeleteTVSeriesDialog deleteTVSeriesDialog = new DeleteTVSeriesDialog(this, conn);
            deleteTVSeriesDialog.setVisible(true);
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TVSeriesAdminPanel::new); // Create and show the GUI on the Event Dispatch Thread
    }
}
*/