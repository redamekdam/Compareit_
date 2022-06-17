package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
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
public class SearchResults extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    List<ProductModelClass> productList;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;


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
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);

                        return true;
                    case R.id.Favorites:
                        startActivity(new Intent(getApplicationContext(),Favorites.class));
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
        SearchContext=SearchContext.replaceAll("\\s+","+");
        String url = "https://serpapi.com/search.json?q="+SearchContext+"&tbm=shop&hl=en&gl=us&api_key=119849874229794268514dfaf7c8e8d1dbb62d41fa6a71fd426c71265c5d935f";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject arg0) {
                try {
                    JSONArray array = arg0.getJSONArray("shopping_results");
                    for (int i = 0; i < array.length(); i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        ProductModelClass modelClass=new ProductModelClass();
                        if (jsonObject.has("title")){
                            modelClass.setTitle(jsonObject.getString("title"));
                        }if(jsonObject.has("price")){
                            modelClass.setPrice(jsonObject.getString("price"));
                        }if(jsonObject.has("source")){
                            modelClass.setStore(jsonObject.getString("source"));
                        }if(jsonObject.has("rating")){
                            modelClass.setRating(jsonObject.getString("rating"));
                        }if(jsonObject.has("link")){
                            modelClass.setLink(jsonObject.getString("link"));
                        }if(jsonObject.has("thumbnail")){
                            modelClass.setImage(jsonObject.getString("thumbnail"));
                        }
                        productList.add(modelClass);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Adaptry adapter = new Adaptry(SearchResults.this , productList);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Toast.makeText(SearchResults.this, arg0.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
}