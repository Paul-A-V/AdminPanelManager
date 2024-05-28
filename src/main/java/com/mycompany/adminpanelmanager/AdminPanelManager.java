package com.mycompany.adminpanelmanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//main class
public class AdminPanelManager extends JFrame implements ActionListener {
    private JButton addVideoButton, updateVideoButton, deleteVideoButton,
                    addMovieButton, updateMovieButton, deleteMovieButton,
                    addTVSeriesButton, updateTVSeriesButton, deleteTVSeriesButton,
                    searchVideoButton, searchMovieButton, searchTVSeriesButton;
    private Connection conn;
    private int videoId;
    private int movieId;
    private int tvSeriesId;

    public AdminPanelManager() {
        super("Admin Panel Manager");
        initializeGUI();
        connectToDatabase();
    }

    private void initializeGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 4));

        addVideoButton = new JButton("Add Video");
        updateVideoButton = new JButton("Update Video");
        deleteVideoButton = new JButton("Delete Video");
        addMovieButton = new JButton("Add Movie");
        updateMovieButton = new JButton("Update Movie");
        deleteMovieButton = new JButton("Delete Movie");
        addTVSeriesButton = new JButton("Add TV Series");
        updateTVSeriesButton = new JButton("Update TV Series");
        deleteTVSeriesButton = new JButton("Delete TV Series");
        searchVideoButton = new JButton("Search Video");
        searchMovieButton = new JButton("Search Movie");
        searchTVSeriesButton = new JButton("Search TV Series");

        addVideoButton.addActionListener(this);
        updateVideoButton.addActionListener(this);
        deleteVideoButton.addActionListener(this);
        addMovieButton.addActionListener(this);
        updateMovieButton.addActionListener(this);
        deleteMovieButton.addActionListener(this);
        addTVSeriesButton.addActionListener(this);
        updateTVSeriesButton.addActionListener(this);
        deleteTVSeriesButton.addActionListener(this);
        searchVideoButton.addActionListener(this);
        searchMovieButton.addActionListener(this);
        searchTVSeriesButton.addActionListener(this);

        add(addVideoButton);
        add(updateVideoButton);
        add(deleteVideoButton);
        add(searchVideoButton);
        add(addMovieButton);
        add(updateMovieButton);
        add(deleteMovieButton);
        add(searchMovieButton);
        add(addTVSeriesButton);
        add(updateTVSeriesButton);
        add(deleteTVSeriesButton);
        add(searchTVSeriesButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/videos4free";
            String username = "root";
            String password = "";
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database");
            System.exit(1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addVideoButton) {
            AddVideoDialog addVideoDialog = new AddVideoDialog(this, conn);
            addVideoDialog.setVisible(true);
        } else if (e.getSource() == updateVideoButton) {
            UpdateVideoDialog updateVideoDialog = new UpdateVideoDialog(this, conn, videoId);
            updateVideoDialog.setVisible(true);
        } else if (e.getSource() == deleteVideoButton) {
            DeleteVideoDialog deleteVideoDialog = new DeleteVideoDialog(this, conn, videoId);
            deleteVideoDialog.setVisible(true);
        } else if (e.getSource() == addMovieButton) {
            AddMovieDialog addMovieDialog = new AddMovieDialog(this, conn);
            addMovieDialog.setVisible(true);
        } else if (e.getSource() == updateMovieButton) {
            UpdateMovieDialog updateMovieDialog = new UpdateMovieDialog(this, conn, movieId);
            updateMovieDialog.setVisible(true);
        } else if (e.getSource() == deleteMovieButton) {
            DeleteMovieDialog deleteMovieDialog = new DeleteMovieDialog(this, conn, movieId);
            deleteMovieDialog.setVisible(true);
        } else if (e.getSource() == addTVSeriesButton) {
            AddTVSeriesDialog addTVSeriesDialog = new AddTVSeriesDialog(this, conn);
            addTVSeriesDialog.setVisible(true);
        } else if (e.getSource() == updateTVSeriesButton) {
            UpdateTVSeriesDialog updateTVSeriesDialog = new UpdateTVSeriesDialog(this, conn, tvSeriesId);
            updateTVSeriesDialog.setVisible(true);
        } else if (e.getSource() == deleteTVSeriesButton) {
            DeleteTVSeriesDialog deleteTVSeriesDialog = new DeleteTVSeriesDialog(this, conn, tvSeriesId);
            deleteTVSeriesDialog.setVisible(true);
        } else if (e.getSource() == searchVideoButton) {
            SearchVideoDialog searchVideoDialog = new SearchVideoDialog(this, conn);
            searchVideoDialog.setVisible(true);
        } else if (e.getSource() == searchMovieButton) {
            SearchMovieDialog searchMovieDialog = new SearchMovieDialog(this, conn);
            searchMovieDialog.setVisible(true);
        } else if (e.getSource() == searchTVSeriesButton) {
            SearchTVSeriesDialog searchTVSeriesDialog = new SearchTVSeriesDialog(this, conn);
            searchTVSeriesDialog.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminPanelManager::new);
    }
}
