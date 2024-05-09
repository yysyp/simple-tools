package ps.demo.common;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleStringReplaceTest {

    @Test
    void testStringFmtReplace() {
        List<String> stringList =
                IntStream.range(0, 10).mapToObj( e -> """
                        Hello "%s", you are %d-year old.
                        """.formatted(StringUtils.randomAlphanumeric(5), RandomUtils.nextInt(3, 100)))
                        .collect(Collectors.toList());

        StringXTool.printOut(stringList);
    }

}
