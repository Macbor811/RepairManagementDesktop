package pl.polsl.repairmanagementdesktop.utils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class TextFieldUtils {

    public static TextFormatter<String> numericTextFormatter(){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        return textFormatter;
    }


    public static void setMaxLength(TextField textField, int length){
        textField.textProperty().addListener( (observable,oldValue,newValue) -> {
            if(newValue.length() > length){
                textField.setText(oldValue);
            }
        });
    }


}
