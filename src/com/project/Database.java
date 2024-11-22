package com.project;

import javax.swing.*;
import java.awt.*;
import java.sql.*;


/**
 *
 *
 *  @author navfalbek.makhfuzullaev
 *
 */

public class Database {
    static JFrame dataFrame;
    static JPasswordField passwordField;
    static JLabel passwordLabel, checkLabel;
    static JButton passButton;
    static ImageIcon checkIcon;
    static String url = "jdbc:mysql://localhost:3306/"; // your MySQL server url
    static String username = ""; // your MySQL server username
    static String password = ""; // your password
    static Connection connection;

    Database(String currencyCode, String currencyCode2, double amount, double rate) {
        try {
            insertingToDatabase(currencyCode, currencyCode2, amount, rate);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertingToDatabase(String currencyCode, String currencyCode2, double amount, double rate) {
        try{
            dataFrame = new JFrame("Authorization");
            dataFrame.setSize(300,200);
            dataFrame.setLocation(618,580);
            dataFrame.setResizable(false);
            dataFrame.getContentPane().setBackground(Color.DARK_GRAY);

            Image passIcon = Toolkit.getDefaultToolkit().getImage("src/resources/assets/password_icon.png");
            dataFrame.setIconImage(passIcon);

            passwordLabel = new JLabel();
            passwordLabel.setForeground(Color.WHITE);
            passwordLabel.setText("Password");
            passwordLabel.setFont(new Font("", Font.BOLD, 14));
            passwordLabel.setBounds(20, 60, 80, 30);

            checkLabel = new JLabel();
            checkLabel.setFont(new Font("", Font.BOLD, 14));
            checkLabel.setBounds(75, 20, 200, 30);

            passwordField = new JPasswordField();
            passwordField.setBounds(100, 65, 120, 20);

            passButton = new JButton();
            passButton.setBackground(Color.GRAY);
            passButton.setForeground(Color.WHITE);
            passButton.setText("Okay");
            passButton.setFont(new Font("", Font.BOLD, 13));
            passButton.setBounds(80, 120, 80, 20);

            Class.forName("com.mysql.cj.jdbc.Driver");

            passButton.addActionListener(e -> {
                try {
                    String password = new String(passwordField.getPassword());
                    // System.out.println(password);

                    if (!password.equals("")) { // your password
                        checkIcon = new ImageIcon("src/resources/assets/wrong.png");

                        checkLabel.setIcon(checkIcon);
                        checkLabel.setText("Wrong password");
                        checkLabel.setForeground(Color.RED);
                    }
                    else {
                        checkIcon = new ImageIcon("src/resources/assets/approved.png");

                        checkLabel.setIcon(checkIcon);
                        checkLabel.setText("Approved");
                        checkLabel.setForeground(Color.decode("#36c71c"));

                        connection = DriverManager.getConnection(url, username, password);

                        Statement statement = connection.createStatement();

                        ResultSet resultSet = statement.executeQuery("select * from currency");

                        String sql = "INSERT INTO currency"
                                + "(code, code2, amount, rate)"
                                + "Value('" + currencyCode + "', '" + currencyCode2 + "', " + amount + ", " + rate + ")";

                        statement.execute(sql);
                        System.out.println("Data has been inserted");
                        connection.close();
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }

            });


            dataFrame.add(passwordField);
            dataFrame.add(passButton);
            dataFrame.add(passwordLabel);
            dataFrame.add(checkLabel);
            dataFrame.setLayout(null);
            dataFrame.setVisible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
