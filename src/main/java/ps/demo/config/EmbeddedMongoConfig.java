package ps.demo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.UserVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@Slf4j
@Configuration
@Profile("dev")
public class EmbeddedMongoConfig {

    @Value("${embedded.mongo.port:28028}")
    private int mongoPort;

    @Bean
    public MongoServer mongodServer() {
        MemoryBackend memoryBackend = new MemoryBackend();
        memoryBackend.openOrCreateDatabase("test");
        MongoServer server = new MongoServer(memoryBackend);
        server.bind("localhost", 28028);
        return server;
    }

    @EventListener
    public void handleMemoryMongoServerClose(@Autowired MongoServer mongoServer) {
        log.info("mongoServer.shutdown ..., {}", mongoServer);
        if (mongoServer != null) {
            mongoServer.shutdownNow();
        }
    }

    @Bean
    public MongoClient mongoClient(@Autowired MongoServer mongoServer) {
        return MongoClients.create("mongodb://localhost:28028/test?retryWrites=false");
    }

    @Bean
    public MongoTemplate mongoTemplate(@Autowired MongoClient mongoClient) throws Exception {
        return new MongoTemplate(mongoClient, "test");
    }

}