package com.js.tmdb.Vistas.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.js.tmdb.BuildConfig;
import com.js.tmdb.Callbacks.IdmovieForDetails;
import com.js.tmdb.Controlador.ControladorPopularMovies;
import com.js.tmdb.Adapters.Movie_Adapter;
import com.js.tmdb.DataBase.DbHelper;
import com.js.tmdb.DataBase.InsertSqlite;
import com.js.tmdb.Model.Model;
import com.js.tmdb.R;

import java.util.ArrayList;


public class PopularMovie extends Fragment implements IdmovieForDetails {
    boolean Press;
    String id;
    private Movie_Adapter adapter;
    RecyclerView recyclerViewMovies;
    View View;
    EditText SearchMovie;
    ArrayList<Model>PopularMovies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PopularMovies = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_popular_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DbHelper dbHelper = new DbHelper(getActivity());
        recyclerViewMovies = view.findViewById(R.id.movies);
        SearchMovie = view.findViewById(R.id.search_movie);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerViewMovies.setLayoutManager(layoutManager);
        View=view;
        ControladorPopularMovies controladorPopularMovies = new ControladorPopularMovies(getActivity());
        PopularMovies = (ArrayList<Model>) controladorPopularMovies.ObtenerDatos();
        adapter = new Movie_Adapter(PopularMovies,getContext(),this);
        recyclerViewMovies.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        SearchMovie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        switch(getFirstTimeRun()) {
            case 0:
                Log.d("appPreferences", "Es la primera vez!");
                InsertSqlite insertSqlite = new InsertSqlite(getActivity());
                insertSqlite.ObtenerDatos();
                PopularMovies = (ArrayList<Model>) controladorPopularMovies.ObtenerDatos();
                adapter = new Movie_Adapter(PopularMovies,getContext(),this);
                recyclerViewMovies.setAdapter(adapter);
                break;
            case 1:
                Log.d("appPreferences", "ya has iniciado la app alguna vez");
                break;
        }
    }

    private void filter(String TextSearch) {
        ArrayList<Model> filtrarlista = new ArrayList<>();
        for(Model model: PopularMovies){
            if(model.getTitle().toLowerCase().contains(TextSearch)){
                filtrarlista.add(model);
            }
        }
        adapter.filtar(filtrarlista);
    }

    @Override
    public void Idmovie(String IdMovie, boolean press) {
        Press = press;
        id = IdMovie;
        navegation();
    }
    public void navegation(){
        if(Press) {
            Bundle bundle = new Bundle();
            bundle.putString("MovieId", id);
            Navigation.findNavController(View).navigate(R.id.action_popularMovie_to_detailsMovie,bundle);
        }
    }
    private int getFirstTimeRun() {
        SharedPreferences sp = getActivity().getSharedPreferences("MYAPP", 0);
        int result, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0; else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        return result;
    }
}