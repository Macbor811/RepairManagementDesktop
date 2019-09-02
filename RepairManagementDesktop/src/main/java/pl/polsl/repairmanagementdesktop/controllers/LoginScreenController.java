package pl.polsl.repairmanagementdesktop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.utils.auth.AuthenticationManager;

import javafx.event.ActionEvent;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;

import java.io.IOException;

@Controller
public class LoginScreenController {

    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    private final AuthenticationManager authenticationManager;
    private final LoaderFactory fxmlLoaderFactory;



    @Autowired
    public LoginScreenController(AuthenticationManager authenticationManager, LoaderFactory fxmlLoaderFactory) {
        this.authenticationManager = authenticationManager;
        this.fxmlLoaderFactory = fxmlLoaderFactory;
    }


    private void loadMainScreen(String fxml, Stage window) throws IOException {
        FXMLLoader loader = fxmlLoaderFactory.load(fxml);
        Parent workerMainScreen = loader.load();
        Scene nextScene = new Scene(workerMainScreen);

        window.setScene(nextScene);
        window.setResizable(true);
        window.centerOnScreen();
        window.show();
    }

    private void handleLogin() throws IOException {
        Stage window = (Stage) loginButton.getScene().getWindow();

        try {
            switch (authenticationManager.authenticate(usernameField.getText(), passwordField.getText())){
                case WORKER:{
                    loadMainScreen("/fxml/workerMainScreen.fxml", window);
                    break;
                }
                case ADMIN:{
                    loadMainScreen("/fxml/adminMainScreen.fxml", window);
                    break;
                }
                case MANAGER:{
                    loadMainScreen("/fxml/managerMainScreen.fxml", window);
                    break;
                }

            }
        } catch (AuthenticationManager.LoginException e){
            messageLabel.setText(e.getMessage());
        }


    }


    @FXML
    private void loginButtonClicked(ActionEvent event) throws IOException {
        handleLogin();
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER){
            handleLogin();
        }
    }
}
