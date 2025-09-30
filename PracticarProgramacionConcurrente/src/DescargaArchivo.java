public class DescargaArchivo implements Runnable{
    private String nombreArchivo;

    public DescargaArchivo(String nombreArchivo){
        this.nombreArchivo = nombreArchivo;
    }
    @Override
    public void run(){
        System.out.println("Iniciando archivo de descarga: " + nombreArchivo);
        for (int i = 0; i<= 100; i+=20){
            try{
                // Simulamos que cada 20% de descarga tarda 1 segundo
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Descargando " + nombreArchivo + ": "+ i + "% completado");
        }
        System.out.println("Descarga completada de: " + nombreArchivo);
    }
}
