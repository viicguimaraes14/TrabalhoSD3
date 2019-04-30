package com.example.ananda.trabalhosd3.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ananda.trabalhosd3.Model.Paises;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {

    private SQLHelper helper;
    private SQLiteDatabase db;

    public Repositorio(Context ctx){
        helper = new SQLHelper(ctx);
    }

    public long inserir(Paises paises){
        db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLHelper.COLUNA_NAME, paises.name);
        cv.put(SQLHelper.COLUNA_CAPITAL, paises.capital);
        cv.put(SQLHelper.COLUNA_REGION, paises.region);
        cv.put(SQLHelper.COLUNA_SUBREGION, paises.subregion);


        long id = db.insert(SQLHelper.TABELA_PAISES, null, cv);

        if(id != -1){
            paises.id = id;
        }
        db.close();
        return id;
    }

    public void excluirAll(){
        db = helper.getWritableDatabase();
        db.delete(SQLHelper.TABELA_PAISES, null, null);
        db.close();
    }

    public List<Paises> listarPaises() {
        String sql = "SELECT * FROM " + SQLHelper.TABELA_PAISES;
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Paises> list = new ArrayList();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(
                    cursor.getColumnIndex(SQLHelper.COLUNA_ID)
            );
            String name = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_NAME)
            );
            String capital = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_CAPITAL)
            );
            String region = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_REGION)
            );
            String subregion = cursor.getString(
                    cursor.getColumnIndex(SQLHelper.COLUNA_SUBREGION)
            );


            Paises paises = new Paises(name,capital,region,subregion);
            list.add(paises);
        }
        cursor.close();
        return list;
    }


}
