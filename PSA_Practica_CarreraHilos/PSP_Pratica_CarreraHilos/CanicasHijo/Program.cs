using System;
using System.Diagnostics;
using System.Threading;

namespace CanicasHijo
{
    internal class Program
    {
        static void Main(string[] args)
        {
            // El proceso padre me pasa 2 datos:
            // args[0] = número de la pista
            // args[1] = cuántas canicas (hilos) tiene esta pista
            int pista = int.Parse(args[0]);
            int numCanicas = int.Parse(args[1]);

            // Array donde cada hilo guardará su tiempo
            long[] tiempos = new long[numCanicas];

            // Creamos un hilo por cada canica
            Thread[] hilos = new Thread[numCanicas];

            for (int i = 0; i < numCanicas; i++)
            {
                int idCanica = i + 1;
                int indice = i;

                hilos[i] = new Thread(() =>
                {
                    // Cronómetro para medir el tiempo de la canica
                    Stopwatch crono = new Stopwatch();
                    crono.Start();

                    // La canica avanza hasta llegar a 100
                    int posicion = 0;
                    Random rnd = new Random(idCanica * 100);

                    while (posicion < 100)
                    {
                        posicion += rnd.Next(1, 6); // avanza de 1 a 5
                        Thread.Sleep(10); // simula trabajo
                    }

                    crono.Stop();

                    // Guardamos el tiempo que tardó esta canica
                    tiempos[indice] = crono.ElapsedMilliseconds;
                });

                hilos[i].Start();
            }

            // Esperamos a que todas las canicas terminen
            foreach (var h in hilos) h.Join();

            // Imprimimos resultados EN FORMATO SIMPLE para que lo lea el proceso padre
            // Formato: pista;canica;tiempo
            for (int i = 0; i < numCanicas; i++)
            {
                int idCanica = i + 1;
                Console.WriteLine($"{pista};{idCanica};{tiempos[i]}");
            }
        }
    }
}