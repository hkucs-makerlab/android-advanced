package com.example.customedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
    https://codelabs.developers.google.com/codelabs/advanced-android-training-customize-view/
    see EditTextWithClear.class
    - get drawable resource
    - use Motion event to find the touched object position in View.OnTouchListener interface
    - trace text change event in TextWatcher interface
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
