package com.example.thecocktailapp;

import android.os.Bundle;
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

public class SearchRecipeFragment extends Fragment {
RecyclerView recyclerView;


String[] cocktails = {"Margarita", "Old Fashioned", "Mojito", "Strawberry Daiquiri", "White Russian", "Mai Tai",
"Gin & Tonic", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink", "Other drink"};

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
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchField = getView().findViewById(R.id.searchField);
                String searchValue = searchField.getText().toString();
                CocktailAdapter cocktailAdapter = new CocktailAdapter(cocktails);
                recyclerView.setAdapter(cocktailAdapter);
            }
        });

        recyclerView = getView().findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}
