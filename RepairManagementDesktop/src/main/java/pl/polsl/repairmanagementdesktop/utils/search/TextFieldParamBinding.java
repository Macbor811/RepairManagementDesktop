package pl.polsl.repairmanagementdesktop.utils.search;

import javafx.scene.control.TextField;
import pl.polsl.repairmanagementdesktop.utils.search.ParamBinding;

public class TextFieldParamBinding implements ParamBinding {


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
