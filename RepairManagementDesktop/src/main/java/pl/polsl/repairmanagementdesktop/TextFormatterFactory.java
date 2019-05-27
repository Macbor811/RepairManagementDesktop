package pl.polsl.repairmanagementdesktop;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class TextFormatterFactory {

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
}
