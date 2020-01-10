package com.example.proyectoingsoft2;

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
import java.util.ArrayList;


public class factura extends AppCompatActivity {

    private EditText etced, etape, etimei, etmar, etval, etiva;
    private ImageButton ibtchcli, ibtchimei, ibtimpr, ibtlimpiar;
    private TextView tvtotal;

    //PDF
    private String[]header={"Id","Nombre","Apellido"};
    private String shortText="Hola";
    private String longText="Nunca consideres el estudio como una obligación, sino como una oportunidad para andentrarte en el bello y maravilloso mundo del saber";
    private TemplatePDF templatePDF;


    //mi trabajo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factura);

        //Pdf
        templatePDF=new TemplatePDF((getApplicationContext()));
        templatePDF.openDocument();
        templatePDF.addMetaData("Clientes ","Ventas","Marines");
        templatePDF.addTitle("Tienda CodigoFacilito","Clientes","6/12/2017");
        templatePDF.addParagraph(shortText);
        templatePDF.addParagraph(longText);
        templatePDF.createTable(header,getClients());
        templatePDF.closeDocument();

        //mi trabajo
        etced = (EditText)findViewById(R.id.etcedula);
        etape = (EditText)findViewById(R.id.etapell);
        etimei = (EditText)findViewById(R.id.etimei);
        etmar = (EditText)findViewById(R.id.etmarca);
        etval = (EditText)findViewById(R.id.etvalor);
        etiva = (EditText)findViewById(R.id.etiva);

        tvtotal = (TextView)findViewById(R.id.tvtotal);

        ibtchcli = (ImageButton)findViewById(R.id.ibtcheckcliente);
        ibtchimei = (ImageButton)findViewById(R.id.ibtcheckimei);
        ibtimpr = (ImageButton)findViewById(R.id.ibtimprimir);
        ibtlimpiar = (ImageButton)findViewById(R.id.ibtlimpiar);

        ibtchimei.setEnabled(false);
        ibtimpr.setEnabled(false);
        ibtlimpiar.setEnabled(false);

        RadioGroup escoge = (RadioGroup)findViewById(R.id.grupoescoge);
        escoge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbventa:

                        RadioButton bt1 = (RadioButton)findViewById(R.id.rbventa);

                        bt1.isChecked();
                        bt1.requestFocus();
                        break;

                    case R.id.rbsertec:

                        Intent i = new Intent(factura.this,facturaservicio.class);
                        startActivity(i);
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
                etimei.setEnabled(true);
                ibtchcli.setEnabled(false);
                ibtchimei.setEnabled(true);
                ibtimpr.setEnabled(false);
                etimei.requestFocus();

            }else{

                etape.setText("");

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Importante: Cédula no existe");
                dialogo1.setMessage("¿ Desea guardar el nuevo cliente ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        Intent i = new Intent(factura.this,clientes.class);
                        //String datos = etced.getText().toString();
                        //Bundle parametros = new Bundle();
                        //parametros.putString("datos", datos);
                        //i.putExtras(parametros);
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

    public void check_imei (View v){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion2", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etimei.getText().toString();

        if(!codigo.isEmpty()) {
            Cursor fila = BaseDeDatos.rawQuery("select marca, valor from inventario where imei='" + codigo + "'", null);
            if (fila.moveToFirst()) {
                etmar.setText(fila.getString(0));
                etval.setText(fila.getString(1));

                etiva.setEnabled(true);
                etimei.setEnabled(false);
                ibtimpr.setEnabled(true);

                String iva = etiva.getText().toString();

                if(iva.isEmpty()){
                    Toast.makeText(this, "Escriba valor de IVA", Toast.LENGTH_LONG).show();
                    tvtotal.setText("");

                }else{

                    DecimalFormat df = new DecimalFormat("0.00");

                    double val = Double.parseDouble(etval.getText().toString());
                    int aux = Integer.parseInt(etiva.getText().toString());
                    double res = ((val*aux)/100)+val;

                    tvtotal.setText(df.format(res));

                    ibtlimpiar.setEnabled(true);
                    ibtimpr.setEnabled(true);
                    ibtimpr.requestFocus();
                }

            }else{

                etape.setText("");

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle("Importante: Imei no existe");
                dialogo1.setMessage("¿ Desea guardar el nuevo IMEI ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Intent i = new Intent(factura.this,inventario.class);
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

            Toast.makeText(this, "Debe ingresar el Imei", Toast.LENGTH_LONG).show();
        }

    }

    public void limpiar (View v){

        etced.setEnabled(true);
        etimei.setEnabled(false);
        etiva.setEnabled(false);
        etape.setText("");
        etimei.setText("");
        etmar.setText("");
        etval.setText("$00.00");
        etiva.setText("12");
        tvtotal.setText("$00.00");
        ibtchcli.setEnabled(true);
        ibtchimei.setEnabled(false);
        ibtimpr.setEnabled(false);
        ibtlimpiar.setEnabled(false);
        etced.requestFocus();

    }

    public void imprimir (View view){
        templatePDF.viewPDF();
    }

    //PDF
    private ArrayList<String[]>getClients(){
        ArrayList<String[]>rows=new ArrayList<>();

        rows.add(new String[]{"1","Pedro","Lopez"});
        rows.add(new String[]{"2","Sofia","Hernandez"});
        rows.add(new String[]{"3","Naomi","Alfaro"});
        rows.add(new String[]{"4","Lorena","Espejel"});
        return rows;
    }
    //PDF

    public void regresar (View v){

        Intent i = new Intent(factura.this,MainActivity.class);
        startActivity(i);

    }

}