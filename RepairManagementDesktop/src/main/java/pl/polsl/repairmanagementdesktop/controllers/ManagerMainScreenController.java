package pl.polsl.repairmanagementdesktop.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ManagerMainScreenController {

    @FXML
    private Button addClientButton;


    @FXML
    private void addClientButtonClicked(ActionEvent event) throws IOException{
        Parent managerMainScreen = FXMLLoader.load(getClass().getResource("/addClientScreen.fxml"));
        Scene nextScene = new Scene(managerMainScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(true);
        window.show();
    }
}

