package com.bignerdranch.android.justspin;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayerStatsDataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "PLAYERSTATS";
    private static final int DB_VERSION = 1;

    PlayerStatsDataBase(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE STATS(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MONEY INTEGER," +
                "LVLDETAILS INTEGER," +
                "LVL INTEGER,"+
                "NEXTLVL INTEGER);");

        ContentValues contentValues = new ContentValues();
        contentValues.put("LVL",1);
        contentValues.put("MONEY",1000);
        contentValues.put("LVLDETAILS", 0);
        contentValues.put("NEXTLVL", 1000);
        db.insert("STATS",null,contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
