package com.example.thecocktailapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public static Retrofit retrofit;
    public static RequestInterface requestInterface;

    //private Retrofit retrofit;
    //private DrinksDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new SearchRecipeFragment());
        fragmentTransaction.commit();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        requestInterface = retrofit.create(RequestInterface.class);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId() == R.id.searchi) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new SearchIngredientFragment());
            fragmentTransaction.commit();

        } else if(menuItem.getItemId() == R.id.searchn) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new SearchRecipeFragment());
            fragmentTransaction.commit();

        } else if(menuItem.getItemId() == R.id.favorites) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new FavoritesFragment());
            fragmentTransaction.commit();

        }
        return true;
    }

    /**
     * Code of how Retrofit can access the database
     * retrofit = new Retrofit.Builder()
     *                 .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
     *                 .addConverterFactory(GsonConverterFactory.create())
     *                 .build();
     *
     *         RequestInterface requestInterface = retrofit.create(RequestInterface.class);
     *         Call<JSONResponse> call = requestInterface.searchByIngredient("lemon");
     *         call.enqueue(new Callback<JSONResponse>() {
     *             @Override
     *             public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
     *                 JSONResponse jsonResponse = response.body();
     *                 Log.i("Information", response.code()+"");
     *
     *                 textView.setText(jsonResponse.getDrinks().get(0).getStrDrink());
     *             }
     *
     *             @Override
     *             public void onFailure(Call<JSONResponse> call, Throwable t) {
     *                 textView.setText(t.getMessage());
     *
     *             }
     *         });
     */

    /**
     * For database
     *
     * //Is on the main thread not a good idea!!!
     * Creates the database
     *         database = Room.databaseBuilder(getApplicationContext(), DrinksDatabase.class, "drinksDB")
     *                 .allowMainThreadQueries()
     *                 .build();
     *
     * create entity
     * DrinkEntity drinkEntity = new DrinkEntity();
     *
     * set varibles for entity
     *                 drinkEntity.setId(Integer.parseInt(jsonResponse.getDrinks().get(0).getIdDrink()));
     *                 drinkEntity.setName(jsonResponse.getDrinks().get(0).getStrDrink());
     *                 drinkEntity.setImageURL(jsonResponse.getDrinks().get(0).getStrDrinkThumb());
     *
     *Create entity to be deleted
     *                 DrinkEntity entity = new DrinkEntity();
     *                 entity.setId(Integer.parseInt(jsonResponse.getDrinks().get(0).getIdDrink()));
     *                 database.drinkEntityDao().deleteDrink(entity);
     *
     *                 get list of all drinks favored
     *                 List<DrinkEntity> drinkEntityList = database.drinkEntityDao().getDrinks();
     *
     */

}
