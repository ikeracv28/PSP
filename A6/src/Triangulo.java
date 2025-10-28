import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Triangulo {

    public String imprimirTriangulos(int x){
        StringBuilder sb = new StringBuilder();
        for(int i = x; i >= 1; i--){
            for(int j = 1; j <= i; j++){
                sb.append(j);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public void salida(int x) {
        try (BufferedWriter r1 = new BufferedWriter(new FileWriter("salida"+x+".txt"))) {
            r1.write(imprimirTriangulos(x));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}