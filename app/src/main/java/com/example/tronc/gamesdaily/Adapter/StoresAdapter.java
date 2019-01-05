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

import com.example.tronc.gamesdaily.Activity.StoresActivity;
import com.example.tronc.gamesdaily.Models.Stores;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.util.List;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ViewHolder>{

    private List<Stores> mList;
    private Context mContext;
    private Activity mActivity;


    public StoresAdapter( Context mContext, List<Stores> mList, Activity activity) {
        this.mList = mList;
        this.mContext = mContext;
        this.mActivity = activity;
    }

    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public StoresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View views = inflater.inflate(R.layout.item_stores, viewGroup, false);

        // Return a new holder instance
        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull StoresAdapter.ViewHolder viewHolder, int position) {
        final Stores myItem = mList.get(position);
        ImageView imageView = viewHolder.imageView;
        if(myItem.getImagem() != null){
            ByteArrayInputStream imageStream = new ByteArrayInputStream(myItem.getImagem());
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(theImage);
        }
        TextView textView = viewHolder.nameTextView;
        TextView textView1 = viewHolder.moradaView;
        TextView textView2 = viewHolder.descricaoView;
        TextView data = viewHolder.dateView;

        textView.setText(myItem.getNome());
        textView1.setText(myItem.getMorada());
        textView2.setText(myItem.getDescricao());

        final String mydata = myItem.getDataInsercao().split(" ")[0];
        String myhora = myItem.getDataInsercao().split(" ")[1];
        myhora = myhora.split(":")[0] + ":" + myhora.split(":")[1];
        String mydatahora = mydata + " " + myhora;
        String textData = getContext().getString(R.string.pretitle_data_insercao);
        data.setText(textData + " " + mydatahora);

        Button selectButton = viewHolder.selectButton;

        selectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StoresActivity.openGame(myItem, mActivity);

            }
        });
        Button slected = viewHolder.comentsButton;
        slected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StoresActivity.openGames(mActivity, myItem);
            }
        });
        Button mapa = viewHolder.mapaButton;
        mapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nome = myItem.getNome();
                String descricao = myItem.getDescricao();
                String morada = myItem.getMorada();
                StoresActivity.openMap(morada, nome, descricao);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView moradaView;
        public TextView descricaoView;
        public TextView dateView;
        public Button selectButton;
        public Button comentsButton;
        public Button mapaButton;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.name);
            moradaView = (TextView) itemView.findViewById(R.id.morada);
            selectButton = (Button) itemView.findViewById(R.id.select_button);
            comentsButton = (Button) itemView.findViewById(R.id.comentarios_button);
            mapaButton = (Button) itemView.findViewById(R.id.mapa_button);
            descricaoView = (TextView) itemView.findViewById(R.id.descricao);
            dateView = (TextView) itemView.findViewById(R.id.date_insercao);
            imageView = (ImageView) itemView.findViewById(R.id.loja_imageView);
        }
    }
}
