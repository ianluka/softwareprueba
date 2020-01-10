package com.example.proyectoingsoft2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void btinvent (View v){

        Intent i = new Intent(MainActivity.this,inventario.class);
        startActivity(i);


    }

    public void btventas (View v){

        Intent i = new Intent(MainActivity.this, clientes.class);
        startActivity(i);

    }

    public void btfactura (View v){

        Intent i = new Intent(MainActivity.this, factura.class);
        startActivity(i);

    }

    public void bthistorial (View v){

        Intent i = new Intent(MainActivity.this, historial.class);
        startActivity(i);

    }
}
