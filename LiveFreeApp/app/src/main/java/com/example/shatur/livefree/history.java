package com.example.shatur.livefree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class history extends AppCompatActivity {

    View view;
    ViewGroup parent;
    JsonObjectRequest jsObjRequest;
    TextView date, time, aqi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getData();
    }

    private void getData(){
        final RequestQueue queue = Volley.newRequestQueue(this);
        jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, "http://livefree.esy.es/history.php", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Hee", response.toString());
                        try {
                            inflateViews(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("Error", String.valueOf(error));

                    }
                });
        queue.add(jsObjRequest);

    }

    private void inflateViews(JSONObject response) throws JSONException {

        JSONArray jsobj = response.getJSONArray("values");

        for (int i=0;i<jsobj.length();i++){
            JSONObject obj = jsobj.getJSONObject(i);

            parent = (ViewGroup) findViewById(R.id.history_layout);
            view = LayoutInflater.from(this).inflate(R.layout.history_views, parent, false);
            date = (TextView) view.findViewById(R.id.date_view);
            time = (TextView) view.findViewById(R.id.time_view);
            aqi = (TextView) view.findViewById(R.id.history_aqi);

            date.setText(obj.getString("date"));
            time.setText(obj.getString("time"));
            aqi.setText(obj.getString("aqi"));
            parent.addView(view);

        }
    }
}
