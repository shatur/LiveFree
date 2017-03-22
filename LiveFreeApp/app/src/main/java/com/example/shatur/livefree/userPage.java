/* This Source Code  is a part of LiveFree project
 * https://github.com/shatur/LiveFreeApp.*/

package com.example.shatur.livefree;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

public class userPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    
    final static String LOGTAG = userPage.class.getSimpleName().toUpperCase();
    static String user_name;

    TextView account_name, quality, impl_data, advi_data;

    CircleProgressView aqi_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Receives user name, to show in side menu
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        Log.e(LOGTAG, "User name :"+user_name);

        // Imports external fonts
        importFont font = new importFont(getBaseContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        aqi_circle = (CircleProgressView) findViewById(R.id.aqi);
        quality = (TextView) findViewById(R.id.quality);
        impl_data= (TextView) findViewById(R.id.imp_data);
        advi_data = (TextView) findViewById(R.id.advices_data);

        getData();

        aqi_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_page, menu);
        account_name = (TextView) findViewById(R.id.account_name);
        account_name.setText(user_name);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.history) {
            Intent intent = new Intent(getApplicationContext(),history.class);
            startActivity(intent);
        } else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(getApplicationContext(),feedback.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getData(){
        final RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, "http://livefree.esy.es/aqi_send.php", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            setAQI(Integer.parseInt(response.getString("value")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        queue.add(jsObjRequest);

    }
    private void setAQI(int aqi_val){
        if (aqi_val<51) {
            quality.setText("Excellent Air Quality");
            impl_data.setText("Air quality is considered satisfacory and air pollution posses no risk.");
            advi_data.setText("None actions are needed.");
        } else if (aqi_val>50 && aqi_val<101) {
            quality.setText("Moderate Air Quality");
            impl_data.setText("Air qulaity is acceptable, however for some pollutants there may be " +
                    "moderate health concern for very small no of people, who are sensitive to pollution.");
            advi_data.setText("Active children and adults and people with respiratory disease, such as" +
                    "asthama, should limit prolonged outdoor exertion.");
        } else if (aqi_val>100 && aqi_val<121) {
            quality.setText("Unhealthy Air Quality");
            impl_data.setText("Members of sensitive groups may experience health effects. The general " +
                    "public is not likely to be affected");
            advi_data.setText("Active children and adults and people with respiratory disease, such as" +
                    "asthama, should limit prolonged outdoor exertion.");
        } else if (aqi_val>150 && aqi_val<201) {
            quality.setText("Poor Air Quality");
            impl_data.setText("Everyone may begin to experience health effects; members of sensitive " +
                    "groups may ecperience more serious health effects");
            advi_data.setText("Active children and adults and people with respiratory disease, such as" +
                    "asthama, should limit prolonged outdoor exertion. Everyone else, especially " +
                    "children should limit outdoor exertion.");
        } else if (aqi_val>200 && aqi_val<301) {
            quality.setText("Worst Air Quality");
            impl_data.setText("Health warnings of emergency conditions, The entire population is more " +
                    "likely to be affected");
            advi_data.setText("Active children and adults and people with respiratory disease, such as" +
                    "asthama, should limit prolonged outdoor exertion. Everyone else, especially " +
                    "children should limit outdoor exertion.");
        } else {
            quality.setText("Hazardous Air Quality");
            impl_data.setText("Health alert: everyone may experience more serious health effects.");
            advi_data.setText("Everuone should avoid all outdoor exertion. Use masks which can avoid " +
                    "PM2.5 particles while going out.");
        }

        aqi_circle.setValue(aqi_val);
        aqi_circle.setTextMode(TextMode.TEXT);
        aqi_circle.setText(""+aqi_val);
    }
}
