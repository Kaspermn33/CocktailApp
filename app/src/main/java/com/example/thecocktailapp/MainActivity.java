package com.example.thecocktailapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textView = findViewById(R.id.textView2);

        retrofit =  new Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CocktailDBApi cocktailDBApi = retrofit.create(CocktailDBApi.class);

        Call<List<Cocktail>> call = cocktailDBApi.getCocktails();

        call.enqueue(new Callback<List<Cocktail>>() {
            @Override
            public void onResponse(Call<List<Cocktail>> call, Response<List<Cocktail>> response) {

                if(!response.isSuccessful()){
                    textView.setText("code " + response.code());
                    return;
                }

                List<Cocktail> cocktails = response.body();

                for (Cocktail cocktail : cocktails) {
                    String content = "";
                    content += "Name: " + cocktail.getDrinkName() + "\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Cocktail>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}
