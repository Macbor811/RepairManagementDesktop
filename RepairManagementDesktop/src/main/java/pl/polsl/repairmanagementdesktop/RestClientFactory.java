package pl.polsl.repairmanagementdesktop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Configuration
public class RestClientFactory {

    @Bean
    RestTemplate createRestTemplate(@Value("${server.address}") String server) {
        RestTemplate rest = new RestTemplate();
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        rest.setRequestFactory(
                (uri, httpMethod) -> simpleClientHttpRequestFactory.createRequest(URI.create(server + uri.toString()), httpMethod)
        );
        return rest;
    }
}
