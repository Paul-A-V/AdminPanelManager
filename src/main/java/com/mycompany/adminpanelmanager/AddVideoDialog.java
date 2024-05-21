/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adminpanelmanager;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author vizit
 */
public class AddVideoDialog extends JDialog {
    private Connection conn;
    private JTextField titleField, descriptionField, videoUrlField, thumbnailUrlField, categoryField;

    public AddVideoDialog(JFrame parent, Connection conn) {
        super(parent, "Add Video", true);
        this.conn = conn;
        initializeGUI();
    }

    private void initializeGUI() {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        
        panel.add(new JLabel("Title:"));
        titleField = new JTextField(20);
        panel.add(titleField);

        panel.add(new JLabel("Description:"));
        descriptionField = new JTextField(20);
        panel.add(descriptionField);

        panel.add(new JLabel("Video URL:"));
        videoUrlField = new JTextField(20);
        panel.add(videoUrlField);

        panel.add(new JLabel("Thumbnail URL:"));
        thumbnailUrlField = new JTextField(20);
        panel.add(thumbnailUrlField);

        panel.add(new JLabel("Category:"));
        categoryField = new JTextField(20);
        panel.add(categoryField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addVideo());
        panel.add(addButton);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void addVideo() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String videoUrl = videoUrlField.getText();
        String thumbnailUrl = thumbnailUrlField.getText();
        String category = categoryField.getText();

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO featured_videos (title, description, video_url, thumbnail_url, category, is_featured) " +
                            "VALUES (?, ?, ?, ?, ?, 1)");
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, videoUrl);
            pstmt.setString(4, thumbnailUrl);
            pstmt.setString(5, category);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Video added successfully!");
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add video. Please try again.");
        }
    }
}