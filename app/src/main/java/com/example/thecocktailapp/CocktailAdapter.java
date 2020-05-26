package com.example.thecocktailapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.ViewHolder> {
    private Drink[] mDataset;
    private Context context;

    public CocktailAdapter(Drink[] myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cocktailtxt;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            cocktailtxt = itemView.findViewById(R.id.cocktail_txt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public CocktailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //holder.cocktailtxt.setText("mDataset[position].getStrDrink()");
        holder.cocktailtxt.setText(mDataset[position].getStrDrink());

        holder.cocktailtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment newFragment = new DisplayCocktailFragment();
                Bundle bundle = new Bundle();
                Drink cocktail = mDataset[holder.getLayoutPosition()];
                bundle.putSerializable("cocktail", cocktail);
                newFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, newFragment).addToBackStack(null).commit();
                System.out.println(mDataset[holder.getLayoutPosition()].getStrDrink());

            }
        });

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

    public interface OnCocktailListener {
        void onCocktailClick(int position);
    }

}
