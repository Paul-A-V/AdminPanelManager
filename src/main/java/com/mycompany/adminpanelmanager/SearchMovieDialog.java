/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adminpanelmanager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SearchMovieDialog extends JDialog implements ActionListener {
    private Connection conn;
    private JTextField searchField;
    private JButton searchButton, updateButton, deleteButton;
    private JTable searchResultTable;
    private DefaultTableModel tableModel;

    public SearchMovieDialog(JFrame parent, Connection conn) {
        super(parent, "Search for Featured Videos", true);
        this.conn = conn;
        initializeGUI();
    }

    private void initializeGUI() {
        JPanel searchPanel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Search by Title:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        updateButton = new JButton("Update Selected");
        deleteButton = new JButton("Delete Selected");
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Description", "Release Year", "Director", "Genre", "Rating","Thumbnail URL"}, 0);
        searchResultTable = new JTable(tableModel);
        searchResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(searchResultTable);

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == searchButton) {
        searchMovies();
    } else if (e.getSource() == updateButton) {
        int selectedRow = searchResultTable.getSelectedRow();
        if (selectedRow != -1) {
            int movieId = (int) searchResultTable.getValueAt(selectedRow, 0);
            UpdateMovieDialog updateDialog = new UpdateMovieDialog((JFrame)getParent(), conn, movieId); 
            updateDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a movie to update.");
        }
    } else if (e.getSource() == deleteButton) {
        int selectedRow = searchResultTable.getSelectedRow();
        if (selectedRow != -1) {
            int movieId = (int) searchResultTable.getValueAt(selectedRow, 0);
            DeleteMovieDialog deleteDialog = new DeleteMovieDialog((JFrame)getParent(), conn, movieId); 
            deleteDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a movie to delete.");
        }
    }
}

    private void searchMovies() {
        String searchQuery = searchField.getText().trim();
        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search query.");
            return;
        }

        try {
            String sql = "SELECT id, title, description, release_year, genre, director, rating, thumbnail_url FROM movies WHERE title LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + searchQuery + "%");
            ResultSet resultSet = statement.executeQuery();

            // Clear previous search results
            tableModel.setRowCount(0);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String releaseYear = resultSet.getString("release_year");
                String genre = resultSet.getString("genre");
                String director = resultSet.getString("director");
                String rating = resultSet.getString("rating");
                String thumbnailUrl = resultSet.getString("thumbnail_url");

                Object[] row = {id, title, description, releaseYear, genre, director, rating, thumbnailUrl};
                tableModel.addRow(row);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to search for movies.");
        }
    }
}
