package com.example.ejemploclasehilos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaginaRegistro extends AppCompatActivity {

    EditText nombreRegistro;
    EditText contraseñaRegistro;

    Button botonCrearCuenta;

    int puntuacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagina_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Firebase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance(
                "https://juegopsp-c4db0-default-rtdb.europe-west1.firebasedatabase.app/"
        );

        nombreRegistro = findViewById(R.id.nombreRegistro);
        contraseñaRegistro = findViewById(R.id.contraseñaRegistro);
        botonCrearCuenta = findViewById(R.id.botonCrearCuenta);

        botonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Read what the user has typed in the EditTexts
                String nuevoNombreUsuario = nombreRegistro.getText().toString();
                String nuevaContraseñaEscrita = contraseñaRegistro.getText().toString();

                // Check that both username and password are filled in
                if (nuevoNombreUsuario.isEmpty() || nuevaContraseñaEscrita.isEmpty()) {
                    Toast.makeText(PaginaRegistro.this,
                            "Rellena usuario y contraseña",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //DatabaseReference usuariosRef = database.getReference("usuarios/usuario");

                // Check that we are not creating a user that already exists
                // Reference to the "usuario" node in the database
                DatabaseReference usuarioRef = database.getReference("usuarios/usuario");

//                DatabaseReference myRef = usuarioRef.push();
//                myRef.setValue(nuevoNombreUsuario + ";" + nuevaContraseñaEscrita);

                // Read the data from Firebase only once
                usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        boolean usuarioYaRegistrado = false;

                        // dataSnapshot contains ALL children: ID1, ID2, ID3...
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {

                            // This gets each string "Nombre;Pass"
                            String valor = hijo.getValue(String.class);

                            // If the value is null, skip to the next one
                            if (valor == null) continue;

                            // Split the value into name and password
                            String[] split = valor.split(";");

                            // Make sure there are at least two parts
                            if (split.length < 2) continue;

                            // First part before the semicolon is the username
                            String nombreBD = split[0];

                            // Second part after the semicolon is the password
                            String passBD = split[1];

                            // puntuacion = Integer.parseInt(split[2]);

                            // Compare with what the user typed, and if it matches
                            if (nuevoNombreUsuario.equals(nombreBD)) {

                                // Mark that the user was found and exit the loop
                                usuarioYaRegistrado = true;
                                break; // we have already found the user
                            }
                        }

                        // If the user is already registered, show a message and go to the next screen
                        if (usuarioYaRegistrado) {
                            Toast.makeText(PaginaRegistro.this,
                                    "Usuario ya esta registrado anteriormente",
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(PaginaRegistro.this, MainActivity1.class);
                            // Pass these values to the next screen
                            i.putExtra("Usuario", nuevoNombreUsuario);
                            // i.putExtra("Contraseña", contrasenaEscrita);
                            startActivity(i);

                        } else if (!usuarioYaRegistrado) {
                            // If the user does not exist, create it
                            DatabaseReference myRef = usuarioRef.push();
                            String usuarioId = myRef.getKey(); // ← User ID in Firebase

                            int puntuacionInicial = 0;

                            // Save username, password and initial score in Firebase
                            myRef.setValue(nuevoNombreUsuario + ";" +
                                    nuevaContraseñaEscrita + ";" +
                                    puntuacionInicial);

                            Toast.makeText(PaginaRegistro.this,
                                    "Registro completado con exito",
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(PaginaRegistro.this, MainActivity2.class);

                            // Pass these values to the next screen
                            i.putExtra("usuarioId", usuarioId);
                            i.putExtra("Usuario", nuevoNombreUsuario);
                            i.putExtra("Contraseña", nuevaContraseñaEscrita);
                            i.putExtra("Puntuacion", puntuacionInicial);

                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(PaginaRegistro.this,
                                "Error al leer de Firebase",
                                Toast.LENGTH_SHORT).show();
                        Log.w("TAG", "Failed to read value.", error.toException());
                    }

                });
            }
        });
    }
}
