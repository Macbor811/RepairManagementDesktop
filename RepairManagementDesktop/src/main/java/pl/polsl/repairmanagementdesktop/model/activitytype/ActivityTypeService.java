package pl.polsl.repairmanagementdesktop.model.activitytype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public ActivityTypeEntity findById(Integer id){
        return client.get();
    }


    public Page<ActivityTypeEntity> findAll(int page, int size){

        return client.getPage(URI.create(client.getBaseUri().toString()), page, size);
    }

    public Page<ActivityTypeEntity> findAllMatching(String params, int page, int size){
        return client.getPage(URI.create(client.getBaseUri().toString() + params), page, size);
    }

}
