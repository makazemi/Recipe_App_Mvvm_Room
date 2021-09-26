package com.makazemi.cooking.repository

import com.makazemi.cooking.model.CategoryFood
import com.makazemi.cooking.model.Food
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun createCategory(item: CategoryFood): Flow<DataState<String>>

    fun createFood(item:Food):Flow<DataState<String>>

    fun getAllCategory():Flow<DataState<List<CategoryFood>>>

    fun getFoodsByCategory(item: CategoryFood):Flow<DataState<List<Food>>>

    fun searchFood(query:String):Flow<DataState<List<Food>>>



}