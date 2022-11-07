package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.services.DatabaseConnection;
import ku.cs.strategy.CurrentObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    @FXML
    Label statusLB;

    @FXML
    TextField usernameTF, passwordTF;

    DatabaseConnection dbConnection;
    Connection connection;
    Statement statement;
    ResultSet results;

    public void initialize(){
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
    }
    public void handleLoginBtn(){
        String username = "\"" + usernameTF.getText() + "\"";
        String password = "\"" + passwordTF.getText() + "\"";

        String query = "SELECT Username FROM Customer WHERE Username LIKE "  + username + " AND Password LIKE " + password;
        System.out.println(query);

        try{
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            if(results.next()) {
                statusLB.setText("Welcome " + results.getString("Username"));
                CurrentObject.setCustomer(username);
                FXRouter.goTo("main");
            }
            else
                statusLB.setText("Username or Password incorrect");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void handleBackBtn(){
        try {
            FXRouter.goTo("home");
            CurrentObject.setCustomer(null);
        } catch (IOException e) {
            throw new RuntimeException("Can't go to Home view");
        }
    }

}
