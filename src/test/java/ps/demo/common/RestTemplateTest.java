package ps.demo.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class RestTemplateTest {

    public static void main(String[] args) throws JsonProcessingException {

        //  请求地址
        String url = "http://baidu.com/";
        RestTemplate client = new RestTemplate();
        client.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                //do nothing
            }
        });
        //  一定要设置header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //  将提交的数据转换为String
        //  最好通过bean注入的方式获取ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> params = new HashMap();
        params.put("username", "国米");
        params.put("password", "123456");
        String value = mapper.writeValueAsString(params);
        HttpEntity<String> requestEntity = new HttpEntity<String>(value, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.postForEntity(url, requestEntity, String.class);
        System.out.println(response.getBody());

    }

}


