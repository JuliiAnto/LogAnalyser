package Config;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Entities.Record;

public class FilterConfig {

    private static FilterConfig instance;

    private Date timeStart;
    private Date timeEnd;
    private String pattern;
    private String userName;

    private FilterConfig(){}

    public static FilterConfig getInstance(){
        if (instance == null)
            instance = new FilterConfig();
        return instance;
    }

    public boolean matchRecord(Record rec){
        //TODO: add for pattern
        boolean a = (!AnalyserConstants.isNull(userName) && !AnalyserConstants.isEmpty(userName))? rec.getUsername().equals(userName): true;
        boolean b = (!AnalyserConstants.isNull(timeStart) & !AnalyserConstants.isNull(timeEnd))?
                (rec.getDateTime().compareTo(timeStart)>=0 & rec.getDateTime().compareTo(timeEnd)<=0)
                : true;
        boolean c = true ;
        if (!AnalyserConstants.isNull(pattern))
        {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(rec.getMessage());
            c = m.matches();
        }
        return a&b&c;
    }

    public String getUserName() {
        return userName;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public String getPattern() {
        return pattern;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
