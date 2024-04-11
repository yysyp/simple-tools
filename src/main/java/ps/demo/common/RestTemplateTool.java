package ps.demo.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;


@Slf4j
public class RestTemplateTool {

    //This to customize the factory to support http get method with requestBody payload
    public static class SimpleClientHttpResponseWithGetBodyFactory extends SimpleClientHttpRequestFactory {
        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            super.prepareConnection(connection, httpMethod);
            if ("GET".equals(httpMethod)) {
                connection.setDoOutput(true);
            }
        }
    }

    private RestTemplate restTemplate;

    private RestTemplate restTemplate4GetWithRequestBody;

    private static RestTemplateTool instance;

    private RestTemplateTool() {
        init();
    }

    // Double check...
    public static RestTemplateTool getInstance() {
        if (instance == null) {
            synchronized (RestTemplateTool.class) {
                if (instance == null) {
                    instance = new RestTemplateTool();
                }
            }
        }
        return instance;
    }

    private void setCharset(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        for(HttpMessageConverter messageConverter:messageConverters){
            if(messageConverter instanceof StringHttpMessageConverter){
                ((StringHttpMessageConverter) messageConverter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }

    //Config SSL
    public HttpComponentsClientHttpRequestFactory getRequestFactory(){
        TrustStrategy trustStrategy = (x509Certificates, s) -> true;
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build();
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
            HttpClient httpClient = HttpClients.custom().setConnectionManager(PoolingHttpClientConnectionManagerBuilder
                    .create().setSSLSocketFactory(socketFactory).build()).build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            requestFactory.setConnectionRequestTimeout(5000);
            requestFactory.setConnectTimeout(10000);
            return requestFactory;
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {
        this.restTemplate = new RestTemplate(getRequestFactory());
        setCharset(restTemplate);
        this.restTemplate4GetWithRequestBody = new RestTemplate(new SimpleClientHttpResponseWithGetBodyFactory());

//        org.apache.http.conn.ssl.TrustStrategy acceptTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//        try {
//
//            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
//                    .loadTrustMaterial(null, acceptTrustStrategy)
//                    .build();
//            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
//            CloseableHttpClient httpClient = HttpClients.custom()
//                    .setSSLSocketFactory(sslConnectionSocketFactory)
//                    .disableAutomaticRetries()
//                    .disableAuthCaching()
//                    .build();
//            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//            requestFactory.setHttpClient(httpClient);
//            this.restTemplate = new RestTemplate(requestFactory);
//            this.restTemplate4GetWithRequestBody = new RestTemplate(new SimpleClientHttpResponseWithGetBodyFactory());
//        } catch (Exception e) {
//           throw new RuntimeException("RestTemplate initialization error!", e);
//        }
    }



    public String submitFormForString(String url, HttpHeaders headers, MultiValueMap<String, Object> formMap) {
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        return submitFormForObject(url, headers, formMap, responseType).getBody();
    }

    public <T> ResponseEntity<T> submitFormForObject(String url, HttpHeaders headers, MultiValueMap<String, Object> formMap, ParameterizedTypeReference<T> responseType) {
        if (null == headers.getContentType()) {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(formMap, headers);
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, responseType);
            return responseEntity;
        } catch (Exception e) {
            log.info("Rest call submitFormForObject error, url={}, message={}", url, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String postJsonObjectForString(String url, String jsonStr) {
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        return postJsonObjectForObject(url, jsonStr, responseType).getBody();
    }

    public <T> ResponseEntity<T> postJsonObjectForObject(String url, String jsonStr, ParameterizedTypeReference<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        return postJsonObjectForObject(url, headers, jsonStr, responseType);
    }

    public <T> ResponseEntity<T> postJsonObjectForObject(String url, HttpHeaders headers, String jsonStr, ParameterizedTypeReference<T> responseType) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonStr, headers);
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, responseType);
            return responseEntity;
        } catch (Exception e) {
            log.info("Rest call postJsonObjectForObject error, url={}, message={}", url, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String getForString(String url, Object... uriVariables) {
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        return getForObject(url, responseType, uriVariables).getBody();
    }

    public <T> ResponseEntity<T> getForObject(String url, ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        return getForObject(url, headers, responseType, uriVariables);
    }

    /**
     * NOTE!!! Current RestTemplate settings has conflict with this get method, if you use
     * "RestTemplate restTemplate = new RestTemplate();" to set the "restTemplate" attribute, it will work.
     * otherwise it will throw:
     * Caused by: java.lang.UnsupportedOperationException: getBody not supported
     * 	at org.springframework.http.client.HttpComponentsStreamingClientHttpRequest.getBodyInternal(HttpComponentsStreamingClientHttpRequest.java:86)
     * 	at org.springframework.http.client.AbstractClientHttpRequest.getBody(AbstractClientHttpRequest.java:47)
     * 	at org.springframework.http.client.BufferingClientHttpRequestWrapper.executeInternal(BufferingClientHttpRequestWrapper.java:62)
     * 	at org.springframework.http.client.AbstractBufferingClientHttpRequest.executeInternal(AbstractBufferingClientHttpRequest.java:48)
     * 	at org.springframework.http.client.AbstractClientHttpRequest.execute(AbstractClientHttpRequest.java:53)
     * 	at org.springframework.http.client.InterceptingClientHttpRequest$InterceptingRequestExecution.execute(InterceptingClientHttpRequest.java:109)
     * 	at p.y.demo.restconfig.LogClientHttpRequestInterceptor.intercept(LogClientHttpRequestInterceptor.java:28)
     * 	at org.springframework.http.client.InterceptingClientHttpRequest$InterceptingRequestExecution.execute(InterceptingClientHttpRequest.java:93)
     * 	at org.springframework.http.client.InterceptingClientHttpRequest.executeInternal(InterceptingClientHttpRequest.java:77)
     * 	at org.springframework.http.client.AbstractBufferingClientHttpRequest.executeInternal(AbstractBufferingClientHttpRequest.java:48)
     * 	at org.springframework.http.client.AbstractClientHttpRequest.execute(AbstractClientHttpRequest.java:53)
     * 	at org.springframework.web.client.RestTemplate.doExecute(RestTemplate.java:739)
     * 	at org.springframework.web.client.RestTemplate.execute(RestTemplate.java:674)
     * 	at org.springframework.web.client.RestTemplate.exchange(RestTemplate.java:612)
     * 	at p.y.demo.restconfig.RestTemplateUtil.getForObject(RestTemplateUtil.java:85)
     * @param url
     * @param headers
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> getForObject(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    responseType, uriVariables
            );
            return responseEntity;
        } catch (Exception e) {
            log.info("Rest call getForObject error, url={}, message={}", url, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This doesNOT work!
     * This is still not working, internally it will convert to use POST method for the request.
     * Use getWithRequestBody instead.
     *
     * @param url: the url to call
     * @param headers: request header
     * @param requestBody: request body
     * @param responseType: response type
     * @param uriVariables: URL variables to replace in url. i.e: ?user={userId}&age={age}
     * @param <T> Response Type.
     * @return Response Http Status & Body
     */
    @Deprecated
    public <T> ResponseEntity<T> getWithRequestBodyForObject(String url, HttpHeaders headers, String requestBody, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<T> responseEntity = restTemplate4GetWithRequestBody.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    responseType, uriVariables
            );
            return responseEntity;
        } catch (Exception e) {
            log.info("Rest call getWithRequestBodyForObject error, url={}, message={}", url, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


//    public static class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
//        private final static String METHOD_NAME = "GET";
//
//        @Override
//        public String getMethod() {
//            return METHOD_NAME;
//        }
//        public HttpGetWithEntity() {
//            super();
//        }
//        public HttpGetWithEntity(final URI uri) {
//            super();
//            setURI(uri);
//        }
//        HttpGetWithEntity(final String uri) {
//            super();
//            setURI(URI.create(uri));
//        }
//
//    }
//
//    /**
//     * This works!
//     * This is not recommended, according to Restful convention, GET should not has request body data.
//     * @param url
//     * @param requestBody
//     * @param encoding
//     * @return
//     * @throws Exception
//     */
//    @SneakyThrows
//    public static String getWithRequestBody(String url, String requestBody, String encoding) {
//        String responseBody = "";
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpGetWithEntity httpGetWithEntity = new HttpGetWithEntity(url);
//        StringEntity httpEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
//        httpGetWithEntity.setEntity(httpEntity);
//        CloseableHttpResponse response = client.execute(httpGetWithEntity);
//        org.apache.http.HttpEntity entity = response.getEntity();
//        if (entity != null) {
//            responseBody = EntityUtils.toString(entity, encoding);
//        }
//        response.close();
//        return responseBody;
//    }


}
