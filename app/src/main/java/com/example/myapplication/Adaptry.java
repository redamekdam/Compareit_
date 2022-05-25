package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adaptry extends RecyclerView.Adapter<Adaptry.MyViewHolder> {
         Context mContext;
         List<ProductModelClass> mData;

    public Adaptry(Context mContext, List<ProductModelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater=LayoutInflater.from(mContext);
        v=inflater.inflate(R.layout.product_item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(mData.get(position).getTitle());
        holder.price.setText(mData.get(position).getPrice());
        Glide.with(mContext)
                .load(mData.get(position).getImage())
                .into(holder.image);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,price;
        ImageView image;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.name_url);
            price=itemView.findViewById(R.id.price_url);
            image=itemView.findViewById(R.id.image_url);
        }
    }
}
