package com.example.proyectoingsoft2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class inventario extends AppCompatActivity {

    private EditText etime, etmar, etnum, etval;
    private ImageButton ibtguar, ibtbusc, ibtnuev, ibtlimp;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventario);

        etime = (EditText)findViewById(R.id.etimei);
        etmar = (EditText)findViewById(R.id.etmarca);
        etnum = (EditText)findViewById(R.id.etnumtel);
        etval = (EditText)findViewById(R.id.etvalor);

       ibtguar = (ImageButton)findViewById(R.id.ibtguardar);
       ibtlimp = (ImageButton)findViewById(R.id.ibtlimpiar);
       ibtbusc = (ImageButton)findViewById(R.id.ibtbuscar);
       ibtnuev = (ImageButton)findViewById(R.id.ibtnuevo);


       ibtguar.setEnabled(false);
       ibtlimp.setEnabled(false);


    }


    public void nuevo (View v){

        ibtguar = (ImageButton)findViewById(R.id.ibtguardar);
        ibtlimp = (ImageButton)findViewById(R.id.ibtlimpiar);

        etime.setEnabled(false);
        etmar.setEnabled(true);
        etnum.setEnabled(true);
        etval.setEnabled(true);

        ibtguar.setEnabled(true);
        ibtbusc.setEnabled(false);
        ibtlimp.setEnabled(true);

        etmar.setText("");
        etnum.setText("");
        etval.setText("");

        etmar.requestFocus();

    }

    public void guardar (View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion2", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String ime = etime.getText().toString();
        String mar = etmar.getText().toString();
        String num = etnum.getText().toString();
        String val = etval.getText().toString();

        if (!ime.isEmpty()&&!mar.isEmpty()&&!val.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("imei",ime);
            registro.put("marca",mar);
            registro.put("numasig",num);
            registro.put("valor",val);

            BaseDeDatos.insert ("inventario", null,registro);
            BaseDeDatos.close();

            etime.setText("");
            etmar.setText("");
            etnum.setText("");
            etval.setText("");

            ibtguar.setEnabled(false);
            ibtlimp.setEnabled(false);
            ibtnuev.setEnabled(true);
            ibtbusc.setEnabled(true);
            etime.setEnabled(true);

            etime.requestFocus();

            Toast.makeText(this, "Se guardó con éxito", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Debes llenar los campos", Toast.LENGTH_LONG).show();
        }

    }

    public void buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion2", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etime.getText().toString();

        ibtguar.setEnabled(false);
        ibtlimp.setEnabled(false);

        etmar.setEnabled(false);
        etnum.setEnabled(false);
        etval.setEnabled(false);

        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select marca, numasig, valor from inventario where imei='"+codigo+"'",null);
            if(fila.moveToFirst()){
                etmar.setText(fila.getString(0));
                etnum.setText(fila.getString(1));
                etval.setText(fila.getString(2));

            }else{

                etmar.setText("");
                etnum.setText("");
                etval.setText("");

                Toast.makeText(this, "IMEI no existe", Toast.LENGTH_LONG).show();
            }
        }else {

            Toast.makeText(this, "Debe ingresar un IMEI", Toast.LENGTH_LONG).show();
        }

    }

    public void limpiar (View v){

       etime = (EditText)findViewById(R.id.etimei);
       etmar = (EditText)findViewById(R.id.etmarca);
       etnum = (EditText)findViewById(R.id.etnumtel);
       etval = (EditText)findViewById(R.id.etvalor);

        ibtbusc = (ImageButton)findViewById(R.id.ibtbuscar);
        ibtnuev = (ImageButton)findViewById(R.id.ibtnuevo);
        ibtguar = (ImageButton)findViewById(R.id.ibtguardar);
        ibtlimp = (ImageButton)findViewById(R.id.ibtlimpiar);

        etmar.setText("");
        etnum.setText("");
        etval.setText("");

        etime.setEnabled(true);
        etmar.setEnabled(false);
        etnum.setEnabled(false);
        etval.setEnabled(false);

        etime.requestFocus();

        ibtbusc.setEnabled(true);
        ibtnuev.setEnabled(true);
        ibtguar.setEnabled(false);
        ibtlimp.setEnabled(false);
    }

    public void regresar (View v){

        Intent i = new Intent(inventario.this,MainActivity.class);
        startActivity(i);

    }

}
