package com.assef.soare.uber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assef.soare.uber.R;
import com.assef.soare.uber.model.Requisicao;
import com.assef.soare.uber.model.Usuario;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequisicoesAdapter extends RecyclerView.Adapter<RequisicoesAdapter.MyViewHolder> {
    private List<Requisicao> listaRequisicoes;
    private Context context;
    private Usuario motorista;

    public RequisicoesAdapter(List<Requisicao> requisicaos, Context context, Usuario motorista) {
        this.listaRequisicoes = requisicaos;
        this.context = context;
        this.motorista = motorista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_requisicoes, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Requisicao requisicao = listaRequisicoes.get(position);
        Usuario passageiro = requisicao.getPassageiro();
        holder.nome.setText(passageiro.getNome());
        holder.distancia.setText("1.4 km - aproximadamente");

    }

    @Override
    public int getItemCount() {
        return listaRequisicoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nome, distancia;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textRequisicaoNome);
            distancia = itemView.findViewById(R.id.textRequisicaoDistancia);
        }
    }
}
