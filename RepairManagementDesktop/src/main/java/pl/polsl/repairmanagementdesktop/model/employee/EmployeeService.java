package pl.polsl.repairmanagementdesktop.model.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.polsl.repairmanagementdesktop.CurrentUser;
import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class EmployeeService implements pl.polsl.repairmanagementdesktop.abstr.Service<EmployeeEntity>{


    private final Client<EmployeeEntity> client;
    private final Client<EmployeeEntity> clientNoHandler;
    private final RestTemplate template;
    private final CurrentUser currentUser;

    @Autowired
    public EmployeeService(ClientFactory factory, @Qualifier("noHandler") ClientFactory noHandlerFactory, CurrentUser user){
        client = factory.create(EmployeeEntity.class);
        clientNoHandler = noHandlerFactory.create(EmployeeEntity.class);
        template = new RestTemplate();
        this.currentUser = user;
        template.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().setBasicAuth(currentUser.getUsername(), currentUser.getPassword());
            return execution.execute(request, body);
        });
    }

    public void save(EmployeeEntity Employee){
        client.post(Employee);
    }

    public EmployeeEntity findById(String id){
        String baseUriStr = client.getBaseUri().toString();
        return client.get(URI.create(baseUriStr + "/" + id));
    }

    public Page<EmployeeEntity> findAll(int page, int size){
        return clientNoHandler.getPage(page, size);
    }

    public Page<EmployeeEntity> findAllMatching(SearchQuery query, int page, int size){
        URI uri = UriComponentsBuilder.fromUri(client.getBaseUri()).query(query.getQueryString()).build().toUri();
        return clientNoHandler.getPage(uri, page, size);
    }

    public List<String> findByFullName(String fullName, String role) {
        var strings = template.getForObject(
                UriComponentsBuilder
                        .fromUri(client.getBaseUri())
                        .path("/search")
                        .queryParam("fullName", fullName)
                        .queryParam("role", role)
                        .build()
                        .toUri(),
                String[].class
        );
        return Arrays.asList(strings);
    }

    public void update(URI uri, EmployeeUserDataDto data) {
        template.put(
                UriComponentsBuilder
                        .fromUri(uri)
                        .path("/update-user")
                        .build()
                        .toUri(),
                data
        );
    }
}
