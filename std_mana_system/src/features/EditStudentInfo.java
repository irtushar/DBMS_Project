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


public class EditStudentInfo {
    public EditStudentInfo() {
        // Create the frame
        JFrame frame = new JFrame("Edit Student Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(null);


        // Add title label
        JLabel titleLabel = new JLabel("Edit Student Info", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(50, 20, 500, 30);
        frame.add(titleLabel);


        // Student ID Label and Text Field
        JLabel idLabel = new JLabel("Student ID:");
        idLabel.setBounds(50, 80, 100, 25);
        frame.add(idLabel);


        JTextField idField = new JTextField();
        idField.setBounds(150, 80, 250, 25);
        frame.add(idField);


        JButton searchButton = new JButton("Search");
        searchButton.setBounds(420, 80, 100, 25);
        frame.add(searchButton);


        // Name Label and Text Field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 120, 100, 25);
        frame.add(nameLabel);


        JTextField nameField = new JTextField();
        nameField.setBounds(150, 120, 250, 25);
        nameField.setEditable(false); // Initially non-editable
        frame.add(nameField);


        // Date of Birth Label and Text Field
        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(50, 160, 100, 25);
        frame.add(dobLabel);


        JTextField dobField = new JTextField();
        dobField.setBounds(150, 160, 250, 25);
        dobField.setEditable(false); // Initially non-editable
        frame.add(dobField);


        // Email Label and Text Field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 200, 100, 25);
        frame.add(emailLabel);


        JTextField emailField = new JTextField();
        emailField.setBounds(150, 200, 250, 25);
        emailField.setEditable(false); // Initially non-editable
        frame.add(emailField);


        // Department Label and Text Field
        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(50, 240, 100, 25);
        frame.add(departmentLabel);


        JTextField departmentField = new JTextField();
        departmentField.setBounds(150, 240, 250, 25);
        departmentField.setEditable(false); // Initially non-editable
        frame.add(departmentField);


        // Mobile Label and Text Field
        JLabel mobileLabel = new JLabel("Mobile:");
        mobileLabel.setBounds(50, 280, 100, 25);
        frame.add(mobileLabel);


        JTextField mobileField = new JTextField();
        mobileField.setBounds(150, 280, 250, 25);
        mobileField.setEditable(false); // Initially non-editable
        frame.add(mobileField);


        // Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 350, 100, 30);
        saveButton.setEnabled(false); // Initially disabled
        frame.add(saveButton);


        // Back to Dashboard Button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBounds(270, 350, 150, 30);
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
                        dobField.setText(resultSet.getString("dob"));
                        emailField.setText(resultSet.getString("email"));
                        departmentField.setText(resultSet.getString("department"));
                        mobileField.setText(resultSet.getString("mobile"));


                        // Enable editing and saving
                        nameField.setEditable(true);
                        dobField.setEditable(true);
                        emailField.setEditable(true);
                        departmentField.setEditable(true);
                        mobileField.setEditable(true);
                        saveButton.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        nameField.setText("");
                        dobField.setText("");
                        emailField.setText("");
                        departmentField.setText("");
                        mobileField.setText("");
                        saveButton.setEnabled(false);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Save Button Action
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = idField.getText().trim();
                String name = nameField.getText();
                String dob = dobField.getText();
                String email = emailField.getText();
                String department = departmentField.getText();
                String mobile = mobileField.getText();


                // Update student details in the database
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "UPDATE students SET name = ?, dob = ?, email = ?, department = ?, mobile = ? WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, dob);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, department);
                    preparedStatement.setString(5, mobile);
                    preparedStatement.setString(6, studentId);


                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Student details updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to update student details!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

