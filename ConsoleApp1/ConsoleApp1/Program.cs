using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EjerciciosRPMI
{
    class ListaEjercicios
    {
        public void EjercicioDeEjemplo()
        {
            //declaracion de variable de tipo string
            string hi;
            //declaracion e inicialización de variable de tipo string
            string hola = "5";
            //asignación/igualación de variable ya declarada
            hi = "asdfasdf";
            //Ejemplo de mostrar información por consola
            Console.WriteLine("Dame un valor: ");
            //asignación/igualación de variable ya declarada al valor dado por el usuario a través de la consola
            hi = Console.ReadLine();

            //declaracion de variable de tipo entero
            int oh;
            //asignación/igualazción de variable ya declarada, al valor del usuario transformado en int
            oh = Convert.ToInt32(hi);

            //Ejemplo de condicional. "Si el valor de oh es mayor que 0..."
            if (oh > 0)
            {
                Console.WriteLine("El valor de oh es mayor que 0");

                //Ejemplo de mostrar valores de variables por consola
                Console.WriteLine(oh);

                //Ejemplo de mostrar valores literales y de variables por consola
                Console.WriteLine("El valor de oh es: " + oh);
            }
            else
            {
                //En caso de que no se cumpla la condicion del "if(condición)", se realiza el "else"

                Console.WriteLine("el valor de oh es menor que 0");
            }

            //Ejemplo condicional con puerta AND
            //"Si el valor de oh es mayor que 0 Y el valor del oh es menor que 100...
            if (oh > 0 && oh < 100)
            {
                Console.WriteLine("el valor de oh es mayor que 0 y menor que 100");
            }

            //Ejemplo condicional con puerta OR
            //"Si el valor de oh es mayor que 0 O el valor del oh es menor que 100...
            if (oh > 0 || oh < 100)
            {
                Console.WriteLine("el valor de oh es mayor que 0 o menor que 100");
            }


            //Ejemplo de pedir valor al usuario, transformarlo en numérico y preguntar si es divisible entre 2
            Console.WriteLine("Dame un valor y te dire si es divisible entre 2: ");//pedir al usuario
            string uservalue = Console.ReadLine();//Dar al usuario la capacidad de escribir y almacenamos su introducción de carácteres
            int numericvalue = Convert.ToInt32(uservalue); //Transformación del valor de tipo string dado por el usuario en un valor numérico de tipo entero (int)
            if (numericvalue % 2 == 0)//"Si numericvalue dividido entre 2 da de resto 0...
            {
                Console.WriteLine(numericvalue + " es divisible entre 2");
            }

            //Ejemplo de bucle while(){}
            int numero = 0;
            while (numero < 5)//"mientras que el valor de la variable numero sea menor que 5, repite el bucle...
            {
                numero = numero + 1;//la variable numero se acumula sobre si misma de 1 en 1
                Console.WriteLine("La variable numero es igual a : " + numero);
            }

            //Ejemplo de bucle for(){}
            for (int i = 0; i < 5; i = i + 1)//"Para la variable i igual a 0, mientras i sea menor que 5, repite el bucle y al terminar suma 1 a su valor actual..."
            {
                Console.WriteLine("la variable i es igual a : " + i);
            }


            //Ejemplo de pedir al usuario que pulse una tecla para evitar que se cierre la consola al terminar el programa
            Console.ReadKey();
        }

        //EJERCICIOS------------------------------------------------------------------------
        //Condicionales
        public void Ejercicio0()
        {
            //1. Pedir un valor numérico al usuario
            //2. Transformar el varlor tipo "string" dado por el usuario en un valor de tipo entero
            //3. Comprobar si dicho valor es positivo o negativo
            //4. Mostrar por consola si el resultado es positivo o negativo: "Es positivo" o "Es negativo"
            bool verificado = false;
            int resultado = 0;
            while (!verificado)
            {
                Console.Write("\nDame un valor númerico, gracias.\n");
                string hi = Console.ReadLine();
                verificado = int.TryParse(hi, out resultado);
            }
            if (resultado > 0)
            {
                Console.WriteLine("\nEl valor " + resultado + " es positivo.\n");
            }
            else if (resultado == 0)
            {
                Console.WriteLine("\nEl valor " + resultado + " es 0.\n");
            }
            else
            {
                Console.WriteLine("\nEl valor " + resultado + " es negativo.\n");
            }
        }

        public void Ejercicio1()
        {
            //1. Verificar rango:
            //2. Pide al usuario que ingrese un número.
            //3. Comprueba si el número está en el rango de 1 a 100 (inclusive).
            //4. Muestra por consola si el número está dentro o fuera del rango.

            bool verificado = false;
            int numero = 0;
            while (!verificado )
            {
                Console.Write("\nIngresa un numero.\n");
                string hi = Console.ReadLine();
                verificado = int.TryParse(hi, out numero);
                if (numero >= 1 && numero <= 100)
                {
                    Console.WriteLine("El numero esta entre el 1 y el 100, incluidos");
                }
                else
                {
                    Console.WriteLine("El numero no esta entre 1 y 100");
                }
            }
           

        }

        public void Ejercicio2()
        {
            //1. Pedir dos valores numérico al usuario
            //2. Transformar los varlores tipo "string" dados por el usuario en valores de tipo entero
            //3. Sumar ambos valores
            //4. Mostrar por consola si el resultado es positivo o negativo: "Es positivo" o "Es negativo"

            bool verificado = false;
            int numero1 = 0;
            int numero2 = 0;
            int resultadoSuma = 0;
            while (!verificado)
            {
                Console.Write("\nDame un valor númerico, gracias.\n");
                string valor1 = Console.ReadLine();
                Console.Write("\nDame un segundo valor númerico, gracias.\n");
                string valor2 = Console.ReadLine();
                if (int.TryParse(valor1, out numero1) && int.TryParse(valor2, out numero2))
                {
                   
                    resultadoSuma = numero1 + numero2;
                    Console.WriteLine("El resultado de la suma es " + resultadoSuma);
                    verificado = true;

                    if (resultadoSuma > 0)
                    {
                        Console.WriteLine("El resultado es positivo");
                    }
                    else if(resultadoSuma == 0)
                    {
                        Console.WriteLine("El resultado es = 0");
                    }
                    else
                    {
                        Console.WriteLine("El resultado es negativo");
                    }
                    

                }
            }
            
        }
        public void Ejercicio3()
        {
            //1. Pedir un valor numérico al usuario
            //2. Transformar el varlor tipo "string" dado por el usuario en un valor de tipo entero
            //3. Comprobar si dicho valor es par o impar
            //4. Mostrar por consola el resultado "Es par" o "Es impar"
        }
        public void Ejercicio4()
        {
            //1. Pide al usuario que ingrese las longitudes de los tres lados de un triángulo.
            //2. Clasifica el triángulo como equilátero, isósceles o escaleno según sus lados.
            //3. Mostrar por consola el resultado: "Es equilátero", "Es isósceles" o "Es escaleno"

        }

        public void Ejercicio5()
        {
            //1. Pide al usuario que ingrese 3 valores numéricos
            //2. Transformar las 3 strings en valores enteros
            //3. Comprobar que valor es el más alto
            //4. Mostar por consola dicho valor
        }


        //Bucles
        public void Ejercicio6()
        {
            //1. Por medio de un bucle while mostrar por consola el siguiente resultado:
            //123456789
        }
        public void Ejercicio7()
        {
            //1. Pedir al usuario un valor numérico
            //2. Transformar dicho valor string en un valor entero
            //2. Por medio de un bucle while mostrar por consola del 0 al número dado por el usuario

        }
        public void Ejercicio8()
        {
            //1. Pedir al usuario un valor numérico
            //2. Transformar dicho valor string en un valor entero
            //3. Utiliza un bucle while para sumar todos los números desde 1 hasta ese número (inclusive).
            //4. Muestra el resultado de la suma.

        }
        public void Ejercicio9()
        {
            //1. Pedir al usuario un valor numérico
            //2. Transformar dicho valor string en un valor entero
            //3. Utiliza un bucle for para mostrar por consola la tabla de multiplicar de ese número del 1 al 10. 

        }
        public void Ejercicio10()
        {
            //1. Pedir al usuario un valor numérico
            //2. Transformar dicho valor string en un valor entero
            //3. Utiliza un bucle for para realizar un conteo regresivo desde ese número hasta 0.
            //4. Muestra cada número en la consola.
        }

        //Arrays

        public void Ejercicio11()
        {
            //1.solicite al usuario ingresar 5 números enteros
            //2. Almacena estos números en un array.
            //3. Luego, muestra en pantalla la suma de todos los elementos del array.

        }

        public void Ejercicio12()
        {
            //1. declare un array de 10 caracteres
            //2. Llena el array con letras del alfabeto
            //3. muestra en pantalla las letras almacenadas en las posiciones pares del array (0, 2, 4, etc.).


        }

        public void Ejercicio13()
        {
            //1. declare un array de 8 números enteros.
            //2. calcula la media de los números almacenados en posiciones impares del array (1, 3, 5, etc.).
            //3. muestralos en pantalla

        }

        public void Ejercicio14()
        {
            //1. declare dos arrays de la misma longitud
            //2. Llena el primer array con nombres de ciudades y el segundo array con la población de cada ciudad. (inventado)
            //3. Muestra en pantalla la ciudad con la mayor población.
        }

        public void Ejercicio15()
        {
            //1. solicite al usuario ingresar un número entero.
            //2. crea un array que almacene los primeros N números primos, donde N es el número ingresado por el usuario
            //3. Muestra los valores almacenados del array por pantalla
        }


    }

    class Program
    {
        static void Main(string[] args)
        {
            //Ejemplo de declaración de objeto
            ListaEjercicios ejercicios = new ListaEjercicios();
            //Ejemplo de llamada de función:
            //ejercicios.EjercicioDeEjemplo();//Comentar con "//" al principio de linea cuando llames a tus funciones
            //Continuar aquí
            //ejercicios.Ejercicio1();
            ejercicios.Ejercicio2();



            //No borrar
            Console.ReadKey();
        }
    }
}