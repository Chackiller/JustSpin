package com.bignerdranch.android.justspin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Cursor cursor;
    SQLiteOpenHelper sqLiteOpenHelper;
    int LVL;
    int Money;
    int lvlDetails;
    int nextlvl;
    TextView moneyBalans;
    TextView lvlBalans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moneyBalans = findViewById(R.id.cash);
        lvlBalans = findViewById(R.id.lvlbalans);

        sqLiteOpenHelper = new PlayerStatsDataBase(this);
        try {
            db = sqLiteOpenHelper.getReadableDatabase();
            cursor = db.query("STATS",
                    new String[]{"_id","MONEY","LVLDETAILS","LVL","NEXTLVL"},
                    null,null,null,null, null);

            cursor.moveToFirst();

            LVL = cursor.getInt(cursor.getColumnIndex("LVL"));
            Money = cursor.getInt(cursor.getColumnIndex("MONEY"));
            lvlDetails = cursor.getInt(cursor.getColumnIndex("LVLDETAILS"));
            nextlvl = cursor.getInt(cursor.getColumnIndex("NEXTLVL"));


            CustomProgressBar customProgressBar = findViewById(R.id.progress);
            customProgressBar.setValue((lvlDetails*100)/nextlvl);

            moneyBalans.setText(Integer.toString(Money));
            lvlBalans.setText(Integer.toString(LVL));

            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
   }

    public void onSpinGameClick(View view) {
        Intent intent = new Intent(MainActivity.this,SpinActivity.class);
        startActivity(intent);
    }



    public void onFirstGameClick(View view) {
        if(LVL < 5){
            Toast.makeText(this, getResources().getString(R.string.first_game_unavailable), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
        }
    }

    public void onSecondGameClick(View view) {
        if(LVL < 10){
            Toast.makeText(this, getResources().getString(R.string.second_game_unavailable), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
        }

    }
}
