package pl.polsl.repairmanagementdesktop.model.itemtype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.repairmanagementdesktop.model.customer.CustomerEntity;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;

@Service
public class ItemTypeService {

    private final Client<ItemTypeEntity> client;

    @Autowired
    public ItemTypeService(ClientFactory factory){

        client = factory.create(ItemTypeEntity.class);

    }

    public ItemTypeEntity findById(String id){
        String baseUriStr = client.getBaseUri().toString();
        return client.get(URI.create(baseUriStr + "/" + id));
    }

    public void save(ItemTypeEntity entity){
        String baseUriStr = client.getBaseUri().toString();
        entity.setUri(URI.create(baseUriStr + "/" + entity.getType()));
        client.post(entity);
    }


    public Page<ItemTypeEntity> findAll(int page, int size){

        return client.getPage(page, size);
    }

}

