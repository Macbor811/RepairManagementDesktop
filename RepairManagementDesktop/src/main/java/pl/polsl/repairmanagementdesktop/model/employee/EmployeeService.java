package pl.polsl.repairmanagementdesktop.model.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pl.polsl.repairmanagementdesktop.model.request.RequestEntity;
import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;

@Service
public class EmployeeService {

        private final Client<EmployeeEntity> client;

        @Autowired
        public EmployeeService(ClientFactory factory){

            client = factory.create(EmployeeEntity.class);

        }

        public void save(EmployeeEntity entity){
            client.post(entity);
        }


        public Page<EmployeeEntity> findAll(int page, int size){

            return client.getPage(page, size);
        }

    public EmployeeEntity findById(String id){
        String baseUriStr = client.getBaseUri().toString();
        return client.get(URI.create(baseUriStr + "/" + id));
    }

        public Page<EmployeeEntity> findAllMatching(SearchQuery query, int page, int size){
            URI uri = UriComponentsBuilder.fromUri(client.getBaseUri()).query(query.getQueryString()).build().toUri();
            return client.getPage(uri, page, size);
        }
}