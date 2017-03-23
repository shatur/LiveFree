/* This Source Code  is a part of LiveFree project
 * https://github.com/shatur/LiveFreeApp.*/

package com.example.shatur.livefree;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.shatur.livefree.userDatabase.createDB;

public class splashScreen extends AppCompatActivity {

    private static String LOGTAG = splashScreen.class.getSimpleName().toUpperCase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        createDB  checkDB = new createDB(getBaseContext());
        SQLiteDatabase db = checkDB.getReadableDatabase();
        final Cursor cursor = checkDB.readEntries(db);

        /* Create an Intent that will start the Menu-Activity.
         * Checks whether the user has logged in in past
         */
        if(cursor.moveToFirst()){
            Log.d(LOGTAG, "User previously logged in");
            Intent mainIntent = new Intent(getApplicationContext(),userPage.class);
            mainIntent.putExtra("user_name",cursor.getString(cursor.getColumnIndex("USER_NAME")));
            startActivity(mainIntent);
        } else {
            Log.d(LOGTAG, "No user found");
            Intent mainIntent = new Intent(getApplicationContext(), checker.class);
            startActivity(mainIntent);
        }
        splashScreen.this.finish();
    }
}
