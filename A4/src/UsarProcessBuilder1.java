import java.io.*;

public class UsarProcessBuilder1 {
    public static void main(String[] args) throws IOException {
        try {
            // con buffer reader leemos el archivo
            BufferedReader brEntrada = new BufferedReader(new FileReader("entrada.txt"));
            int entradaInt = Integer.parseInt(brEntrada.readLine());


            // con esto creamos el archivo en el qyue nos va a hacer la tabla de multiplicar
            BufferedWriter brSalida = new BufferedWriter(new FileWriter("salida.txt"));
            // Bucle for para sacar la tabla de multiplicar
            for (int i = 1; i <= 10; i++) {
                int tabla = entradaInt * i;
                brSalida.write(entradaInt + " * " + i + " = " + tabla + "\n");
            }
            // Cerramos los buffer
            brEntrada.close();
            brSalida.close();

        } catch (Exception e) {
            // Si ocurre un error, crea un archivo y lo escribe ahi
            BufferedWriter brError = new BufferedWriter(new FileWriter("error.txt"));
            brError.write(e.getMessage());
            brError.close();
        }
    }
}


