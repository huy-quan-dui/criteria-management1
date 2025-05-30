package com.example.quan_ly_chi_tieu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.example.quan_ly_chi_tieu.R;

import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectViews();
        CountDownTimer count = new CountDownTimer(1000,500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {



                Intent intent = new Intent(MainActivity.this, Entrance.class);
                startActivity(intent);
            }
        }.start();

    }

    public void ConnectViews()
    {
        img=(ImageView) findViewById(R.id.loading_img);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        CountDownTimer count = new CountDownTimer(1000,500) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//
//
//                Intent intent = new Intent(MainActivity.this, Entrance.class);
//                startActivity(intent);
//            }
//        }.start();
//    }
}