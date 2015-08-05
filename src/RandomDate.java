import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RandomDate {
    public static String randomDOB(int duration, String d1, String d2) throws ParseException {

        int a = (int)(getDays(d1, d2));
        int b = (int)(Math.random()*(a+1)+0);

        return getDate(d1, b) + "|" + getStartTime();
    }

    public static long getDays(String d1, String d2) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = format.parse(d1);
                date2 = format.parse(d2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long difference = date2.getTime() - date1.getTime();
           long days = (int) (difference / (24 * 60 * 60 * 1000));


        return days;
    }

    public static String getDate(String d1, int addDays) throws ParseException {
        String[] tmp;
        tmp = d1.split("\\.");
        String dt = tmp[2] + "-" + tmp[1] + "-" + tmp[0];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, addDays);
        dt = sdf.format(c.getTime());
        tmp = dt.split("-");
        return tmp[2] + "." + tmp[1] + "." + tmp[0];
    }

    public static String getStartTime() {
        int secondsInDay = 86400;
        int rnd = (int)(Math.random()*secondsInDay+1);

        String startHour = Integer.toString(rnd/3600);
        String startMinute = Integer.toString((rnd/60)%60);
        String startSecond = Integer.toString(rnd%60);

        if (Integer.parseInt(startHour) < 10) {
            startHour = "0" + startHour;
        }

        if (Integer.parseInt(startMinute) < 10) {
            startMinute = "0" + startMinute;
        }

        if (Integer.parseInt(startSecond) < 10) {
            startSecond = "0" + startSecond;
        }

        String startTime = startHour + ":" + startMinute + ":" + startSecond;

        return startTime;
    }

    static boolean isValid(String date, String datePattern) {

        if (date == null || datePattern == null || datePattern.length() <= 0)
            return false;

        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        formatter.setLenient(false);

        try {
            formatter.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

}

