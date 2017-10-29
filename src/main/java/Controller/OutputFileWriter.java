package Controller;

import Config.ToolConfig;
import Entities.Record;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class OutputFileWriter {
    public static char recordSeparator = '\n';
    private static OutputFileWriter instance;
    LinkedBlockingQueue<Record> queue;
    BufferedWriter bw;

    private OutputFileWriter() {
        queue = new LinkedBlockingQueue<>();
        createFile();
    }

    public static OutputFileWriter getInstance() {
        if (instance == null)
            instance = new OutputFileWriter();
        return instance;
    }

    private void createFile() {
        File file = new File(ToolConfig.getInstance().getOutputPath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void putToTheQueue(Record log) {
        queue.offer(log);
        writeQueueToFile();
    }

    public void writeQueueToFile() {
        try {
            if (bw == null)
                bw = new BufferedWriter(new FileWriter(ToolConfig.getInstance().getOutputPath(), false));

            while (!queue.isEmpty()) {
                bw.write(queue.poll().toString() + recordSeparator);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopWriting() {
        if (!queue.isEmpty()) writeQueueToFile();
        try {
            if (bw != null)
                bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


