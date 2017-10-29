package Controller;

import Config.AnalyserConstants;
import Config.FilterConfig;
import Config.GroupingConfig;
import Config.ToolConfig;
import Entities.LogException;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class InputController {

    private static InputController controller;

    private InputController() {
    }

    public static InputController getInstance() {
        if (controller == null)
            controller = new InputController();
        return controller;
    }

    public void validateInput() throws LogException {

        if (AnalyserConstants.isNull(FilterConfig.getInstance().getUserName()) &
                AnalyserConstants.isNull(FilterConfig.getInstance().getPattern()) &
                AnalyserConstants.isNull(FilterConfig.getInstance().getTimeStart())
                ) {
            throw new LogException("Wrong input for filter parameters: at least one should be specified");
        }

        if (!GroupingConfig.getInstance().hasUserName() &
                AnalyserConstants.isNull(GroupingConfig.getInstance().getTimeUnit())
                ) {
            throw new LogException("Wrong input for grouping parameters: at least one should be specified");
        }

        if (ToolConfig.getInstance().getThreadsNumber() < 1)
            throw new LogException("Threads number should be positive integer");

        if (AnalyserConstants.isNull(ToolConfig.getInstance().getLogsPath()))
            throw new LogException("Directory for analyzed logs should be specified");

        File f = new File(ToolConfig.getInstance().getOutputPath());
        if (!f.exists())
            throw new LogException("Wrong path to Output file");

        f = new File(ToolConfig.getInstance().getLogsPath());
        if (!f.exists())
            throw new LogException("Wrong path to Input file");
    }

    public boolean handleInput(String params) throws LogException {
        String[] allParams = params.split(" -");
        boolean isHelp = false;

        DateFormat format = new SimpleDateFormat(AnalyserConstants.DATE_FORMAT, Locale.ENGLISH);
        FilterConfig filterC = FilterConfig.getInstance();
        GroupingConfig groupingC = GroupingConfig.getInstance();
        for (int i = 0; i < allParams.length; i++) {
            String[] data = allParams[i].split("(\\s)+");
            switch (Parameter.getParameterFromStr(data[0])) {
                case F_USERNAME:
                    filterC.setUserName(data[1]);
                    break;
                case F_TIMEPERIOD:
                    String[] dates = data[1].split("-");
                    try {
                        filterC.setTimeStart(format.parse(dates[0]));
                        filterC.setTimeEnd(format.parse(dates[1]));
                    } catch (ParseException e) {
                        throw new LogException("Wrong data format: " + e.getMessage());
                    }
                    break;
                case F_PATTERN:
                    filterC.setPattern(data[1]);
                    break;
                case G_USERNAME:
                    groupingC.setUserName();
                    break;
                case G_TIMEUNIT:
                    groupingC.setTimeUnit(AnalyserConstants.TimeUnit.getTimeUnitFromStr(data[1]));
                    break;
                case OUTPATH:
                    //Check if Path starts and ends with semicilons
                    if (data[1].startsWith("\"") && data[1].endsWith("\""))
                        ToolConfig.getInstance().setOutputPath(data[1].substring(1, data[1].length() - 1));
                    else
                        ToolConfig.getInstance().setOutputPath(data[1]);
                    break;
                case THREADS:
                    try {
                        ToolConfig.getInstance().setThreadsNumber(Integer.parseInt(data[1]));
                    } catch (NumberFormatException e) {
                        throw new LogException("Threads number should be positive integer");
                    }
                    break;
                case HELP:
                    isHelp = true;
                    break;
                case INPUTPATH:
                    if (data[1].startsWith("\"") && data[1].endsWith("\""))
                        ToolConfig.getInstance().setLogsPath(data[1].substring(1, data[1].length() - 1));
                    else
                        ToolConfig.getInstance().setLogsPath(data[1]);
                    break;
                default:
                    throw new LogException("Wrong input format" + data[0]);
                    //break;
            }
        }
        if (!isHelp)
            validateInput();

        return isHelp;
    }

    public enum Parameter {
        F_USERNAME("usr"), F_TIMEPERIOD("timeP"), F_PATTERN("pat"),
        G_USERNAME("grusr"), G_TIMEUNIT("grtime"),
        THREADS("thrN"), OUTPATH("pathO"), HELP("h"), INPUTPATH("pathI");

        private String paramName;

        Parameter(String unit) {
            this.paramName = unit;
        }

        public static Parameter getParameterFromStr(String name) {
            for (Parameter value : values()) {
                if (value.paramName.equalsIgnoreCase(name)) return value;
            }
            return null;
        }
    }
}
