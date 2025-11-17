using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace EjemploDebugging
{
    internal class Program
    {
        static void Main(string[] args)
        {
            ProcesosGuapardos p = new ProcesosGuapardos();
            HilosAunMasGuapos hm = new HilosAunMasGuapos();
            contadorHilos ch = new contadorHilos();
            Thread hilo1 = new Thread(new ParameterizedThreadStart(hm.EjemploHilos1));
            Thread hilo2 = new Thread(new ParameterizedThreadStart(hm.EjemploHilos1));
            Thread hilo3 = new Thread(new ParameterizedThreadStart(hm.EjemploHilos1));

            hilo1.Start('a');
            hilo2.Start('b');
            hilo3.Start('c');

            /////////////////////////////////
            //Thread hc = new Thread(ch.hilosecundario);

            //hc.Start();
            //ch.hiloprincipal();

            /////////////////////////////////
            // p.ejemplosGuapardos();
            //int contador = 0;
            //if (args.Length < 2)
            //{
            //    Console.WriteLine("Uso: Subproceso_SumaParcial <inicio> <fin>");
            //    return;
            //}

            //int inicio = int.Parse(args[0]);
            //int fin = int.Parse(args[1]);
            //long suma = 0;

            //for (int i = inicio; i <= fin; i++)
            //{
            //    if(p.esPrimo(i))
            //    {
            //        contador ++;
            //    }

            //}

            //Console.WriteLine(contador);

        }
    }
    class ProcesosGuapardos
    {
        public bool esPrimo(int n)
        {
            if (n <= 1) return false;        // 0 y 1 no son primos

            for (int i = 2; i * i <= n; i++)
            {   // Basta llegar hasta raíz cuadrada
                if (n % i == 0)
                {
                    return false;             // Tiene divisor → NO es primo
                }
            }
            return true;                      // No tuvo divisores → SI es primo
        }
        public void ejemplosGuapardos()
        {
            Console.WriteLine("\nHola, que programa quieres abrir?");
            //Process paint = Process.Start("mspaint.exe");
            bool input = false;
            while (!input)
            {
                Console.WriteLine("\n1. Calculadora, \n2. Explorador carpetas \n3. Paint \n Enter para salir");
                ConsoleKeyInfo key = Console.ReadKey(true);
                if (key.Key == ConsoleKey.D1 || key.Key == ConsoleKey.NumPad1)
                {
                    Console.WriteLine("\nAbriendo Calculadora...");
                    Process paint = Process.Start("calc.exe");
                    paint.WaitForExit();
                    while (Console.KeyAvailable) //Limpiar el Buffer
                    {
                        Console.ReadKey(true);
                    }
                    Console.WriteLine("\nCerrando Calculadora");
                }
                else if (key.Key == ConsoleKey.D2 || key.Key == ConsoleKey.NumPad2)
                {
                    Console.WriteLine("\nAbriendo Explorador...");
                    Process paint = Process.Start("calc.exe");
                    paint.WaitForExit();
                    while (Console.KeyAvailable) //Limpiar el Buffer
                    {
                        Console.ReadKey(true);
                    }
                    Console.WriteLine("\nCerrando Explorador");
                }
                else if (key.Key == ConsoleKey.D3 || key.Key == ConsoleKey.NumPad3)
                {
                    Console.WriteLine("\nAbriendo Paint...");
                    Process paint = Process.Start("mspaint.exe");
                    paint.WaitForExit();
                    while (Console.KeyAvailable) //Limpiar el Buffer
                    {
                        Console.ReadKey(true);
                    }
                    Console.WriteLine("\nCerrando Paint");

                }
                else if (key.Key == ConsoleKey.Enter)
                {
                    input = true; break;
                }
                else { Console.WriteLine("No valido"); }
            }


        }


    }
    class HilosAunMasGuapos
    {
        public void EjemploHilos()
        {
            for (int i = 0; i <= 5; i++)
            {
                Console.WriteLine("Hilo secundario : i = " + i);
                Thread.Sleep(1000);
            }
            Console.WriteLine("Fin del hilo secundario");
        }

        public void EjemploHilos1(object valor)
        {
            char id = (char)valor;
            for (int i = 0; i <= 5; i++)
            {
                Console.WriteLine($"Hilo {id} => iteracion " + i);
                Thread.Sleep(200);
            }

        }

        public void EjemploHilos2(object valor)
        {
            char id = (char)valor;
            for (int i = 0; i <= 5; i++)
            {
                Console.WriteLine($"Hilo {id} => iteracion " + i);
                Thread.Sleep(200);

            }

        }
        public void EjemploTrabajoHiloPrincipalConEjHilo2()
        {
            Stopwatch cronoParalelo = new Stopwatch();
            cronoParalelo.Start();

            Thread hiloA = new Thread(() => EjemploHilos2("Hilo A", 1000));
            Thread hiloB = new Thread(() => EjemploHilos2("Hilo B", 1000));

            hiloA.Start();
            hiloB.Start();

            hiloA.Join();
            hiloB.Join();

            cronoParalelo.Stop();

            Console.WriteLine("Tiempo total (paralelo) :  " + cronoParalelo.ElapsedMilliseconds + " ms");

            Stopwatch cronoEnSecuencial = new Stopwatch();

            cronoEnSecuencial.Start();

            EjemploHilos2("Secuencial A", 1000);
            EjemploHilos2("Secuencial B", 1000);

            cronoEnSecuencial.Stop();
            Console.WriteLine("Tiempo total (secuencial) :  " + cronoEnSecuencial.ElapsedMilliseconds + " ms");
        }


        public void EjemploTrabajoHiloPrincipal()
        {
            for (int i = 0; i <= 5; i++)
            {
                Console.WriteLine("Hilo principal : i = " + i);
                Thread.Sleep(1000);
            }
            Console.WriteLine("Fin del hilo principal");
        }
    }

    class contadorHilos
    {
        public void hiloprincipal()
        {
            for (int i = 1; i <= 6; i++)
            {
                Thread.Sleep(10000);
                Console.WriteLine("Continuo trabajando");

            }
            Console.WriteLine("Fin del hilo secundario");
        }

        public void hilosecundario()
        {
            for (int i = 1; i <= 60; i++)
            {
                Console.WriteLine("Contador : i = " + i);
                Thread.Sleep(1000);
            }
            Console.WriteLine("Fin del contador");
        }
    }
}