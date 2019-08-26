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
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;

@Scope("prototype")
@Controller
public class DetailsScreenController {


    @FXML
    private TextArea resultTextArea;
    @FXML
    private Label statusLabel;
    @FXML
    private Label endedOnStaticLabel;
    @FXML
    private Label registerDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label itemLabel;
    @FXML
    private Label ownerLabel;
    @FXML
    private Label managerLabel;
    @FXML
    private TextArea descriptionTextArea;
    
    
    public void setRequest(RequestEntity request){
        var data = new RequestTableRow(request);
        
        statusLabel.setText(data.getStatus());
        resultTextArea.setText(request.getResult());
        registerDateLabel.setText(data.getRegisteredDate());
        itemLabel.setText(data.getItem());
        ownerLabel.setText(data.getClient());
        var manager = request.getManager();
        managerLabel.setText(manager.getFirstName() + " " + manager.getLastName());
        descriptionTextArea.setText(request.getDescription());

        if (request.getEndDate() == null){
            endedOnStaticLabel.setVisible(false);
        } else {
            endDateLabel.setText(data.getFinalizedDate());
        }
    }


}
