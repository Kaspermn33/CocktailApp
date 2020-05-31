package com.example.thecocktailapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DrinkEntityDao {

    @Insert
    public void addDrink(DrinkEntity drink);

    @Query("select * from drinks")
    public List<DrinkEntity> getDrinks();

    @Delete
    public void deleteDrink(DrinkEntity drinkEntity);


}
