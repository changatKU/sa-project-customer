package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import java.io.IOException;

public class HelloController {

    public void handleLoginBtn(){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException("Can't go to Login View");
        }
    }

    public void handleRegisterBtn(){
        try {
            FXRouter.goTo("register");
        } catch (IOException e) {
            throw new RuntimeException("Can't go to Register");
        }
    }
}