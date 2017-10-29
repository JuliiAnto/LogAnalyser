import Controller.InputController;
import Controller.LogReader;
import Entities.LogException;

import java.util.Scanner;

public class Analysator {


    public static void main(String[] args){

        System.out.println("For help print -h\n");
        Scanner scanner = new Scanner(System.in);

        boolean readDataAgain = true;
        while (readDataAgain) {

            String params= scanner.nextLine();
            try {
                if (InputController.getInstance().handleInput(params.substring(1))) {
                    readDataAgain = true;
                    System.out.println("Filter parameters (at least one should be specified):\n -usr <userName>\n -timeP <timeStart>-<timeEnd>\n" +
                            " -pat <pattern regular expression>\n");
                    System.out.println("Enter Grouping parameters(at least one should be specified): \n-grusr <userName>\n " +
                            " -grtime  <time unit: {day, year, month}>.\n");
                    System.out.println("Enter Other parameters:\n -thrN <integer number of threads>\n" +
                            "-pathO <Path to output file>\n -pathI <Path to directory with log files>");
                    System.out.println("Date is in format dd/MMM/yyyy:hh:mm:ss\n");
                }
                else
                    readDataAgain = false;
            } catch (LogException e) {
                readDataAgain = true;
                System.out.println(e);
            }
        }
        LogReader logReader = new LogReader();
        logReader.readLogs();
        scanner.close();
    }
}
