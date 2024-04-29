package com.example.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


class Item{
    public int id;
    public String value;

    Item(String value){
        this.value = value;
    }
}

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "items", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE items (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "VALUE TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if EXISTS items");
        this.onCreate(db);

    }

    public void create(String value){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("value", value);
        db.insert("items",null,values);
    }

    public void update(String oldValue, String newValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put("value", newValue);
        db.update("items",values, "value=?", new String[]{oldValue});
    }

    public void delete(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("items", "value=?", new String[]{value});
    }

    public ArrayList<Item> read(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM items", null);

        ArrayList<Item> items  = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                String value = cursor.getString(1);
                items.add(new Item(value));
            }
            while(cursor.moveToNext());
        }
        return items;
    }

}
