package pl.polsl.repairmanagementdesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RepairManagmentApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent rootNode;
    private FXMLLoader fxmlLoader;

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(RepairManagmentApplication.class);
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
    }

    public static void main(String[] args) {
        //SpringApplication.run(RepairManagmentApplication.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        fxmlLoader.setLocation(getClass().getResource("/main.fxml"));
        rootNode = fxmlLoader.load();

        primaryStage.setTitle("Repair Management System");
        Scene scene = new Scene(rootNode, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.stop();
    }

}
