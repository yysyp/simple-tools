package ps.demo.service;

import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles(value = "sit") //-Dspring.profiles.active=ut,sit
class MongoServiceTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void findAll() {
        Document document = Document.parse("""
                {"basic": {"name": "xiaoh", "time": "2024-01-01T00:00:00Z", "ver": 1}, "user": "555", "data": {"x": 123, "y": "hhi"}}
                """);
        mongoTemplate.insert(document, "test");
        List<Document> documentList = mongoTemplate.findAll(Document.class, "test");
        log.info("documentList = {}", documentList);

        Query query = new Query();
        Criteria criteria = Criteria.where("user").regex(".*"+Pattern.quote("555")+".*", "si");

        query.addCriteria(criteria);

        DeleteResult deleteResult = mongoTemplate.remove(query, "test");
        log.info("delete count={}", deleteResult.getDeletedCount());
    }

    @Test
    void findByKey() {
    }

    @Test
    void insert() {
    }
}