package pl.polsl.repairmanagementdesktop.model.item;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Configuration;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class ItemService {

    private final Client<ItemEntity> client;

    @Autowired
    public ItemService(ClientFactory factory){

        client = factory.create(ItemEntity.class);

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
}
