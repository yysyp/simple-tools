package ps.demo.common;

import cn.hutool.db.nosql.mongo.MongoFactory;
import cn.hutool.extra.spring.SpringUtil;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ComponentScan(basePackages={"cn.hutool.extra.spring"})
@Import(cn.hutool.extra.spring.SpringUtil.class)
@Slf4j
class FileUtilToolTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
      log.info("This is before each setUp");
    }

    @AfterEach
    void tearDown() {
        log.info("This is after each tearDown");
    }

    @Test
    void toValidFileName() {
        log.info("This is to valid file name, mongoTemplate = {}", mongoTemplate);
        final ThreadPoolTaskExecutor threadPoolTaskExecutor = SpringUtil.getBean("taskExecutor");
        log.info("ThreadPoolTaskExecutor = {}", threadPoolTaskExecutor);
        //MongoDatabase db = MongoFactory.getDS("master", "slave").getDb("test");

    }

    @Test
    void getUserHomeDir() {
        log.info("this is get user home dir");
    }


}