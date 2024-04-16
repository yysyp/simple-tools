package ps.demo.common;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.concurrent.Immutable;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ThymeleafToolTest {

    @Test
    void processText() {
        Map kv = new HashMap<>();
        kv.put("name", "xxx");
        String str = ThymeleafTool.processText("hello [(${env.TMP})] [(${name})]", kv);
        log.info("str={}", str);

    }



}