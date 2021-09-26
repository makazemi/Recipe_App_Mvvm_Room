package com.makazemi.cooking.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.makazemi.cooking.model.CategoryFood
import com.makazemi.cooking.model.ConvertersRawMaterial
import com.makazemi.cooking.model.Food


@Database(
    entities = [Food::class, CategoryFood::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    ConvertersRawMaterial::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFoodDao():FoodDao

    abstract fun getCategoryFoodDao():CategoryFoodDao
}








