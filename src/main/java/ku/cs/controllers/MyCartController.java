package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ku.cs.models.Product;
import ku.cs.services.DatabaseConnection;
import ku.cs.strategy.CurrentObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyCartController {

    DatabaseConnection dbConnection;
    Connection connection;
    Statement statement;
    ResultSet results;
    @FXML
    ListView<String> myCartLV;

    public void initialize(){
        dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();

        for(String s : CurrentObject.getProducts().keySet()){
            myCartLV.getItems().add(s.replace("\"",""));
        }
    }

    public void handleOrderProductBtn(){
        try {
            FXRouter.goTo("order-product");
        } catch (IOException e) {
            throw new RuntimeException("Can't go to Order product view");
        }
    }

    public void handleBackBtn(){
        try {
            FXRouter.goTo("main");
        } catch (IOException e) {
            throw new RuntimeException("Can't go to Main view");
        }
    }

    public void handleConfirmBtn(){

        try {
            String query = "SELECT SO_ID FROM Sale_Order ORDER BY SO_ID DESC";
            statement = connection.createStatement();
            results = statement.executeQuery(query);
            System.out.println(query);

            String so_id;
            if(!results.next())
                so_id = "\"0001\"";
            else{
                so_id = results.getString("SO_ID");
                int id = (Integer.parseInt(so_id)) + 1;
                so_id = "\"" + String.format("%04d", id) + "\"";
            }
            String username = CurrentObject.getCustomer();

            LocalDate date = LocalDate.now();
            String time = "\"" + date.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\"";


            String insert_to_so =
                    "INSERT INTO Sale_Order (SO_ID, SO_Date, Username)" +
                            "VALUES (" + so_id + "," + time + "," + username + ")";
            System.out.println(insert_to_so);

            statement.execute(insert_to_so);

            if(CurrentObject.getProducts().size() > 0){
                for(String s : CurrentObject.getProducts().keySet()){
                    String insert_sod = "INSERT INTO Sale_Order_Detail(SO_ID, P_ID, Price, QTY)" +
                            "VALUES(" + so_id + "," + "\"" + s +"\"" + "," +
                            Product.calculatePrice(s, CurrentObject.getProducts().get(s))
                            + "," + CurrentObject.getProducts().get(s) + ")";
                    System.out.println(insert_sod);
                    statement.execute(insert_sod);

                }
                CurrentObject.clearProduct();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        myCartLV.getItems().clear();
        myCartLV.refresh();
    }
}
