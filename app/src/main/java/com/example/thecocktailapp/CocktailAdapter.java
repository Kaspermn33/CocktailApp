package com.example.thecocktailapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.ViewHolder> {
    private Drink[] mDataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cocktailtxt;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            cocktailtxt = itemView.findViewById(R.id.cocktail_txt);
        }
    }

    public CocktailAdapter(Drink[] myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public CocktailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.cocktailtxt.setText("mDataset[position].getStrDrink()");
        holder.cocktailtxt.setText(mDataset[position].getStrDrink());

        GlideRunnable glideRunnable = new GlideRunnable(mDataset[position].getStrDrinkThumb(), holder.imageView);
        new Thread(glideRunnable).start();

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class GlideRunnable implements Runnable {
        private String URL;
        private ImageView imageView;

        public GlideRunnable(String URL, ImageView imageView) {
            this.URL = URL;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    Glide.with(context).load(URL).apply(new RequestOptions().override(150, 150)).into(imageView);
                }
            });
        }
    }

}
