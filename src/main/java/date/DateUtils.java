package date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by HBooo on 2018/1/11.
 */
public class DateUtils {

    /**
     * 获取两个时间戳之间的时间集合
     *
     * @param startStr
     * @param endStr
     * @return
     */
    private List<String> getBetweenDates(String startStr, String endStr) {


        SimpleDateFormat Sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start = null;
        Date end = null;
        try {
            start = Sdf.parse(startStr);
            end = Sdf.parse(endStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> result = new ArrayList<String>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        result.add(Sdf.format(start));

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {

            result.add(Sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        if (!startStr.equals(endStr)) {
            result.add(Sdf.format(end));
        }
        return result;
    }
}
