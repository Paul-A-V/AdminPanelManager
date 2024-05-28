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

public class SearchTVSeriesDialog extends JDialog implements ActionListener {
    private Connection conn;
    private JTextField searchField;
    private JButton searchButton, updateButton, deleteButton;
    private JTable searchResultTable;
    private DefaultTableModel tableModel;

    public SearchTVSeriesDialog(JFrame parent, Connection conn) {
        super(parent, "Search for TV Series", true);
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

        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Description", "Creator", "Genre","Thumbnail URL"}, 0);
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
        searchVideos();
    } else if (e.getSource() == updateButton) {
        int selectedRow = searchResultTable.getSelectedRow();
        if (selectedRow != -1) {
            int tvSeriesId = (int) searchResultTable.getValueAt(selectedRow, 0);
            UpdateTVSeriesDialog updateDialog = new UpdateTVSeriesDialog((JFrame)getParent(), conn, tvSeriesId); // Pass videoId
            updateDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a tv series to update.");
        }
    } else if (e.getSource() == deleteButton) {
        int selectedRow = searchResultTable.getSelectedRow();
        if (selectedRow != -1) {
            int tvSeriesId = (int) searchResultTable.getValueAt(selectedRow, 0);
            DeleteTVSeriesDialog deleteDialog = new DeleteTVSeriesDialog((JFrame)getParent(), conn, tvSeriesId); // Pass videoId
            deleteDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a video to delete.");
        }
    }
}

    private void searchVideos() {
        String searchQuery = searchField.getText().trim();
        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search query.");
            return;
        }

        try {
            String sql = "SELECT id, title, description, creator, genre, thumbnail_url FROM tv_series WHERE title LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + searchQuery + "%");
            ResultSet resultSet = statement.executeQuery();

            // Clear previous search results
            tableModel.setRowCount(0);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String creator = resultSet.getString("creator");
                String genre = resultSet.getString("genre");
                String thumbnailUrl = resultSet.getString("thumbnail_url");


                Object[] row = {id, title, description, creator, genre, thumbnailUrl};
                tableModel.addRow(row);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to search for tv series.");
        }
    }
}
