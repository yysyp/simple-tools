package ps.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        for (int i = 0; i < 10; i++) {
            //Date rdate = RandomDataTool.randomDateIn(LocalDate.of(2000, 1, 1), LocalDate.of(2024, 12, 30));
            //log.info("random date={}", rdate);
            Date rdate2 = RandomDataTool.randomDateTimeIn(LocalDateTime.of(2000, 1, 1, 0, 0, 0), LocalDateTime.of(2010, 12, 30, 23, 59, 59));
            log.info("random datetime={}", rdate2);
        }

    }


}