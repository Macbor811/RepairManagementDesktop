package pl.polsl.repairmanagementdesktop.utils;

import javafx.scene.control.TextField;

public class TextFieldParamBinding implements ParamBinding{


    private final TextField textField;
    private final String queryParam;

    public TextFieldParamBinding(TextField textField, String queryParam) {
        this.textField = textField;
        this.queryParam = queryParam;
    }

    @Override
    public String toString() {
       return this.bind();
    }

    @Override
    public String bind() {
        if (!textField.getText().isEmpty()) {
            return "&" + queryParam + "=" + textField.getText();
        } else {
            return "";
        }
    }
}
