package features;


import dashboard.Dashboard;
import database.DatabaseConnection;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DeleteStudent {
    public DeleteStudent() {
        // Create the frame
        JFrame frame = new JFrame("Delete Student");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);


        // Add title label
        JLabel titleLabel = new JLabel("DELETE STUDENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(50, 20, 500, 30);
        frame.add(titleLabel);


        // Enter ID Label and Text Field
        JLabel idLabel = new JLabel("Enter Student ID:");
        idLabel.setBounds(50, 80, 150, 25);
        frame.add(idLabel);


        JTextField idField = new JTextField();
        idField.setBounds(200, 80, 200, 25);
        frame.add(idField);


        JButton searchButton = new JButton("Search");
        searchButton.setBounds(420, 80, 100, 25);
        frame.add(searchButton);


        // Name Label and Text Field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 120, 100, 25);
        frame.add(nameLabel);


        JTextField nameField = new JTextField();
        nameField.setBounds(200, 120, 300, 25);
        nameField.setEditable(false);
        frame.add(nameField);


        // Department Label and Text Field
        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(50, 160, 100, 25);
        frame.add(departmentLabel);


        JTextField departmentField = new JTextField();
        departmentField.setBounds(200, 160, 300, 25);
        departmentField.setEditable(false);
        frame.add(departmentField);


        // Delete Button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(200, 240, 100, 30);
        deleteButton.setEnabled(false); // Initially disabled
        frame.add(deleteButton);


        // Back to Dashboard Button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBounds(320, 240, 150, 30);
        frame.add(backButton);


        // Search Button Action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = idField.getText().trim();


                if (studentId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a Student ID!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                // Fetch student details from the database
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "SELECT * FROM students WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, studentId);


                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        nameField.setText(resultSet.getString("name"));
                        departmentField.setText(resultSet.getString("department"));


                        deleteButton.setEnabled(true); // Enable Delete Button
                    } else {
                        JOptionPane.showMessageDialog(frame, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        nameField.setText("");
                        departmentField.setText("");
                        deleteButton.setEnabled(false); // Disable Delete Button
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Delete Button Action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = idField.getText().trim();


                // Confirm deletion
                int confirmation = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to delete this student?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );


                if (confirmation == JOptionPane.YES_OPTION) {
                    try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "DELETE FROM students WHERE id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, studentId);


                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(frame, "Student deleted successfully!");


                            // Clear fields and disable Delete Button
                            idField.setText("");
                            nameField.setText("");
                            departmentField.setText("");
                            deleteButton.setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to delete student!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        // Back to Dashboard Action
        backButton.addActionListener(e -> {
            frame.dispose();
            new Dashboard();
        });


        frame.setVisible(true);
    }
}
