package pl.polsl.repairmanagementdesktop;


import javafx.scene.control.TextField;

public class TextFieldQueryBinding {

    private final TextField textField;

    private final String queryParam;


    public TextFieldQueryBinding(TextField textField, String queryParam) {
        this.textField = textField;
        this.queryParam = queryParam;
    }

    public String bind(){
        if (!textField.getText().isEmpty()){
            return  queryParam + "=" + textField.getText();
        } else {
            return null;
        }
    }


}
