package com.example.thecocktailapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayCocktailFragment extends Fragment {
    private ImageView imageView;
    private TextView textView;
    private ScrollView scrollView;
    private TextView textScrollview;
    private Button favoriteButton;
    private boolean isFavorite = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_cocktail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imageView = getView().findViewById(R.id.display_cocktail_imageview);
        textView = getView().findViewById(R.id.cocktail_display_textview);
        scrollView = getView().findViewById(R.id.cocktail_display_scrollview);
        textScrollview = getView().findViewById(R.id.scroll_textview);
        favoriteButton = getView().findViewById(R.id.favorite_button);

        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        final Drink cocktail = (Drink) bundle.getSerializable("cocktail");
        System.out.println("cocktail is now on the display");

        CheckIfFavoriteRunnable checkIfFavoriteRunnable = new CheckIfFavoriteRunnable(cocktail);
        new Thread(checkIfFavoriteRunnable).start();

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteDrinkRunnable favoriteDrinkRunnable = new FavoriteDrinkRunnable(cocktail);
                new Thread(favoriteDrinkRunnable).start();
            }
        });




        GlideRunnable glideRunnable = new GlideRunnable(cocktail.getStrDrinkThumb(), imageView);
        new Thread(glideRunnable).start();
        textView.setText(cocktail.getStrDrink());



        Call<JSONResponse> call = MainActivity.requestInterface.getFavorite(cocktail.getIdDrink());
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                List<Drink> drinks = response.body().getDrinks();

                Drink drink = drinks.get(0);

                if (drinks == null) {return;}

                String[] measures = drink.getMeasures();
                String[] ingredients = drink.getIngredients();


                StringBuilder sb = new StringBuilder();
                sb.append(drink.getStrGlass() + "\n");

                for (int i = 0; i < 15; i++) {
                    if (measures[i] == null && ingredients[i] == null) {
                        Log.i("StringBuilder", "Both are null");
                        break;
                    }

                    //Some null data is saved as \n
                    if (measures[i] != null && !measures[i].contains("\n")) {
                        Log.i("StringBuilder", "Appending measure " + measures[i]);
                        sb.append(measures[i] + "\t");
                    }
                    if (ingredients[i] != null) {
                        Log.i("StringBuilder", "Appending ingredient " + ingredients[i]);
                        sb.append(ingredients[i] + "\n");
                    }
                }

                sb.append("\n" + drink.getStrInstructions());





                textScrollview.setText(sb.toString());

                Log.i("Info", "Search found: " + drinks.size() + " items");

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.i("Error", t.getMessage());
            }
        });


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
                    Glide.with(getContext()).load(URL).apply(new RequestOptions().override(300, 300)).into(imageView);
                }
            });
        }
    }

    public class FavoriteDrinkRunnable implements Runnable {
        private Drink drink;

        public FavoriteDrinkRunnable(Drink drink) {
            this.drink = drink;
        }

        @Override
        public void run() {
            DrinkEntity de = new DrinkEntity();
            de.setId(Integer.parseInt(drink.getIdDrink()));
            de.setName(drink.getStrDrink());
            de.setImageURL(drink.getStrDrinkThumb());

            if (!isFavorite) {
                List<DrinkEntity> drinks = MainActivity.database.drinkEntityDao().getDrinks();

                for (DrinkEntity savedDrink : drinks) {
                    if (Integer.parseInt(drink.getIdDrink()) == savedDrink.getId()) {
                        return;
                    }
                }


                MainActivity.database.drinkEntityDao().addDrink(de);
                isFavorite = true;
                favoriteButton.post(new Runnable() {
                    @Override
                    public void run() {
                        favoriteButton.setText("Unfavorite");
                    }
                });
            } else {

                MainActivity.database.drinkEntityDao().deleteDrink(de);
                isFavorite = false;
                favoriteButton.post(new Runnable() {
                    @Override
                    public void run() {
                        favoriteButton.setText("Favorite");
                    }
                });

            }


        }
    }

    public class CheckIfFavoriteRunnable implements Runnable {
        private Drink drink;

        public CheckIfFavoriteRunnable(Drink drink) {
            this.drink = drink;
        }

        @Override
        public void run() {

            List<DrinkEntity> drinks = MainActivity.database.drinkEntityDao().getDrinks();

            for (DrinkEntity currentDrink : drinks) {
                if (currentDrink.getId() == Integer.parseInt(drink.getIdDrink())) {
                    isFavorite = true;
                    favoriteButton.post(new Runnable() {
                        @Override
                        public void run() {
                            favoriteButton.setText("Unfavorite");
                        }
                    });
                    return;
                }
            }

        }
    }

}
