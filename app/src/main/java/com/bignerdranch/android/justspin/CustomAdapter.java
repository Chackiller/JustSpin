package com.bignerdranch.android.justspin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Integer> lol;

    public CustomAdapter(Context context, List<Integer> lol){
        this.lol = lol;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.simple_image_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int drawable = lol.get(position);
        holder.mImageView.setImageResource(drawable);
    }

    @Override
    public int getItemCount() {
        return lol.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final ImageView mImageView;
        ViewHolder(View view){
            super(view);
            mImageView = view.findViewById(R.id.simple_imageView);
        }
    }
}
