package Config;

import static Config.AnalyserConstants.*;
import Entities.Record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class GroupingConfig {

    private static GroupingConfig instance;

    private boolean userName = false;
    private AnalyserConstants.TimeUnit timeUnit;

    public GroupingConfig(){}

    public static GroupingConfig getInstance(){
        if (instance == null)
            instance = new GroupingConfig();
        return instance;
    }

    public String getKeyByRecod(Record rec){
        String key = (userName)? rec.getUsername()+"\t" : EMPTY_STRING;
        if (!isNull(timeUnit)) {
            DateFormat format = new SimpleDateFormat(timeUnit.getDateFormat(), Locale.ENGLISH);
            key = key.concat(format.format(rec.getDateTime()));
        }
        return key;
    }

    public boolean hasUserName() {
        return userName;
    }

    public AnalyserConstants.TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setUserName() {
        this.userName = true;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

}
