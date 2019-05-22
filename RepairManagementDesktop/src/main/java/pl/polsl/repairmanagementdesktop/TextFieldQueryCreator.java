package pl.polsl.repairmanagementdesktop;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class TextFieldQueryCreator {

    private List<TextFieldParamBinding> paramBindings= new ArrayList<>();

    public List<TextFieldParamBinding> getBindings() {
        return paramBindings;
    }

    public String createQueryString(){

        StringBuilder builder = new StringBuilder("?");
        for (TextFieldParamBinding binding : paramBindings){
            builder.append(binding.toString());
        }
        return builder.toString();
    }

    @Override
    public String toString(){
        return createQueryString();
    }

}
