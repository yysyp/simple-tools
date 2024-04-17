package ps.demo.common;

import cn.hutool.json.JSONUtil;
import cn.hutool.setting.Setting;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.jupiter.api.Assertions.*;

class MongoDbToolTest {

    @Test
    void mongoClient() {
    }

    @Test
    void mongoTemplate() {
        Setting setting = new Setting("c:/myconfigs/config.setting", true);
        String mongoConn = setting.getByGroup("mongoConn", "mongo");
        String db = setting.getByGroup("db", "mongo");
        MongoClient mongoClient = MongoDbTool.mongoClient(mongoConn);
        MongoTemplate template = MongoDbTool.mongoTemplate(mongoClient, db);

//        template.insert("""
//                {"a": 123, "b": 456}
//                """, "c1");
//
//        //update part of the doc
//        String id = "xxx";
//        Query query = new Query(Criteria.where("_id").is(id));
//        Update update = new Update();
//        update.set("k1", "newv");
//        template.updateFirst(query, update, "c1");

        //update whole doc
        Document document = Document.parse("""
                {
                        "basic": {
                            "name": "V29",
                            "time": 1713332551545,
                            "bizDate": 1713139200000,
                            "ver": 4
                        },
                        "user": "VVQM40",
                        "theData": {
                            "x": 899,
                            "y": 471
                        }
                    }
                """);
        //document.append("_id", new ObjectId());
        document.append("sum", new Document("xysum", 899+471));
        document.append("_id", new ObjectId("661f66c9b8b5087dfebe8f04"));
        template.save(document, "c1");

        mongoClient.close();
    }

}