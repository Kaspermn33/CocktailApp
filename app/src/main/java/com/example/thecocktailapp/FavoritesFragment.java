package com.example.thecocktailapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class FavoritesFragment extends Fragment {
    RecyclerView recyclerView;
    private Drink[] drinks;


    private List<DrinkEntity> savedDrinks;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.favorite_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        GetDrinksRunnable getDrinksRunnable = new GetDrinksRunnable();
        new Thread(getDrinksRunnable).start();
    }

    public class GetDrinksRunnable implements Runnable {
        @Override
        public void run() {
            savedDrinks = MainActivity.database.drinkEntityDao().getDrinks();

            //For testing purposes
            //Adds an DrinkEntity to the database if there are none
            if (savedDrinks.size() == 0) {
                DrinkEntity de = new DrinkEntity();
                de.setId(11007);
                de.setName("Margarita");
                de.setImageURL("https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg");
                MainActivity.database.drinkEntityDao().addDrink(de);
                savedDrinks = MainActivity.database.drinkEntityDao().getDrinks();
            }

            drinks = new Drink[savedDrinks.size()];


            for (int i = 0; i < savedDrinks.size(); i++) {
                drinks[i] = new Drink(savedDrinks.get(i).getId()+"", savedDrinks.get(i).getName(), savedDrinks.get(i).getImageURL());
            }

            final CocktailAdapter cocktailAdapter = new CocktailAdapter(drinks, getContext());

            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setAdapter(cocktailAdapter);
                }
            });
        }
    }
}
