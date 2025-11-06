package com.example.ejemploclasehilos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button variable_que_guarda_un_boton;
    private TextView texto_donde_va_incrementar_1;

    private ImageView imagenAlfa;
    int concurrencia = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        variable_que_guarda_un_boton = findViewById(R.id.buttonGuapardo);

        texto_donde_va_incrementar_1 = findViewById(R.id.textView);

        imagenAlfa = findViewById(R.id.imageView);

        int cpu_cores = Runtime.getRuntime().availableProcessors();



        texto_donde_va_incrementar_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MiHiloPausa().start();
            }
        });

        variable_que_guarda_un_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new MiHiloPausa().start();
                //AsyncTask a = new MiClaseAsincrona().execute();
                if(cpu_cores >concurrencia){
                    AsyncTask a = new MiClaseAsincrona().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    concurrencia ++;
                }


            }
        });

    }

    private class MiClaseAsincrona extends AsyncTask<String, Integer, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //texto_donde_va_incrementar_1.setText("Comienza la cuenta atras");
            if(texto_donde_va_incrementar_1 != null)
                Toast.makeText(MainActivity.this, "Comienza el cambio del alfa de la imagen", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected String doInBackground(String... strings) {

            int i = 0;
            while(i <=100)
            {
                // este le pasa, en este caso el Integer, a onProgressUpdate
                publishProgress(i);
                try{
                    Thread.sleep(500);
                    i++;
                }catch (InterruptedException e){
                    return e.getLocalizedMessage();
                }
            }
            return "Se acabo de mostrar la imagen";


//            int i = 0;
//            while(i <=20)
//            {
//                // este le pasa, en este caso el Integer, a onProgressUpdate
//                publishProgress(i);
//                try{
//                    Thread.sleep(1000);
//                    i++;
//                }catch (InterruptedException e){
//                    return e.getLocalizedMessage();
//                }
//            }
//            return "Se acabo la cuenta atras";
        }

        @Override
        // values es el valor que le pasamos en publishProgress(i)
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(imagenAlfa != null){
                texto_donde_va_incrementar_1.setText("El alfa de la imagen es de: " + values[0]);
                imagenAlfa.setImageAlpha(values[0]);

            }

//            if(texto_donde_va_incrementar_1 != null){
//                texto_donde_va_incrementar_1.setText("Contador : " + values[0] * 1000);
//            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //texto_donde_va_incrementar_1.setText(s);
            texto_donde_va_incrementar_1.setText(s);
            concurrencia --;

        }
    }



    class MiHiloPausa extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                for (int i= 0; i <= 2000; i++){
                    int valorActual = i;

                    // Actualizamos el TextView en el hilo principal
                    runOnUiThread(() -> {
                        texto_donde_va_incrementar_1.setText(String.valueOf(valorActual));
                    });

                    Thread.sleep(10); // para que se vea el incremento



                }

                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "TERMINÉ EL CONTADOR", Toast.LENGTH_SHORT).show()
                );
//                Thread.sleep(10000);
//                // Vuelve al hilo principal para tocar la UI
//                runOnUiThread(() ->
//                        Toast.makeText(MainActivity.this, "TERMINÉ EL SLEEP", Toast.LENGTH_SHORT).show()
//                );



            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}