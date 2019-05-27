package pl.polsl.repairmanagementdesktop;

import javafx.scene.control.TextField;

public class TextFieldParamBinding {


    private final TextField textField;
    private final String queryParam;

    public TextFieldParamBinding(TextField textField, String queryParam) {
        this.textField = textField;
        this.queryParam = queryParam;
    }

    @Override
    public String toString() {
        if (!textField.getText().isEmpty()) {
            return "&" + queryParam + "=" + textField.getText();
        } else {
            return "";
        }
    }

}
