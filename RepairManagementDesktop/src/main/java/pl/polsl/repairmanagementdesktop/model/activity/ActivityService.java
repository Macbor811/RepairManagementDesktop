package pl.polsl.repairmanagementdesktop.model.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;
import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;

public class ActivityService {

    private final Client<ActivityEntity> client;

    @Autowired
    public ActivityService(ClientFactory factory){

        client = factory.create(ActivityEntity.class);

    }

    public void save(ActivityEntity activity){
        client.post(activity);
    }

    public ActivityEntity findById(String id){
        String baseUriStr = client.getBaseUri().toString();
        return client.get(URI.create(baseUriStr + "/" + id));
    }


    public Page<ActivityEntity> findAll(int page, int size){

        return client.getPage(page, size);
    }

    public Page<ActivityEntity> findAllMatching(SearchQuery query, int page, int size){
        URI uri = UriComponentsBuilder.fromUri(client.getBaseUri()).query(query.getQueryString()).build().toUri();
        return client.getPage(uri, page, size);
    }
}
