package com.example.listatarefas.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatarefas.R;
import com.example.listatarefas.adapter.TarefaAdapter;
import com.example.listatarefas.model.Tarefa;
import com.example.listatarefas.persistence.TarefaDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TarefaAdapter adapter;
    private TarefaDAO tarefaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewTarefas);
        FloatingActionButton fab = findViewById(R.id.fabAdicionar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tarefaDAO = new TarefaDAO(this);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdicionarEditarActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarLista();
    }

    private void carregarLista() {
        List<Tarefa> tarefas = tarefaDAO.listar();

        adapter = new TarefaAdapter(tarefas,
                tarefa -> {
                    Intent intent = new Intent(MainActivity.this, AdicionarEditarActivity.class);
                    intent.putExtra("tarefa", tarefa);
                    startActivity(intent);
                },
                tarefa -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Excluir Tarefa")
                            .setMessage("Deseja excluir esta tarefa?")
                            .setPositiveButton("Sim", (dialog, which) -> {
                                tarefaDAO.deletar(tarefa);
                                carregarLista();
                            })
                            .setNegativeButton("Não", null)
                            .show();
                });

        recyclerView.setAdapter(adapter);
    }
}
