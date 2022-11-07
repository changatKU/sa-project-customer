package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ku.cs.services.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController {

    @FXML
    TextField usernameTF, passwordTF, confirmPasswordTF, telephoneTF, nameTF, taxIdTF;

    @FXML
    TextArea addressTA;

    @FXML
    Label statusLB;

    DatabaseConnection dbConnection;
    Connection connection;
    Statement statement;
    ResultSet results;

    public void initialize(){
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
    }
    public void handleBackBtn(){
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException("Can't go to Home view");
        }
    }

    public void handleRegisterBtn(){
        String username = "\"" + usernameTF.getText() + "\"";
        String taxId = "\"" + taxIdTF.getText() + "\"";

        try{
            String query = "SELECT Username, Tax_ID FROM Customer WHERE Username LIKE " + username
                    + "OR Tax_ID LIKE " + taxId ;
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            System.out.println(query);

            int cnt = 0;
            while (results.next())
                cnt++;
            if(cnt > 0){
                statusLB.setText("Tax ID or Username is exit");
            }else{
                String password = "\"" + passwordTF.getText() + "\"";
                String confirmPassword = "\"" + confirmPasswordTF.getText() + "\"";
                String telephone = "\"" + telephoneTF.getText() + "\"";
                String name = "\"" + nameTF.getText() + "\"";
                String address = "\"" + addressTA.getText() + "\"";

                String insert = "INSERT INTO Customer (Username, Password, Tax_ID, C_Name, C_Phone, Address)"
                        + "VALUES(" + username + "," + password + "," + taxId + "," + name + "," + telephone + "," + address + ")";

                try {
                    statement = connection.createStatement();
                    statement.execute(insert);
                    System.out.println(insert);
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    FXRouter.goTo("login");
                } catch (IOException e) {
                    throw new RuntimeException("Can't go to Login view");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
