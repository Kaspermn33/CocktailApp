package com.example.thecocktailapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchRecipeFragment extends Fragment {
    RecyclerView recyclerView;

    Drink[] cocktails;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_recipe, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button searchButton = getView().findViewById(R.id.search_button);

        recyclerView = getView().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchField = getView().findViewById(R.id.searchField);
                String searchValue = searchField.getText().toString();
                Log.i("Info", "Search string: " + searchValue);

                Call<JSONResponse> call = MainActivity.requestInterface.searchByName(searchValue);
                call.enqueue(new Callback<JSONResponse>() {
                    @Override
                    public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                        List<Drink> drinks = response.body().getDrinks();

                        if (drinks == null) {
                            return;
                        }

                        Log.i("Info", "Search found: " + drinks.size() + " items");
                        cocktails = new Drink[drinks.size()];
                        drinks.toArray(cocktails);
                        CocktailAdapter cocktailAdapter = new CocktailAdapter(cocktails, getContext());
                        recyclerView.setAdapter(cocktailAdapter);
                    }

                    @Override
                    public void onFailure(Call<JSONResponse> call, Throwable t) {
                        Log.i("Error", t.getMessage());
                    }
                });
            }
        });
    }



}
