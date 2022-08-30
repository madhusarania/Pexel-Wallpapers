package com.example.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    private EditText searchEdt;
    private ImageView searchIV;
    private RecyclerView categoryRV,wallpaperRV;
    private ProgressBar loadingPB;
    private ArrayList<String> wallpaperArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private WallpaperRVAdapter wallpaperRVAdapter;

    private String BASE_URL="https://api.pexels.com/v1/";
    private String getImages="curated?per_page=30&page=1";
    private String searchImages="search?query=technology&per_page=30&page=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEdt = findViewById(R.id.idEdtSearch);
        searchIV = findViewById(R.id.idIVSearch);
        categoryRV = findViewById(R.id.idRVCategory);
        wallpaperRV = findViewById(R.id.idRVWallpapers);
        loadingPB = findViewById(R.id.idPBLoading);
        wallpaperArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false);
        categoryRV.setLayoutManager(linearLayoutManager);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        categoryRV.setAdapter(categoryRVAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        wallpaperRV.setLayoutManager(gridLayoutManager);
        wallpaperRVAdapter = new WallpaperRVAdapter(wallpaperArrayList,this);
        wallpaperRV.setAdapter(wallpaperRVAdapter);
         getCategories();
         getWallpapers();
          searchIV.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String searchStr = searchEdt.getText().toString();
                  if(searchStr.isEmpty()){
                      Toast.makeText(MainActivity.this,"Please enter your search query", Toast.LENGTH_SHORT).show();
                  }else{
                      getWallpapersByCategory(searchStr);
                  }

              }
          });
    }

    private void getWallpapersByCategory(String category) {
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/search?query="+category+"&per_page=30&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray photoArray = null;
                loadingPB.setVisibility(View.VISIBLE);
                try {
                    photoArray = response.getJSONArray("photos");
                    for(int i=0; i< photoArray.length();i++){
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);

                    }
                    wallpaperRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to load wallpapers.", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String>headers = new HashMap<>();
                headers.put("Authorization","563492ad6f91700001000001a7bc8af8bb5247c49f60e5692a835e8d");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    private void getWallpapers() {
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?per_page=30&page=1";
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try {

                    JSONArray photoArray = response.getJSONArray("photos");
                    for (int i = 0; i < photoArray.length(); i++) {
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);
                    }
                    wallpaperRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to load wallpapers..", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers=new HashMap<>();
                headers.put("Authorization","563492ad6f91700001000001a7bc8af8bb5247c49f60e5692a835e8d");

                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }



    private void getCategories() {
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://www.pexels.com/photo/black-and-gray-motherboard-2582937/"));
        categoryRVModalArrayList.add(new CategoryRVModal("Programming","https://www.pexels.com/photo/full-frame-shot-of-abstract-pattern-249798/"));
        categoryRVModalArrayList.add(new CategoryRVModal("Nature","https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto-compress&cs-tinysrgb&dpr=1&w=500"));
        categoryRVModalArrayList.add(new CategoryRVModal("Travel","https://www.pexels.com/photo/airplane-windowpane-showing-city-buildings-316794/"));
        categoryRVModalArrayList.add(new CategoryRVModal("Architecture","https://www.pexels.com/photo/empty-cathedral-135018/"));
        categoryRVModalArrayList.add(new CategoryRVModal("Arts","https://www.pexels.com/photo/person-with-body-painting-1209843/"));
        categoryRVModalArrayList.add(new CategoryRVModal("Music","https://www.pexels.com/photo/turned-on-black-samsung-smartphone-between-headphones-1337753/"));
        categoryRVModalArrayList.add(new CategoryRVModal("Abstract","https://www.pexels.com/photo/silhouette-of-person-holding-sparkler-digital-wallpaepr-266429/"));
        categoryRVModalArrayList.add(new CategoryRVModal("Cars","https://www.pexels.com/photo/white-coiupe-274974/"));
        categoryRVModalArrayList.add(new CategoryRVModal("Flowers","https://www.pexels.com/photo/landscape-nature-night-relaxation-36478/"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(int position) {
        String category=categoryRVModalArrayList.get(position).getCategory();
       getWallpapersByCategory(category);

    }
}