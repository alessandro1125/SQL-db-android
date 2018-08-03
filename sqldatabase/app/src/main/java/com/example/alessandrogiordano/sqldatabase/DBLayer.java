package com.example.alessandrogiordano.sqldatabase;

/**
 * Created by alessandro giordano on 08/12/2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBLayer {

    public static enum TipoQuery {
        Selezione,
        Comando
    }

    private static final String DATABASE_NAME = "testDB";
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private  static Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE tabella1 (nome_id integer primary key autoincrement, nome1 TEXT);");
            db.execSQL("INSERT INTO tabella1 (nome1) VALUES ('new01');");
            db.execSQL("INSERT INTO tabella1 (nome1) VALUES ('new01');");
            db.execSQL("INSERT INTO tabella1 (nome1) VALUES ('new01');");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public DBLayer(Context c){
        this.ourContext = c;
    }

    public DBLayer open() throws SQLException {
        this.ourHelper = new DbHelper(ourContext);
        this.ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        this.ourHelper.close();
    }


    public Cursor Execute(String Query, TipoQuery tipoCmd){
        Cursor c = null;

        try{
            switch(tipoCmd){
                case Comando:
                    ourDatabase.execSQL(Query);
                    break;
                case Selezione:
                    c = ourDatabase.rawQuery(Query,null);
                    break;
            }
        }catch (Exception e){}

        return c;
    }
}
