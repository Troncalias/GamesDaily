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
import com.example.tronc.gamesdaily.Models.User;
import com.example.tronc.gamesdaily.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<User> mList;
    private Context mContext;
    private ViewHolder contactView;

    public UserAdapter(Context mContext, ArrayList<User> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View views = inflater.inflate(R.layout.item_user, viewGroup, false);

        // Return a new holder instance
        return new UserAdapter.ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder viewHolder, int position) {
        final User myItem = mList.get(position);

        ImageView imageView = viewHolder.imageView;
        if(myItem.getImagem() != null){
            ByteArrayInputStream imageStream = new ByteArrayInputStream(myItem.getImagem());
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(theImage);
        }

        TextView usernameView = viewHolder.usernameView;
        usernameView.setText(myItem.getUsername());
        TextView emailView = viewHolder.emailView;
        emailView.setText(getContext().getString(R.string.pretitle_email) + " " + myItem.getEmail());

        TextView dataInsercaoTv =viewHolder.dataInsercaoView;
        String mydata = myItem.getDataRegisto().split(" ")[0];
        String myhora = myItem.getDataRegisto().split(" ")[1];
        myhora = myhora.split(":")[0] + ":" + myhora.split(":")[1];
        String mydatahora = mydata + " " + myhora;
        String textData = getContext().getString(R.string.pretitle_data_registo);
        dataInsercaoTv.setText(textData + " " + mydatahora);

        Button removeButton = viewHolder.removeButton;
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!myItem.getUsername().equals("admin")){
                    AdminActivity.RemoveUser(myItem);
                }
            }
        });

        Button makeAdminButton = viewHolder.makeAdminButton;
        if(myItem.getTipo_utilizador_id() == 2){
            makeAdminButton.setText("Defenir como user");
        }else{
            makeAdminButton.setText("Defenir como admin");
        }
        makeAdminButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AdminActivity.MakeAdmin(myItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<User> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameView;
        public TextView dataInsercaoView;
        public TextView emailView;
        public Button removeButton;
        public Button makeAdminButton;
        public ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameView = (TextView) itemView.findViewById(R.id.utilizador_usernameTv);
            dataInsercaoView = (TextView) itemView.findViewById(R.id.dataInsercaoTv);
            emailView = (TextView) itemView.findViewById(R.id.emailTv);
            removeButton = (Button) itemView.findViewById(R.id.button_remove);
            makeAdminButton = (Button) itemView.findViewById(R.id.button_make_admin);
            imageView = (ImageView) itemView.findViewById(R.id.utilizador_imageView);
        }
    }
}
