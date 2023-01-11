package EX2;
import java.io.File;
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
        File currentFile = new File(this.filename);
//        System.out.println("reading file: " + this.filename);
        Scanner lineScanner = new Scanner(currentFile);
        while (lineScanner.hasNextLine()) {
            this.totalLines++;
            lineScanner.nextLine();
        }
        lineScanner.close();
        return this.totalLines;
    }
}
