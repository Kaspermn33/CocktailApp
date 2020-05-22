package com.example.thecocktailapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CocktailDBApi {

    @GET("lookup.php?i=11007")
    Call<List<Cocktail>> getCocktails();

}
