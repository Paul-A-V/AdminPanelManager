/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adminpanelmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public 
class UpdateTVSeriesDialog extends JDialog {
    private Connection conn;
    private JComboBox<String> tvSeriesComboBox;
    private JTextField titleField, descriptionField, creatorField, genreField, thumbnailUrlField;
    private int tvSeriesId;

    public UpdateTVSeriesDialog(JFrame parent, Connection conn, int tvSeriesId) {
        super(parent, "Update TV Series", true);
        this.conn = conn;
        this.tvSeriesId = tvSeriesId;
        initializeGUI();
        populateTVSeriesComboBox();
    }

    private void initializeGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select TV Series:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        tvSeriesComboBox = new JComboBox<>();
        panel.add(tvSeriesComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        titleField = new JTextField(20);
        panel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        panel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Creator:"), gbc);

        gbc.gridx = 1;
        creatorField = new JTextField(20);
        panel.add(creatorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Genre:"), gbc);

        gbc.gridx = 1;
        genreField = new JTextField(20);
        panel.add(genreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Thumbnail URL:"), gbc);

        gbc.gridx = 1;
        thumbnailUrlField = new JTextField(20);
        panel.add(thumbnailUrlField, gbc);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateTVSeries());
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(updateButton, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void populateTVSeriesComboBox() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, title FROM tv_series");

        while (rs.next()) {
            int currentTVSeriesId = rs.getInt("id");
            String title = rs.getString("title");
            tvSeriesComboBox.addItem(currentTVSeriesId + ": " + title);
            // Pre-select the correct video in the combo box
            if (currentTVSeriesId == tvSeriesId) {
                tvSeriesComboBox.setSelectedItem(currentTVSeriesId + ": " + title);
            }
        }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateTVSeries() {
        String selectedTVSeries = (String) tvSeriesComboBox.getSelectedItem();
        int tvSeriesId = Integer.parseInt(selectedTVSeries.split(":")[0]);
        String title = titleField.getText();
        String description = descriptionField.getText();
        String creator = creatorField.getText();
        String genre = genreField.getText();
        String thumbnailUrl = thumbnailUrlField.getText();

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE tv_series SET title=?, description=?, creator=?, genre=?, thumbnail_url=? " +
                            "WHERE id=?");
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, creator);
            pstmt.setString(4, genre);
            pstmt.setString(5, thumbnailUrl);
            pstmt.setInt(6, tvSeriesId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "TV Series updated successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No TV Series found with the selected ID.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update TV Series. Please try again.");
        }
    }
}
