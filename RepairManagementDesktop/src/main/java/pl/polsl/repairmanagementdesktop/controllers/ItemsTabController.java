package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.LoaderFactory;

import java.io.IOException;

@Scope("prototype")
@Controller
public class ItemsTabController {


    private final LoaderFactory fxmlLoaderFactory;

    @Autowired
    public ItemsTabController(LoaderFactory fxmlLoaderFactory) {
        this.fxmlLoaderFactory = fxmlLoaderFactory;
    }


    @FXML
    private void addItemButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = fxmlLoaderFactory.load("/fxml/addItemScreen.fxml");

        Parent managerMainScreen = loader.load();

        Scene nextScene = new Scene(managerMainScreen);

        Stage window = new Stage();

        window.setScene(nextScene);
        window.setResizable(false);
        window.show();
    }

}
