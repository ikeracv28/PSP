import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ProcessBuilder processs = new ProcessBuilder("java", "-cp","out/production/A6","Lanzador");
        Process proceso = processs.start();

    }
}
