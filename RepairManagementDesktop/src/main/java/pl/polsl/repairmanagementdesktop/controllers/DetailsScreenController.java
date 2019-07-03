package pl.polsl.repairmanagementdesktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Scope("prototype")
@Controller
public class DetailsScreenController {

    @FXML
    private Text descriptionText;
    @FXML
    private Text resultText;

    public void setText(String description,String result)
    {
        descriptionText.setText(description);
        resultText.setText(result);
    }

    @FXML
    private void cancelButtonClicked(ActionEvent event){

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }
}
