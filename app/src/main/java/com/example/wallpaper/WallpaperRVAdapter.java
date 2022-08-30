package com.example.wallpaper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WallpaperRVAdapter extends RecyclerView.Adapter<WallpaperRVAdapter.viewHolder> {
    private ArrayList<String> wallpaperRVArrayList;
    private Context context;

    public WallpaperRVAdapter(ArrayList<String> wallpaperRVArrayList, Context context) {
        this.wallpaperRVArrayList = wallpaperRVArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public WallpaperRVAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_rv_item, parent, false);
        return new WallpaperRVAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperRVAdapter.viewHolder holder, int position) {
        Glide.with(context).load(wallpaperRVArrayList.get(position)).into(holder.wallpaperIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,WallpaperActivity.class);
                i.putExtra("imgUrl",wallpaperRVArrayList.get(position));
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return wallpaperRVArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView wallpaperIV;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaperIV = itemView.findViewById(R.id.idIVWallpaper);
        }
    }
}
