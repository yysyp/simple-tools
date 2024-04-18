package ps.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class StringXToolTest {

    public static void main(String[] args) {
        String str = StringXTool.readLineFromSystemIn("please type the hello world to continue:");
        log.info("str = {}, correct={}", str, str.equals("hello world"));
    }
}