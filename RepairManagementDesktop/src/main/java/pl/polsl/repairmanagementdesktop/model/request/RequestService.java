package pl.polsl.repairmanagementdesktop.model.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.polsl.repairmanagementdesktop.CurrentUser;
import pl.polsl.repairmanagementdesktop.FinalizationData;
import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;
import uk.co.blackpepper.bowman.Client;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Page;

import java.io.IOException;
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


    public Page<RequestEntity> findAll(int page, int size){

        return client.getPage(page, size);
    }
    public RequestEntity findById(String id){
        String baseUriStr = client.getBaseUri().toString();
        return client.get(UriComponentsBuilder.fromUri(
                client.getBaseUri())
                .path("/{id}")
                .buildAndExpand(id).toUri());
    }


    public Page<RequestEntity> findAllMatching(SearchQuery query, int page, int size){
        URI uri = UriComponentsBuilder.fromUri(client.getBaseUri()).query(query.getQueryString()).build().toUri();
        return client.getPage(uri, page, size);
    }


    @Autowired
    private CurrentUser currentUser;

    public void finalize(String id, String result, String status){
        RestTemplate template = new RestTemplate();

        template.getInterceptors().add( new ClientHttpRequestInterceptor() {

            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                    throws IOException {
                request.getHeaders().setBasicAuth(currentUser.getUsername(), currentUser.getPassword());
                //request.getHeaders().add(createHeaders(currentUser.getUsername(), currentUser.getPassword()));
                return execution.execute(request, body);
            }
        });

        var data = new FinalizationData();
        data.setResult(result);
        data.setStatus(status);

        String baseUriStr = client.getBaseUri().toString();

        template.put(
                UriComponentsBuilder.fromUri(
                client.getBaseUri())
                .path("/{id}/finalize")
                .buildAndExpand(id).toUri(),
                data
        );

    }
}
