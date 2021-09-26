package com.makazemi.cooking.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makazemi.cooking.model.Food
import kotlinx.coroutines.flow.Flow


@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:Food)

    @Query("SELECT * FROM food")
    fun fetchAllFood(): Flow<List<Food>>

    @Query("SELECT * FROM food WHERE category_key=:categoryKey")
    fun fetchFoodByCategory(categoryKey:Int):Flow<List<Food>>


    @Query("SELECT * FROM food WHERE name LIKE '%' || :query || '%' OR category_name LIKE '%' || :query || '%'")
    fun searchFood(query:String):Flow<List<Food>>

}