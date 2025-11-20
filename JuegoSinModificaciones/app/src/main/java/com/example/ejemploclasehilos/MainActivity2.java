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

        // botonJuego button
        botonJuego = findViewById(R.id.botonJuego);

        TextView textView = findViewById(R.id.textView3);

        // Connect to the database
        database = FirebaseDatabase.getInstance("https://juegopsp-c4db0-default-rtdb.europe-west1.firebasedatabase.app/");

        // Retrieve data passed from previous Activity
        usuarioId = getIntent().getStringExtra("usuarioId");
        nombreUsuario = getIntent().getStringExtra("Usuario");
        passwordUsuario = getIntent().getStringExtra("Contraseña");
        puntuacion = getIntent().getIntExtra("Puntuacion", 0);

        // Reference the currently logged-in user so we can update their score later
        usuarioRef = database.getReference("usuarios/usuario").child(usuarioId);

        // Show the score on screen
        mostrarPuntuacion = findViewById(R.id.mostrarPuntuacion);
        mostrarPuntuacion.setText(String.valueOf(puntuacion));

        ranking1 = findViewById(R.id.ranking1);
        ranking2 = findViewById(R.id.ranking2);
        ranking3 = findViewById(R.id.ranking3);

        botonJuego.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (puntuacion < 100) {
                    puntuacion++;

                    // Show the toast only the first time we reach 100
                    if (puntuacion == 100 && !nivel2Mostrado) {
                        Toast.makeText(MainActivity2.this,
                                "Acabas de subir al nivel 2, ahora cuenta por 2",
                                Toast.LENGTH_SHORT).show();
                        nivel2Mostrado = true;

                    }
                } else {
                    puntuacion += 2;
                }

                mostrarPuntuacion.setText(String.valueOf(puntuacion));

                // Update the database with the new value
                String valor = nombreUsuario + ";" + passwordUsuario + ";" + puntuacion;
                usuarioRef.setValue(valor);

                // Load the ranking
                DatabaseReference refRanking = database.getReference("usuarios/usuario");

                // Ask Firebase to give us the data only once
                refRanking.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        // List to store each user with their score
                        ArrayList<Usuario> lista = new ArrayList<>();

                        // Loop through all users in the database
                        for (DataSnapshot hijo : snapshot.getChildren()) {

                            // Get the text value of the user
                            String valor = hijo.getValue(String.class);
                            if (valor == null) continue;

                            // Split the text by ';'
                            String[] partes = valor.split(";");
                            if (partes.length < 3) continue;

                            // First part before the semicolon is the name
                            String nombre = partes[0];

                            // Third part is the score, convert it to int
                            int puntos = Integer.parseInt(partes[2]);

                            // Add user to the list
                            lista.add(new Usuario(nombre, puntos));
                        }

                        // Sort from highest score to lowest
                        Collections.sort(lista, (u1, u2) -> u2.puntuacion - u1.puntuacion);

                        // Show the top 3 users on screen (if they exist)
                        if (lista.size() > 0) {
                            Usuario u1 = lista.get(0); // First in the list (most points)
                            ranking1.setText("1º " + u1.nombre + " - " + u1.puntuacion + " puntos");
                        }

                        if (lista.size() > 1) {
                            Usuario u2 = lista.get(1); // Second place
                            ranking2.setText("2º " + u2.nombre + " - " + u2.puntuacion + " puntos");
                        }

                        if (lista.size() > 2) {
                            Usuario u3 = lista.get(2); // Third place
                            ranking3.setText("3º " + u3.nombre + " - " + u3.puntuacion + " puntos");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {}
                });
            }
        });

        // Show welcome message on screen
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            textView.setText("Bienvenido, " + nombreUsuario);
        } else {
            textView.setText("Bienvenido");
        }

        Log.d("tag", nombreUsuario);
        Log.d("tag", "" + puntuacion);
    }
}
