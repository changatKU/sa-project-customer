package ku.cs.models;

import ku.cs.services.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Product {

    public static DatabaseConnection dbConnection = new DatabaseConnection();
    public static Connection connection = dbConnection.getConnection();

    public static double calculatePrice(String p_id, int quantity){
        double price = 0;
        int gram;
        String query_gram = "SELECT Gram FROM Product WHERE P_ID LIKE "  + "\"" + p_id + "\"";

        try {
            Statement statement = connection.createStatement();
            System.out.println(query_gram);
            ResultSet results = statement.executeQuery(query_gram);
            if(results.next()){
                gram = results.getInt("Gram") / 100 * 100;
                String query_price = "SELECT P_Price FROM Product_Price WHERE P_Gram LIKE " + gram;
                System.out.println(query_price);
                results = statement.executeQuery(query_price);
                results.next();
                price = results.getInt("P_Price") * quantity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return price;
    }
}
