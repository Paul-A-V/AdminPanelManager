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

public class UpdateVideoDialog extends JDialog {
    private Connection conn;
    private JComboBox<String> videoComboBox;
    private JTextField titleField, descriptionField, videoUrlField, thumbnailUrlField, categoryField;
    private int videoId;

    public UpdateVideoDialog(JFrame parent, Connection conn, int videoId) {
        super(parent, "Update Video", true);
        this.conn = conn;
        this.videoId = videoId;
        initializeGUI();
        populateVideoComboBox();
    }

    private void initializeGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Video:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        videoComboBox = new JComboBox<>();
        panel.add(videoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("New Title:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        titleField = new JTextField(20);
        panel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("New Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        descriptionField = new JTextField(20);
        panel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("New Video URL:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        videoUrlField = new JTextField(20);
        panel.add(videoUrlField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("New Thumbnail URL:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        thumbnailUrlField = new JTextField(20);
        panel.add(thumbnailUrlField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("New Category:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        categoryField = new JTextField(20);
        panel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateVideo());
        panel.add(updateButton, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

private void populateVideoComboBox() {
    try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, title FROM featured_videos");

        while (rs.next()) {
            int currentVideoId = rs.getInt("id");
            String title = rs.getString("title");
            videoComboBox.addItem(currentVideoId + ": " + title);
            // Pre-select the correct video in the combo box
            if (currentVideoId == videoId) {
                videoComboBox.setSelectedItem(currentVideoId + ": " + title);
            }
        }

        rs.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

private void updateVideo() {
    String selectedVideo = (String) videoComboBox.getSelectedItem();
    int videoId = Integer.parseInt(selectedVideo.split(":")[0]);
    String title = titleField.getText();
    String description = descriptionField.getText();
    String videoUrl = videoUrlField.getText();
    String thumbnailUrl = thumbnailUrlField.getText();
    String category = categoryField.getText();

    try {
        StringBuilder sql = new StringBuilder("UPDATE featured_videos SET ");
        if (!title.isEmpty()) {
            sql.append("title=?, ");
        }
        if (!description.isEmpty()) {
            sql.append("description=?, ");
        }
        if (!videoUrl.isEmpty()) {
            sql.append("video_url=?, ");
        }
        if (!thumbnailUrl.isEmpty()) {
            sql.append("thumbnail_url=?, ");
        }
        if (!category.isEmpty()) {
            sql.append("category=?, ");
        }
        // Remove the last comma and space
        sql.delete(sql.length() - 2, sql.length());
        sql.append(" WHERE id=?");

        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        int parameterIndex = 1;
        if (!title.isEmpty()) {
            pstmt.setString(parameterIndex++, title);
        }
        if (!description.isEmpty()) {
            pstmt.setString(parameterIndex++, description);
        }
        if (!videoUrl.isEmpty()) {
            pstmt.setString(parameterIndex++, videoUrl);
        }
        if (!thumbnailUrl.isEmpty()) {
            pstmt.setString(parameterIndex++, thumbnailUrl);
        }
        if (!category.isEmpty()) {
            pstmt.setString(parameterIndex++, category);
        }
        pstmt.setInt(parameterIndex, videoId);

        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Video updated successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No video found with the selected ID.");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to update video. Please try again.");
    }
}

}