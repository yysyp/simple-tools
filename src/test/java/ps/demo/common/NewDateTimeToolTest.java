package ps.demo.common;

import cn.hutool.core.lang.Console;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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

    @Test
    void zonedDateTimeDiff() {
        ZonedDateTime d1 = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-05-01");
        ZonedDateTime d2 = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-15");
        long days = NewDateTimeTool.zonedDateTimeBetweenIn(d1, d2, ChronoUnit.DAYS);
        Console.log("days d1 - d2 = {}", days);

    }

    @Test
    void getFirstDayOfMonth() {
        ZonedDateTime d1 = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-02-11");
        ZonedDateTime d2 = NewDateTimeTool.getFirstDayOfMonth(d1);
        Console.log("d2 = {}", d2);
    }

    @Test
    void getLastDayOfMonth() {
        ZonedDateTime d1 = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-02-11");
        ZonedDateTime d2 = NewDateTimeTool.getLastDayOfMonth(d1);
        Console.log("d2 = {}", d2);
    }


    @Test
    void calcDaysInMonthDaysPercentage1() {
        ZonedDateTime dealBegin = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-11");
        ZonedDateTime dealEnd = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-21");

        ZonedDateTime monthdStart = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-01");
        ZonedDateTime monthdEnd = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-31");

        ZonedDateTime a = null;
        ZonedDateTime b = null;
        if (dealBegin.isBefore(monthdStart)) {
            a = monthdStart;
        } else {
            a = dealBegin;
        }
        if (dealEnd.isAfter(monthdEnd)) {
            b = monthdEnd;
        } else {
            b = dealEnd;
        }

        long subDays = NewDateTimeTool.zonedDateTimeBetweenIn(a, b, ChronoUnit.DAYS);
        long totalDays = NewDateTimeTool.zonedDateTimeBetweenIn(monthdStart, monthdEnd, ChronoUnit.DAYS);

        Console.log("b - a = {} - {} / total == {}/{}", b, a, subDays, totalDays);
    }

    @Test
    void calcDaysInMonthDaysPercentage2() {
        ZonedDateTime dealBegin = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-02-11");
        ZonedDateTime dealEnd = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-21");

        ZonedDateTime monthdStart = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-01");
        ZonedDateTime monthdEnd = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-31");

        ZonedDateTime a = null;
        ZonedDateTime b = null;
        if (dealBegin.isBefore(monthdStart)) {
            a = monthdStart;
        } else {
            a = dealBegin;
        }
        if (dealEnd.isAfter(monthdEnd)) {
            b = monthdEnd;
        } else {
            b = dealEnd;
        }

        long subDays = NewDateTimeTool.zonedDateTimeBetweenIn(a, b, ChronoUnit.DAYS);
        long totalDays = NewDateTimeTool.zonedDateTimeBetweenIn(monthdStart, monthdEnd, ChronoUnit.DAYS);

        Console.log("b - a = {} - {} / total == {}/{}", b, a, subDays, totalDays);
    }

    @Test
    void calcDaysInMonthDaysPercentage3() {
        ZonedDateTime dealBegin = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-11");
        ZonedDateTime dealEnd = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-04-21");

        ZonedDateTime monthdStart = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-01");
        ZonedDateTime monthdEnd = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-31");

        ZonedDateTime a = null;
        ZonedDateTime b = null;
        if (dealBegin.isBefore(monthdStart)) {
            a = monthdStart;
        } else {
            a = dealBegin;
        }
        if (dealEnd.isAfter(monthdEnd)) {
            b = monthdEnd;
        } else {
            b = dealEnd;
        }

        long subDays = NewDateTimeTool.zonedDateTimeBetweenIn(a, b, ChronoUnit.DAYS);
        long totalDays = NewDateTimeTool.zonedDateTimeBetweenIn(monthdStart, monthdEnd, ChronoUnit.DAYS);

        Console.log("b - a = {} - {} / total == {}/{}", b, a, subDays, totalDays);
    }

    @Test
    void calcDaysInMonthDaysPercentage4() {
        ZonedDateTime dealBegin = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-02-11");
        ZonedDateTime dealEnd = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-04-21");

        ZonedDateTime monthdStart = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-01");
        ZonedDateTime monthdEnd = NewDateTimeTool.getUtcZonedDateTimeByYmd("2024-03-31");

        ZonedDateTime a = null;
        ZonedDateTime b = null;
        if (dealBegin.isBefore(monthdStart)) {
            a = monthdStart;
        } else {
            a = dealBegin;
        }
        if (dealEnd.isAfter(monthdEnd)) {
            b = monthdEnd;
        } else {
            b = dealEnd;
        }

        long subDays = NewDateTimeTool.zonedDateTimeBetweenIn(a, b, ChronoUnit.DAYS);
        long totalDays = NewDateTimeTool.zonedDateTimeBetweenIn(monthdStart, monthdEnd, ChronoUnit.DAYS);

        Console.log("b - a = {} - {} / total == {}/{}", b, a, subDays, totalDays);
    }

}