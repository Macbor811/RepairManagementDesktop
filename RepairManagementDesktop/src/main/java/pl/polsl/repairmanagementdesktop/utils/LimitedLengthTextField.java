package pl.polsl.repairmanagementdesktop.utils;

import javafx.scene.control.TextField;

public class LimitedLengthTextField extends TextField {

    public void setMaxLength(int length){
        textProperty().addListener( (observable,oldValue,newValue) -> {
                    if(newValue.length() > length){
                        setText(oldValue);
                    }
        });
    }
}


