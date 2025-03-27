package com.example.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyConnection extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notedb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "notetab";


    public MyConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE "+TABLE_NAME +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " title TEXT NOT NULL," +
                    " message TEXT NOT NULL," +
                    " time TEXT NOT NULL)");
        }
        catch (Exception e){
            Log.d("Table Creation", "Error - "+e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);

        onCreate(db);
    }

    public boolean Insert_Operation(String title_val, String msg_val, String time_val){
        try{
            //Log.d("DATAA", title_val+" = "+msg_val+" = "+time_val);
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues val = new ContentValues();
            val.put("title", title_val);
            val.put("message", msg_val);
            val.put("time", time_val);

            long res = db.insert(TABLE_NAME, null, val);
            if (res > 0){
                return true;
            }

        }catch (Exception e){
            Log.d("Insert Error", "Insert Deniete - "+e.toString());
        }
        return false;
    }

    //Reading Operation
    public List<item_class> Display_Operation(){
        SQLiteDatabase db = this.getWritableDatabase();
        item_class itemclass = new item_class();
        List<item_class> list = new ArrayList<>();
        try{

            Cursor cur = db.rawQuery("select * from notetab", null);

            while (cur.moveToNext()){
                item_class item = new item_class();
                item.id = cur.getInt(0);
                item.title = cur.getString(1);
                item.msg = cur.getString(2);
                item.time = cur.getString(3);

                list.add(item);
            }
        }catch (Exception e){
            Log.d("Display Error", "Reading Deniete - "+e.toString());
        }

        return list;
    }

    //Delete Operation
    public boolean Delete_Operation(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            int res = db.delete("notetab", "id = "+id, null);

            if(res > 0){

                return true;
            }
        }
        catch (Exception e){
            Log.d("Delete Error", "Delete Deniete - "+e.toString());
        }
        return false;
    }

    //Update Operation
    public boolean Update_Operation(int id, String title_val, String msg_val, String time_val){
        try{
            //Log.d("DATAA", title_val+" = "+msg_val+" = "+time_val);
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues val = new ContentValues();
            val.put("title", title_val);
            val.put("message", msg_val);
            val.put("time", time_val);

            long res = db.update("notetab", val, "id ="+id, null);
            if (res > 0){
                return true;
            }

        }catch (Exception e){
            Log.d("Update Error", "Insert Deniete - "+e.toString());
        }
        return false;
    }

    //Reading Operation
    public List<item_class> Get_Note(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        item_class itemclass = new item_class();
        List<item_class> list = new ArrayList<>();
        try{
            Cursor cur = db.rawQuery("select * from notetab where id = "+id, null);

            if(cur.moveToNext()){
                item_class item = new item_class();
                item.setId(cur.getInt(0));
                item.setTitle(cur.getString(1));
                item.setMsg(cur.getString(2));
                item.setTime(cur.getString(3));

                list.add(item);
                Log.d("edited note Error", cur.getString(1)+" rec");
            }
        }catch (Exception e){
            Log.d("Display Error", "Reading Deniete - "+e.toString());
        }

        return list;
    }
}
