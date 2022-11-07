package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.services.DatabaseConnection;
import ku.cs.strategy.CurrentObject;
import org.w3c.dom.DOMImplementation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

public class OrderProductController {

    @FXML
    Label nameLB;

    @FXML
    TextField widthTF, lengthTF, gramTF, quantityTF;

    DatabaseConnection dbConnection;
    Connection connection;
    Statement statement;
    ResultSet results;

    public void initialize(){
        nameLB.setText(CurrentObject.getCustomer());
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
    }

    public void handleAddToCartBtn(){
        String width = widthTF.getText();
        String length = lengthTF.getText();
        String gram = gramTF.getText();

        widthTF.setText("");
        lengthTF.setText("");
        gramTF.setText("");

        int quantity = Integer.parseInt(quantityTF.getText());
        String query = "SELECT P_ID FROM Product WHERE " +
                "Width LIKE " + width + " AND " +
                "Length LIKE " + length + " AND " +
                "Gram LIKE " + gram;
        try {
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            System.out.println(query);

            if(results.next()){
                CurrentObject.addProduct(results.getString("P_ID"), quantity);
            }else{
                results = statement.executeQuery("SELECT P_ID FROM Product ORDER BY P_ID DESC");
                String p_id;
                if(!results.next())
                    p_id = "\"0001\"";
                else {
                    p_id = results.getString("P_ID");
                    int id = (Integer.parseInt(p_id)) + 1;
                    p_id = "\"" + String.format("%04d", id) + "\"";
                }

                String insert = "INSERT INTO Product (P_ID, Width, Length, Gram)" +
                        "VALUES (" + p_id + "," + width + "," + length + "," + gram + ")";
                statement.execute(insert);
                CurrentObject.addProduct(p_id.replace("\"",""), quantity);
            }
            System.out.println(CurrentObject.getProducts().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleBackBtn(){
        try {
            FXRouter.goTo("main");
        } catch (IOException e) {
            throw new RuntimeException("Can't go to Main view");
        }
    }
}
