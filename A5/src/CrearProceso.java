import java.io.IOException;
import java.time.LocalTime;

public class CrearProceso {
        public static void main(String[] args) throws IOException {
            ProcessBuilder proceso1 = new ProcessBuilder("java", "-cp", "out/production/A5", "Lanzador");
            ProcessBuilder proceso2 = new ProcessBuilder("java", "-cp", "out/production/A5", "Lanzador");
            ProcessBuilder proceso3 = new ProcessBuilder("java", "-cp", "out/production/A5", "Lanzador");

            proceso1.inheritIO();
            proceso2.inheritIO();
            proceso3.inheritIO();

            System.out.println("Lanzando proceso 1 a las: " + LocalTime.now());
            Process process1 = proceso1.start();

            System.out.println("Lanzando proceso 2 a las: " + LocalTime.now());
            Process process2 = proceso2.start();

            System.out.println("Lanzando proceso 3 a las: " + LocalTime.now());
            Process process3 = proceso3.start();
        }
}
