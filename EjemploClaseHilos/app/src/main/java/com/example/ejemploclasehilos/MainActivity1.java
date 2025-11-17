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
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://juegopsp-c4db0-default-rtdb.europe-west1.firebasedatabase.app/");


        DatabaseReference usuariosRef = database.getReference("usuarios/usuario");

//        DatabaseReference myRef = usuariosRef.push();
//        myRef.setValue("Iker;1234");
//
//        DatabaseReference myRef2 = usuariosRef.push();
//        myRef2.setValue("Pepe;1234");




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






        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity1.this, PaginaRegistro.class);
                startActivity(i);
            }
        });


        // funcion para iniciar sesion, comprobandolo en la base de datos
        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1. Leer lo que ha escrito el usuario en los EditText
                String usuarioEscrito = nombreUsuario.getText().toString();
                String contrasenaEscrita = passwordUsuario.getText().toString();


                // 2. Referencia al nodo "usuario" en la BBDD
                DatabaseReference usuarioRef = database.getReference("usuarios/usuario");

                // 3. Leer UNA SOLA VEZ los datos de Firebase
                usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        boolean loginCorrecto = false;

                        // dataSnapshot contiene TODOS los hijos: ID1, ID2, ID3...
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {


                            // Esto recoge cada string "Nombre;Pass"
                            String valor = hijo.getValue(String.class);

                            // si el valor es null continua al siguiente
                            if (valor == null) continue;

                            // esto separa el valor en nombre y contraseña
                            String[] split = valor.split(";");

                            // y con esto comporbamos que hay dos partes
                            if (split.length < 3) continue;

                            // aqui gaurdamos la primera parte, antes del punto y coma en nombre
                            String nombreBD = split[0];

                            // y despues del punto y cooma en contraseña
                            String passBD   = split[1];

                            puntuacion = Integer.parseInt(split[2]);

                            // Comparamos con lo escrito por el usuario, y si coincide
                            if (usuarioEscrito.equals(nombreBD) &&
                                    contrasenaEscrita.equals(passBD)) {

                                // cambiamos el login a true y salimos del bucle
                                loginCorrecto = true;

                                //para guarduar el id del nodo
                                usuarioId = hijo.getKey();

                                break; // ya hemos encontrado al usuario
                            }
                        }

                        // si el login es true, mostramos un mensaje y pasamos a la siguiente pantalla
                        if (loginCorrecto) {
                            Toast.makeText(MainActivity1.this,
                                    "Inicio de sesión correcto",
                                    Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(MainActivity1.this, MainActivity2.class);
                            // esto es para pasarle estos valores a la siguiente pantalla
                            i.putExtra("usuarioId", usuarioId);
                            i.putExtra("Usuario", usuarioEscrito);
                            i.putExtra("Contraseña", contrasenaEscrita);
                            i.putExtra("Puntuacion", puntuacion);

                            startActivity(i);
                        } else {
                            Toast.makeText(MainActivity1.this,
                                    "Usuario o contraseña incorrectos",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

//                        }
//
//                        // 4. Obtener lo que hay guardado en la BBDD
//                        String nombre = dataSnapshot.child("nombre").getValue(String.class);
//                        String [] split = nombre.split(";");
//                        String nombreBD = split[0];
//                        String passBD = split[1];
//
//
//
//
//                        //String passBD = dataSnapshot.child("contraseña").getValue(String.class);
//
//                        // (Por si acaso son null)
//                        if (nombreBD == null || passBD == null) {
//                            Toast.makeText(MainActivity1.this,
//                                    "No hay usuario guardado en la base de datos",
//                                    Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        // 5. Comparar lo que ha escrito el usuario con lo de Firebase
//                        if (usuarioEscrito.equals(nombreBD) && contrasenaEscrita.equals(passBD)) {
//                            // LOGIN CORRECTO
//                            Toast.makeText(MainActivity1.this,
//                                    "Inicio de sesión correcto",
//                                    Toast.LENGTH_SHORT).show();
//
//                            // Ir a la segunda Activity enviando los datos
//                            Intent i = new Intent(MainActivity1.this, MainActivity2.class);
//                            i.putExtra("Usuario", usuarioEscrito);
//                            i.putExtra("Contraseña", contrasenaEscrita);
//                            startActivity(i);
//
//                        } else {
//                            // LOGIN INCORRECTO
//                            Toast.makeText(MainActivity1.this,
//                                    "Usuario o contraseña incorrectos",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }


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



//                i.putExtra("Usuario", "Iker");
//                i.putExtra("Contraseña", "1234");
              // startActivity(i);

//            }
//        });
//
//
//
//
//        // Read from the database
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




