package com.js.tmdb.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.js.tmdb.Callbacks.IdmovieForDetails;
import com.js.tmdb.Model.Model;
import com.js.tmdb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Movie_Adapter extends RecyclerView.Adapter<Movie_Adapter.MovieviewHolder> {
    List<Model> movies;
    Context mContext;
    IdmovieForDetails callback;

    public Movie_Adapter(List<Model> movies, Context mContext, IdmovieForDetails callback) {
        this.movies = movies;
        this.mContext = mContext;
        this.callback = callback;
    }

    @NonNull
    @Override
    public MovieviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_populate_movie,parent,false);
        MovieviewHolder holder =new MovieviewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MovieviewHolder holder, int position) {
        Model model= movies.get(position);
        holder.TitleMovie.setText(model.getTitle());
        holder.RatedMovie.setText(model.getVote_average() +"/10");

        Picasso.with(mContext)
                .load(movies.get(position).getPoster_path())
                .into(holder.ImageMovie);
        String Idmovie = model.getId();
        holder.Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.Idmovie(Idmovie,true);
            }
        });


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieviewHolder extends RecyclerView.ViewHolder{

        TextView TitleMovie,RatedMovie;
        ImageView ImageMovie;
        ImageView Details;

        public MovieviewHolder(@NonNull View itemView) {
            super(itemView);
            TitleMovie = itemView.findViewById(R.id.title_movie);
            RatedMovie = itemView.findViewById(R.id.rated_movie);
            ImageMovie = itemView.findViewById(R.id.image_movie);
            Details = itemView.findViewById(R.id.btndetails);
        }
    }
    public void filtar(ArrayList<Model> filtroMovies){
        this.movies = filtroMovies;
        notifyDataSetChanged();
    }
}
