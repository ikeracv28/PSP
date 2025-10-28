
import java.util.Scanner;

public class Tablas {
    Scanner scanner = new Scanner(System.in);
    public void dibujar(int numero){
        try{
            for (int i = 1; i <= 10; i++) {
                System.out.println(numero + " * " + i + " = " + numero * i + "\n");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}