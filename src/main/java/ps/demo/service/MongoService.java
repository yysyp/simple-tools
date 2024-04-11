package ps.demo.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoService {


    @Autowired
    MongoTemplate mongoTemplate;

    public List<Document> findAll(String collectionName) {
        return mongoTemplate.findAll(Document.class, collectionName);
    }


    public Document insert(Document document, String collectionName) {
        return mongoTemplate.insert(document, collectionName);
    }

}
