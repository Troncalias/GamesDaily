package com.example.tronc.gamesdaily.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tronc.gamesdaily.Activity.AdminActivity;
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.R;

import java.util.ArrayList;

/**
 * Adapter que permite realizar a remoção de Chats da apicação
 */
public class ChatRemoveAdapter extends RecyclerView.Adapter<ChatRemoveAdapter.ViewHolder>{
    private ArrayList<Chat> mList;
    private Context mContext;
    private ChatAdapter.ViewHolder contactView;

    public ChatRemoveAdapter(Context mContext, ArrayList<Chat> mList) {
        this.mList = mList;
        this.mContext = mContext;;
    }

    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View views = inflater.inflate(R.layout.item_char_remove, viewGroup, false);

        // Return a new holder instance
        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Chat myItem = mList.get(position);

        TextView textView = viewHolder.Titulo;
        textView.setText(myItem.getTitulo());

        TextView textView1 = viewHolder.Descricao;
        textView1.setText(myItem.getDescricao());

        TextView data = viewHolder.Data;
        String mydata = myItem.getDataCriação().split(" ")[0];
        String myhora = myItem.getDataCriação().split(" ")[1];
        myhora = myhora.split(":")[0] + ":" + myhora.split(":")[1];
        String mydatahora = mydata + " " + myhora;
        String textData = getContext().getString(R.string.pretitle_data_insercao);
        data.setText(textData + " " + mydatahora);

        Button remover = viewHolder.Remover;
        remover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AdminActivity.RemoveChat(myItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<Chat> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Titulo;
        public TextView Descricao;
        public TextView Data;
        public Button Remover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Titulo = (TextView) itemView.findViewById(R.id.Title_chatTv);
            Descricao = (TextView) itemView.findViewById(R.id.descricao_ChatTv);
            Data = (TextView) itemView.findViewById(R.id.data_ChatTv);
            Remover = (Button) itemView.findViewById(R.id.select_button);
        }
    }
}
