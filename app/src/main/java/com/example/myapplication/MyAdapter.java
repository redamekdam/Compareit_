package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<ProductModelClass> list;

    public MyAdapter(Context context, ArrayList<ProductModelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fav_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ProductModelClass favorit=list.get(position);
            holder.title1.setText(favorit.getTitle());
            holder.price1.setText(favorit.getPrice());
            holder.store1.setText(favorit.getStore());
            holder.rating1.setText(favorit.getRating());
            holder.link1.setText(favorit.getLink());
            Glide.with(context)
                .load(favorit.getImage())
                .into(holder.image1);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(favorit.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title1,price1,store1,rating1,link1;
        ImageView image1;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title1=itemView.findViewById(R.id.name_url1);
            price1=itemView.findViewById(R.id.price_url1);
            store1=itemView.findViewById(R.id.store_url1);
            rating1=itemView.findViewById(R.id.rate_url1);
            image1=itemView.findViewById(R.id.image_url1);
            link1=itemView.findViewById(R.id.link_url1);
            constraintLayout=itemView.findViewById(R.id.main_layout1);

        }
    }
}
