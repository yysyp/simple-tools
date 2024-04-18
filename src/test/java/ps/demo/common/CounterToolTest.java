package ps.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CounterToolTest {

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
//        CounterTool.getInstance().increment("setup");
//        if (CounterTool.getInstance().increasedMoreThenOnce("setup")) return;

        log.info("setUp continued...");
    }

    @AfterEach
    void tearDown() {
        log.info("---------------tearDown entered, t={}", Thread.currentThread());
//        CounterTool.getInstance().increment("teardown");
//        if (CounterTool.getInstance().increasedMoreThenOnce("teardown")) return;

        log.info("tearDown continued...");
    }

    @Test
    void test1() {
        log.info("this is test1");
    }

    @Test
    void test2() {
        log.info("this is test2");
    }

//    public static void main(String [] args) {
//        long x = CounterTool.getInstance().increment("a");
//        long y = CounterTool.getInstance().increment("a");
//        long z = CounterTool.getInstance().increment("a");
//        long x1 = CounterTool.getInstance().increment("b");
//
//        log.info("x={}, y={}, z={}, x1={}", x, y, z, x1); //x=1, y=2, z=3, x1=1
//    }
}