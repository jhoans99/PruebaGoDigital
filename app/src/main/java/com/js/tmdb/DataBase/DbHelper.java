package com.js.tmdb.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 public class DbHelper extends SQLiteOpenHelper {

        private final SQLiteDatabase database;

        // Tabla Peliculas Populares
        public static final String TABLE_MOVIE_POPULAR= "MOVIEPOPULAR";         // Table name
        public static final String MOVIE_TABLE_COLUMN_ID = "_id";     // a column named "_id" is required for cursor
        public static final String MOVIE_TABLE_COLUMN_ID_MOVIE = "id";     // a column named "_id" is required for cursor
        public static final String MOVIE_TABLE_COLUMN_TITLE = "TITLE";
        public static final String MOVIE_TABLE_COLUMN_RATED = "RATED";
        public static final String MOVIE_TABLE_COLUMN_POSTER = "POSTER";

        // database configuration
        // if you want the onUpgrade to run then change the database_version
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "POPULARMOVIE";
        public DbHelper(Context aContext) {
            super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
            database = getWritableDatabase();
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            String buildSqlUser="CREATE TABLE " + TABLE_MOVIE_POPULAR + "( "
                    + MOVIE_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + MOVIE_TABLE_COLUMN_ID_MOVIE + " TEXT, "
                    + MOVIE_TABLE_COLUMN_TITLE + " TEXT, "
                    + MOVIE_TABLE_COLUMN_POSTER + " TEXT, "
                    + MOVIE_TABLE_COLUMN_RATED + " TEXT)";
            sqLiteDatabase.execSQL(buildSqlUser);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            String buildSQL = "DROP TABLE IF EXISTS " + TABLE_MOVIE_POPULAR;
            sqLiteDatabase.execSQL(buildSQL);

        }
        public void InserMoviePopular(ContentValues contentValues){
            SQLiteDatabase sqLiteDatabase=getWritableDatabase();
            sqLiteDatabase.insert(TABLE_MOVIE_POPULAR,null,contentValues);
        }
 }

