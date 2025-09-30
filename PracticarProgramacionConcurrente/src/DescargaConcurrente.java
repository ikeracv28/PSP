public class DescargaConcurrente {
    public static void main(String[] args){
        // Crear hilos para simular descargas de múltiples archivos
        Thread descarga1= new Thread(new DescargaArchivo("archivo1.zip"));
        Thread descarga2= new Thread(new DescargaArchivo("archivo2.zip"));
        Thread descarga3= new Thread(new DescargaArchivo("archivo3.zip"));

        // iniciar los hilos ( inician las descargas )
        descarga1.start();
        descarga2.start();
        descarga3.start();

        //Esperar a que todas las descargas terminen
        try{
            descarga1.join();
            descarga2.join();
            descarga3.join();

        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Todas las descargas han finalizado");
    }
}
