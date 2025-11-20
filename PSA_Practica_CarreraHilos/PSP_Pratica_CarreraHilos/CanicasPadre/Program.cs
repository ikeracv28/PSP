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
            // Decimos cuántas pistas y cuántas canicas tiene cada pista
            int numPistas = 3;
            int canicasPorPista = 3;

            // Archivo .exe del proyecto hijo
<<<<<<< HEAD
            string exeHijo = @"C:\repositorio\PSP\PSA_Practica_CarreraHilos\PSP_Pratica_CarreraHilos\CanicasHijo\bin\Debug\net8.0\CanicasHijo.exe";
=======
            string exeHijo = @"C:\Repositorio\PSP\PSA_Practica_CarreraHilos\PSP_Pratica_CarreraHilos\CanicasHijo\bin\Debug\net8.0\CanicasHijo.exe";
>>>>>>> 56bd556 (subida de proyecto moviles despues de apto)

            // Aquí guardaremos TODOS los resultados
            List<Resultado> lista = new List<Resultado>();

            for (int pista = 1; pista <= numPistas; pista++)
            {
                Console.WriteLine($"[Padre] Lanzando proceso hijo para la pista {pista}...");

                // Cronómetro del proceso hijo
                Stopwatch cronoHijo = new Stopwatch();
                cronoHijo.Start();


                // Preparamos los argumentos para el hijo
                string argumentos = $"{pista} {canicasPorPista}";

                // Configuramos el proceso hijo
                ProcessStartInfo psi = new ProcessStartInfo();
                psi.FileName = exeHijo; // Especifica el archivo del ejecutable del hijo
                psi.Arguments = argumentos; // Pasa los argumentos preparados al hijo
                psi.UseShellExecute = false; // No usa el shell del sistema, lo que permite redirigir la salida
                psi.RedirectStandardOutput = true; // Redirige la salida estándar para leerla en el padre


                // Lanzamos el proceso hijo (la pista)
                Process p = Process.Start(psi);

                // Leemos lo que escribe el hijo
                string salida = p.StandardOutput.ReadToEnd();
                // esperamos a que termine de ejecutarse
                p.WaitForExit();


                cronoHijo.Stop();
                Console.WriteLine($"[Padre] Pista {pista} terminó en {cronoHijo.ElapsedMilliseconds} ms\n");

                // Procesamos cada línea
                string[] lineas = salida.Split('\n');
                foreach (string l in lineas)
                {
                    // si la linea esta vacia, seguimos
                    if (string.IsNullOrWhiteSpace(l)) continue;

                    // creamos lissta partes, y cada vez que encuentre; es una parte
                    string[] partes = l.Split(';');

                    lista.Add(new Resultado
                    {
                        Pista = int.Parse(partes[0]),
                        Canica = int.Parse(partes[1]),
                        Tiempo = long.Parse(partes[2])
                    });
                }
            }

            // Ordenamos por tiempo
            var ranking = lista.OrderBy(r => r.Tiempo).ToList();

            Console.WriteLine("\n=== CLASIFICACIÓN GLOBAL ===\n");

            int pos = 1;
            foreach (var r in ranking)
            {
                Console.WriteLine($"{pos}. Pista {r.Pista} - Canica {r.Canica} - Tiempo: {r.Tiempo} ms");
                pos++;
            }

            Console.WriteLine("\nTerminado.");
        }
    }

    class Resultado
    {
        public int Pista { get; set; }
        public int Canica { get; set; }
        public long Tiempo { get; set; }
    }
}
