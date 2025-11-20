using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;

namespace CanicasPadre
{
    internal class Program
    {
        static void Main(string[] args)
        {
            int numPistas = 2;
            int canicasPorPista = 5;

            // 🔥 Países fijos que tú has indicado
            string[] paises = new string[]
            {
                "Argentina", "España", "Brasil", "Holanda", "Mexico"
            };

            string exeHijo = @"C:\repositorio\PSP\CarreraCanicas\CanicasHijos\bin\Debug\net8.0\CanicasHijos.exe";

            List<Resultado> lista = new List<Resultado>();

            for (int pista = 1; pista <= numPistas; pista++)
            {
                Console.WriteLine($"[Padre] Lanzando proceso hijo para la pista {pista}...");

                Stopwatch crono = new Stopwatch();
                crono.Start();

                // Pasamos: pista + cantidad + lista de países
                string argumentos = $"{pista} {canicasPorPista} " + string.Join(" ", paises);

                ProcessStartInfo psi = new ProcessStartInfo
                {
                    FileName = exeHijo,
                    Arguments = argumentos,
                    UseShellExecute = false,
                    RedirectStandardOutput = true
                };

                Process p = Process.Start(psi);
                string salida = p.StandardOutput.ReadToEnd();
                p.WaitForExit();

                crono.Stop();
                Console.WriteLine($"[Padre] Pista {pista} terminó en {crono.ElapsedMilliseconds} ms\n");

                foreach (var linea in salida.Split('\n'))
                {
                    if (string.IsNullOrWhiteSpace(linea)) continue;

                    string[] partes = linea.Split(';');

                    lista.Add(new Resultado
                    {
                        Pista = int.Parse(partes[0]),
                        Pais = partes[1],
                        Tiempo = long.Parse(partes[2])
                    });
                }
            }

            // Orden global
            var ranking = lista.OrderBy(r => r.Tiempo).ToList();

            Console.WriteLine("\n=== CLASIFICACIÓN GLOBAL ===");
            int pos = 1;
            foreach (var r in ranking)
            {
                Console.WriteLine($"{pos}. Pista {r.Pista} - {r.Pais} - {r.Tiempo} ms");
                pos++;
            }

            // Mostrar el país más rápido
            var ganador = ranking.First();
            Console.WriteLine($"\n🏆 País más rápido: {ganador.Pais} (Pista {ganador.Pista}) con {ganador.Tiempo} ms");
        }
    }

    class Resultado
    {
        public int Pista { get; set; }
        public string Pais { get; set; }
        public long Tiempo { get; set; }
    }
}
