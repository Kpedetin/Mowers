package com.company;


import com.company.entities.Lawn;
import com.company.entities.Mower;
import com.company.functionnalities.InOut;
import com.company.functionnalities.MowerProcess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static Logger logger = Logger.getLogger("Main class");

    public static void main(String[] args) {
        System.out.println("Where everything started");
        try {
            Lawn lawn = new Lawn(0L, 0L, new ConcurrentHashMap<>());
            ArrayList<Mower> mowers = new ArrayList<>();
            ArrayList<String> instructions = new ArrayList<>();
            List<String> lines = InOut.readFile("/Users/Zodiac/Bulls/Mowers/src/main/resources/file.txt");

            for (int i = 0; i < lines.size(); i++) {
                if (i == 0) {
                    lawn = InOut.extractLawnDimension(lines.get(i));
                }
                if (i % 2 != 0) {
                    Mower mower = InOut.extractMowers(lines.get(i), i);
                    mowers.add(mower);
                    lawn.updateMowerPosition(mower.getName(),mower.getCurrentPosition());
                }
                if (i != 0 && i % 2 == 0) {
                    instructions.add(lines.get(i));
                }
            }

            ExecutorService executor = Executors.newFixedThreadPool(mowers.size());

            for(int i=0; i<mowers.size();i++){
                executor.execute(new MowerProcess(mowers.get(i),lawn,instructions.get(i)));
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.DAYS);

            logger.log(Level.INFO, lawn.toString());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occured in the main method");
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occured in the main method");
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }
}
