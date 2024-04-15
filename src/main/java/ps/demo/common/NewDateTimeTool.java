package ps.demo.common;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class NewDateTimeTool {

    public static String dateFormat(Date date, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static Date addSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(13, second);
        return calendar.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(12, minute);
        return calendar.getTime();
    }

    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(10, hour);
        return calendar.getTime();
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, day);
        return calendar.getTime();
    }

    public static Date addMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, month);
        return calendar.getTime();
    }

    public static Date addYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(1, year);
        return calendar.getTime();
    }

    public static final String[] WEEK_DAY_OF_CHINESE = new String[]{"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"};

    public static String dateToWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return WEEK_DAY_OF_CHINESE[cal.get(7) - 1];
    }

    public static Date getStartTimeOfDay(Date date) {
        if (date == null) {
            return null;
        } else {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
            LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
            return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        }
    }

    public static Date getEndTimeOfDay(Date date) {
        if (date == null) {
            return null;
        } else {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
            LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
            return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        }
    }

    public static Boolean betweenStartAndEnd(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        return (date.equals(begin) || date.after(begin)) && date.before(end);
    }

    //New ways

    public static String dateFormat(LocalDateTime date, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return date.format(formatter);
    }

    public static LocalDateTime addSecond(LocalDateTime date, int second) {
        return date.plusSeconds(second);
    }

    public static LocalDateTime addMinute(LocalDateTime date, int minute) {
        return date.plusMinutes(minute);
    }

    public static LocalDateTime addHour(LocalDateTime date, int hour) {
        return date.plusHours(hour);
    }

    public static LocalDateTime addDay(LocalDateTime date, int day) {
        return date.plusDays(day);
    }

    public static LocalDateTime addMonth(LocalDateTime date, int month) {
        return date.plusMonths(month);
    }

    public static LocalDateTime addYear(LocalDateTime date, int year) {
        return date.plusYears(year);
    }

    public static String dateToWeek(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return WEEK_DAY_OF_CHINESE[dayOfWeek.getValue() % 7];
    }

    public static LocalDateTime getStartTimeOfDay(LocalDateTime date) {
        if (date == null) {
            return null;
        } else {
            // 获取一天的开始时间，即00:00
            return date.toLocalDate().atStartOfDay();
        }
    }

    public static LocalDateTime getEndTimeOfDay(LocalDateTime date) {
        if (date == null) {
            return null;
        } else {
            // 获取一天的结束时间，即23:59:59.999999999
            return date.toLocalDate().atTime(LocalTime.MAX);
        }
    }

    public static Boolean betweenStartAndEnd(Instant nowTime, Instant beginTime, Instant endTime) {
        return (nowTime.equals(beginTime) || nowTime.isAfter(beginTime)) && nowTime.isBefore(endTime);
    }

}
