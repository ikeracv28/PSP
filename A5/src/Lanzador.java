

import java.util.Scanner;

public class Lanzador {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("De que numeros quieres la tabla:  ");
        System.out.println("Numero: ");
        int numero1 = scanner.nextInt();
        scanner.nextLine();

        Tablas tablas = new Tablas();
        tablas.dibujar(numero1);

    }
}