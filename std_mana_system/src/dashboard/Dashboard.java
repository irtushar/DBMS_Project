package dashboard;


import features.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class Dashboard {
    public Dashboard() {
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(null);


        JLabel welcomeLabel = new JLabel("Welcome to our System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(100, 50, 400, 50);
        frame.add(welcomeLabel);


        JPanel sidePanel = new JPanel();
        sidePanel.setBounds(20, 150, 200, 250);
        sidePanel.setLayout(new GridLayout(6, 1, 10, 10));
        frame.add(sidePanel);


        JButton registerStudentButton = new JButton("Register New Student");
        JButton editStudentInfoButton = new JButton("Edit Student Info");
        JButton viewAllStudentsButton = new JButton("View All Students");
        JButton searchStudentButton = new JButton("Search Student");
        JButton deleteStudentButton = new JButton("Delete Student");
        JButton logoutButton = new JButton("Logout");


        sidePanel.add(registerStudentButton);
        sidePanel.add(editStudentInfoButton);
        sidePanel.add(viewAllStudentsButton);
        sidePanel.add(searchStudentButton);
        sidePanel.add(deleteStudentButton);
        sidePanel.add(logoutButton);


        registerStudentButton.addActionListener(e -> {
            frame.dispose();
            new RegisterNewStudent();
        });


        editStudentInfoButton.addActionListener(e -> {
            frame.dispose();
            new EditStudentInfo();
        });


        viewAllStudentsButton.addActionListener(e -> {
            frame.dispose();
            new ViewStudents();
        });


        searchStudentButton.addActionListener(e -> {
            frame.dispose();
            new SearchStudent();
        });


        deleteStudentButton.addActionListener(e -> {
            frame.dispose();
            new DeleteStudent();
        });


        logoutButton.addActionListener(e -> System.exit(0));


        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new Dashboard();
    }
}
