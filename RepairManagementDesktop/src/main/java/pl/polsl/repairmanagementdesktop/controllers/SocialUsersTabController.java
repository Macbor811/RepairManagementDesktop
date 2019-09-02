package pl.polsl.repairmanagementdesktop.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import pl.polsl.repairmanagementdesktop.abstr.TabController;
import pl.polsl.repairmanagementdesktop.model.socialuser.SocialUserEntity;
import pl.polsl.repairmanagementdesktop.model.socialuser.SocialUserService;
import pl.polsl.repairmanagementdesktop.model.socialuser.SocialUserTableRow;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;
import pl.polsl.repairmanagementdesktop.utils.TableColumnFactory;
import pl.polsl.repairmanagementdesktop.utils.TextFieldUtils;
import pl.polsl.repairmanagementdesktop.utils.search.TextFieldParamBinding;

import java.io.IOException;
import java.util.Arrays;

@Scope("prototype")
@Controller
public class SocialUsersTabController extends TabController<SocialUserEntity, SocialUserTableRow> {


    @FXML
    private TextField idTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField providerTextField;


    private final SocialUserService socialUserService;
    private final LoaderFactory loaderFactory;

    @Autowired
    public SocialUsersTabController(SocialUserService socialUserService, LoaderFactory loaderFactory) {
        super(socialUserService, SocialUserTableRow::new);
        this.socialUserService = socialUserService;
        this.loaderFactory = loaderFactory;
    }

    @Override
    protected void initTableView() {
        tableView.getColumns().clear();

        TableColumn<SocialUserTableRow, String> idColumn = TableColumnFactory.createColumn("ID", "id");
        TableColumn<SocialUserTableRow, String> emailColumn = TableColumnFactory.createColumn("Email", "email");
        TableColumn<SocialUserTableRow, String> providerColumn = TableColumnFactory.createColumn("Provider", "provider");


        tableView.getColumns().addAll(
                idColumn,
                emailColumn,
                providerColumn
        );

        for (var column : tableView.getColumns()) {
            column.setStyle("-fx-alignment: CENTER;");
        }

    }

    @Override
    protected void initQueryFields() {

        idTextField.setTextFormatter(TextFieldUtils.numericTextFormatter());
        rowsPerPageTextField.setTextFormatter(TextFieldUtils.numericTextFormatter());

        uriSearchQuery.getBindings().addAll(
                Arrays.asList(
                        new TextFieldParamBinding(idTextField, "id"),
                        new TextFieldParamBinding(emailTextField, "email"),
                        new TextFieldParamBinding(providerTextField, "provider")
                )
        );
    }


    @FXML
    private void activateSocialUserButtonClicked(ActionEvent event) throws IOException {
        SocialUserTableRow selection = tableView.getSelectionModel().getSelectedItem();
        if (selection != null) {

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

