package com.bignerdranch.android.justspin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SpinActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Cursor cursor;
    SQLiteOpenHelper sqLiteOpenHelper;
    int LVL;
    int Money;
    int lvlDetails;
    TextView moneyBalans;
    TextView lvlBalans;
    ImageView spinView;
    int nextlvl;
    TextView cashSum;
    boolean isSpin = false;
    boolean isSpin2 = false;
    boolean isSpin3;
    int stavka = 100;
    int win;
    List<Integer> result = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);

        moneyBalans = findViewById(R.id.cash);
        lvlBalans = findViewById(R.id.lvl);
        spinView = findViewById(R.id.imageView3);

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

        cashSum = findViewById(R.id.cash_sum);

        firstSpin(R.id.recyclerViewww,3);
        firstSpin(R.id.recyclerViewww2,3);
        firstSpin(R.id.recyclerViewww3,3);
        firstSpin(R.id.recyclerViewww4,3);
        firstSpin(R.id.recyclerViewww5,3);

    }

    public void onLobbyButtonClick(View view) {
        Intent intent = new Intent(SpinActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void onAutoSpinClick(View view) {
        isSpin = !isSpin;
        final Handler handler = new Handler();
        if(isSpin) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(isSpin) {
                        spin();
                    }
                    handler.postDelayed(this, 1250);
                }
            });
        }
    }

    public void onSpinClick(View view) {
        spin();
        spinView.setEnabled(false);
        new CountDownTimer(1250,10){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                spinView.setEnabled(true);
            }
        }.start();


        }

    private void spin() {
        firstSpin(R.id.recyclerViewww,30);
        firstSpin(R.id.recyclerViewww2,40);
        firstSpin(R.id.recyclerViewww3,50);
        firstSpin(R.id.recyclerViewww4,60);
        firstSpin(R.id.recyclerViewww5,70);

       int[] counter = new int[5];
        for(int i = 0; i < 5;i++){
            counter[result.get(i)]++;
        }
        int []a = counter;
        int max = a[0];
        for (int i =0;i<a.length; i++){
            if(a[i] > max){
                int tmp = a[i];
                a[i] = max;
                max = tmp;
            }
        }
        if(max > 1) {
            win = stavka * max;
        } else {
            win = -stavka;
        }

        sqLiteOpenHelper = new PlayerStatsDataBase(this);
        try {
            db = sqLiteOpenHelper.getWritableDatabase();
            cursor = db.query("STATS",
                    new String[]{"_id","MONEY","LVLDETAILS","LVL","NEXTLVL"},
                    null,null,null,null, null);

            cursor.moveToFirst();

            LVL = cursor.getInt(cursor.getColumnIndex("LVL"));
            Money = cursor.getInt(cursor.getColumnIndex("MONEY"));
            lvlDetails = cursor.getInt(cursor.getColumnIndex("LVLDETAILS"));
            nextlvl = cursor.getInt(cursor.getColumnIndex("NEXTLVL"));

            Money = Money + win;
            lvlDetails = lvlDetails + 100 * max;

            ContentValues cv = new ContentValues();
            if(lvlDetails >= nextlvl){
                lvlDetails = 0;
                LVL = LVL + 1;
                nextlvl = nextlvl + 1000;
            }

            CustomProgressBar customProgressBar = findViewById(R.id.progress);
            customProgressBar.setValue((lvlDetails*100)/nextlvl);
            cv.put("MONEY",Money);
            cv.put("LVLDETAILS",lvlDetails);
            cv.put("LVL", LVL);
            cv.put("NEXTLVL",nextlvl);

            db.update("STATS",cv, null, null);

            moneyBalans.setText(Integer.toString(Money));
            lvlBalans.setText(Integer.toString(LVL));

            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
        cashSum.setText("WIN " + Integer.toString(win));
        result.clear();
    }

    private void firstSpin(int id,int sum) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        final Integer[] randomFrames3_1 = new Integer[sum];
        for(int i = 0; i <sum; i++){
            randomFrames3_1[i] = random.nextInt(5);
        }
        for(int i = 0; i<randomFrames3_1.length; i++){
            switch (randomFrames3_1[i]){
                case 0:
                    list.add(R.drawable.image1);
                    break;
                case 1:
                    list.add(R.drawable.image2);
                    break;
                case 2:
                    list.add(R.drawable.image3);
                    break;
                case 3:
                    list.add(R.drawable.image4);
                    break;
                case 4:
                    list.add(R.drawable.image5);
                    break;
            }
        }

        result.add(randomFrames3_1[sum - 2]);

        final RecyclerView recyclerView = findViewById(id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CustomAdapter customAdapter = new CustomAdapter(this,list);
        recyclerView.setAdapter(customAdapter);
        recyclerView.smoothScrollToPosition(randomFrames3_1.length);
        //recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                if (action == MotionEvent.ACTION_DOWN) {
//                    recyclerView.smoothScrollToPosition(randomFrames3_1.length);
//                    return true;
//                } else if (action == MotionEvent.ACTION_UP) {
//                    recyclerView.smoothScrollToPosition(randomFrames3_1.length);
//                    return true;
//                }
                recyclerView.setLayoutFrozen(true);
                    return false;
            }
        });

    }

}
