package com.example.ejemploclasehilos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import Modelo.Usuario;

public class MainActivity2 extends AppCompatActivity {

    Button botonJuego;
    TextView mostrarPuntuacion;
    boolean nivel2Mostrado = false;

    int puntuacion = 0;

    String usuarioId;
    String nombreUsuario;
    String passwordUsuario;

    FirebaseDatabase database;

    DatabaseReference usuarioRef;

    TextView ranking1;
    TextView ranking2;
    TextView ranking3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //String nombreUsuario = getIntent().getStringExtra("Usuario");





        botonJuego = findViewById(R.id.botonJuego);

        TextView textView = findViewById(R.id.textView3);

        //mostrarPuntuacion = findViewById(R.id.mostrarPuntuacion);

        // conectar con la abse de datos
        database = FirebaseDatabase.getInstance("https://juegopsp-c4db0-default-rtdb.europe-west1.firebasedatabase.app/");

        // recogemos los datos
        usuarioId = getIntent().getStringExtra("usuarioId");
        nombreUsuario = getIntent().getStringExtra("Usuario");
        passwordUsuario = getIntent().getStringExtra("Contraseña");
        puntuacion = getIntent().getIntExtra("Puntuacion", 0);

        // esto es para referirnos al usuario que tiene sesion iniciada, para luego poder actualizar su puntuacion
        usuarioRef = database.getReference("usuarios/usuario").child(usuarioId);


        // para mostrar la puntuacion por pantalla.
        mostrarPuntuacion = findViewById(R.id.mostrarPuntuacion);
        mostrarPuntuacion.setText(String.valueOf(puntuacion));

        ranking1 = findViewById(R.id.ranking1);

        ranking2 = findViewById(R.id.ranking2);

        ranking3 = findViewById(R.id.ranking3);



        botonJuego.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                if (puntuacion < 100) {
                    puntuacion ++;

                    // Mostrar el toast solo la primera vez cuando lleguemos a 100
                    if (puntuacion == 100 && !nivel2Mostrado) {
                        Toast.makeText(MainActivity2.this,
                                "Acabas de subir al nivel 2, ahora cuenta por 2",
                                Toast.LENGTH_SHORT).show();
                        nivel2Mostrado = true;

                    }
                }else{
                    puntuacion +=2;
                }

                mostrarPuntuacion.setText(String.valueOf(puntuacion));

                // para actualizar la base de datos
                String valor = nombreUsuario + ";" + passwordUsuario + ";" + puntuacion;
                usuarioRef.setValue(valor);

                // esto es para cargar el raniknk
                DatabaseReference refRanking = database.getReference("usuarios/usuario");

                // esto es para decirle "Dame los datos una sola vez"
                refRanking.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {


                        // lista para guardar cada usuario con su puntuacion
                        ArrayList<Usuario> lista = new ArrayList<>();

                        //recorremos todos los usuarios de la base de datos
                        for (DataSnapshot hijo : snapshot.getChildren()) {

                            // cogemos el texto del usuario
                            String valor = hijo.getValue(String.class);
                            if (valor == null) continue;

                            // separamos el texto por ;
                            String[] partes = valor.split(";");
                            if (partes.length < 3) continue;

                            // aqui gaurdamos la primera parte, antes del punto y coma en nombre
                            String nombre = partes[0];

                            // aqui guardamos los puntos y los convertimos a int
                            int puntos = Integer.parseInt(partes[2]);

                            // metemos usuario a la lista
                            lista.add(new Usuario(nombre, puntos));
                        }

                        // para odenar de mayor a menor
                        Collections.sort(lista, (u1, u2) -> u2.puntuacion - u1.puntuacion);

                        // ahora enseñamos los 3 primeros en pantalla (si existen)
                        if (lista.size() > 0) {
                            Usuario u1 = lista.get(0); // El primero de la lista (más puntos)
                            ranking1.setText("1º " + u1.nombre + " - " + u1.puntuacion + " pts");
                        }

                        if (lista.size() > 1) {
                            Usuario u2 = lista.get(1); // Segundo en puntos
                            ranking2.setText("2º " + u2.nombre + " - " + u2.puntuacion + " pts");
                        }

                        if (lista.size() > 2) {
                            Usuario u3 = lista.get(2); // Tercer puesto
                            ranking3.setText("3º " + u3.nombre + " - " + u3.puntuacion + " pts");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {}
                });



            }
        });







        // 3. Poner el mensaje en pantalla
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            textView.setText("Bienvenido, " + nombreUsuario);
        } else {
            textView.setText("Bienvenido");
        }



        //String contraseña = getIntent().getStringExtra("Contraseña");

        Log.d("tag", nombreUsuario);

        Log.d("tag" , "" + puntuacion);

    }
}