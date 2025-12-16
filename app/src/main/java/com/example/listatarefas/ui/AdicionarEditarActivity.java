package com.example.listatarefas.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.listatarefas.R;
import com.example.listatarefas.model.Tarefa;
import com.example.listatarefas.persistence.TarefaDAO;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarEditarActivity extends AppCompatActivity {

    private TextInputEditText editNome;
    private Tarefa tarefa;
    private TarefaDAO tarefaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_editar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editNome = findViewById(R.id.editNomeTarefa);
        tarefaDAO = new TarefaDAO(this);

        if (getIntent().hasExtra("tarefa")) {
            tarefa = (Tarefa) getIntent().getSerializableExtra("tarefa");
            editNome.setText(tarefa.getNome());
        } else {
            tarefa = new Tarefa();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_salvar) {
            salvar();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvar() {
        String nome = editNome.getText().toString().trim();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Informe o nome da tarefa", Toast.LENGTH_SHORT).show();
            return;
        }

        tarefa.setNome(nome);
        tarefaDAO.salvar(tarefa);
        finish();
    }
}
