/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 

package com.mycompany.adminpanelmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MovieAdminPanel extends JFrame implements ActionListener {
    private JButton addButton, updateButton, deleteButton;
    private Connection conn;

    public MovieAdminPanel() {
        super("Movie Admin Panel");
        initializeGUI();
        connectToDatabase();
    }

    private void initializeGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        addButton = new JButton("Add Movie");
        updateButton = new JButton("Update Movie");
        deleteButton = new JButton("Delete Movie");

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);

        add(addButton);
        add(updateButton);
        add(deleteButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/videos4free";
            String username = "root";
            String password = "";

            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database");
            System.exit(1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            AddMovieDialog addMovieDialog = new AddMovieDialog(this, conn);
            addMovieDialog.setVisible(true);
        } else if (e.getSource() == updateButton) {
            UpdateMovieDialog updateMovieDialog = new UpdateMovieDialog(this, conn);
            updateMovieDialog.setVisible(true);
        } else if (e.getSource() == deleteButton) {
            DeleteMovieDialog deleteMovieDialog = new DeleteMovieDialog(this, conn);
            deleteMovieDialog.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MovieAdminPanel::new);
    }
}
*/