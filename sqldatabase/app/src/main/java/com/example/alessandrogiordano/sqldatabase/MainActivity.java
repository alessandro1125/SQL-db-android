package com.example.alessandrogiordano.sqldatabase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import static android.view.View.*;

public class MainActivity extends Activity implements OnClickListener{

    Button btoScriviRecord;
    Button btoLeggiDati;
    Button eliminoDati;
    Button leggiUno;
    ListView lstDati;

int edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //apriamo il database
        //se non esiste viene creato
        try{
            DBLayer db = new DBLayer(this);
            db.open();
            db.close();
        }catch (Exception e){}


        //Inizializza controlli
        btoScriviRecord = (Button) findViewById(R.id.btoScriviRecord);
        btoLeggiDati = (Button) findViewById(R.id.btoLeggiDati);
        eliminoDati = (Button) findViewById(R.id.eliminoDati);
        leggiUno = (Button) findViewById(R.id.btnLeggiUno);
        lstDati = (ListView) findViewById(R.id.lvDati);

        btoLeggiDati.setOnClickListener(this);
        btoScriviRecord.setOnClickListener(this);
        eliminoDati.setOnClickListener(this);
        leggiUno.setOnClickListener(this);
        LeggiDati();
        edit=5;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btoScriviRecord:
                Scrivirecord();
                break;
            case R.id.eliminoDati:
                inserisciEdit();
                break;
            case R.id.btoLeggiDati:
                ArrayList<String> dati = LeggiDati();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dati);

                lstDati.setAdapter(adapter);
            case R.id.btnLeggiUno:

        }
    }

    private void Scrivirecord() {
        try{
            DBLayer db = new DBLayer(this);
            db.open();

            String strQuery = "INSERT INTO tabella1 (nome) VALUES ";
            EditText edit1=(EditText)findViewById(R.id.editText);
            Editable s= edit1.getText();
            String a=s.toString();
            strQuery +="('"+a+"');";

            Log.i("query",strQuery);

            db.Execute(strQuery, DBLayer.TipoQuery.Comando);

            db.close();
        }catch (Exception e){}
    }
    private void eliminoDati() {
        DBLayer db = new DBLayer(this);
        db.open();

        String strQuery = "delete from tabella1;";
        Log.i("query",strQuery);
        db.Execute(strQuery, DBLayer.TipoQuery.Comando);

        db.close();
    }
    private void inserisciEdit() {
        try{
            DBLayer db = new DBLayer(this);
            db.open();
            String valore=Integer.toString(edit);
            String strQuery ="update tabella1 set nome='"+valore+"' where nome_id=3;";
            Log.i("query",strQuery);
            db.Execute(strQuery, DBLayer.TipoQuery.Comando);
            db.close();
        }catch (Exception e){}
        leggiEdit();
    }
    private ArrayList<String> leggiEdit(){
        ArrayList<String> lstD1 = new ArrayList<String>();

        try{
            DBLayer db = new DBLayer(this);
            db.open();

            String strQuery = "SELECT nome FROM tabella1 where nome_id=3;";
            Cursor c = db.Execute(strQuery, DBLayer.TipoQuery.Selezione);


            if (c != null){
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                    lstD1.add(c.getString(c.getColumnIndex("nome")));
                }
            }else{

            }
        }catch (Exception e){}
        edit= Integer.parseInt(lstD1.get(0));
        TextView txt1=(TextView)findViewById(R.id.txt1);
        txt1.setText(Integer.toString(edit));
        return lstD1;
    }
    private ArrayList<String> LeggiDati(){
        ArrayList<String> lstD = new ArrayList<String>();

        try{
            DBLayer db = new DBLayer(this);
            db.open();

            String strQuery = "select nome from tabella1;";
            Cursor c = db.Execute(strQuery, DBLayer.TipoQuery.Selezione);


            if (c != null){
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                    lstD.add(c.getString(c.getColumnIndex("nome")));
                }
            }else{

            }


        }catch (Exception e){}


        return lstD;
    }


}
