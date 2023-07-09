package com.example.game2048.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class GameDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_GAME = "create table G_INFINITE ("
            + "id integer primary key autoincrement, "
            + "x integer, "
            + "y integer, "
            + "num integer)";

    public GameDatabaseHelper(@Nullable Context context, @Nullable String name,
                              @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
