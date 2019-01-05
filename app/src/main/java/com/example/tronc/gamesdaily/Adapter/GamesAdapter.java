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

/**
 * Adapter que permite apresentar os Games por lista
 */
public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {
    private ArrayList<Games> mList;
    private Context mContext;
    private Activity mActivity;
    private String user;

    public GamesAdapter(Context mContext, ArrayList<Games> mList, Activity activity, String user) {
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

        Button selectButton = viewHolder.selectButton;
        selectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GamesActivity.openGame(myItem, mActivity, user);

            }
        });
        Button comentsButton = viewHolder.comentsButton;
        comentsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GamesActivity.openChat(myItem, mActivity, user);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView numberPlayers;
        public TextView ratingView;
        public TextView dateView;
        public TextView descriptionView;
        public Button selectButton;
        public Button comentsButton;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.game_nameTv);
            numberPlayers = (TextView) itemView.findViewById(R.id.number_playersTv);
            ratingView = (TextView) itemView.findViewById(R.id.game_ratingTv);
            dateView = (TextView) itemView.findViewById(R.id.date_insercaoTv);
            descriptionView = (TextView) itemView.findViewById(R.id.game_descriptionTv);
            imageView = (ImageView) itemView.findViewById(R.id.game_imageView);
            selectButton = (Button) itemView.findViewById(R.id.select_button);
            comentsButton = (Button) itemView.findViewById(R.id.comentarios_button);
        }
    }
}
