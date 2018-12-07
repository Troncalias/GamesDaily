package com.example.tronc.gamesdaily.Adapter;

import android.app.Activity;
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

import com.example.tronc.gamesdaily.Activity.GamesActivity;
import com.example.tronc.gamesdaily.Models.Games;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class GamesEditAdapter extends RecyclerView.Adapter<GamesEditAdapter.ViewHolder> {
    private ArrayList<Games> mList;
    private Context mContext;
    private ViewHolder contactView;
    private Activity mActivity;

    public GamesEditAdapter(Context mContext, ArrayList<Games> mList, Activity activity) {
        this.mList = mList;
        this.mContext = mContext;;
        this.mActivity = activity;
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
        View views = inflater.inflate(R.layout.item_game, viewGroup, false);

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
        TextView textView1 = viewHolder.numberPlayers;
        TextView rating = viewHolder.ratingView;
        TextView data = viewHolder.dateView;
        textView.setText(myItem.getNome());
        textView1.setText("Jogadores: " +  myItem.getNumber_jogadores());
        String textRating = getContext().getString(R.string.pretitle_rating);
        String rate = GamesActivity.getRatingGame(myItem.getId());
        rating.setText( textRating + " " + rate);

        String mydata = myItem.getDataInsercao().split(" ")[0];
        String myhora = myItem.getDataInsercao().split(" ")[1];
        myhora = myhora.split(":")[0] + ":" + myhora.split(":")[1];
        String mydatahora = mydata + " " + myhora;
        String textData = getContext().getString(R.string.pretitle_data_insercao);
        data.setText(textData + " " + mydatahora);

        Button selectButton = viewHolder.selectButton;
        selectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GamesActivity.openGame(myItem, mActivity);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView numberPlayers;
        public TextView ratingView;
        public TextView dateView;
        public Button selectButton;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.game_nameTv);
            numberPlayers = (TextView) itemView.findViewById(R.id.number_playersTv);
            selectButton = (Button) itemView.findViewById(R.id.select_button);
            ratingView = (TextView) itemView.findViewById(R.id.game_ratingTv);
            dateView = (TextView) itemView.findViewById(R.id.date_insercaoTv);
            imageView = (ImageView) itemView.findViewById(R.id.game_imageView);
        }
    }
}
