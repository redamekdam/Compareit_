package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ALL")
public class Favori extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    List<ProductModelClass> productList;
    RecyclerView recyclerView;
    RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favori);
        Objects.requireNonNull(getSupportActionBar()).hide();
        productList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        FechData();
        bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.favorite);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.favorite:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);

                        return true;

                }
                return false;
            }
        });

    }

    private void FechData() {
        Intent i = getIntent();
        String SearchContext = i.getStringExtra("SearchContext");
        String url = "https://serpapi.com/search.json?q="+SearchContext+"&tbm=shop&location=Dallas&hl=en&gl=us&api_key=10e228ad129da5e65612ac2406e01c65837a61f16a2d8c0e55c369c476db2635";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject arg0) {
                try {
                    JSONArray array = arg0.getJSONArray("shopping_results");
                    for (int i = 0; i < 10; i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        ProductModelClass modelClass=new ProductModelClass();
                        modelClass.setTitle(jsonObject.getString("title"));
                        modelClass.setPrice(jsonObject.getString("price"));
                        modelClass.setStore(jsonObject.getString("source"));
                        modelClass.setRating(jsonObject.getString("rating"));
                        modelClass.setImage(jsonObject.getString("thumbnail"));
                        productList.add(modelClass);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Adaptry adapter = new Adaptry(Favori.this , productList);
                recyclerView.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Toast.makeText(Favori.this, arg0.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);
    }
}