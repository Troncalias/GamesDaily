package com.example.tronc.gamesdaily.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tronc.gamesdaily.Models.Mensage;
import com.example.tronc.gamesdaily.R;

import java.util.ArrayList;

/**
 * Adapter que apresenta todas as mensagens de um chat especifico
 */
public class MensageAdapter extends RecyclerView.Adapter<MensageAdapter.ViewHolder>{
    private ArrayList<Mensage> mList;
    private Context mContext;
    private ChatAdapter.ViewHolder contactView;


    public MensageAdapter(Context mContext, ArrayList<Mensage> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View views = inflater.inflate(R.layout.item_mensage, viewGroup, false);

        // Return a new holder instance
        return new ViewHolder(views);
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Mensage myItem = mList.get(position);

        TextView textView = viewHolder.Titulo;
        TextView textView1 = viewHolder.Descricao;
        TextView data = viewHolder.Data;
        textView.setText(myItem.getUsername());
        textView1.setText(myItem.getConteudo());

        String mydata = myItem.getDataInsercao().split(" ")[0];
        String myhora = myItem.getDataInsercao().split(" ")[1];
        myhora = myhora.split(":")[0] + ":" + myhora.split(":")[1];
        String mydatahora = mydata + " " + myhora;
        String textData = getContext().getString(R.string.pretitle_data_insercao);
        data.setText(textData + " " + mydatahora);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<Mensage> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Titulo;
        public TextView Descricao;
        public TextView Data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Titulo = (TextView) itemView.findViewById(R.id.user_mensagem);
            Descricao = (TextView) itemView.findViewById(R.id.descricao_mensagem);
            Data = (TextView) itemView.findViewById(R.id.data_mensagem);
        }
    }

}
