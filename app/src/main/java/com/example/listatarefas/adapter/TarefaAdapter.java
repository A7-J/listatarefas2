package com.example.listatarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatarefas.R;
import com.example.listatarefas.model.Tarefa;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onClick(Tarefa tarefa);
    }

    public interface OnItemLongClickListener {
        void onLongClick(Tarefa tarefa);
    }

    private List<Tarefa> tarefas;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    public TarefaAdapter(List<Tarefa> tarefas,
                         OnItemClickListener clickListener,
                         OnItemLongClickListener longClickListener) {
        this.tarefas = tarefas;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarefa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarefa tarefa = tarefas.get(position);
        holder.txtNome.setText(tarefa.getNome());

        holder.itemView.setOnClickListener(v -> clickListener.onClick(tarefa));
        holder.itemView.setOnLongClickListener(v -> {
            longClickListener.onLongClick(tarefa);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNome;

        ViewHolder(View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNomeTarefa);
        }
    }
}
