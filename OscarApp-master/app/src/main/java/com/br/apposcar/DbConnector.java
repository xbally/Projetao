package com.br.apposcar;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbConnector {
    public static final String DATABASE_NAME = "Usuario";
    private SQLiteDatabase database; // objeto bd
    private DatabaseOpenHelper databaseOpenHelper; // helper bd

    // construtor
    public DbConnector(Context context){
        // Cria um novo DatabaseOpenHelper
        databaseOpenHelper =
                new DatabaseOpenHelper(context, DATABASE_NAME,null,1);
    } // fim construtor

    //abre conexao
    public void open() throws SQLException {
        //cria ou abre uma bd para leitura/escrita
        database = databaseOpenHelper.getWritableDatabase();
    }//fim open

    //fecha conexao
    public void close(){
        if (database != null)
            database.close();
    } // fim close

    //insere novo contato
    public void insertContact(String name, String email, String phone,
                              String street, String city){
        ContentValues newContact = new ContentValues();
        newContact.put("name",name);
        newContact.put("email",email);
        newContact.put("phone",phone);
        newContact.put("street",street);
        newContact.put("city",city);

        open();
        database.insert("usuario",null,newContact);
        close();
    } //fim insertContact

    //edita contato
    public void confirmarVoto(int usuario, String filme, String diretor){
        ContentValues editContact = new ContentValues();
        editContact.put("votou",1);
        editContact.put("votofilme", filme);
        editContact.put("votodiretor", diretor);

        open();
        database.update("usuario ",editContact," usuario=" + usuario,null);
        close();
    } //fim updateContact

    public Usuario autenticaLogin(String nome, String senha){
        Cursor result = database.query("Usuario", new String[]{"*,count(*)"},"nome='"+nome+"' and senha='"+senha+"'",null,null,null,null);
        result.moveToFirst();
        Usuario usuario1 = new Usuario(
                result.getString(result.getColumnIndex("nome"))
                , result.getString(result.getColumnIndex("senha"))
                , result.getString(result.getColumnIndex("filme"))
                , result.getString(result.getColumnIndex("diretor"))
                , result.getInt(result.getColumnIndex("usuario"))
                , result.getInt(result.getColumnIndex("votou"))
                , result.getInt(result.getColumnIndex("token"))
        );
        return usuario1;
    }

    public Usuario getUsuario(int usuario){
        Cursor result = database.query("Usuario", null,"usuario="+usuario,null,null,null,
                null);
        result.moveToFirst();
        Usuario usuario1 = new Usuario(
                result.getString(result.getColumnIndex("nome"))
                , result.getString(result.getColumnIndex("senha"))
                , result.getString(result.getColumnIndex("filme"))
                , result.getString(result.getColumnIndex("diretor"))
                , result.getInt(result.getColumnIndex("usuario"))
                , result.getInt(result.getColumnIndex("votou"))
                , result.getInt(result.getColumnIndex("token"))
        );
        return usuario1;
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        //construtor
        public DatabaseOpenHelper(Context context, String name,SQLiteDatabase.CursorFactory factory, int version){
            super(context,name, factory, version);
        } // fim constructor

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("DROP TABLE IF EXISTS Usuario");
            String createQuery = "CREATE TABLE " + "Usuario"
                    + "(usuario integer primary key,"
                    + "nome TEXT,"
                    + "senha TEXT,"
                    + "votofilme TEXT,"
                    + "votodiretor TEXT,"
                    + "votou integer);";
            db.execSQL(createQuery);
            db.execSQL("insert into usuario values (111,'Amanda','s',null,null,0)");
            db.execSQL("insert into usuario values (222,'Flavio','s',null,null,0)");
            db.execSQL("insert into usuario values (333,'Fernando','s',null,null,0)");


        } // fim onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        } // fim onUpgrade
    }
}
