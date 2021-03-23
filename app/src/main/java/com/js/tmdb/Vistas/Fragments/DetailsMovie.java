package com.js.tmdb.Vistas.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.js.tmdb.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsMovie extends Fragment {
    private RequestQueue queue;
    private String IdMovie;
    ImageView imageView;
    private Toolbar toolbar;
    TextView title,Descripcion,Genres,Tagline,ReleaseDate;
    FloatingActionButton BackButton;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        progressDialog= new ProgressDialog(activity);
        progressDialog.show();
        progressDialog.setTitle("Cargando detalles");
        queue = Volley.newRequestQueue(requireContext());
        imageView = view.findViewById(R.id.MoviePost);
        title = view.findViewById(R.id.titleDetails);
        Descripcion = view.findViewById(R.id.descripcionDetails);
        Tagline = view.findViewById(R.id.tagline);
        Genres = view.findViewById(R.id.genres);
        ReleaseDate = view.findViewById(R.id.releasedate);
        BackButton = view.findViewById(R.id.backFragment);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_detailsMovie_to_popularMovie);
            }
        });
        IdMovie = getArguments().getString("MovieId");
        ObtenerDatos();
    }
    private void ObtenerDatos(){
        String url = "https://api.themoviedb.org/3/movie/"+IdMovie+"?api_key=25bebfcc6fad633f8d046c49c7af3c7b&language=es";
        Log.d("UrlRequest", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                        JSONArray jsonArray = response.getJSONArray("genres");
                        JSONObject json = response;
                        CargarDatosVista(json.getString("original_title"),
                                json.getString("overview"),
                                json.getString("poster_path"),
                                json.getString("release_date"),
                                json.getString("tagline"));
                    for(int i=0;i<jsonArray.length() ; i++){
                        json = jsonArray.getJSONObject(i);
                        Genres.setText(json.getString("name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"VUELVA RECARGAR LOS DATOS",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }
    private void CargarDatosVista(String Title,String descripcion,String poster, String realeaseDate, String lema){
        title.setText(Title);
        Descripcion.setText(descripcion);
        ReleaseDate.setText(realeaseDate);
        Tagline.setText(lema);
        Picasso.with(getActivity())
                .load("https://image.tmdb.org/t/p/w500"+poster)
                .into(imageView);
        progressDialog.dismiss();
    }
}