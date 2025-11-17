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



    EditText nombreRegistro ;
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

        // Instancia de Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://juegopsp-c4db0-default-rtdb.europe-west1.firebasedatabase.app/");


        nombreRegistro = findViewById(R.id.nombreRegistro);
        contraseñaRegistro = findViewById(R.id.contraseñaRegistro);
        botonCrearCuenta = findViewById(R.id.botonCrearCuenta);


        botonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1. Leer lo que ha escrito el usuario en los EditText
                String nuevoNombreUsuario = nombreRegistro.getText().toString();
                String nuevaContraseñaEscrita = contraseñaRegistro.getText().toString();


                if (nuevoNombreUsuario.isEmpty() || nuevaContraseñaEscrita.isEmpty()) {
                    Toast.makeText(PaginaRegistro.this,
                            "Rellena usuario y contraseña",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //DatabaseReference usuariosRef = database.getReference("usuarios/usuario");




                // comprobacion de que no crear un usuario que ya existe
                // 2. Referencia al nodo "usuario" en la BBDD
                DatabaseReference usuarioRef = database.getReference("usuarios/usuario");

//                DatabaseReference myRef = usuarioRef.push();
//                myRef.setValue(nuevoNombreUsuario + ";" + nuevaContraseñaEscrita);

                // 3. Leer UNA SOLA VEZ los datos de Firebase
                usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        boolean usuarioYaRegistrado = false;

                        // dataSnapshot contiene TODOS los hijos: ID1, ID2, ID3...
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {

                            // Esto recoge cada string "Nombre;Pass"
                            String valor = hijo.getValue(String.class);

                            // si el valor es null continua al siguiente
                            if (valor == null) continue;

                            // esto separa el valor en nombre y contraseña
                            String[] split = valor.split(";");

                            // y con esto comporbamos que hay dos partes
                            if (split.length < 2) continue;

                            // aqui gaurdamos la primera parte, antes del punto y coma en nombre
                            String nombreBD = split[0];

                            // y despues del punto y cooma en contraseña
                            String passBD = split[1];

                            //puntuacion = Integer.parseInt(split[2]);

                            // Comparamos con lo escrito por el usuario, y si coincide
                            if (nuevoNombreUsuario.equals(nombreBD)) {

                                // cambiamos el usuario encontrado a true y salimos del bucle
                                usuarioYaRegistrado = true;
                                break; // ya hemos encontrado al usuario
                            }
                        }

                        // si el login es true, mostramos un mensaje y pasamos a la siguiente pantalla
                        if (usuarioYaRegistrado) {
                            Toast.makeText(PaginaRegistro.this,
                                    "Usuario ya esta registrado anteriormente",
                                    Toast.LENGTH_SHORT).show();


                            Intent i = new Intent(PaginaRegistro.this, MainActivity1.class);
                            // esto es para pasarle estos valores a la siguiente pantalla
                            i.putExtra("Usuario", nuevoNombreUsuario);
                            //i.putExtra("Contraseña", contrasenaEscrita);
                            startActivity(i);

                        } else if (!usuarioYaRegistrado) {
                            // si el usuario no existe lo creamos
                            DatabaseReference myRef = usuarioRef.push();
                            String usuarioId = myRef.getKey(); // ← ID del usuario en Firebase

                            int puntuacionInicial = 0;


                            myRef.setValue(nuevoNombreUsuario + ";" + nuevaContraseñaEscrita + ";" + puntuacionInicial);



                            Toast.makeText(PaginaRegistro.this,
                                    "Registro completado con exito",
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(PaginaRegistro.this, MainActivity2.class);

                            // esto es para pasarle estos valores a la siguiente pantalla
                            i.putExtra("usuarioId",usuarioId);
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




