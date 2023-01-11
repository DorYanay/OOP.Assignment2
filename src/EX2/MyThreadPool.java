package EX2;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.Callable;
        import java.io.File;
        import java.util.Scanner;
        import java.util.concurrent.Callable;

public class MyThreadPool implements Callable {
    private String filename;
    private int totalLines;
    public MyThreadPool(String filename){
        this.filename = filename;
    }

    @Override
    public Object call() throws Exception {
        if (this.filename == null) {
            this.totalLines =0;
            return null;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while (reader.readLine() != null) {
                totalLines++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.totalLines;
    }
}
