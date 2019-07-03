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
        return new ClientHttpRequestInterceptor() {

            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                    throws IOException {
                request.getHeaders().setBasicAuth(username, password);
                //request.getHeaders().add(createHeaders(currentUser.getUsername(), currentUser.getPassword()));
                return execution.execute(request, body);
            }
        };
    }



    @Bean
    public ClientFactory configuredClient(@Value("${server.address}") String server, RestErrorHandler handler) {
        ClientFactory factory = Configuration.builder()
                .setRestTemplateConfigurer(restTemplate -> {
                    //restTemplate.setRequestFactory(sslRequestFactory());
                    restTemplate.setErrorHandler(handler);

                    restTemplate.getInterceptors().add( new ClientHttpRequestInterceptor() {

                        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                                throws IOException {
                            request.getHeaders().setBasicAuth(currentUser.getUsername(), currentUser.getPassword());
                            //request.getHeaders().add(createHeaders(currentUser.getUsername(), currentUser.getPassword()));
                            return execution.execute(request, body);
                        }
                    });

                })
                .setBaseUri(server + "/api")
                .build()
                .buildClientFactory();
        return factory;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory sslRequestFactory(){
        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        BasicHttpClientConnectionManager connectionManager =
                new BasicHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                .setConnectionManager(connectionManager).build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return requestFactory;

    }





}
