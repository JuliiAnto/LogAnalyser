package Controller;

import Config.ToolConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LogReader {

    LogFileProcessor[] processorsPull;
    private HashMap<String, Integer> stats = new HashMap<>();
    private FileOutputStream outputFile;

    public LogReader() {

    }

    public ArrayList<File> getFileNames() {
        File folder = new File(ToolConfig.getInstance().getLogsPath());
        ArrayList<File> files = new ArrayList<>(Arrays.asList(folder.listFiles()));//listFiles
        return files;
    }


    public void readLogs() {
        ArrayList<File> files = getFileNames();
        MapController.getInstance();
        processorsPull = new LogFileProcessor[(ToolConfig.getInstance().getThreadsNumber())];

        for (int i = 0; i < ToolConfig.getInstance().getThreadsNumber(); i++)
            processorsPull[i] = new LogFileProcessor(i);

        for (File curFile : files) {
            boolean fileIsTaken = false;
            do {
                for (LogFileProcessor proc : processorsPull)
                    if (proc.isFree()) {
                        proc.setCurProcFile(curFile);
                        proc.start();
                        fileIsTaken = true;
                        break;
                    }
            } while (!fileIsTaken);
        }
        boolean threadsFinished = false;
        while (!threadsFinished) {
            threadsFinished = true;
            for (LogFileProcessor proc : processorsPull)
                threadsFinished &= proc.isFinished();
        }
        OutputFileWriter.getInstance().stopWriting();
        System.out.println("Grouping:");
        MapController.getInstance().printMap();
    }
}
