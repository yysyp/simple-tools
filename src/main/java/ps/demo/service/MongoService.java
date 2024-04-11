package ps.demo.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class MongoService {


    @Autowired
    MongoTemplate mongoTemplate;

    public List<Document> findAll(String collectionName) {
        return mongoTemplate.findAll(Document.class, collectionName);
    }

    public List<Document> findByKey(String collectionName, String key) {
        Query query = new Query();
        //query.fields().
        StringBuilder regex = new StringBuilder("(?s).*");
        regex.append(Pattern.quote(key));

        Criteria criteria = Criteria.where("user").regex(regex.toString(), "si");
        query.addCriteria(criteria);

        query.with(Sort.by(Sort.Direction.DESC, "basic.ver"));

        return mongoTemplate.find(query, Document.class, collectionName);
    }


    public Document insert(Document document, String collectionName) {
        return mongoTemplate.insert(document, collectionName);
    }

}
