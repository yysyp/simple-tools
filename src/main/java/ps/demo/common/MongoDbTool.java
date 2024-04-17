package ps.demo.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoDbTool {

    //mongodb://user:pass@1.1.1.1,2.2.2.2/?tls=true&replicaSet=xxx
    public static MongoClient mongoClient(String mongodbConn) {
        if (StringUtils.isBlank(mongodbConn)) {
            mongodbConn = "mongodb://127.0.0.1:27017/test?retryWrites=false";
        }
        ConnectionString connectionString = new ConnectionString(mongodbConn);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();

        MongoClient mongoClient = MongoClients.create(connectionString);

        return mongoClient;
    }

    public static MongoTemplate mongoTemplate(MongoClient mongoClient, String databaseName) {
        return new MongoTemplate(mongoClient, databaseName);
    }

    public static void closeClient(MongoClient mongoClient) {
        mongoClient.close();
    }

}
