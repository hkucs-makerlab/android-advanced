package com.example.customfancontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        DialView dialView=findViewById(R.id.dialView);
        //dialView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e("test","dial view is clicked!");

    }
}
