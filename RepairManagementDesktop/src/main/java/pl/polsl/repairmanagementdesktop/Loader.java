package pl.polsl.repairmanagementdesktop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Loader {
    private final ConfigurableApplicationContext springContext;

    public Loader(ConfigurableApplicationContext springContext) {
        this.springContext = springContext;
    }

    public Parent load(String fxml) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
        fxmlLoader.setLocation(getClass().getResource(fxml));
        return fxmlLoader.load();
    }
}
