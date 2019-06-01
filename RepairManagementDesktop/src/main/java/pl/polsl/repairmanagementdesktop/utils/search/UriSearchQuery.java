package pl.polsl.repairmanagementdesktop.utils.search;

import org.springframework.util.MultiValueMap;
import pl.polsl.repairmanagementdesktop.utils.search.ParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;

import java.util.ArrayList;
import java.util.List;

public class UriSearchQuery implements SearchQuery {

    private final List<ParamBinding> paramBindings = new ArrayList<>();

    private String queryString = "";

    public List<ParamBinding> getBindings() {
        return paramBindings;
    }

    public void update(){
        StringBuilder builder = new StringBuilder("?");
        for (var binding : paramBindings){
            builder.append(binding.bind());
        }
        queryString = builder.toString();
    }

    @Override
    public String getQueryString(){
        return queryString;
    }

}
