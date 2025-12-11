using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Net.Sockets;


namespace Ejemplo_TCP_Cliente
{
    internal class Program
    {
        static void Main(string[] args)
        {
            //creamos cliente tcp sin conectarme aun
            TcpClient clientardo = new TcpClient();

           
            //intento conectar al server que este en la ip (localHost, es decir, ya mismo
            //se bloquea hasta que se conecta
            clientardo.Connect("127.0.0.1", 5000);

            //obtengo el flujo de datos para recibir datos
            NetworkStream flujillo = clientardo.GetStream();

       
            byte[] yoQuieroBuffer = new byte[1024];

            bool salir = true;

            while (salir)
            {
                // Read se bloquea hasta que el server envie algo
                // devuelve el numero de bytes leidos / recibidos
                int cosas_leidas = flujillo.Read(yoQuieroBuffer, 0, yoQuieroBuffer.Length);

                string mensajardo;

                mensajardo = Encoding.UTF8.GetString(yoQuieroBuffer, 0, cosas_leidas);

                Console.WriteLine("El server te dice esto macho: " + mensajardo);
            }
           
        }
    }
}
