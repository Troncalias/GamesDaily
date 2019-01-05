package com.example.tronc.gamesdaily.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tronc.gamesdaily.Activity.DefenitionActivity;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class GamesStoresAdapter extends RecyclerView.Adapter<GamesStoresAdapter.ViewHolder> {
    private ArrayList<Games> mList;
    private Context mContext;
    private boolean add = true;

    public GamesStoresAdapter(Context mContext, ArrayList<Games> mList){
        this.mList = mList;
        this.mContext = mContext;
    }

    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public GamesStoresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View views = inflater.inflate(R.layout.item_game_add_to_store, viewGroup, false);

        // Return a new holder instance
        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Games myItem = mList.get(position);
        ImageView imageView = viewHolder.imageView;
        if(myItem.getImagem() != null){
            ByteArrayInputStream imageStream = new ByteArrayInputStream(myItem.getImagem());
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(theImage);
        }
        TextView textView = viewHolder.nameTextView;
        TextView description = viewHolder.descriptionView;
        TextView textView1 = viewHolder.numberPlayers;
        TextView rating = viewHolder.ratingView;
        TextView data = viewHolder.dateView;

        textView.setText(myItem.getName());
        description.setText(myItem.getDescription());
        textView1.setText("Jogadores: " +  myItem.getNumberGamers());
        String textRating = getContext().getString(R.string.pretitle_rating);
        String rate = Float.toString(myItem.getRating());
        rating.setText( textRating + " " + rate);

        String mydata = myItem.getDate().split(" ")[0];
        String myhora = myItem.getDate().split(" ")[1];
        myhora = myhora.split(":")[0] + ":" + myhora.split(":")[1];
        String mydatahora = mydata + " " + myhora;
        String textData = getContext().getString(R.string.pretitle_data_insercao);
        data.setText(textData + " " + mydatahora);

        Button confirmar = viewHolder.selectButton;
        if(add == false){
            confirmar.setText("Remove");
        }
        confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DefenitionActivity.decisionGame(myItem, add);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<Games> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void reversAdd(boolean add){
        this.add = add;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView numberPlayers;
        public TextView ratingView;
        public TextView dateView;
        public TextView descriptionView;
        public Button selectButton;
        public ImageView imageView;
        public ViewHolder(View views) {
            super(views);
            nameTextView = (TextView) itemView.findViewById(R.id.game_nameTv);
            descriptionView = (TextView) itemView.findViewById(R.id.game_descriptionTv);
            numberPlayers = (TextView) itemView.findViewById(R.id.number_playersTv);
            selectButton = (Button) itemView.findViewById(R.id.select_button);
            ratingView = (TextView) itemView.findViewById(R.id.game_ratingTv);
            dateView = (TextView) itemView.findViewById(R.id.date_insercaoTv);
            imageView = (ImageView) itemView.findViewById(R.id.game_imageView);
        }
    }
}
