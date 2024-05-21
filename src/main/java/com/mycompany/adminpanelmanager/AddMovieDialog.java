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

public class AddMovieDialog extends JDialog {
    private Connection conn;
    private JTextField titleField, descriptionField, releaseYearField, directorField, genreField, ratingField, thumbnailUrlField;

    public AddMovieDialog(JFrame parent, Connection conn) {
        super(parent, "Add Movie", true);
        this.conn = conn;
        initializeGUI();
    }

    private void initializeGUI() {
        JPanel panel = new JPanel(new GridLayout(8, 2));

        panel.add(new JLabel("Title:"));
        titleField = new JTextField(20);
        panel.add(titleField);

        panel.add(new JLabel("Description:"));
        descriptionField = new JTextField(20);
        panel.add(descriptionField);

        panel.add(new JLabel("Release Year:"));
        releaseYearField = new JTextField(20);
        panel.add(releaseYearField);

        panel.add(new JLabel("Director:"));
        directorField = new JTextField(20);
        panel.add(directorField);

        panel.add(new JLabel("Genre:"));
        genreField = new JTextField(20);
        panel.add(genreField);

        panel.add(new JLabel("Rating:"));
        ratingField = new JTextField(20);
        panel.add(ratingField);

        panel.add(new JLabel("Thumbnail URL:"));
        thumbnailUrlField = new JTextField(20);
        panel.add(thumbnailUrlField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addMovie());
        panel.add(addButton);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void addMovie() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        int releaseYear = Integer.parseInt(releaseYearField.getText());
        String director = directorField.getText();
        String genre = genreField.getText();
        double rating = Double.parseDouble(ratingField.getText());
        String thumbnailUrl = thumbnailUrlField.getText();

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO movies (title, description, release_year, director, genre, rating, thumbnail_url) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setInt(3, releaseYear);
            pstmt.setString(4, director);
            pstmt.setString(5, genre);
            pstmt.setDouble(6, rating);
            pstmt.setString(7, thumbnailUrl);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Movie added successfully!");
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add movie. Please try again.");
        }
    }
}