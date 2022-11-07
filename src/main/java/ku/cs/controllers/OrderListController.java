package ku.cs.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.saacsos.FXRouter;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ku.cs.services.DatabaseConnection;
import ku.cs.strategy.CurrentObject;

public class OrderListController {
  private DatabaseConnection dbConnection;
  protected Connection connection;
  @FXML
  ListView<String> orderItemsLV;

  public void initialize(){
    dbConnection = new DatabaseConnection();
    connection = dbConnection.getConnection();
    String username = CurrentObject.getCustomer();
    String query = "SELECT " +
        "SO_ID, SO_DATE, Status " +
        "FROM Sale_Order " +
        "WHERE Username LIKE " + username;

    try {
      Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery(query);
      while (results.next()) {
        String saleOrderId = results.getString("SO_ID");
        String saleOrderDate = results.getString("SO_DATE");
        String saleOrderStatus = results.getString("Status");

        orderItemsLV.getItems().add(
          "id: " + saleOrderId +
          ", ordered at: " + saleOrderDate +
          ", status: " + saleOrderStatus
        );
        System.out.println(saleOrderId);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    for(String s : CurrentObject.getProducts().keySet()){
      orderItemsLV.getItems().add(s.replace("\"",""));
    }
}

  public void handleBackBtn() {
    try {
      FXRouter.goTo("main");
    } catch (IOException e) {
      throw new RuntimeException("Can't go to Main view");
    }
  }

}
