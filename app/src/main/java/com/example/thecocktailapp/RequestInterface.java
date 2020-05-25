package com.example.thecocktailapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {

    @GET("lookup.php?i=11007")
    Call<JSONResponse> getJSON();

    @GET("search.php")
    Call<JSONResponse> searchByName(@Query("s") String searchString);

    /**
     *
     * Only strDrink, strDrinkThumb and idDrink are available with this request
     * @param ingredientName
     * @return
     */
    @GET("filter.php")
    Call<JSONResponse> searchByIngredient(@Query("i") String ingredientName);

}
