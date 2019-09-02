package pl.polsl.repairmanagementdesktop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import org.springframework.http.HttpStatus;
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
        var body = inputStreamToString(response.getBody());
        if (response.getStatusCode() == HttpStatus.BAD_REQUEST){
            ObjectMapper objectMapper = new ObjectMapper();
            ValidationErrorWrapper errors = objectMapper.readValue(body, ValidationErrorWrapper.class);

            StringBuilder builder = new StringBuilder();

            for(var error: errors.getErrors()){
                builder.append("Invalid property \"" + error.getProperty() + "\" with value: \"" + error.getInvalidValue() + "\"\n");
                builder.append("Reason: " + error.getMessage() + "\n\n");
            }

            errorAlert.setContentText(builder.toString());
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND){
            if (body.length() == 0){
                return;
            }
            errorAlert.setContentText("Can't connect to the server.");
        } else {
            errorAlert.setContentText(body);
        }
        errorAlert.show();

    }
}