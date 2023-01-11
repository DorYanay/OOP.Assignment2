package EX2;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static EX2.Ex_1.*;

public class Ex_1 {
    public static String[] createTextFiles(int n, int seed, int bound) throws IOException {
        String[] filenames = new String[n];
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            int randomInt = rand.nextInt(bound);
            String filename = "file_" + (i + 1) + ".txt";
            filenames[i] = filename;
            File file = new File(filename);
            FileWriter writer = new FileWriter(file);
            for (int j = 0; j < randomInt; j++) {
                writer.write(+j + "\n");
            }
            writer.close();
        }
        return filenames;
    }

    public static int getNumOfLines(String[] fileNames) throws IOException {
        Instant start = Instant.now();
        if (fileNames == null) {
            return 0;
        }
        int sumoflines = 0;
        for (int i = 0; i < fileNames.length; i++) {
            String filename = fileNames[i];
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while (reader.readLine() != null) {
                sumoflines++;
            }
            reader.close();
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken numoflines: "+ timeElapsed.toMillis() +" milliseconds");
        return sumoflines;
    }
    public static int getNumOfLinesThreads(String[] fileNames) throws InterruptedException{
        Instant start = Instant.now();
        if (fileNames == null) {
            return 0;
        }
        int totalLines = 0;
        int n = fileNames.length;
        MyThread[] currentFileThread = new MyThread[fileNames.length];
        for (int i = 0; i < n; i++) {
            currentFileThread[i] = new MyThread(fileNames[i]);
            currentFileThread[i].start();
        }
        for (int i = 0;i<n;i++) {
            currentFileThread[i].join();
            totalLines = totalLines+ currentFileThread[i].returnLines();
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken numoflinesthread: "+ timeElapsed.toMillis() +" milliseconds");
        return totalLines;
    }

    public static int getNumOfLinesThreadPool(String[] fileNames){
        Instant start = Instant.now();
        if (fileNames == null) {
            return 0;
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length);
        MyThreadPool[] fileThreads = new MyThreadPool[fileNames.length];
        int totalLines=0;
        for(int i=0; i< fileNames.length; i++){
            fileThreads[i] = new MyThreadPool(fileNames[i]);//callable
            Future<Integer> f = threadPool.submit(fileThreads[i]);//future for current fileThread
            Integer numOfLinesInFile;
            try {
                numOfLinesInFile= f.get();
                totalLines = totalLines + numOfLinesInFile;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken numoflinesthreadpool: "+ timeElapsed.toMillis() +" milliseconds");
        threadPool.shutdown();
        return totalLines;
    }



    public static void main(String[] args) throws IOException, InterruptedException {
        String[] output = createTextFiles(1000, 1, 1000000);
        int numofr = getNumOfLines(output);
        int numoft = getNumOfLinesThreads(output);
        int numoftp = getNumOfLinesThreadPool(output);
        System.out.println(numofr);
        System.out.println(numoft);
        System.out.println(numoftp);
    }
}