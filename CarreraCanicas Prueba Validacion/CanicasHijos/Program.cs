using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Threading;

namespace CanicasHijo
{
    internal class Program
    {
        static void Main(string[] args)
        {
            // args[0] = pista
            // args[1] = número de canicas
            // args[2..] = países
            int pista = int.Parse(args[0]);
            int numCanicas = int.Parse(args[1]);

            List<string> paises = new List<string>();
            for (int i = 2; i < args.Length; i++)
                paises.Add(args[i]);

            long[] tiempos = new long[numCanicas];
            Thread[] hilos = new Thread[numCanicas];

            for (int i = 0; i < numCanicas; i++)
            {
                int indice = i;

                hilos[i] = new Thread(() =>
                {
                    Stopwatch crono = new Stopwatch();
                    crono.Start();

                    int posicion = 0;
                    while (posicion < 100)
                    {
                        posicion += Random.Shared.Next(1, 6);
                        Thread.Sleep(10);
                    }

                    crono.Stop();
                    tiempos[indice] = crono.ElapsedMilliseconds;
                });

                hilos[i].Start();
            }

            foreach (var h in hilos) h.Join();

            // Formato: pista;pais;tiempo
            for (int i = 0; i < numCanicas; i++)
                Console.WriteLine($"{pista};{paises[i]};{tiempos[i]}");
        }
    }
}
