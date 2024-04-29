package ps.demo.service;


import cn.hutool.core.lang.Console;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FuncBaseTest {

    protected String str = "base";

    protected String getStr() {
        return str;
    }

    @Test
    void commonTest1() {
        Console.log("This is from common str={}", getStr());
    }

}
