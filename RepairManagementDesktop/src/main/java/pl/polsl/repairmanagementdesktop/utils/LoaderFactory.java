package pl.polsl.repairmanagementdesktop.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoaderFactory {
    private final ConfigurableApplicationContext springContext;

    public LoaderFactory(ConfigurableApplicationContext springContext) {
        this.springContext = springContext;
    }


    public FXMLLoader load(String fxml) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
        fxmlLoader.setLocation(getClass().getResource(fxml));
        return fxmlLoader;
    }


}
