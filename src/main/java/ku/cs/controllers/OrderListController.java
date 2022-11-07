package ku.cs.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import com.github.saacsos.FXRouter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import ku.cs.services.DatabaseConnection;
import ku.cs.strategy.CurrentObject;

public class OrderListController {
  private DatabaseConnection dbConnection;
  protected Connection connection;
  @FXML
  ListView<String> orderItemsLV;
  @FXML
  Text orderDetailText;

  public void initialize() {
    orderDetailText.setText("");
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
                ", status: " + saleOrderStatus);
        System.out.println(saleOrderId);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    for (String s : CurrentObject.getProducts().keySet()) {
      orderItemsLV.getItems().add(s.replace("\"", ""));
    }

    setListViewListsener();
  }

  public void handleBackBtn() {
    try {
      FXRouter.goTo("main");
    } catch (IOException e) {
      throw new RuntimeException("Can't go to Main view");
    }
  }

  private void handleOnChangeItem(String orderId) {

    String query = "SELECT " +
        "COUNT(P_ID) as pcount " +
        "FROM sale_order_detail " +
        "WHERE SO_ID = " + '"' + orderId + '"';
    System.out.println(query);
    Statement statement;
    try {
      statement = connection.createStatement();
      ResultSet results = statement.executeQuery(query);

      if (results.next()) {
        orderDetailText.setText(
          "จำนวนสินค้าในคำสั่งซื้อ " +
          orderId + " มีทั้งสิ้น " +
          results.getString("pcount"));
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void setListViewListsener() {
    this.orderItemsLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // TODO Auto-generated method stub
        String id = newValue.substring(4, 8);
        handleOnChangeItem(id);
      }
    });
  }

}
