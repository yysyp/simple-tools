package ps.demo.x;

import com.google.common.base.Preconditions;
import lombok.*;
import org.awaitility.Awaitility;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class RetryUntil {

    private final Callable<Result> callable;

    private int timeoutSeconds = 300;

    private int intervalSeconds = 5;

    public RetryUntil(Callable<Result> callable) {

        this.callable = callable;
    }

    public RetryUntil(Callable<Result> callable, int timeoutSeconds, int intervalSeconds) {
        this.callable = callable;
        this.timeoutSeconds = timeoutSeconds;
        this.intervalSeconds = intervalSeconds;
        Preconditions.checkArgument(timeoutSeconds > 0, "Timeout setting should > 0");
        Preconditions.checkArgument(intervalSeconds > 0, "Interval setting should > 0");
    }

    public Object call() {
        return Awaitility.await().timeout(timeoutSeconds, TimeUnit.SECONDS).pollDelay(1, TimeUnit.SECONDS).pollInterval(Duration.ofSeconds(intervalSeconds)).until(callable, r -> (r.until)).getData();

    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class Result {
        private Boolean until;
        private Object data;

    }

}
