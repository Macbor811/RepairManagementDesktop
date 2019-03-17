package pl.polsl.repairmanagementdesktop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Component
public class RestClient {


    private TypeReferenceMap typeReferenceMap = new TypeReferenceMap();

    //@Value("${server.address}")
    private String server = "http://localhost:8080";
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;


    public RestClient() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
       // headers.add("Content-Type", "application/json");
       // headers.add("Accept", "*/*");
    }

//    public <T> T get(String uri, Class<T> forClass) {
//        HttpEntity<T> requestEntity = new HttpEntity<T>(null, headers);
//        ResponseEntity<T> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, typeReferenceMap.get(forClass));
//        this.setStatus(responseEntity.getStatusCode());
//        return responseEntity.getBody();
//    }

    public String post(String uri, String json) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }


    public <T> List<T> toResponseList(String uri, T object ,Class<T[]> setType) {
        HttpEntity<?> body = new HttpEntity<>(object, headers);
        ResponseEntity<T[]> response = rest.exchange(server + uri, HttpMethod.GET, body, setType);
        setStatus(response.getStatusCode());
        return new ArrayList<T>(Arrays.asList(response.getBody()));
    }

//    public void put(String uri, String json) {
//        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
//        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.PUT, requestEntity, null);
//        this.setStatus(responseEntity.getStatusCode());
//    }
//
//    public void delete(String uri) {
//        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
//        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.DELETE, requestEntity, null);
//        this.setStatus(responseEntity.getStatusCode());
//    }

    public HttpStatus getStatus() {
        return status;
    }

    private void setStatus(HttpStatus status) {
        this.status = status;
    }
}
