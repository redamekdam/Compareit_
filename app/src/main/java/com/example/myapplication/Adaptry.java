package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Adaptry extends RecyclerView.Adapter<Adaptry.MyViewHolder> {
    private Context mContext;
    private List<ProductModelClass> mData;

    public  Adaptry(Context mContext, List<ProductModelClass> mata) {
        this.mContext = mContext;
        mData= mata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater=LayoutInflater.from(mContext);
        v=inflater.inflate(R.layout.item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductModelClass modelClass=mData.get(position);
        holder.title.setText(modelClass.getTitle());
        holder.price.setText(modelClass.getPrice());
        holder.store.setText(modelClass.getStore());
        holder.rating.setText(modelClass.getRating());
        holder.link.setText(modelClass.getLink());
        Glide.with(mContext)
                .load(modelClass.getImage())
                .into(holder.image);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , ProductDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("title" ,modelClass.getTitle());
                bundle.putString("price",modelClass.getPrice());
                bundle.putString("store",modelClass.getStore());
                bundle.putString("rating",modelClass.getRating());
                bundle.putString("image",modelClass.getImage());
                bundle.putString("link",modelClass.getLink());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,price,store,rating,link;
        ImageView image;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.name_url);
            price=itemView.findViewById(R.id.price_url);
            store=itemView.findViewById(R.id.store_url);
            rating=itemView.findViewById(R.id.rate_url);
            image=itemView.findViewById(R.id.image_url);
            link=itemView.findViewById(R.id.link_url);
            constraintLayout=itemView.findViewById(R.id.main_layout);
        }
    }
}
