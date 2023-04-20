package library_DB.com.yulim.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getToday() {
        // 오늘 날짜 정보를 가져옴
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    // 20131020 형태로 출력하게
    public static String formattedDateToString(Date day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(day);
    }

    public static Date addDate(Date date, int i) {

        // 얻은 날짜정보를 Calendar에 설정한다
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Calendar에서 일(day) 값을 얻는다.
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 얻은 값에 100을 더한 값을 Calendar의 일에 설정한다
        calendar.set(Calendar.DAY_OF_MONTH, day + i);

        // Calendar의 날짜정보를 Date 형으로 변환한다
        return calendar.getTime();

    }
}
