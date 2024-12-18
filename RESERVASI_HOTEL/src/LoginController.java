import javafx.fxml.FXML;  
import javafx.scene.control.TextField;  
import javafx.scene.control.Label;  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  

public class LoginController {  
    @FXML  
    private TextField emailfield;   
    @FXML  
    private TextField namefield;  
    @FXML  
    private TextField notelpfield;   
    @FXML  
    private TextField passfield;   
    @FXML  
    private Label messageLabel;   

    @FXML  
    private void buttonlogin() {  
        String email = emailfield.getText();  
        String name = namefield.getText();  
        String notelp = notelpfield.getText();  
        String password = passfield.getText();   

        try (Connection connection = new DatabaseHotel().getConnection()) {  
            // Check if the user exists  
            String query = "SELECT * FROM customer WHERE email = ? AND password = ?";  
            PreparedStatement preparedStatement = connection.prepareStatement(query);  
            preparedStatement.setString(1, email);  
            preparedStatement.setString(2, password); // Consider hashing the password  
            ResultSet resultSet = preparedStatement.executeQuery();  

            if (resultSet.next()) {  
                // If email and password are found, welcome the user  
                System.out.println("Welcome, " + resultSet.getString("name") + "!");  
                messageLabel.setText("Welcome, " + resultSet.getString("name") + "!");  
                HotelMainn.showHotel(); // Transition to the hotel management screen  
            } else {  
                // If the user does not exist, insert a new user  
                insertNewUser(email, name, notelp, password);  
            }  

        } catch (SQLException e) {  
            e.printStackTrace(); // Print the stack trace for debugging  
            messageLabel.setText("Login error: " + e.getMessage());  
        }  
    }  

    private void insertNewUser(String email, String name, String notelp, String password) {  
        try (Connection connection = new DatabaseHotel().getConnection()) {  
            // Insert the new user into the database  
            String insertQuery = "INSERT INTO customer (name, email, phone, password) VALUES (?, ?, ?, ?)";  
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);  
            insertStatement.setString(1, name);  
            insertStatement.setString(2, email);  
            insertStatement.setString(3, notelp);  
            insertStatement.setString(4, password); // Consider hashing the password  
            int rowsAffected = insertStatement.executeUpdate();  

            if (rowsAffected > 0) {  
                messageLabel.setText("New user created successfully. Welcome!");  
                System.out.println("New user created: " + email);  
                HotelMainn.showHotel(); // Transition to the hotel management screen  
            } else {  
                messageLabel.setText("Failed to create new user.");  
            }  

        } catch (SQLException e) {  
            e.printStackTrace(); // Print the stack trace for debugging  
            messageLabel.setText("Error creating user: " + e.getMessage());  
        }  
    }  
}