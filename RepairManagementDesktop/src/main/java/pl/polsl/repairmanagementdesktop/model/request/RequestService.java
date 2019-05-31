package pl.polsl.repairmanagementdesktop.model.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;

@Service
public class RequestService {

    private final Client<RequestEntity> client;

    @Autowired
    public RequestService(ClientFactory factory){

        client = factory.create(RequestEntity.class);

    }

    public void save(RequestEntity entity){
        client.post(entity);
    }

    public RequestEntity findById(String id){
        String baseUriStr = client.getBaseUri().toString();
        return client.get(URI.create(baseUriStr + "/" + id));
    }


    public Page<RequestEntity> findAll(int page, int size){

        return client.getPage(URI.create(client.getBaseUri().toString()), page, size);
    }

    public Page<RequestEntity> findAllMatching(String params, int page, int size){
        return client.getPage(URI.create(client.getBaseUri().toString() + params), page, size);
    }

}
