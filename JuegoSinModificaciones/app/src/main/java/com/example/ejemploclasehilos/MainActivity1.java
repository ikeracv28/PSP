package com.example.ejemploclasehilos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import android.widget.Toast;


public class MainActivity1 extends AppCompatActivity {

    Button inicioSesion;
    Button botonRegistro;

    EditText nombreUsuario;
    EditText passwordUsuario;

    int puntuacion;

    String usuarioId = null;

    //int contador = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Firebase database instance
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://juegopsp-c4db0-default-rtdb.europe-west1.firebasedatabase.app/");

        // Reference to "usuarios/usuario" node
        DatabaseReference usuariosRef = database.getReference("usuarios/usuario");

//        Example of inserting users manually:
//
//        DatabaseReference myRef = usuariosRef.push();
//        myRef.setValue("Iker;1234");
//
//        DatabaseReference myRef2 = usuariosRef.push();
//        myRef2.setValue("Pepe;1234");

//        Other example references:
//
//        DatabaseReference myRef2 = database.getReference("usuarios/usario/nombre/contraseña");
//        myRef2.setValue("1234");
//
//        DatabaseReference usuarionuevo = database.getReference("usuarios/usuario/nombre");
//        usuarionuevo.setValue("Iker");
//        DatabaseReference contraseñaNueva = database.getReference("usuarios/usario/nombre/contraseña");
//        contraseñaNueva.setValue("1234");

        nombreUsuario = findViewById(R.id.nombreUsuario);
        passwordUsuario = findViewById(R.id.passwordUsuario);
        inicioSesion = findViewById(R.id.botonInicioSesion);
        botonRegistro = findViewById(R.id.botonRegistro);

        // Open the registration screen when the register button is clicked
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity1.this, PaginaRegistro.class);
                startActivity(i);
            }
        });

        // Function to log in, checking credentials in the database
        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1. Read what the user typed in the EditTexts
                String usuarioEscrito = nombreUsuario.getText().toString();
                String contrasenaEscrita = passwordUsuario.getText().toString();

                // 2. Reference to the "usuario" node in the database
                DatabaseReference usuarioRef = database.getReference("usuarios/usuario");

                // 3. Read data from Firebase ONLY ONCE
                usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        boolean loginCorrecto = false;

                        // dataSnapshot contains ALL children: ID1, ID2, ID3...
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {

                            // This gets each string "Nombre;Pass;Puntuacion"
                            String valor = hijo.getValue(String.class);

                            // If the value is null, continue to the next one
                            if (valor == null) continue;

                            // Split value into name, password and score
                            String[] split = valor.split(";");

                            // Make sure there are at least three parts
                            if (split.length < 3) continue;

                            // First part before ';' is the username
                            String nombreBD = split[0];

                            // Second part is the password
                            String passBD = split[1];

                            // Third part is the score
                            puntuacion = Integer.parseInt(split[2]);

                            // Compare with what the user typed, and if it matches:
                            if (usuarioEscrito.equals(nombreBD) &&
                                    contrasenaEscrita.equals(passBD)) {

                                // Set loginCorrecto to true and exit the loop
                                loginCorrecto = true;

                                // Save the node ID (user ID in Firebase)
                                usuarioId = hijo.getKey();

                                break; // We already found the user
                            }
                        }

                        // If login is correct, show a message and go to the next screen
                        if (loginCorrecto) {
                            Toast.makeText(MainActivity1.this,
                                    "Inicio de sesión correcto",
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(MainActivity1.this, MainActivity2.class);
                            // Pass these values to the next screen
                            i.putExtra("usuarioId", usuarioId);
                            i.putExtra("Usuario", usuarioEscrito);
                            i.putExtra("Contraseña", contrasenaEscrita);
                            i.putExtra("Puntuacion", puntuacion);

                            startActivity(i);
                        } else {
                            // Login incorrect → show error message
                            Toast.makeText(MainActivity1.this,
                                    "Usuario o contraseña incorrectos",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

//                        Legacy example code:
//
//                        // Get what is stored in the database
//                        String nombre = dataSnapshot.child("nombre").getValue(String.class);
//                        String [] split = nombre.split(";");
//                        String nombreBD = split[0];
//                        String passBD = split[1];
//
//                        // String passBD = dataSnapshot.child("contraseña").getValue(String.class);
//
//                        // (In case they are null)
//                        if (nombreBD == null || passBD == null) {
//                            Toast.makeText(MainActivity1.this,
//                                    "No hay usuario guardado en la base de datos",
//                                    Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        // Compare what the user typed with what is stored in Firebase
//                        if (usuarioEscrito.equals(nombreBD) && contrasenaEscrita.equals(passBD)) {
//                            // CORRECT LOGIN
//                            Toast.makeText(MainActivity1.this,
//                                    "Inicio de sesión correcto",
//                                    Toast.LENGTH_SHORT).show();
//
//                            // Go to the second Activity sending the data
//                            Intent i = new Intent(MainActivity1.this, MainActivity2.class);
//                            i.putExtra("Usuario", usuarioEscrito);
//                            i.putExtra("Contraseña", contrasenaEscrita);
//                            startActivity(i);
//
//                        } else {
//                            // INCORRECT LOGIN
//                            Toast.makeText(MainActivity1.this,
//                                    "Usuario o contraseña incorrectos",
//                                    Toast.LENGTH_SHORT).show();
//                        }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(MainActivity1.this,
                                "Error al leer de Firebase",
                                Toast.LENGTH_SHORT).show();
                        Log.w("TAG", "Failed to read value.", error.toException());
                    }
                });
            }
        });
    }
}



//                Example of sending fixed user data:
//                i.putExtra("Usuario", "Iker");
//                i.putExtra("Contraseña", "1234");
//                startActivity(i);

//            }
//        });
//
//        // Example of reading from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("TAG", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("TAG", "Failed to read value.", error.toException());
//            }
//        });
//
//    }

