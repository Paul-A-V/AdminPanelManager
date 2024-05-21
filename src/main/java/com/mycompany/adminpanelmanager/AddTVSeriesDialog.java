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
public class AddTVSeriesDialog extends JDialog {
    private Connection conn;
    private JTextField titleField, descriptionField, creatorField, genreField, thumbnailUrlField;

    public AddTVSeriesDialog(JFrame parent, Connection conn) {
        super(parent, "Add TV Series", true);
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

        panel.add(new JLabel("Creator:"));
        creatorField = new JTextField(20);
        panel.add(creatorField);

        panel.add(new JLabel("Genre:"));
        genreField = new JTextField(20);
        panel.add(genreField);

        panel.add(new JLabel("Thumbnail URL:"));
        thumbnailUrlField = new JTextField(20);
        panel.add(thumbnailUrlField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addTVSeries());
        panel.add(addButton);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void addTVSeries() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String creator = creatorField.getText();
        String genre = genreField.getText();
        String thumbnailUrl = thumbnailUrlField.getText();

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO tv_series (title, description, creator, genre, thumbnail_url) " +
                            "VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, creator);
            pstmt.setString(4, genre);
            pstmt.setString(5, thumbnailUrl);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "TV Series added successfully!");
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add TV Series. Please try again.");
        }
    }
}