package Config;

public final class AnalyserConstants {

    public static final String EMPTY_STRING = "";

    public static final String DATE_FORMAT= "dd/MMM/yyyy:hh:mm:ss";

    public enum TimeUnit {
        HOUR("hour", "yyy/MMM/dd:hh"), DAY("day", "yyy/MMM/dd"), MONTH("month", "yyy/MMM"),
        YEAR("year", "yyyy");

        private String unitName;
        private String dateFromat;

        private TimeUnit(String unit, String dateFromat){
            this.unitName = unit;
            this.dateFromat = dateFromat;
        }

        public static TimeUnit getTimeUnitFromStr(String unit){
            for (TimeUnit value : values()){
                if (value.unitName.equalsIgnoreCase(unit)) return value;
            }
            return null;
        }

        public String getDateFormat() {
            return dateFromat;
        }

        public String getUnitName() {
            return unitName;
        }
    }

    public static boolean isEmpty(String str){
        return str.equals(EMPTY_STRING);
    }

    public static boolean isNull(Object obj){
        return obj==null;
    }
}
