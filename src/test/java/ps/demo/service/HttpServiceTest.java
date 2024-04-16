package ps.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootTest
@ActiveProfiles(value = "sit") //-Dspring.profiles.active=ut,sit
public class HttpServiceTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;


    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void testHttpGet() {
        ImmutableMap<String, Object> immutableMap = ImmutableMap.<String, Object>builder()
                .put("par1", "v1")
                .put("par2", "v2")
                .put("par3", "v3")
                .build();

        String baseUrl = "https://baidu.com";
        ResponseEntity<String> response = restTemplate.getForEntity(
                baseUrl+"?par1={par1}&par2={par2}&par3={par3}",
                String.class,
                immutableMap
        );

        assertEquals(200, response.getStatusCode().value());
        String body = response.getBody();
        //JSONAssert
        String str = JsonPath.read("""
        {"basic": [{"name": "xiaoh", "time": "2024-01-01T00:00:00Z", "ver": 1}], "user": "555", "data": {"x": 123, "y": "hhi"}}
        """, "$.basic[0].name");
        log.info("==> str = {}", str);

    }

}
