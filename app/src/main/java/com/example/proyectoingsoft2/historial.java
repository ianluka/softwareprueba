package com.example.proyectoingsoft2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class historial extends AppCompatActivity {

    private EditText etimeser, etmarcser, etdescser, etvalser;
    private ImageButton ibtlimp, ibtbusc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial);

        etimeser = (EditText)findViewById(R.id.etimeiservhistorial);
        etmarcser = (EditText)findViewById(R.id.etmarcaservhistorial);
        etdescser = (EditText)findViewById(R.id.etdescripcionservhistorial);
        etvalser = (EditText)findViewById(R.id.etvalorservhistorial);

        ibtlimp = (ImageButton)findViewById(R.id.ibtcancelar);
        ibtbusc = (ImageButton)findViewById(R.id.ibtbuscar);

    }

    public void buscar (View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion2", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etimeser.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select marcaserv, descripserv, valorserv from servicio where imeiserv='"+codigo+"'",null);
            if(fila.moveToFirst()){

                etmarcser.setText(fila.getString(0));
                etdescser.setText(fila.getString(1));
                etvalser.setText(fila.getString(2));

                DecimalFormat df = new DecimalFormat("0.00");

                double val = Double.parseDouble(etvalser.getText().toString());
                double res = val;

                etvalser.setText(df.format(res));

                ibtbusc.setEnabled(false);
                etimeser.setEnabled(false);
                ibtlimp.setEnabled(true);

            }else{
                Toast.makeText(this, "IMEI no tiene historial", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "Debe ingresar IMEI", Toast.LENGTH_LONG).show();
        }

    }

    public void limpiar (View v){


        etmarcser.setText("");
        etdescser.setText("");
        etvalser.setText("");

        etimeser.setEnabled(true);
        etimeser.requestFocus();

        ibtlimp.setEnabled(false);
        ibtbusc.setEnabled(true);

    }

    public void regresar (View v){

        Intent i = new Intent(historial.this,MainActivity.class);
        startActivity(i);

    }

}
