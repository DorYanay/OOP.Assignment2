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
import static java.lang.Thread.sleep;

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

        return sumoflines;
    }
    public static int getNumOfLinesThreads(String[] fileNames) throws InterruptedException{

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
        return totalLines;
    }

    public static int getNumOfLinesThreadPool(String[] fileNames){

        if (fileNames == null) {
            return 0;
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length);
        int totalLines=0;
        Integer numOfLinesInFile;
        Future<Integer>[] f = new Future[fileNames.length];
        for(int i=0; i< fileNames.length; i++) {
            f[i] = threadPool.submit(new MyThreadPool(fileNames[i]));//future for current fileThread
        }
        for(int i = 0;i<fileNames.length;i++){
            try {
                numOfLinesInFile= f[i].get();
                totalLines = totalLines + numOfLinesInFile;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        threadPool.shutdown();
        return totalLines;
    }



    public static void main(String[] args) throws IOException, InterruptedException {
        String[] output = createTextFiles(800, 1, 100000);
        Instant start = Instant.now();
        System.out.println(getNumOfLines(output));
        Instant end = Instant.now();
        System.out.println("Time taken numoflines: "+ Duration.between(start, end).toMillis() +" milliseconds");
        start = Instant.now();
        System.out.println(getNumOfLinesThreads(output));
        end = Instant.now();
        System.out.println("Time taken numoflinesthreads: "+ Duration.between(start, end).toMillis() +" milliseconds");
        start = Instant.now();
        System.out.println(getNumOfLinesThreadPool(output));
        end = Instant.now();
        System.out.println("Time taken numoflinesthreadpool: "+ Duration.between(start, end).toMillis() +" milliseconds");

    }
}