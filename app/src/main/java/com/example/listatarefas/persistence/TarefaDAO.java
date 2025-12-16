package com.example.listatarefas.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.listatarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public TarefaDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void salvar(Tarefa tarefa) {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.COL_NOME, tarefa.getNome());

        if (tarefa.getId() == null) {
            database.insert(DbHelper.TABLE_TAREFAS, null, values);
        } else {
            database.update(
                    DbHelper.TABLE_TAREFAS,
                    values,
                    DbHelper.COL_ID + " = ?",
                    new String[]{tarefa.getId().toString()}
            );
        }
        database.close();
    }

    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(
                DbHelper.TABLE_TAREFAS,
                null,
                null,
                null,
                null,
                null,
                DbHelper.COL_ID + " DESC"
        );

        if (cursor.moveToFirst()) {
            do {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.COL_ID)));
                tarefa.setNome(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COL_NOME)));
                tarefas.add(tarefa);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return tarefas;
    }

    public void deletar(Tarefa tarefa) {
        database = dbHelper.getWritableDatabase();
        database.delete(
                DbHelper.TABLE_TAREFAS,
                DbHelper.COL_ID + " = ?",
                new String[]{tarefa.getId().toString()}
        );
        database.close();
    }
}
