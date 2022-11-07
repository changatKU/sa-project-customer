package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ku.cs.services.DatabaseConnection;
import ku.cs.strategy.CurrentObject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderProductController {

    @FXML
    Label nameLB;

    @FXML
    TextField widthTF, lengthTF, gramTF, quantityTF;

    @FXML
    Text errorText;

    DatabaseConnection dbConnection;
    Connection connection;
    Statement statement;
    ResultSet results;

    public void initialize(){
        nameLB.setText(CurrentObject.getCustomer());
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
        errorText.setText("");
    }

    public void handleAddToCartBtn(){
        String width = widthTF.getText();
        String length = lengthTF.getText();
        String gram = gramTF.getText();
        String quantity = quantityTF.getText();
        errorText.setText("");

        double widthDouble = Double.parseDouble(width);
        double lengthDouble = Double.parseDouble(length);
        int gramInt = Integer.parseInt(gram);
        int quantityInt = Integer.parseInt(quantity);

        String inputError = "";

        if (widthDouble <= 0) {
            if (!inputError.isEmpty()) {
                inputError += " ";
            }
            inputError += "ความกว้างควรมากกว่ากว่า 0";
        }
        if (quantityInt <= 0) {
            if (!inputError.isEmpty()) {
                inputError += " ";
            }
            inputError += "น้ำหนักควรมากกว่ากว่า 0";
        }
        if (lengthDouble <= 0) {
            if (!inputError.isEmpty()) {
                inputError += " ";
            }
            inputError += "ความยาวควรมากกว่ากว่า 0";
        }
        if (gramInt < 200 || gramInt > 700) {
            if (!inputError.isEmpty()) {
                inputError += " ";
            }
            inputError += "จำนวนแกมควรอยู่ในช่วงที่บอกไว้";
        }

        if (!inputError.isEmpty()) {
            errorText.setText(inputError);
            return;
        }

        widthTF.setText("");
        lengthTF.setText("");
        gramTF.setText("");
        quantityTF.setText("");

        String query = "SELECT P_ID FROM Product WHERE " +
                "Width LIKE " + width + " AND " +
                "Length LIKE " + length + " AND " +
                "Gram LIKE " + gram;
        try {
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            System.out.println(query);

            if(results.next()){
                CurrentObject.addProduct(results.getString("P_ID"), quantityInt);
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
                CurrentObject.addProduct(p_id.replace("\"",""), quantityInt);
            }
            System.out.println(CurrentObject.getProducts().toString());
        } catch (Exception e) {
            errorText.setText("มีบางอย่างผิดพลาด");
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
