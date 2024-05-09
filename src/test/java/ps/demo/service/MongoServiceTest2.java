package ps.demo.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles(value = "sit") //-Dspring.profiles.active=ut,sit
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MongoServiceTest2 {

    @BeforeAll
    void beforeAll() {
        log.info("===============before all");
    }

    @AfterAll
    void afterAll() {
        log.info("===============after all");
    }
    @BeforeEach
    void setUp() {
        log.info("---------------setUp entered t={}", Thread.currentThread());
        log.info("setUp continued...");
    }

    @AfterEach
    void tearDown() {
        log.info("---------------tearDown entered, t={}", Thread.currentThread());
        log.info("tearDown continued...");
    }



}
