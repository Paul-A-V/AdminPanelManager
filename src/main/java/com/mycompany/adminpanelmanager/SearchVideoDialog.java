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

public class SearchVideoDialog extends JDialog implements ActionListener {
    private Connection conn;
    private JTextField searchField;
    private JButton searchButton, updateButton, deleteButton;
    private JTable searchResultTable;
    private DefaultTableModel tableModel;

    public SearchVideoDialog(JFrame parent, Connection conn) {
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

        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Description", "Video URL", "Thumbnail URL", "Category"}, 0);
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
            int videoId = (int) searchResultTable.getValueAt(selectedRow, 0);
            UpdateVideoDialog updateDialog = new UpdateVideoDialog((JFrame)getParent(), conn, videoId); // Pass videoId
            updateDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a video to update.");
        }
    } else if (e.getSource() == deleteButton) {
        int selectedRow = searchResultTable.getSelectedRow();
        if (selectedRow != -1) {
            int videoId = (int) searchResultTable.getValueAt(selectedRow, 0);
            DeleteVideoDialog deleteDialog = new DeleteVideoDialog((JFrame)getParent(), conn, videoId); // Pass videoId
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
            String sql = "SELECT id, title, description, video_url, thumbnail_url, category FROM featured_videos WHERE title LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + searchQuery + "%");
            ResultSet resultSet = statement.executeQuery();

            // Clear previous search results
            tableModel.setRowCount(0);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String videoUrl = resultSet.getString("video_url");
                String thumbnailUrl = resultSet.getString("thumbnail_url");
                String category = resultSet.getString("category");

                Object[] row = {id, title, description, videoUrl, thumbnailUrl, category};
                tableModel.addRow(row);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to search for videos.");
        }
    }
}
