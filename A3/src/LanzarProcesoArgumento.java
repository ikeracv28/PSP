public class LanzarProcesoArgumento {
    public static void main(String[] args) {
        try {
            // Nombre del archivo que se abrirá con el Bloc de notas
            String archivo = "documento.txt";

            // Ejecuta el Bloc de notas (notepad.exe) y le pasa el archivo como argumento
            // Esto abrirá 'documento.txt' en el Bloc de notas
            Process proceso = Runtime.getRuntime().exec("notepad.exe " + archivo);

            // Mensaje informativo indicando que el proceso fue lanzado
            System.out.println("Proceso lanzado: bloc de notas " + archivo);
        } catch (Exception e) {
            // Si ocurre un error al lanzar el proceso, imprime la traza del error
            e.printStackTrace();
        }
    }
}


