package com.example.proyectoingsoft2;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;


public class facturaservicio extends AppCompatActivity {

    private EditText etced, etape, etimserv, etmarserv, etdescripserv, etvalserv;
    private ImageButton ibtchcli, ibtchimserv, ibtimpr, ibtupda, ibtlimp;
    private TextView tvtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facturaservicio);

        etced = (EditText)findViewById(R.id.etcedula);
        etape = (EditText)findViewById(R.id.etapell);
        etimserv = (EditText)findViewById(R.id.etimeiservicio);
        etmarserv = (EditText)findViewById(R.id.etmarcaservicio);
        etdescripserv = (EditText)findViewById(R.id.etdescripcionservicio);
        etvalserv = (EditText)findViewById(R.id.etvalorservicio);

        tvtotal = (TextView)findViewById(R.id.tvtotal);

        ibtchcli = (ImageButton)findViewById(R.id.ibtcheckcliente);
        ibtchimserv = (ImageButton)findViewById(R.id.ibtcheckimeiservicio);
        ibtimpr = (ImageButton)findViewById(R.id.ibtimprimir);
        ibtupda = (ImageButton)findViewById(R.id.ibtmodificar);
        ibtlimp = (ImageButton)findViewById(R.id.ibtlimpiar);

        ibtchimserv.setEnabled(false);
        ibtimpr.setEnabled(false);
        ibtlimp.setEnabled(false);

        RadioGroup escoge = (RadioGroup)findViewById(R.id.grupoescoge);
        escoge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbventa:

                        Intent i = new Intent(facturaservicio.this,factura.class);
                        startActivity(i);
                        break;

                    case R.id.rbsertec:
                        RadioButton bt2 = (RadioButton)findViewById(R.id.rbsertec);

                        bt2.isChecked();
                        bt2.requestFocus();
                        break;

                }
            }
        });

    }

    public void check_cliente (View v){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion2", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String cedula = etced.getText().toString();

        if(!cedula.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select apellido from clientes where cedula='"+cedula+"'",null);
            if(fila.moveToFirst()){
                etape.setText(fila.getString(0));

                etced.setEnabled(false);
                etimserv.setEnabled(true);
                ibtchcli.setEnabled(false);
                ibtchimserv.setEnabled(true);
                ibtimpr.setEnabled(false);
                etimserv.requestFocus();

            }else{

                etape.setText("");

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Importante: Cédula no existe");
                dialogo1.setMessage("¿Desea guardar el nuevo cliente?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Intent i = new Intent(facturaservicio.this,clientes.class);
                        startActivity(i);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        return;
                    }
                });
                dialogo1.show();
            }
        }else {

            Toast.makeText(this, "Debe ingresar la Cedula", Toast.LENGTH_LONG).show();
        }

    }

    public void check_imei_servicio (View v){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion2", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etimserv.getText().toString();

        ibtlimp.setEnabled(false);


        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select marcaserv, descripserv, valorserv from servicio where imeiserv='"+codigo+"'",null);
            if(fila.moveToFirst()){

                etmarserv.setText(fila.getString(0));
                etdescripserv.setText(fila.getString(1));
                etvalserv.setText(fila.getString(2));

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Importante: Imei con historial");
                dialogo1.setMessage("¿Desea actualizar información del servicio?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        etimserv.setEnabled(false);
                        etmarserv.setEnabled(false);
                        etdescripserv.setEnabled(true);
                        etvalserv.setEnabled(true);

                        ibtchimserv.setEnabled(false);
                        ibtupda.setVisibility(View.VISIBLE);
                        ibtupda.setEnabled(true);
                        ibtlimp.setEnabled(false);
                        ibtlimp.setEnabled(false);

                        etdescripserv.requestFocus();

                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        etimserv.setEnabled(false);
                        etmarserv.setEnabled(false);
                        etdescripserv.setEnabled(false);
                        etvalserv.setEnabled(false);
                        ibtlimp.setEnabled(true);
                        ibtimpr.setEnabled(true);

                        return;
                    }
                });
                dialogo1.show();

            }else{

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Importante: Imei sin historial");
                dialogo1.setMessage("¿Desea guardar información del servicio?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        etmarserv.setEnabled(true);
                        etdescripserv.setEnabled(true);
                        etvalserv.setEnabled(true);
                        etimserv.requestFocus();

                        guardar();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        return;
                    }
                });
                dialogo1.show();
            }
        }else {
            Toast.makeText(this, "Debe ingresar IMEI", Toast.LENGTH_LONG).show();
        }

    }

    public void modificar(View view) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion2", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String imeiser = etimserv.getText().toString();
        String marser = etmarserv.getText().toString();
        String desser = etdescripserv.getText().toString();
        String valser = etvalserv.getText().toString();

        if(!imeiser.isEmpty() && !marser.isEmpty() && !desser.isEmpty() && !valser.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("imeiserv", imeiser);
            registro.put("marcaserv", marser);
            registro.put("descripserv", desser);
            registro.put("valorserv", valser);

            int cantidad = BaseDeDatos.update("servicio", registro,"imeiserv='"+imeiser+"'", null);
            BaseDeDatos.close();

            if(cantidad == 1){
                etimserv.setEnabled(false);
                etmarserv.setEnabled(false);
                etdescripserv.setEnabled(false);
                etvalserv.setEnabled(false);
                ibtimpr.setEnabled(true);
                ibtlimp.setEnabled(true);

                DecimalFormat df = new DecimalFormat("0.00");

                double val = Double.parseDouble(etvalserv.getText().toString());
                double res = val;

                etvalserv.setText(df.format(res));
                tvtotal.setText(df.format(res));

                Toast.makeText(this, "Se modificó correctamen", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "El IMEI no existe", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "De llenar los campos", Toast.LENGTH_LONG).show();
        }
    }

    private void guardar() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion2", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String imeiser = etimserv.getText().toString();
        String marser = etmarserv.getText().toString();
        String desser = etdescripserv.getText().toString();
        String valser = etvalserv.getText().toString();

        if(!imeiser.isEmpty() && !marser.isEmpty() && !desser.isEmpty() && !valser.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("imeiserv", imeiser);
            registro.put("marcaserv", marser);
            registro.put("descripserv", desser);
            registro.put("valorserv", valser);

            BaseDeDatos.insert("servicio", null, registro);
            BaseDeDatos.close();

            etimserv.setEnabled(false);
            etmarserv.setEnabled(false);
            etdescripserv.setEnabled(false);
            etvalserv.setEnabled(false);
            ibtimpr.setEnabled(true);
            ibtlimp.setEnabled(true);

            DecimalFormat df = new DecimalFormat("0.00");

            double val = Double.parseDouble(etvalserv.getText().toString());
            double res = val;

            etvalserv.setText(df.format(res));
            tvtotal.setText(df.format(res));

            Toast.makeText(this, "Se guardó con éxito", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "De llenar los campos", Toast.LENGTH_LONG).show();
        }

    }


    public void limpiar (View v){

        etced.setEnabled(true);
        etimserv.setEnabled(false);
        etmarserv.setEnabled(false);
        etdescripserv.setEnabled(false);
        etape.setText("");
        etimserv.setText("");
        etdescripserv.setText("");
        etvalserv.setText("00.00");
        tvtotal.setText("00.00");
        ibtchcli.setEnabled(true);
        ibtchimserv.setEnabled(false);
        ibtimpr.setEnabled(false);
        ibtlimp.setEnabled(false);
        etced.requestFocus();

    }

    public void regresar (View v){

        Intent i = new Intent(facturaservicio.this,MainActivity.class);
        startActivity(i);

    }

}