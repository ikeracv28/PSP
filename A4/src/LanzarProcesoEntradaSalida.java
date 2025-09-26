import java.io.*;

public class LanzarProcesoEntradaSalida {
    public static void main(String[] args) {
        try {
            // Crea el proceso para ejecutar la clase UsarProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "java",
                    "-cp",
                    ".",
                    "UsarProcessBuilder1"
            );
            // Inicia el proceso
            Process process = processBuilder.start();
            // Espera a que termine
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}