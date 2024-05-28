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


public class DeleteMovieDialog extends JDialog {
    private Connection conn;
    private JComboBox<String> movieComboBox;
        private int movieId;

    public DeleteMovieDialog(JFrame parent, Connection conn, int movieId) {
        super(parent, "Delete Movie", true);
        this.conn = conn;
        this.movieId = movieId;
        initializeGUI();
        populateMovieComboBox();
    }

    private void initializeGUI() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        panel.add(new JLabel("Select Movie:"));
        movieComboBox = new JComboBox<>();
        panel.add(movieComboBox);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteMovie());
        panel.add(deleteButton);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void populateMovieComboBox() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, title FROM movies");

        while (rs.next()) {
            int currentMovieId = rs.getInt("id");
            String title = rs.getString("title");
            movieComboBox.addItem(currentMovieId + ": " + title);
            // Pre-select the correct video in the combo box
            if (currentMovieId == movieId) {
                movieComboBox.setSelectedItem(currentMovieId + ": " + title);
            }
        }

            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteMovie() {
        String selectedMovie = (String) movieComboBox.getSelectedItem();
        int movieId = Integer.parseInt(selectedMovie.split(":")[0]);

        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM movies WHERE id=?");
            pstmt.setInt(1, movieId);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Movie deleted successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No movie found with the selected ID.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete movie. Please try again.");
        }
    }
}