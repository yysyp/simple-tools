package ps.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class NewDateTimeToolTest {

    @Test
    void dateFormat() {
    }

    @Test
    void addSecond() {
    }

    @Test
    void addMinute() {
    }

    @Test
    void addHour() {
    }

    @Test
    void addDay() {
    }

    @Test
    void addMonth() {
    }

    @Test
    void addYear() {
    }

    @Test
    void dateToWeek() {
    }

    @Test
    void getStartTimeOfDay() {
        Date startDate = NewDateTimeTool.getStartTimeOfDay(new Date());
        log.info("startDate = {}", startDate);
    }

    @Test
    void getEndTimeOfDay() {
        Date endDate = NewDateTimeTool.getEndTimeOfDay(new Date());
        log.info("endDate = {}", endDate);
    }

    @Test
    void betweenStartAndEnd() {
    }

    @Test
    void testDateFormat() {
    }

    @Test
    void testAddSecond() {
    }

    @Test
    void testAddMinute() {
    }

    @Test
    void testAddHour() {
    }

    @Test
    void testAddDay() {
    }

    @Test
    void testAddMonth() {
    }

    @Test
    void testAddYear() {
    }

    @Test
    void testDateToWeek() {
    }

    @Test
    void testGetStartTimeOfDay() {
    }

    @Test
    void testGetEndTimeOfDay() {
    }

    @Test
    void testBetweenStartAndEnd() {
    }

    @Test
    void testGetUtcZonedDateTime() {
        ZonedDateTime zonedDateTime = NewDateTimeTool.getUtcZonedDateTime("2024-01-01T10:11:12.123Z");
        log.info("getUtcZonedDateTime = {}", zonedDateTime);
        log.info("getUtcZonedDateTime epoch seconds = {}", zonedDateTime.toInstant().getEpochSecond());


    }

    @Test
    void testGetUtcZonedDateTimeByYmd() {
        ZonedDateTime zonedDateTime = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-01-01");
        log.info("getUtcZonedDateTime = {}", zonedDateTime);
        log.info("getUtcZonedDateTime epoch seconds = {}", zonedDateTime.toInstant().getEpochSecond());

    }

    @Test
    void testGetUtcZonedDateTimeByYmdHms() {
        ZonedDateTime zonedDateTime = NewDateTimeTool.getUtcZonedDateTimeByYmdHms("2024-01-01T10:11:12");
        log.info("getUtcZonedDateTime = {}", zonedDateTime);
        log.info("getUtcZonedDateTime epoch seconds = {}", zonedDateTime.toInstant().getEpochSecond());
    }

}