package com.example.listatarefas.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tarefas.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_TAREFAS = "tarefas";
    public static final String COL_ID = "id";
    public static final String COL_NOME = "nome";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_TAREFAS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NOME + " TEXT NOT NULL);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAREFAS);
        onCreate(db);
    }
}
