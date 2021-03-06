package pl.polsl.repairmanagementdesktop.model.activitytype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;

@Service
public class ActivityTypeService {

    private final Client<ActivityTypeEntity> client;

    @Autowired
    public ActivityTypeService(ClientFactory factory){

        client = factory.create(ActivityTypeEntity.class);

    }

    public void save(ActivityTypeEntity entity){
        client.post(entity);
    }

    public ActivityTypeEntity findById(String id){
        String baseUriStr = client.getBaseUri().toString();
        return client.get(URI.create(baseUriStr + "/" + id));
    }

    public Page<ActivityTypeEntity> findAll(int page, int size){

        return client.getPage(page, size);
    }

    public ActivityTypeEntity findByType(String type) {
        String baseUriStr = client.getBaseUri().toString();
        return client.getAll(UriComponentsBuilder.fromUri(client.getBaseUri()).queryParam("type", type).build().toUri()).iterator().next();
    }
}
