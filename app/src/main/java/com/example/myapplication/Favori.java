package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String JSON_URL = "https://serpapi.com/search.json?engine=walmart&query=Coffee&api_key=10e228ad129da5e65612ac2406e01c65837a61f16a2d8c0e55c369c476db2635";
    List<ProductModelClass> productList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favori);
        Objects.requireNonNull(getSupportActionBar()).hide();
        productList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        Favori.GetData getData=new Favori.GetData();
        getData.execute();

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


    public class GetData extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... strings) {
        String current= "";
        try {
            URL url;
            HttpURLConnection urlConnection=null;
            try {
                url=new URL(JSON_URL);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream is=urlConnection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                int data=isr.read();
                while(data!=-1){
                    current+=(char)data;
                    data=isr.read();
                }
                return current;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (urlConnection!=null){
                    urlConnection.disconnect();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return current;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("organic_results");
            for (int i =0 ;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                ProductModelClass modelClass=new ProductModelClass();
                modelClass.setTitle(jsonObject1.getString("title"));
                modelClass.setPrice(jsonObject1.getString("offer_price"));
                modelClass.setImage(jsonObject1.getString("thumbnail"));
                productList.add(modelClass);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        PutDataIntoRecyclerView(productList);
    }

}
    private void  PutDataIntoRecyclerView(List<ProductModelClass> productList){
        Adaptry adaptry=new Adaptry(this,productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptry);
    }
}