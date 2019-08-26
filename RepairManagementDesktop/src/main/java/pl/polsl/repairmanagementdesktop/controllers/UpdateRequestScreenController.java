package pl.polsl.repairmanagementdesktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.model.request.RequestService;
import pl.polsl.repairmanagementdesktop.model.request.RequestStatus;
import pl.polsl.repairmanagementdesktop.model.request.RequestTableRow;
import pl.polsl.repairmanagementdesktop.model.request.RequestUpdateDto;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;


@Scope("prototype")
@Controller
public class UpdateRequestScreenController {


    @FXML
    private TextArea descriptionTextArea;
    private RequestTableRow data;
    private final RequestService service;

    @FXML
    private ChoiceBox<RequestStatus> statusChoiceBox;
    @FXML
    private TextArea resultTextArea;


    @Autowired
    public UpdateRequestScreenController(LoaderFactory fxmlLoaderFactory, RequestService service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
    }

    private void handleStatusChange(Event event){
        final var status = statusChoiceBox.getSelectionModel().getSelectedItem();
        switch (status){
            case OPEN:{
                resultTextArea.setDisable(true);
                break;
            }
            case IN_PROGRESS:{
                resultTextArea.setDisable(true);
                break;
            }
            case FINISHED:
            case CANCELLED: {
                resultTextArea.setDisable(false);
                break;
            }
        }
        resultTextArea.setText(data.getResult());
        descriptionTextArea.setText(data.getDescription());
    }

    private void setCheckBoxItems(RequestStatus... items){
        statusChoiceBox.setItems(FXCollections.checkedObservableList(
                FXCollections.observableArrayList(items),
                RequestStatus.class
        ));
    }

    public void setRequestData(RequestTableRow data){
        this.data = data;
        statusChoiceBox.setOnAction(this::handleStatusChange);
        final var status = RequestStatus.fromString(data.getStatus());
        switch (status){
            case OPEN:{
                setCheckBoxItems(
                        RequestStatus.OPEN
                );
                resultTextArea.setDisable(true);
                break;
            }
            case IN_PROGRESS:{
                setCheckBoxItems(
                        RequestStatus.IN_PROGRESS,
                        RequestStatus.FINISHED,
                        RequestStatus.CANCELLED
                );
                resultTextArea.setDisable(true);
                break;
            }
            case FINISHED:
            case CANCELLED: {
                descriptionTextArea.setDisable(true);
                setCheckBoxItems(status);
                break;
            }
        }
        statusChoiceBox.getSelectionModel().select(status);

        resultTextArea.setText(data.getResult());
        descriptionTextArea.setText(data.getDescription());

    }

    @FXML
    private void cancelButtonClicked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    private void updateButtonClicked(ActionEvent event) {
        var status = statusChoiceBox.getSelectionModel().getSelectedItem();

        var dto = new RequestUpdateDto(
                descriptionTextArea.getText(),
                resultTextArea.getText(),
                statusChoiceBox.getSelectionModel().getSelectedItem().toString()
        );


        service.update(data.getId(), dto);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }



}
