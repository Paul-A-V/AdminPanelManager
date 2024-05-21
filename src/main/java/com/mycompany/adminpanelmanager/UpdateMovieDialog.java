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

/**
 *
 * @author vizit
 */
public class UpdateMovieDialog extends JDialog {
    private Connection conn;
    private JComboBox<String> movieComboBox;
    private JTextField titleField, descriptionField, releaseYearField, directorField, genreField, ratingField, thumbnailUrlField;

    public UpdateMovieDialog(JFrame parent, Connection conn) {
        super(parent, "Update Movie", true);
        this.conn = conn;
        initializeGUI();
        populateMovieComboBox();
    }

    private void initializeGUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Movie:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        movieComboBox = new JComboBox<>();
        panel.add(movieComboBox, gbc);

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
        panel.add(new JLabel("Release Year:"), gbc);

        gbc.gridx = 1;
        releaseYearField = new JTextField(20);
        panel.add(releaseYearField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Director:"), gbc);

        gbc.gridx = 1;
        directorField = new JTextField(20);
        panel.add(directorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Genre:"), gbc);

        gbc.gridx = 1;
        genreField = new JTextField(20);
        panel.add(genreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Rating:"), gbc);

        gbc.gridx = 1;
        ratingField = new JTextField(20);
        panel.add(ratingField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Thumbnail URL:"), gbc);

        gbc.gridx = 1;
        thumbnailUrlField = new JTextField(20);
        panel.add(thumbnailUrlField, gbc);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateMovie());
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(updateButton, gbc);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void populateMovieComboBox() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, title FROM movies");

            while (rs.next()) {
                int movieId = rs.getInt("id");
                String title = rs.getString("title");
                movieComboBox.addItem(movieId + ": " + title);
            }

            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateMovie() {
        String selectedMovie = (String) movieComboBox.getSelectedItem();
        int movieId = Integer.parseInt(selectedMovie.split(":")[0]);
        String title = titleField.getText();
        String description = descriptionField.getText();
        int releaseYear = Integer.parseInt(releaseYearField.getText());
        String director = directorField.getText();
        String genre = genreField.getText();
        double rating = Double.parseDouble(ratingField.getText());
        String thumbnailUrl = thumbnailUrlField.getText();

        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE movies SET title=?, description=?, release_year=?, director=?, genre=?, rating=?, thumbnail_url=? " +
                            "WHERE id=?");
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setInt(3, releaseYear);
            pstmt.setString(4, director);
            pstmt.setString(5, genre);
            pstmt.setDouble(6, rating);
            pstmt.setString(7, thumbnailUrl);
            pstmt.setInt(8, movieId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Movie updated successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No movie found with the selected ID.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update movie. Please try again.");
        }
    }
}
