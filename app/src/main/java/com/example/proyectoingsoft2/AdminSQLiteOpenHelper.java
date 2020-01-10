package com.example.proyectoingsoft2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {

        BaseDeDatos.execSQL("create table inventario(imei integer primary key, marca text, numasig text, valor real)");
        BaseDeDatos.execSQL("create table clientes(cedula integer primary key, apellido text, numtel text, direccion text)");
        BaseDeDatos.execSQL("create table servicio (imeiserv integer primary key, marcaserv text, descripserv text, valorserv real)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
