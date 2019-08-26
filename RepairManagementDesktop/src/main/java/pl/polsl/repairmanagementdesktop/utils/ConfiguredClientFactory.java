package pl.polsl.repairmanagementdesktop.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.polsl.repairmanagementdesktop.CurrentUser;
import pl.polsl.repairmanagementdesktop.RestErrorHandler;
import uk.co.blackpepper.bowman.ClientFactory;
import uk.co.blackpepper.bowman.Configuration;
import uk.co.blackpepper.bowman.RestTemplateConfigurer;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@org.springframework.context.annotation.Configuration
public class ConfiguredClientFactory {

    @Autowired
    private CurrentUser currentUser;

    private String username;
    private String password;

    ClientHttpRequestInterceptor interceptor;

    public void setCredentials(String username, String password){
        this.username = username;
        this.password = password;
    }

    public static ClientHttpRequestInterceptor basicAuthInterceptor(String username, String password){
        return (request, body, execution) -> {
            request.getHeaders().setBasicAuth(username, password);
            return execution.execute(request, body);
        };
    }


    @Bean
    @Primary
    public ClientFactory configuredClient(@Value("${server.address}") String server, RestErrorHandler handler) {
        ClientFactory factory = Configuration.builder()
                .setRestTemplateConfigurer(restTemplate -> {
                    restTemplate.setErrorHandler(handler);

                    restTemplate.getInterceptors().add((request, body, execution) -> {
                        request.getHeaders().add("Authorization", currentUser.getBasicAuth());
                        return execution.execute(request, body);
                    });

                })
                .setBaseUri(server + "/api")
                .build()
                .buildClientFactory();
        return factory;
    }

    @Bean("noHandler")
    public ClientFactory configuredClientNoHandler(@Value("${server.address}") String server, RestErrorHandler handler) {
        ClientFactory factory = Configuration.builder()
                .setRestTemplateConfigurer(restTemplate -> {
                    restTemplate.getInterceptors().add((request, body, execution) -> {
                        request.getHeaders().add("Authorization", currentUser.getBasicAuth());
                        return execution.execute(request, body);
                    });

                })
                .setBaseUri(server + "/api")
                .build()
                .buildClientFactory();
        return factory;
    }

}
