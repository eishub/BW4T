package nl.tudelft.bw4t.batchrunner;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


import goal.core.mas.MASProgram;
import goal.tools.BatchRun;
import goal.tools.Run;
import goal.tools.SingleRun;
import goal.tools.errorhandling.exceptions.GOALParseException;

public class Launcher {
    
    private static LinkedList<Simulation> scheduledSimulations;
    private static TimeoutChecker timeoutChecker;
    
    private static long lastStartTime;
    
    private static class Simulation {
        
    }
    
    private static class TimeoutChecker implements Runnable {

        @Override
        public void run() {
            while (true) {
                if (System.currentTimeMillis() - lastStartTime > 100000) {
                    //time out occured
                }
            }
        }
        
    }
    
    public static void main(String[] args) {
        scheduledSimulations = new LinkedList<Simulation>();
        
        timeoutChecker = new TimeoutChecker();
        new Thread(timeoutChecker).start();
        
        //Run.run(args);
        
        //ok, so the Run object was supposed to work as below:
        //new Run(new File("MAS File Loc Here")).run();
        
        //but apparently the GOAL code has changed, so below are some attempts:
//        try {
//            Run.run("what", "are", "these?");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        //possibly use BatchRun object instead of Run object (just guess work, we need some documentation):
//        @SuppressWarnings("unused")
//        MASProgram p = new MASProgram(null, null);
//        try {
//            new BatchRun((List<MASProgram>) new LinkedList<MASProgram>()).run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        //another method/attempt suggested by Wouter:
//        try {
//            @SuppressWarnings("unused")
//            SingleRun run = new SingleRun(new File("MAS File Loc Here"));
//        } catch (GOALParseException e) {
//            e.printStackTrace();
//        }
        
    }
    
    private void executeSimulation(Simulation task) {
        lastStartTime = System.currentTimeMillis();
        //TODO: new Run(new File("MAS File Loc Here")).run();
    }

}
