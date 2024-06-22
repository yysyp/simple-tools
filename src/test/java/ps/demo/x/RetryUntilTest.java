package ps.demo.x;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.thymeleaf.util.StringUtils;

class RetryUntilTest {


    @Test
    public void testRetryUntilTrue1() {
        Object data = new RetryUntil(() -> {
            int x = RandomUtils.nextInt(0, 10);
            System.out.println("x = "+x);
            return RetryUntil.Result.builder()
                    .until(x == 9)
                    .data(StringUtils.randomAlphanumeric(10))
                    .build();
        }, 5, 1).call();

        System.out.println("result data = " + data);
    }

}