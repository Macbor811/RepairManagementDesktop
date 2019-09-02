package pl.polsl.repairmanagementdesktop.model.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.polsl.repairmanagementdesktop.utils.auth.CurrentUser;
import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;

@Service
public class ActivityService implements pl.polsl.repairmanagementdesktop.abstr.Service<ActivityEntity> {

    private final Client<ActivityEntity> client;
    private final Client<ActivityEntity> noHandlerClient;
    private final RestTemplate template = new RestTemplate();
    private final CurrentUser currentUser;

    @Autowired
    public ActivityService(ClientFactory factory, @Qualifier("noHandler") ClientFactory noHandlerFactory, CurrentUser currentUser){
        this.client = factory.create(ActivityEntity.class);
        this.currentUser = currentUser;
        this.noHandlerClient = noHandlerFactory.create(ActivityEntity.class);
        this.template.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().setBasicAuth(this.currentUser.getUsername(), this.currentUser.getPassword());
            return execution.execute(request, body);
        });
    }

    public void save(ActivityEntity activity){
        client.post(activity);
    }

    public ActivityEntity findById(String id){
        String baseUriStr = client.getBaseUri().toString();
        return client.get(URI.create(baseUriStr + "/" + id));
    }


    public Page<ActivityEntity> findAll(int page, int size){

        return noHandlerClient.getPage(page, size);
    }

    public Page<ActivityEntity> findAllMatching(SearchQuery query, int page, int size){
        URI uri = UriComponentsBuilder.fromUri(noHandlerClient.getBaseUri()).query(query.getQueryString()).build().toUri();
        return noHandlerClient.getPage(uri, page, size);
    }



    public void update(Integer id, ActivityUpdateDto dto){
        template.put(
                UriComponentsBuilder.fromUri(
                        client.getBaseUri())
                        .path("/{id}/update")
                        .buildAndExpand(id).toUri(),
                dto
        );

    }

    public void reorder(String id, Integer delta) {
        template.put(
                UriComponentsBuilder.fromUri(
                        client.getBaseUri())
                        .path("/{id}/reorder")
                        .queryParam("delta", delta)
                        .buildAndExpand(id).toUri(),
                null
        );
    }
}
