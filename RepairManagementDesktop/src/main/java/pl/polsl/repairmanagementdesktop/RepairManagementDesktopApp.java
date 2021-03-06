package pl.polsl.repairmanagementdesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class RepairManagementDesktopApp extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent rootNode;
    private FXMLLoader fxmlLoader = new FXMLLoader();

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(RepairManagementDesktopApp.class);
        fxmlLoader.setControllerFactory(springContext::getBean);
    }

    @Bean
    @Scope("prototype")
    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public static void main(String[] args) {
        //SpringApplication.run(RepairManagmentApplication.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        fxmlLoader.setLocation(getClass().getResource("/fxml/loginScreen.fxml"));
        rootNode = fxmlLoader.load();

        primaryStage.setTitle("Repair Management System");
        Scene scene = new Scene(rootNode);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.stop();
    }

}
