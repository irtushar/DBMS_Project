package main;


import dashboard.Dashboard;
import database.DatabaseConnection;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class StudentManagementSystem {
    public StudentManagementSystem() {
        // Create the frame
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);


        // Add title label
        JLabel titleLabel = new JLabel("Student Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(50, 20, 300, 30);
        frame.add(titleLabel);


        // Add User ID label and text field
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setBounds(50, 80, 80, 25);
        frame.add(userIdLabel);


        JTextField userIdField = new JTextField();
        userIdField.setBounds(150, 80, 180, 25);
        frame.add(userIdField);


        // Add Password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 120, 80, 25);
        frame.add(passwordLabel);


        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 120, 180, 25);
        frame.add(passwordField);


        // Add Log In button
        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(80, 180, 100, 30);
        frame.add(loginButton);


        // Add Sign Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(200, 180, 100, 30);
        frame.add(signUpButton);


        // Log In Button Action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText().trim();
                String password = new String(passwordField.getPassword());


                if (userId.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both User ID and Password!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                // Validate user credentials from the database
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "SELECT * FROM users WHERE user_id = ? AND password = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, userId);
                    preparedStatement.setString(2, password);


                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(frame, "Login Successful!");
                        frame.dispose();
                        new Dashboard();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid User ID or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Sign Up Button Action
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Sign Up feature is not implemented yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new StudentManagementSystem();
    }
}
