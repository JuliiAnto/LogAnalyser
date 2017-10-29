package Config;

public class ToolConfig {

    private int threadsNumber = 1;
    private String outputPath;
    private String logsPath;

    private static ToolConfig instance;

    private ToolConfig(){}

    public static ToolConfig getInstance(){
        if (instance == null)
            instance = new ToolConfig();
        return instance;
    }

    public int getThreadsNumber() {
        return threadsNumber;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getLogsPath() {
        return logsPath;
    }

    public void setThreadsNumber(int threadsNumber) {
        this.threadsNumber = threadsNumber;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public void setLogsPath(String logsPath) {
        this.logsPath = logsPath;
    }
}
