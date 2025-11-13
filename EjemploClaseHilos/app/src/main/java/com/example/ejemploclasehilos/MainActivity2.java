package com.example.ejemploclasehilos;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {


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

        String nombreUsuario = getIntent().getStringExtra("Usuario");

        TextView textView = findViewById(R.id.textView3);


        // 3. Poner el mensaje en pantalla
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            textView.setText("Bienvenido, " + nombreUsuario);
        } else {
            textView.setText("Bienvenido");
        }



        String contraseña = getIntent().getStringExtra("Contraseña");

        Log.d("tag", nombreUsuario);

        Log.d("tag" , "" + contraseña);
    }
}