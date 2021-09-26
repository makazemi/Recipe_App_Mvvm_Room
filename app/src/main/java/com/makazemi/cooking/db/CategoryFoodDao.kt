package com.makazemi.cooking.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makazemi.cooking.model.CategoryFood
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryFoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CategoryFood)

    @Query("SELECT * FROM categoryfood")
    fun fetchAllCategoryFood(): Flow<List<CategoryFood>>

}