package pl.polsl.repairmanagementdesktop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import pl.polsl.repairmanagementdesktop.AuthenticationManager;

import javafx.event.ActionEvent;
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


    private final RestTemplate client;// = new RestClient();
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginScreenController(RestTemplate client, AuthenticationManager authenticationManager) {
        this.client = client;
        this.authenticationManager = authenticationManager;
    }

    @FXML
    private void loginButtonClicked(ActionEvent event) throws IOException {

//        List<AddressDTO> addresses = Arrays.asList(client.getForObject("/address", AddressDTO[].class));
//        label.setText(addresses.get(0).toString());

        switch (authenticationManager.authorizeForRole(usernameField.getText(), passwordField.getText())){
            case FAILED:{
                messageLabel.setText("Login failed. Wrong username or password.");
                break;
            }
            case WORKER:{
                break;
            }
            case MANAGER:{
                Parent managerMainScreen = FXMLLoader.load(getClass().getResource("/managerMainScreen.fxml"));
                Scene nextScene = new Scene(managerMainScreen);

                Stage window = (Stage) ((Node)event.getSource() ).getScene().getWindow();

                window.setScene(nextScene);
                window.show();
                break;
            }

        }


    }


}
