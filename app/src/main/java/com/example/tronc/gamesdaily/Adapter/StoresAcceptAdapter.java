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

import com.example.tronc.gamesdaily.Activity.AdminActivity;
import com.example.tronc.gamesdaily.Models.Stores;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class StoresAcceptAdapter extends RecyclerView.Adapter<StoresAcceptAdapter.ViewHolder> {
    private List<Stores> mList;
    private Context mContext;


    public StoresAcceptAdapter( Context mContext, List<Stores> mList) {
        this.mList = mList;
        this.mContext = mContext;
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
        View views = inflater.inflate(R.layout.item_stores_accept, viewGroup, false);

        // Return a new holder instance
        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
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

        String mydata = myItem.getDataInsercao().split(" ")[0];
        String myhora = myItem.getDataInsercao().split(" ")[1];
        myhora = myhora.split(":")[0] + ":" + myhora.split(":")[1];
        String mydatahora = mydata + " " + myhora;
        String textData = getContext().getString(R.string.pretitle_data_insercao);
        data.setText(textData + " " + mydatahora);

        Button confirmar = viewHolder.selectButton;
        confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AdminActivity.DecisionStores(myItem, true);
            }
        });

        Button negar = viewHolder.comentsButton;
        negar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AdminActivity.DecisionStores(myItem, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setList(ArrayList<Stores> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView moradaView;
        public TextView descricaoView;
        public TextView dateView;
        public Button selectButton;
        public Button comentsButton;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.name);
            moradaView = (TextView) itemView.findViewById(R.id.morada);
            selectButton = (Button) itemView.findViewById(R.id.select_button);
            comentsButton = (Button) itemView.findViewById(R.id.comentarios_button);
            descricaoView = (TextView) itemView.findViewById(R.id.descricao);
            dateView = (TextView) itemView.findViewById(R.id.date_insercao);
            imageView = (ImageView) itemView.findViewById(R.id.loja_imageView);
        }
    }
}
