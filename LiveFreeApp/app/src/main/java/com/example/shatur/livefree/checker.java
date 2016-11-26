/* This Source Code  is a part of LiveFree project
 * https://github.com/shatur/LiveFreeApp.*/

package com.example.shatur.livefree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class checker extends AppCompatActivity {

    String LOGCAT = checker.class.getSimpleName().toUpperCase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_checker);
    }

    public void signIn(View view){
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);
    }

     public void signUp(View view) {
        Intent intent = new Intent(getApplicationContext(), signUp.class);
        startActivity(intent);
    }
}
