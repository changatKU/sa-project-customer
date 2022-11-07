package ku.cs;

import com.github.saacsos.FXRouter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "SA PROJECT", 1200, 700);
        configRoute();
        FXRouter.goTo("home");
    }

    public void configRoute(){
        String packageStr = "ku/cs/";
        FXRouter.when("home", packageStr + "hello-view.fxml");
        FXRouter.when("login", packageStr + "login-view.fxml");
        FXRouter.when("register", packageStr + "register-view.fxml");
        FXRouter.when("main", packageStr + "main-view.fxml");
        FXRouter.when("order-product", packageStr + "order-product-view.fxml");
        FXRouter.when("my-cart", packageStr + "my-cart-view.fxml");
        FXRouter.when("my-order", packageStr + "ordered-items-view.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}