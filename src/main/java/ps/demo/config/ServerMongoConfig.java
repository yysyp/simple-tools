package ps.demo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;

@Slf4j
@Configuration
@Profile("!dev")
public class ServerMongoConfig {

    @Value("${spring.mongo.connection:mongodb://localhost:28028/test?retryWrites=false}")
    private String mongoDbString;

    @Value("${spring.mongo.database:test}")
    private String database;


    private MongoClient mongoClient;

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoDbString);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        mongoClient = MongoClients.create(mongoClientSettings);
        return mongoClient;
    }

    @Bean
    public MongoTemplate mongoTemplate(@Autowired MongoClient mongoClient) throws Exception {
        return new MongoTemplate(mongoClient, this.database);
    }

    @PreDestroy
    public void stopMongoClient() {
        mongoClient.close();
    }

}