package pl.polsl.repairmanagementdesktop.model.socialuser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.polsl.repairmanagementdesktop.FinalizationData;
import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;

@Service
public class SocialUserService {

    private final Client<SocialUserEntity> client;

    @Autowired
    public SocialUserService(ClientFactory factory){

        client = factory.create(SocialUserEntity.class);

    }

    public void save(SocialUserEntity entity){
        client.post(entity);
    }


    public Page<SocialUserEntity> findAll(int page, int size){

        return client.getPage(page, size);
    }
    public SocialUserEntity findById(String id){
        String baseUriStr = client.getBaseUri().toString();
        return client.get(UriComponentsBuilder.fromUri(
                client.getBaseUri())
                .path("/{id}")
                .buildAndExpand(id).toUri());
    }


    public Page<SocialUserEntity> findAllMatching(SearchQuery query, int page, int size){
        URI uri = UriComponentsBuilder.fromUri(client.getBaseUri()).query(query.getQueryString()).build().toUri();
        return client.getPage(uri, page, size);
    }

    public void update(SocialUserEntity entity){
        client.put(entity);
    }
   
}

