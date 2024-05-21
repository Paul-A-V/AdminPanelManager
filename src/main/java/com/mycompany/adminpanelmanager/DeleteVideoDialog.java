/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adminpanelmanager;

import java.awt.GridLayout;
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

public class DeleteVideoDialog extends JDialog {
    private Connection conn;
    private JComboBox<String> videoComboBox;
    private int videoId;

    public DeleteVideoDialog(JFrame parent, Connection conn, int videoId) {
        super(parent, "Delete Video", true);
        this.conn = conn;
        this.videoId = videoId;
        initializeGUI();
        populateVideoComboBox();
    }

    private void initializeGUI() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        panel.add(new JLabel("Select Video:"));
        videoComboBox = new JComboBox<>();
        panel.add(videoComboBox);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteVideo());
        panel.add(deleteButton);

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

    private void deleteVideo() {
        String selectedVideo = (String) videoComboBox.getSelectedItem();
        int videoId = Integer.parseInt(selectedVideo.split(":")[0]);

        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM featured_videos WHERE id=?");
            pstmt.setInt(1, videoId);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Video deleted successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No video found with the selected ID.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete video. Please try again.");
        }
    }
}
