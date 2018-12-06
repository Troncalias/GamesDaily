package com.example.tronc.gamesdaily.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tronc.gamesdaily.Activity.GamesActivity;
import com.example.tronc.gamesdaily.Models.News;
import com.example.tronc.gamesdaily.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private List<News> mList;
    private Context mContext;
    private NewsAdapter.ViewHolder contactView;

    public NewsAdapter(Context mContext, List<News> mList) {
        this.mList = mList;
        this.mContext = mContext;;
    }

    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View views = inflater.inflate(R.layout.item_news, viewGroup, false);

        // Return a new holder instance
        return new NewsAdapter.ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder viewHolder, int position) {
        final News myItem = mList.get(position);

        TextView textView = viewHolder.nameNews;
        TextView textView1 = viewHolder.descricaoNews;
        TextView data = viewHolder.dateView;
        textView.setText(myItem.getNewsName());
        textView1.setText(myItem.getDescricao());
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


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameNews;
        public TextView descricaoNews;
        public TextView dateView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameNews = (TextView) itemView.findViewById(R.id.news_name);
            descricaoNews = (TextView) itemView.findViewById(R.id.descricao);
            dateView = (TextView) itemView.findViewById(R.id.date_insercao);

        }
    }
}
