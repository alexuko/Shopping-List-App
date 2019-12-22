package com.example.myapplicationbsl;
/**
 * @Author: Roberto Alejandro Rivera Mejia
 * @version 1, 24 Oct 2019
 * */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * DataBaseHelper extending the SQLiteOpenHelper to manage database creation and version management
 * we declare private static final variables that will be use for the creation of our table
 */
public class DataBaseHelper extends SQLiteOpenHelper {


    // a bunch of constant strings that will be needed to create and drop databases
    private static final String table = "basicSL";

    private static final String create_table = "create table " + table +"(" +
            "ITEM_ID integer primary key autoincrement, " +
            "ITEM_NAME string," +
            "ITEM_QTY integer, " +
            "IS_ITEM_CROSSED boolean" +
            ")";
    private static final String drop_table = "drop table " + table;

    /**
     * constructor takes four arguments that are required the SQLiteOpenHelper class
     * and take them in super class constructor of the  SQLiteOpenHelper.
     * @param context is the owning context. name of  the activity
     * @param name is the name of the database that you are trying to start/open
     * @param factory specify a custom CursorFactory class for creating Cursor objects
     * @param version is the version of the database
     */
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }


    /**
     * method which gets called if the database is to be constructed from scratch.
     * @param db takes a name for the database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table);
    }

    /**
     * method which gets called if and upgrade in the database to a new version
     * @param db name of the database
     * @param oldVersion the number of the old version of the DB
     * @param newVersion the new number of the DB version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_table);
        db.execSQL(create_table);

    }

    /**
     * method deletes an item from the DB table, takes an item id as its parameter
     * @param id is the id number of the item in the shopping list
     */
    public void deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, "ITEM_ID == " + id, null);
    }

    /**
     * updates the db table taking 3 parameters,
     * catch an error in case that the update is not performed successfully
     * @param idItem if the item we want to update
     * @param newName is the ner name that will replace the previous name if changed
     * @param newQty quantity of the item if its modify in the NumberPicker
     */
    public void updateItem(int idItem, String newName, int newQty){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("ITEM_NAME", newName);
            cv.put("ITEM_QTY",newQty);
            db.update(table,cv,"ITEM_ID = '" + idItem + "' ",null);
        }catch (Error e){
            System.out.println("error " + e);
        }

    }

    /**
     * overridden method that will clear out the contents of the database
     * takes no parameters, uses the SQLiteOpenHelper parent method delete to delete the data
     * from the table basicSL
     */
    protected void onDestroy() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, null, null);
    }






}
