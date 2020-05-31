package com.example.thecocktailapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DrinkEntity.class}, version = 1)
public abstract class DrinksDatabase extends RoomDatabase {
    public abstract DrinkEntityDao drinkEntityDao();
}
