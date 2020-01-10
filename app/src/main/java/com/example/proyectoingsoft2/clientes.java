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

public class clientes extends AppCompatActivity {

    private EditText etced, etape, etnum, etdir;
    private ImageButton ibtguar, ibtbusc, ibtnuev, ibtlimp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientes);

        Bundle parametros = this.getIntent().getExtras();
        if(parametros!=null){
            String datos = parametros.getString("datos","");
            etced.setText(datos);
        }


        etced = (EditText)findViewById(R.id.etcedula);
        etape = (EditText)findViewById(R.id.etapell);
        etnum = (EditText)findViewById(R.id.etnumtel);
        etdir = (EditText)findViewById(R.id.etdireccion);

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

        etced.setEnabled(false);
        etape.setEnabled(true);
        etnum.setEnabled(true);
        etdir.setEnabled(true);

        ibtguar.setEnabled(true);
        ibtbusc.setEnabled(false);
        ibtlimp.setEnabled(true);

        etape.setText("");
        etnum.setText("");
        etdir.setText("");

        etape.requestFocus();

    }

    public void guardar (View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion2", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String ced = etced.getText().toString();
        String ape = etape.getText().toString();
        String num = etnum.getText().toString();
        String dir = etdir.getText().toString();

        if (!ced.isEmpty()&&!ape.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("cedula",ced);
            registro.put("apellido",ape);
            registro.put("numtel",num);
            registro.put("direccion",dir);

            BaseDeDatos.insert ("clientes", null,registro);
            BaseDeDatos.close();

            etced.setText("");
            etape.setText("");
            etnum.setText("");
            etdir.setText("");

            ibtguar.setEnabled(false);
            ibtlimp.setEnabled(false);
            ibtnuev.setEnabled(true);
            ibtbusc.setEnabled(true);
            etced.setEnabled(true);

            etced.requestFocus();

            Toast.makeText(this, "Se guardó con éxito", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Debes llenar los campos", Toast.LENGTH_LONG).show();
        }

    }

    public void buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion2", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String cedula = etced.getText().toString();

        ibtguar.setEnabled(false);
        ibtlimp.setEnabled(false);

        etape.setEnabled(false);
        etnum.setEnabled(false);
        etdir.setEnabled(false);

        if(!cedula.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select apellido, numtel, direccion from clientes where cedula='"+cedula+"'",null);
            if(fila.moveToFirst()){
                etape.setText(fila.getString(0));
                etnum.setText(fila.getString(1));
                etdir.setText(fila.getString(2));

            }else{

                etape.setText("");
                etnum.setText("");
                etdir.setText("");

                Toast.makeText(this, "Cedula no existe", Toast.LENGTH_LONG).show();
            }
        }else {

            Toast.makeText(this, "Debe ingresar la Cedula", Toast.LENGTH_LONG).show();
        }

    }

    public void limpiar (View v){

        etced = (EditText)findViewById(R.id.etcedula);
        etape = (EditText)findViewById(R.id.etapell);
        etnum = (EditText)findViewById(R.id.etnumtel);
        etdir = (EditText)findViewById(R.id.etdireccion);

        ibtbusc = (ImageButton)findViewById(R.id.ibtbuscar);
        ibtnuev = (ImageButton)findViewById(R.id.ibtnuevo);
        ibtguar = (ImageButton)findViewById(R.id.ibtguardar);
        ibtlimp = (ImageButton)findViewById(R.id.ibtlimpiar);

        etape.setText("");
        etnum.setText("");
        etdir.setText("");

        etced.setEnabled(true);
        etape.setEnabled(false);
        etnum.setEnabled(false);
        etdir.setEnabled(false);

        etced.requestFocus();

        ibtbusc.setEnabled(true);
        ibtnuev.setEnabled(true);
        ibtguar.setEnabled(false);
        ibtlimp.setEnabled(false);

    }

    public void regresar (View v){

        Intent i = new Intent(clientes.this,MainActivity.class);
        startActivity(i);

    }

}