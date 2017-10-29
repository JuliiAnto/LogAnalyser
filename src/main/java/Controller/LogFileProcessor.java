package Controller;

import Config.FilterConfig;
import Config.GroupingConfig;
import Entities.Record;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LogFileProcessor implements Runnable {

    private File curProcFile;
    private BufferedReader br = null;
    private Thread t;
    private int threadId;
    private boolean isFree = true;

    public LogFileProcessor(int threadId) {
        this.threadId = threadId;
    }

    @Override
    public void run() {
        isFree = false;
        process();
    }

    public void start() {
        isFree = false;
        //System.out.println("Starting " +  threadId + " " + curProcFile.getName());
        t = new Thread(this);
        t.start();
    }

    public boolean isFinished() {
        return !t.isAlive();
    }

    public void process() {
        try {
            FileReader reader = new FileReader(curProcFile);
            br = new BufferedReader(reader);
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                Record rec = new Record(sCurrentLine);
                //Filter records
                if (FilterConfig.getInstance().matchRecord(rec)) {
                    //System.out.println("ID:" + threadId + " " + rec.toString());
                    OutputFileWriter.getInstance().putToTheQueue(rec);
                }
                //Aggregate statistic
                String mapKey = GroupingConfig.getInstance().getKeyByRecod(rec);
                MapController.getInstance().addRecord(mapKey);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            isFree = true;
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isFree() {
        return isFree;
    }

    public void setCurProcFile(File curProcFile) {
        this.curProcFile = curProcFile;
    }
}
