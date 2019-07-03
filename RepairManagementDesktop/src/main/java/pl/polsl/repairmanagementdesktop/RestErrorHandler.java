package pl.polsl.repairmanagementdesktop;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.client.DefaultResponseErrorHandler;
import pl.polsl.repairmanagementdesktop.model.errors.ErrorsHolder;
import pl.polsl.repairmanagementdesktop.model.errors.ValidationErrorWrapper;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
public class RestErrorHandler extends DefaultResponseErrorHandler {

    private String inputStreamToString(InputStream is){
        StringBuilder textBuilder = new StringBuilder();
        try {
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (is, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textBuilder.toString();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        var json = inputStreamToString(response.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        ValidationErrorWrapper errors = objectMapper.readValue(json, ValidationErrorWrapper.class);

        StringBuilder builder = new StringBuilder();

        for(var error: errors.getErrors()){
            builder.append("Invalid property \"" + error.getProperty() + "\" with value: \"" + error.getInvalidValue() + "\"\n");
            builder.append("Reason: " + error.getMessage() + "\n\n");
        }

        errorAlert.setContentText(builder.toString());
        errorAlert.showAndWait();




    }
}
