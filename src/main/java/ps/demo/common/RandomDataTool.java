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

    public static Date randomDateIn(LocalDate inMin, LocalDate exMax) {
        int year = RandomUtils.nextInt(inMin.getYear(), exMax.getYear());
        int month = RandomUtils.nextInt(inMin.getMonthValue(), exMax.getMonthValue());
        int day = RandomUtils.nextInt(inMin.getDayOfMonth(), exMax.getDayOfMonth());
        LocalDate localDate = LocalDate.of(year, month, day);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Date randomDateTimeIn(LocalDateTime inMin, LocalDateTime exMax) {
        int year = RandomUtils.nextInt(inMin.getYear(), exMax.getYear());
        int month = RandomUtils.nextInt(inMin.getMonthValue(), exMax.getMonthValue());
        int day = RandomUtils.nextInt(inMin.getDayOfMonth(), exMax.getDayOfMonth());
        int hour = RandomUtils.nextInt(inMin.getHour(), exMax.getHour());
        int minute = RandomUtils.nextInt(inMin.getMinute(), exMax.getMinute());
        int second = RandomUtils.nextInt(inMin.getSecond(), exMax.getSecond());
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute, second);
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(dateTime.atZone(zoneId).toInstant());
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
