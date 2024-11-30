package features;


import dashboard.Dashboard;
import database.DatabaseConnection;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class RegisterNewStudent {
    public RegisterNewStudent() {
        // Create the frame
        JFrame frame = new JFrame("Register New Student");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);


        // Add title label at the top
        JLabel titleLabel = new JLabel("Register New Student", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(50, 20, 400, 30);
        frame.add(titleLabel);


        // Name Label and Text Field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 80, 100, 25);
        frame.add(nameLabel);


        JTextField nameField = new JTextField();
        nameField.setBounds(150, 80, 250, 25);
        frame.add(nameField);


        // Gender Label and Combo Box
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 120, 100, 25);
        frame.add(genderLabel);


        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        genderComboBox.setBounds(150, 120, 100, 25);
        frame.add(genderComboBox);


        // Date of Birth Label and Text Field
        JLabel dobLabel = new JLabel("Date of Birth (YYYY-MM-DD):");
        dobLabel.setBounds(50, 160, 200, 25);
        frame.add(dobLabel);


        JTextField dobField = new JTextField();
        dobField.setBounds(250, 160, 150, 25);
        frame.add(dobField);


        // Email Label and Text Field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 200, 100, 25);
        frame.add(emailLabel);


        JTextField emailField = new JTextField();
        emailField.setBounds(150, 200, 250, 25);
        frame.add(emailField);


        // Department Label and Text Field
        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(50, 240, 100, 25);
        frame.add(departmentLabel);


        JTextField departmentField = new JTextField();
        departmentField.setBounds(150, 240, 250, 25);
        frame.add(departmentField);


        // Mobile Number Label and Text Field
        JLabel mobileLabel = new JLabel("Mobile Number:");
        mobileLabel.setBounds(50, 280, 100, 25);
        frame.add(mobileLabel);


        JTextField mobileField = new JTextField();
        mobileField.setBounds(150, 280, 250, 25);
        frame.add(mobileField);


        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(150, 350, 100, 30);
        frame.add(submitButton);


        // Back to Dashboard Button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBounds(270, 350, 150, 30);
        frame.add(backButton);


        // Submit Button Action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve input data
                String name = nameField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                String dob = dobField.getText();
                String email = emailField.getText();
                String department = departmentField.getText();
                String mobile = mobileField.getText();


                // Input validation
                if (name.isEmpty() || gender == null || dob.isEmpty() || email.isEmpty() || department.isEmpty() || mobile.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                // Insert data into the database
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "INSERT INTO students (name, gender, dob, email, department, mobile) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, gender);
                    preparedStatement.setString(3, dob);
                    preparedStatement.setString(4, email);
                    preparedStatement.setString(5, department);
                    preparedStatement.setString(6, mobile);


                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Student Registered Successfully!");
                        // Clear the fields
                        nameField.setText("");
                        dobField.setText("");
                        emailField.setText("");
                        departmentField.setText("");
                        mobileField.setText("");
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

