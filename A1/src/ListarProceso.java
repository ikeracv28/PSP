import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ListarProceso {
    public static void main(String[] args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
            Process process = processBuilder.start();
            // Ejecuta el comando 'tasklist' para obtener la lista de procesos en Windows
            //Process proceso = Runtime.getRuntime().exec("tasklist");

            // Crea un BufferedReader para leer la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String linea;

            // Lee e imprime cada línea de la salida del comando
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }

            // Cierra el BufferedReader
            reader.close();
        } catch (Exception e) {
            // Si ocurre un error, imprime la traza de la excepción
            e.printStackTrace();
        }
    }
}


