package pl.polsl.repairmanagementdesktop.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import pl.polsl.repairmanagementdesktop.model.socialuser.SocialUserEntity;
import pl.polsl.repairmanagementdesktop.model.socialuser.SocialUserService;
import pl.polsl.repairmanagementdesktop.model.socialuser.SocialUserTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.utils.TextFormatterFactory;
import pl.polsl.repairmanagementdesktop.utils.search.*;
import uk.co.blackpepper.bowman.Page;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Scope("prototype")
@Controller
public class SocialUsersTabController {

    private static final Integer DEFAULT_ROWS_PER_PAGE = 20;

    @FXML
    private Pagination pagination;

    @FXML
    private Integer rowsPerPage = DEFAULT_ROWS_PER_PAGE;
    @FXML
    private TextField rowsPerPageTextField;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField providerTextField;



    private final UriSearchQuery uriSearchQuery = new UriSearchQuery();

    @FXML
    private TableView<SocialUserTableRow> socialUsersTableView;

    private final SocialUserService socialUserService;
    private final LoaderFactory loaderFactory;

    @Autowired
    public SocialUsersTabController(SocialUserService socialUserService, LoaderFactory loaderFactory) {
        this.socialUserService = socialUserService;
        this.loaderFactory = loaderFactory;
    }

    private void initActivityTableView() {
        socialUsersTableView.getColumns().clear();

        TableColumn<SocialUserTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<SocialUserTableRow, String> emailColumn = TableColumnFactory.createColumn("First name", "email");
        TableColumn<SocialUserTableRow, String> providerColumn = TableColumnFactory.createColumn("Last name", "provider");


        socialUsersTableView.getColumns().addAll(
                idColumn,
                emailColumn,
                providerColumn
        );

        for (var column : socialUsersTableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }

    }

    private void initQueryFields() {

        idTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());
        rowsPerPageTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());

        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new TextFieldParamBinding(emailTextField, "email"),
                        new TextFieldParamBinding(providerTextField, "provider")
                )
        );
    }


    private void initPagination() {
        rowsPerPageTextField.setText(DEFAULT_ROWS_PER_PAGE.toString());
        pagination.setPageFactory(this::createPage);
        pagination.setMaxPageIndicatorCount(10);
        pagination.setPageCount(1);

        rowsPerPageTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());
    }

    @FXML
    public void initialize() {
        initActivityTableView();
        initQueryFields();
        initPagination();
    }

    //The parameter and return value are required by pagination control, but not needed in this case.
    private Node createPage(int pageIndex) {
        updateTable();

        return new Pane();
    }

    public void addParamBindings(ParamBinding... bindings){
        for (var binding : bindings){
            uriSearchQuery.getBindings().add(binding);
        }
        uriSearchQuery.update();
    }

    private void updateTable() {
        try{
            Page<SocialUserEntity> page = socialUserService.findAllMatching(uriSearchQuery, pagination.getCurrentPageIndex(), rowsPerPage);
            pagination.setPageCount((int) page.getTotalPages());
            socialUsersTableView.getItems().clear();


            for (SocialUserEntity socialUser: page.getResources()) {
                socialUsersTableView.getItems().add(new SocialUserTableRow(socialUser));
            }
        } catch (ResourceAccessException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Connection error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.show();
        }

    }



    @FXML
    private void showUsersButtonClicked() {
        rowsPerPage = Integer.valueOf(rowsPerPageTextField.getText());
        uriSearchQuery.update();
        updateTable();
    }


    SocialUserTableRow getCurrentSelection(){
        return socialUsersTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void activateSocialUserButtonClicked(ActionEvent event) throws IOException{
        SocialUserTableRow selection = socialUsersTableView.getSelectionModel().getSelectedItem();
        if (selection!= null){

            FXMLLoader loader = loaderFactory.load("/fxml/activateUserScreen.fxml");
            Parent manageActivitiesScreen = loader.load();

            ActivateUserScreenController manageActivitiesScreenController = loader.getController();

            manageActivitiesScreenController.setUser(selection);

            Scene nextScene = new Scene(manageActivitiesScreen);

            Stage window = new Stage();

            window.setScene(nextScene);
            window.setResizable(false);
            window.show();

        }

    }
}

