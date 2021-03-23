package com.js.tmdb.Controlador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.js.tmdb.DataBase.DbHelper;
import com.js.tmdb.Model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ControladorPopularMovies {
    Context mContext;
    DbHelper dbHelper;
    SQLiteDatabase db;
    List<Model> movies;
    public ControladorPopularMovies(Context mContext) {
        this.mContext = mContext;
        movies = new ArrayList<>();
        dbHelper = new DbHelper(mContext);
        db= dbHelper.getReadableDatabase();
    }
    public List<Model> ObtenerDatos(){
        String Columns[] = {DbHelper.MOVIE_TABLE_COLUMN_ID_MOVIE,DbHelper.MOVIE_TABLE_COLUMN_TITLE,DbHelper.MOVIE_TABLE_COLUMN_POSTER,DbHelper.MOVIE_TABLE_COLUMN_RATED};
        Cursor cursor = db.query(DbHelper.TABLE_MOVIE_POPULAR,Columns,null,null,null,null,DbHelper.MOVIE_TABLE_COLUMN_RATED+" DESC");
        movies.removeAll(movies);
        while (cursor.moveToNext()){
            Model modelMovie = new Model();
            modelMovie.setId(cursor.getString(0));
            modelMovie.setTitle(cursor.getString(1));
            modelMovie.setPoster_path(cursor.getString(2));
            modelMovie.setVote_average(cursor.getString(3));
            movies.add(modelMovie);
        }
        return movies;
    }

}
