using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Net.Sockets;



namespace TCPServerBueno
{
    internal class Program
    {
        static void Main(string[] args)
        {
            //servidor que escucha(que cualquiera se puede conectar a el) en cualquier IP local
            TcpListener miServerGuapo = new TcpListener(IPAddress.Any, 5000);

            // abre el puerto y empieza a escuchar conexiones entrantes 
            miServerGuapo.Start();
            Console.WriteLine("Esperando a que se conecte mi pana...");

            // cualquiera que se conecte me lo guardara en cliente
            // AcceptTcpClient es una llamada bloqueante
            // se queda esperando a qye un cliente se conecte
            //cuando alguien se conecta devuelve el tcpClient
            TcpClient cliente = miServerGuapo.AcceptTcpClient();
            Console.WriteLine("Cliente conectado");

            //obtener el flujo de datos asociado en el cliente especifico
            // todo lo que escriba en flujoDatos se envia al cliente
            NetworkStream flujoDatos = cliente.GetStream();

            // esto lo convierte a bytes la cadena de texto
            //byte[] puroDataChorizo = Encoding.UTF8.GetBytes("Soy el server, klk manin...");
            //flujoDatos.Write(puroDataChorizo, 0, puroDataChorizo.Length);


            bool salir = true;

            string mensaje = "";

            while (salir)
            {
                // esto es para lo que escriba por la consola del servidor 
                // le salga en la consola del cliente
                
                mensaje = Console.ReadLine();

                byte[] mensajePorConsola = Encoding.UTF8.GetBytes(mensaje);
                flujoDatos.Write(mensajePorConsola, 0, mensajePorConsola.Length);
            }
           


            //cierra la conexion con el clientardo
            cliente.Close();

            //cierra el puerto y deja de escuchar
            miServerGuapo.Stop();

        }
    }
}
