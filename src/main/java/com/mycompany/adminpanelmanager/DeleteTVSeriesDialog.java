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


public class DeleteTVSeriesDialog extends JDialog {
    private Connection conn;
    private JComboBox<String> tvSeriesComboBox;
    private int tvSeriesId;

    public DeleteTVSeriesDialog(JFrame parent, Connection conn, int tvSeriesId) {
        super(parent, "Delete TV Series", true);
        this.conn = conn;
        this.tvSeriesId = tvSeriesId;
        initializeGUI();
        populateTVSeriesComboBox();
    }

    private void initializeGUI() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        panel.add(new JLabel("Select TV Series:"));
        tvSeriesComboBox = new JComboBox<>();
        panel.add(tvSeriesComboBox);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteTVSeries());
        panel.add(deleteButton);

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

    private void deleteTVSeries() {
        String selectedTVSeries = (String) tvSeriesComboBox.getSelectedItem();
        int tvSeriesId = Integer.parseInt(selectedTVSeries.split(":")[0]);

        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM tv_series WHERE id=?");
            pstmt.setInt(1, tvSeriesId);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "TV Series deleted successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No TV Series found with the selected ID.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete TV Series. Please try again.");
        }
    }
}
