package com.example.tronc.gamesdaily.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tronc.gamesdaily.Activity.ChatActivity;
import com.example.tronc.gamesdaily.Models.Chat;
import com.example.tronc.gamesdaily.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    private ArrayList<Chat> mList;
    private Context mContext;
    private Activity mActivity;
    private String user;

    public ChatAdapter(Context mContext, ArrayList<Chat> mList, Activity activity, String user) {
        this.mList = mList;
        this.mContext = mContext;
        this.mActivity = activity;
        this.user = user;
    }

    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View views = inflater.inflate(R.layout.item_chat, viewGroup, false);

        // Return a new holder instance
        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder viewHolder, int position) {
        final Chat myItem = mList.get(position);

        TextView textView = viewHolder.Titulo;
        TextView textView1 = viewHolder.Descricao;
        TextView data = viewHolder.Data;
        textView.setText(myItem.getTitulo());
        textView1.setText(myItem.getDescricao());

        String mydata = myItem.getDataCriação().split(" ")[0];
        String myhora = myItem.getDataCriação().split(" ")[1];
        myhora = myhora.split(":")[0] + ":" + myhora.split(":")[1];
        String mydatahora = mydata + " " + myhora;
        String textData = getContext().getString(R.string.pretitle_data_insercao);
        data.setText(textData + " " + mydatahora);

        Button buttao = viewHolder.Butao;
        buttao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChatActivity.openChat(myItem, mActivity, user);
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
        public Button Butao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Titulo = (TextView) itemView.findViewById(R.id.Title_chatTv);
            Descricao = (TextView) itemView.findViewById(R.id.descricao_ChatTv);
            Data = (TextView) itemView.findViewById(R.id.data_ChatTv);
            Butao = (Button) itemView.findViewById(R.id.select_button);
        }
    }
}
