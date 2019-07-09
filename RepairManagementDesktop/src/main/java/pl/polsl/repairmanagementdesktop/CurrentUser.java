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
import org.springframework.util.Assert;
import pl.polsl.repairmanagementdesktop.model.employee.EmployeeEntity;
import pl.polsl.repairmanagementdesktop.utils.LoaderFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Stream;

@Component
public class CurrentUser {

    private Integer id;
    private String username;
    private String password;
    private String basicAuth;
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

    private String initBasicAuth(){
        Assert.notNull(username, "Username must not be null");
        Assert.notNull(password, "Password must not be null");
        var  charset = StandardCharsets.ISO_8859_1;

        CharsetEncoder encoder = charset.newEncoder();
        if (!encoder.canEncode(username) || !encoder.canEncode(password)) {
            throw new IllegalArgumentException(
                    "Username or password contains characters that cannot be encoded to " + charset.displayName());
        }

        String credentialsString = username + ":" + password;
        byte[] encodedBytes = Base64.getEncoder().encode(credentialsString.getBytes(charset));
        String encodedCredentials = new String(encodedBytes, charset);
        return  "Basic " + encodedCredentials;
    }

    public String getBasicAuth(){
        if (basicAuth == null){
            basicAuth = initBasicAuth();
            return basicAuth;
        } else {
            return basicAuth;
        }
    }

    public void signOut(Stage window){
        id = null;
        username = null;
        password = null;
        role = null;

        try {
            FXMLLoader loader = fxmlLoaderFactory.load("/fxml/loginScreen.fxml");
            Parent loginScreen = loader.load();

            //Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
