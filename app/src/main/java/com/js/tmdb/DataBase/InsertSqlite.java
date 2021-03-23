package com.js.tmdb.DataBase;

import android.content.ContentValues;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InsertSqlite {
    Context mContex;
    private RequestQueue queue;

    public InsertSqlite(Context mContex) {
        this.mContex = mContex;
        queue = Volley.newRequestQueue(mContex);
    }
    public void ObtenerDatos(){
        for(int i = 1; i<=5;i++){
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=25bebfcc6fad633f8d046c49c7af3c7b&language=es&page="+i;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for(int i=0;i<jsonArray.length() ; i++){
                        DbHelper dbHelper = new DbHelper(mContex);
                        JSONObject json = jsonArray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DbHelper.MOVIE_TABLE_COLUMN_ID_MOVIE,json.getString("id"));
                        contentValues.put(DbHelper.MOVIE_TABLE_COLUMN_TITLE,json.getString("title"));
                        contentValues.put(DbHelper.MOVIE_TABLE_COLUMN_RATED,json.getString("vote_average"));
                        contentValues.put(DbHelper.MOVIE_TABLE_COLUMN_POSTER,"https://image.tmdb.org/t/p/w500"+json.getString("poster_path"));
                        dbHelper.InserMoviePopular(contentValues);
                    }
                    //progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(request);
      }
    }
}
