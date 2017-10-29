package Entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static Config.AnalyserConstants.DATE_FORMAT;

public class Record {

    private String username;
    private String message;
    private Date dateTime;

    public Record(String username, String message, Date dateTime) {
        this.username = username;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Record(String logLine) {
        String[] params = logLine.split("\t");
        DateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        try {
            dateTime = format.parse(params[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        username = params[1];
        message = params[2];//TODO:
    }

    @Override
    public String toString() {
        DateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
//        return username + '\'' +
//                ", message='" + message + '\'' +
//                ", dateTime=" + format.format(dateTime) +
//                '}';
        return format.format(dateTime) + '\t' + username + '\t' + message;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

}