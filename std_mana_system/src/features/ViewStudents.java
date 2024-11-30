package features;


import dashboard.Dashboard;
import database.DatabaseConnection;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class ViewStudents {
    public ViewStudents() {
        // Create the frame
        JFrame frame = new JFrame("View Students");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(null);


        // Add title label
        JLabel titleLabel = new JLabel("View Students", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(50, 20, 600, 30);
        frame.add(titleLabel);


        // Add a table to display students
        String[] columnNames = {"ID", "Name", "Gender", "DOB", "Email", "Department", "Mobile"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(50, 80, 600, 300);
        frame.add(scrollPane);


        // Footer Label to show total number of students
        JLabel footerLabel = new JLabel("Total Students: 0", SwingConstants.LEFT);
        footerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        footerLabel.setBounds(50, 400, 300, 25);
        frame.add(footerLabel);


        // Back to Dashboard Button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBounds(500, 400, 150, 30);
        frame.add(backButton);


        // Fetch students from the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM students";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);


            int studentCount = 0;
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                String dob = resultSet.getString("dob");
                String email = resultSet.getString("email");
                String department = resultSet.getString("department");
                String mobile = resultSet.getString("mobile");


                tableModel.addRow(new Object[]{id, name, gender, dob, email, department, mobile});
                studentCount++;
            }


            footerLabel.setText("Total Students: " + studentCount);


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


        // Back to Dashboard Action
        backButton.addActionListener(e -> {
            frame.dispose();
            new Dashboard();
        });


        frame.setVisible(true);
    }
}


