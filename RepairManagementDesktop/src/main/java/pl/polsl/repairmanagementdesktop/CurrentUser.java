package pl.polsl.repairmanagementdesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;

import java.io.IOException;
import java.net.URI;
import java.util.stream.Stream;

@Component
public class CurrentUser {

    private Integer id;
    private String username;
    private String password;
    private AuthenticationManager.AuthorizedRole role;

    public void setData(Integer id, String username, String password, AuthenticationManager.AuthorizedRole role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    private final LoaderFactory fxmlLoaderFactory;

    @Autowired
    public CurrentUser(LoaderFactory fxmlLoaderFactory) {
        this.fxmlLoaderFactory = fxmlLoaderFactory;
    }


    public Integer getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public AuthenticationManager.AuthorizedRole getRole() {
        return role;
    }

    public void signOut(ActionEvent event){
        id = null;
        username = null;
        password = null;
        role = null;

        try {
            FXMLLoader loader = fxmlLoaderFactory.load("/fxml/loginScreen.fxml");
            Parent loginScreen = loader.load();

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene nextScene = new Scene(loginScreen);

            window.setScene(nextScene);
            window.setResizable(true);
            window.centerOnScreen();
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
