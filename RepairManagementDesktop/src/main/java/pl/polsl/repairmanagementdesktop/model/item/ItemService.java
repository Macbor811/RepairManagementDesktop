package pl.polsl.repairmanagementdesktop.model.item;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.polsl.repairmanagementdesktop.utils.auth.CurrentUser;
import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;
import java.util.Arrays;
import java.util.List;


@Service
public class ItemService implements pl.polsl.repairmanagementdesktop.abstr.Service<ItemEntity>{

    private final Client<ItemEntity> client;
    private final CurrentUser currentUser;
    private final RestTemplate template;

    @Autowired
    public ItemService(ClientFactory factory, CurrentUser user){

        client = factory.create(ItemEntity.class);

        this.currentUser = user;

        template = new RestTemplate();
        template.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().setBasicAuth(currentUser.getUsername(), currentUser.getPassword());
            return execution.execute(request, body);
        });
    }

    public void save(ItemEntity Item){
        client.post(Item);
    }

    public ItemEntity findById(Integer id){
        return client.get();

    }
    public ItemEntity findById(String id){
        String baseUriStr = client.getBaseUri().toString();
        return client.get(URI.create(baseUriStr + "/" + id));
    }



    public Page<ItemEntity> findAll(int page, int size){

        return client.getPage(page, size);
    }

    public Page<ItemEntity> findAllMatching(SearchQuery query, int page, int size){
        URI uri = UriComponentsBuilder.fromUri(client.getBaseUri()).query(query.getQueryString()).build().toUri();
        return client.getPage(uri, page, size);
    }

    public List<String> findByFullName(String fullName){

        var strings = template.getForObject(
                UriComponentsBuilder
                        .fromUri(client.getBaseUri())
                        .path("/search")
                        .queryParam("fullName", fullName)
                        .build()
                        .toUri(),
                String[].class
        );
        return Arrays.asList(strings);
    }
}
