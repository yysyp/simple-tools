package ps.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
class RandomDataToolTest {

    @Test
    void randomDateIn() {
    }

    @Test
    void randomDateTimeIn() {
    }

    @Test
    void formatDateToString() {
    }

    @Test
    void formatDateToUTC() {
        String str = RandomDataTool.formatDateToIso8601(new Date());
        log.info("-->>str={}", str);

        Date date = RandomDataTool.iso8601ToDate(str);
        log.info("-->>date={}", date);

        //log.info("-->>utc now date={}", RandomDataTool.getUTCNowDate());
        //log.info("-->>cmp new date={}", new Date());

        for (int i = 0; i < 1000000; i++) {
            ZonedDateTime zonedDateTime = RandomDataTool.randomDateIn(null, 1999, 2024, 1, 2, 30, 31);
            ZonedDateTime zonedDateTime1 = RandomDataTool.addRandomTime(zonedDateTime);
            log.info("random zonedDateTime={}, withRandomTime={}", zonedDateTime, zonedDateTime1);
        }

    }


}