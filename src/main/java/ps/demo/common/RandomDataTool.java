package ps.demo.common;

import org.apache.commons.lang3.RandomUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class RandomDataTool {

    public static ZonedDateTime randomDateIn(ZoneId zoneId, int ... minMaxYearsMinMaxMonthsMinMaxDays) {
        int inMinYear = minMaxYearsMinMaxMonthsMinMaxDays[0];
        int exMaxYear = minMaxYearsMinMaxMonthsMinMaxDays[1]+1;
        int inMinMonth = minMaxYearsMinMaxMonthsMinMaxDays[2];
        int exMaxMonth = minMaxYearsMinMaxMonthsMinMaxDays[3]+1;
        int inMinDay = minMaxYearsMinMaxMonthsMinMaxDays[4];
        int exMaxDay = minMaxYearsMinMaxMonthsMinMaxDays[5]+1;

        LocalDate localDate = null;
        int i = 0;
        while(i++ < 1000) {
            try {
                int year = RandomUtils.nextInt(inMinYear, exMaxYear);
                int month = RandomUtils.nextInt(inMinMonth, exMaxMonth);
                int day = RandomUtils.nextInt(inMinDay, exMaxDay);
                localDate = LocalDate.of(year, month, day);
                break;
            } catch (java.time.DateTimeException e) {
                //ignore Invalid date and retry
            }
        }
        //ZoneId zoneId = ZoneId.systemDefault();
        if (zoneId == null) {
            zoneId = ZoneId.of("UTC");
        }
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        //return Date.from(zdt.toInstant());
        return zdt;
    }

    public static ZonedDateTime addRandomTime(ZonedDateTime zonedDateTime) {
        zonedDateTime = zonedDateTime.withHour(RandomUtils.nextInt(0, 24));
        zonedDateTime = zonedDateTime.withMinute(RandomUtils.nextInt(0, 60));
        zonedDateTime = zonedDateTime.withSecond(RandomUtils.nextInt(0, 60));
        zonedDateTime = zonedDateTime.withNano(RandomUtils.nextInt(0, 1+999999999));
        return zonedDateTime;
    }

    //java.util.Date 本身存储的是时间戳（1970年1月1日 00:00:00 GMT以来此对象表示的毫秒数 ），所以它和时区地域是无关的，只是在显示的时候加载了TimeZone来调整了时间的显示。
    public static String formatDateToString(Date date, String format, String timeZone) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
            timeZone = Calendar.getInstance().getTimeZone().getID();
        }
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(date);
    }

    public static String iso8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static String formatDateToIso8601(Date date) {
        //String iso8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        return formatDateToString(date, iso8601, "UTC");
    }

    public static Date iso8601ToDate(String iso8601String) {
        SimpleDateFormat sdf = new SimpleDateFormat(iso8601);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date isoStringToDate = null;
        try {
            isoStringToDate = sdf.parse(iso8601String);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isoStringToDate;
    }

    /* Same as new Date();
    public static Date getUTCNowDate() {
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneId.of("UTC"));
        return Date.from(utcNow.toInstant());
        //Same as return  new Date();
    }*/


}
