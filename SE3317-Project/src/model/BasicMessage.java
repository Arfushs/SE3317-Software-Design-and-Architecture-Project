package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicMessage implements Message {
    @Override
    public String getMessage() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        return "Day: " + dayFormat.format(now) + ", Date: " + dateFormat.format(now);
    }
}
