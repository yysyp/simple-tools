package ps.demo.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.Collections.sort;

import ps.demo.common.StringXTool;

@Service
public class MongoService {


    @Autowired
    MongoTemplate mongoTemplate;

    public List<Document> findAll(String collectionName) {
        List<Document> list = mongoTemplate.findAll(Document.class, collectionName);
        StringXTool.printOut(list, System.out);
        return list;
    }

    public List<Document> findByKey(String collectionName, String key) {
        Query query = new Query();
        //query.fields().
        query.fields().include("basic.name", "basic.time", "basic.ver", "user", "data.x", "data.y")
                .projectAs(MongoExpression.create("""
                $dateFromString: {
                    dateString: "$basic.time",
                    format: "%Y-%m-%dT%H:%M:%SZ"
                }
                """), "date1");

        StringBuilder regex = new StringBuilder("(?s).*");
        regex.append(Pattern.quote(key));

        Criteria criteria = Criteria.where("user").regex(regex.toString(), "si");
        query.addCriteria(criteria);

        query.with(Sort.by(Sort.Direction.DESC, "date1", "basic.ver")); //TODO: Issue: The date1 doesn't work...


        return mongoTemplate.find(query, Document.class, collectionName);
    }


    public Document insert(Document document, String collectionName) {
        return mongoTemplate.insert(document, collectionName);
    }

}
