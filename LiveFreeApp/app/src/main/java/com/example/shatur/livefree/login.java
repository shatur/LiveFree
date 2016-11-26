/* This Source Code  is a part of LiveFree project
 * https://github.com/shatur/LiveFreeApp.*/

package com.example.shatur.livefree;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shatur.livefree.userDatabase.DBConstants;
import com.example.shatur.livefree.userDatabase.createDB;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    String LOGTAG = login.class.getSimpleName().toUpperCase();
    TextView login_id, pass;
    Context context;
    JSONObject jsObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        context = login.this;
        login_id = (TextView) findViewById(R.id.login_id);
        pass = (TextView) findViewById(R.id.login_password);

    }

    // Verify credentials
    public void login(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Check for network connection
        if (networkInfo != null && networkInfo.isConnected()) {
            // URL to verify credentials
            String url = "http://livefree.esy.es/verify.php";
            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // Response received
                            if (response.compareTo("Not found")==0){
                                Toast.makeText(getBaseContext(),"User Name or Password is incorrect.",Toast.LENGTH_SHORT).show();
                                Log.d(LOGTAG, "Record not found");
                            }
                            else {
                                Log.d(LOGTAG,"Record Found");
                                try {
                                    jsObject = new JSONObject(response);
                                    // Writes data to DB
                                    writeDatatoDB();
                                    Intent intent = new Intent(getApplicationContext(),userPage.class);
                                    intent.putExtra("user_name",jsObject.getString("user_name"));
                                    startActivity(intent);
                                    login.this.finish();
                                } catch (Exception e) {
                                    Log.d(LOGTAG, "Caught Exception while writng to DB");
                                }
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(LOGTAG, "Error received by volley.");
                        }
                    }
            ) {
                // Data to be sent to server
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("id", login_id.getText().toString());
                    params.put("password", pass.getText().toString());

                    return params;
                }
            };
            queue.add(postRequest);

        }
        else {
            Toast.makeText(getBaseContext(),"No network available",Toast.LENGTH_SHORT).show();
        }
    }

    // After successful login write data to DB
    public void writeDatatoDB() throws Exception{
        createDB dbHelper = new createDB(getBaseContext());
        SQLiteDatabase sqlWrite= dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBConstants.USER_NAME, jsObject.getString("user_name"));
        values.put(DBConstants.USER_EMAIL, jsObject.getString("user_email"));
        values.put(DBConstants.USER_DEVICE_ID, jsObject.getString("device_id"));

        long flag = dbHelper.insertEntries(values, sqlWrite);
        if (flag == 0) {
            Log.d(LOGTAG, "Error writing data to table");
        }
    }

    // Switch to SignUp activity
    public void signUp(View view) {
        Intent intent = new Intent(getApplicationContext(), signUp.class);
        startActivity(intent);
    }
}
