/* This Source Code  is a part of LiveFree project
 * https://github.com/shatur/LiveFreeApp.*/

package com.example.shatur.livefree;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shatur.livefree.userDatabase.DBConstants;
import com.example.shatur.livefree.userDatabase.createDB;

import java.util.HashMap;
import java.util.Map;

public class signUp extends AppCompatActivity {

    String LOGTAG = signUp.class.getSimpleName().toUpperCase();

    EditText signUp_name, signUp_email, signup_location, signUp_password, signUp_device_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp_name = (EditText) findViewById(R.id.signup_name);
        signUp_email = (EditText) findViewById(R.id.signup_email);
        signup_location = (EditText) findViewById(R.id.signup_location);
        signUp_password = (EditText) findViewById(R.id.password);
        signUp_device_id = (EditText) findViewById(R.id.signup_device_id);
    }

    public void registerUser(View view){
        String url = "http://livefree.esy.es/register.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // Response received
                       if (response.compareTo("Not Found")==0){
                            Toast.makeText(getBaseContext(), "Device ID does not exists.",Toast.LENGTH_SHORT).show();
                            Log.d(LOGTAG, "Device not found");
                        }
                        else {
                            Log.d(LOGTAG,"Response : "+response);
                           try {
                               // Writing to DB
                               writeDatatoDB();
                           } catch (Exception e) {
                               Log.d(LOGTAG, "Error while writng to DB");
                           }
                           // Swithcing to userPage activity
                           Intent intent = new Intent(getBaseContext(), userPage.class);
                           intent.putExtra("user_name",signUp_name.getText().toString());
                           startActivity(intent);
                           signUp.this.finish();
                       }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(LOGTAG, "Error received by Volley.");
                    }
                }
        ) {
            // Sending data to server
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", signUp_name.getText().toString());
                params.put("email", signUp_email.getText().toString());
                params.put("device_id", signUp_device_id.getText().toString());
                params.put("password", signUp_password.getText().toString());

                return params;
            }
        };
        queue.add(postRequest);
    }

    // After successful signup write data to DB
    public void writeDatatoDB() throws Exception{
        createDB dbHelper = new createDB(getBaseContext());
        SQLiteDatabase sqlWrite= dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBConstants.USER_NAME, signUp_name.getText().toString());
        values.put(DBConstants.USER_EMAIL, signUp_email.getText().toString());
        values.put(DBConstants.USER_DEVICE_ID, signUp_device_id.getText().toString());

        long flag = dbHelper.insertEntries(values, sqlWrite);
        if (flag == 0) {
            Log.d(LOGTAG, "Error writing data to table");
        } else {
            Log.d(LOGTAG, "Data writen to table.");
        }
    }
}
