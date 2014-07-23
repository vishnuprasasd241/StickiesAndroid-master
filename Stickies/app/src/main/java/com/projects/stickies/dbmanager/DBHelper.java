package com.projects.stickies.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.projects.stickies.models.Stickies;

import java.util.ArrayList;

/**
 * Created by ullasjoseph on 18/07/14.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String STICKIE_TABLE_NAME = "stickie";
    public static final String STICKIE_COLUMN_TIME = "time";
    public static final String STICKIE_COLUMN_TEXT = "text";
    public static final String STICKIE_COLUMN_ID = "id";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table stickie " +
                        "(id integer primary key, text text,time long)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS stickie");
        onCreate(db);
    }

    public void resetDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS stickie");
        onCreate(db);
    }

    public boolean insertStickie  (Stickies stickie)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(STICKIE_COLUMN_TEXT, stickie.getText());
        contentValues.put(STICKIE_COLUMN_TIME, stickie.getTime());

        db.insert(STICKIE_TABLE_NAME, null, contentValues);
        return true;
    }

    public Stickies getStickie(long time){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from stickie where time="+time+"", null );

        if(res == null)
            return null;

        res.moveToFirst();
        while(res.isAfterLast() == false){
            Stickies stickies = new Stickies(res.getLong(res.getColumnIndex(STICKIE_COLUMN_TIME)));
            stickies.setText(res.getString(res.getColumnIndex(STICKIE_COLUMN_TEXT)));
            return stickies;
        }
        return null;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, STICKIE_TABLE_NAME);
        return numRows;
    }

    public boolean updateStickie (Stickies stickie) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STICKIE_COLUMN_TEXT, stickie.getText());
        db.update("stickie", contentValues, "time=" + stickie.getTime(), null);

        return true;
    }

    public Integer deleteStickie (long time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("stickie",
                "time = ? ",
                new String[] { Long.toString(time) });
    }

    public ArrayList<Stickies> getAllStickie()
    {
        ArrayList<Stickies> array_list = new ArrayList<Stickies>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from stickie", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            Stickies stickies = new Stickies(res.getLong(res.getColumnIndex(STICKIE_COLUMN_TIME)));
            stickies.setText(res.getString(res.getColumnIndex(STICKIE_COLUMN_TEXT)));
            array_list.add(stickies);
            res.moveToNext();
        }
        return array_list;
    }

}
