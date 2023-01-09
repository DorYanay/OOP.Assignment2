package EX2;
import java.io.*;
import java.util.Arrays;
import java.util.Random;
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
                writer.write(+j+"\n");
            }
            writer.close();
        }
        return filenames;
    }

    public static int getNumOfLines(String[] fileNames) throws IOException {
        int sumoflines = 0;
        for (int i = 0;i < fileNames.length;i++) {
            String filename = fileNames[i];
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while (reader.readLine() != null) {
                sumoflines++;
            }
            reader.close();
        }
        return sumoflines;
    }
    public static int getNumOfLinesThreads(String[] fileNames) throws InterruptedException {
        int n = fileNames.length;
        AtomicInteger sumoflines = new AtomicInteger();
        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            String filename = fileNames[i];
            threads[i] = new Thread()  {
                public void run() {

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(filename));
                    while (reader.readLine() != null) {
                        sumoflines.incrementAndGet();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }};
            threads[i].start();
        }
        for (int i = 0; i < n; i++) {
            threads[i].join();
        }
        return sumoflines.get();
    }



public int getNumOfLinesThreadPool(String[] fileNames){


        return 0;
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        String[] output = createTextFiles(10,1,10000);
        System.out.println(Arrays.toString(output));
        int numofr = getNumOfLines(output);
        int numoft = getNumOfLinesThreads(output);
        System.out.println(numofr);
        System.out.println(numoft);


    }
}