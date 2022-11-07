package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.strategy.CurrentObject;

import java.io.IOException;

public class MainController {

    @FXML
    Label nameLB;

    public void initialize(){
        nameLB.setText("Welcome " + CurrentObject.getCustomer());
    }

    public void handleLogOutBtn(){
        try {
            FXRouter.goTo("home");
        } catch (IOException e) {
            throw new RuntimeException("Can't go to Home view");
        }
    }

    public void handleOrderProductBtn(){
        try {
            FXRouter.goTo("order-product");
        } catch (IOException e) {
            throw new RuntimeException("Can't go to Order product view");
        }
    }

    public void handleMyCartBtn(){
        try {
            FXRouter.goTo("my-cart");
        } catch (IOException e) {
            throw new RuntimeException("Can't go to My Cart view");
        }
    }

    public void handleMyOrderBtn(){
        try {
            FXRouter.goTo("my-order");
        } catch (IOException e) {
            throw new RuntimeException("Can't go to My Cart view");
        }
    }
}
