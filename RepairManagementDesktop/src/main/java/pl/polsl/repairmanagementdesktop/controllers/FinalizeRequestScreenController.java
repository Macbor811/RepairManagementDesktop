package pl.polsl.repairmanagementdesktop.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.request.RequestService;
import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;

@Scope("prototype")
@Controller
public class FinalizeRequestScreenController {


    private RequestTableRow requestTableRow;

    @Autowired
    private RequestService service;

    @FXML
    private ChoiceBox statusChoiceBox;
    @FXML
    private TextArea resultTextArea;

    @FXML
    public void initialize() {
        statusChoiceBox.setItems(FXCollections.observableArrayList("FIN", "CAN"));
        statusChoiceBox.getSelectionModel().select("FIN");
    }

    @FXML
    private void cancelButtonClicked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    private void finalizeButtonClicked(ActionEvent event) {
        var request = service.findById(requestTableRow.getId());

        service.finalize(requestTableRow.getId(), resultTextArea.getText(), (String) statusChoiceBox.getSelectionModel().getSelectedItem());

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void setRequestTableRow(RequestTableRow requestTableRow) {
        this.requestTableRow = requestTableRow;
    }
}
