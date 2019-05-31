package pl.polsl.repairmanagementdesktop.utils;

import pl.polsl.repairmanagementdesktop.utils.TextFieldParamBinding;

import java.util.ArrayList;
import java.util.List;

public class QueryCreator {

    private final List<ParamBinding> paramBindings = new ArrayList<>();

    public List<ParamBinding> getBindings() {
        return paramBindings;
    }

    public String createQueryString(){

        StringBuilder builder = new StringBuilder("?");
        for (var binding : paramBindings){
            builder.append(binding.bind());
        }
        return builder.toString();
    }

    @Override
    public String toString(){
        return createQueryString();
    }

}
