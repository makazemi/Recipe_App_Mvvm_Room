package com.makazemi.cooking.repository

import com.makazemi.cooking.db.CategoryFoodDao
import com.makazemi.cooking.db.FoodDao
import com.makazemi.cooking.model.CategoryFood
import com.makazemi.cooking.model.Food
import com.makazemi.cooking.util.Constant.ERROR_UNKNOWN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Singleton


@Singleton
class MainRepositoryImp(
    private val foodDao: FoodDao,
    private val categoryFoodDao: CategoryFoodDao
) : MainRepository {


    override fun createCategory(item: CategoryFood): Flow<DataState<String>> = flow {
        emit(DataState.loading(true))
        categoryFoodDao.insert(item)
        emit(DataState.data("success"))

    }.flowOn(Dispatchers.IO)
        .catch {
            emit(
                buildError(message = ERROR_UNKNOWN)
            )
        }

    override fun createFood(item: Food): Flow<DataState<String>> = flow {
        emit(DataState.loading(true))
        foodDao.insert(item)
        emit(DataState.data("success"))

    }.flowOn(Dispatchers.IO)
        .catch {
            emit(
                buildError(message = ERROR_UNKNOWN)
            )
        }

    override fun getAllCategory(): Flow<DataState<List<CategoryFood>>> = flow {
        emit(DataState.loading(true))

        emitAll(categoryFoodDao.fetchAllCategoryFood().map { DataState.data(it) })

    }.flowOn(Dispatchers.IO)
        .catch {
            emit(
                buildError(message = ERROR_UNKNOWN)
            )
        }

    override fun getFoodsByCategory(item: CategoryFood): Flow<DataState<List<Food>>> = flow {
        emit(DataState.loading(true))

        emitAll(foodDao.fetchFoodByCategory(item.id).map { DataState.data(it) })

    }.flowOn(Dispatchers.IO)
        .catch {
            emit(
                buildError(message = ERROR_UNKNOWN)
            )
        }

    override fun searchFood(query: String): Flow<DataState<List<Food>>> = flow {
        emit(DataState.loading(true))

        if (query.isNotEmpty())
            emitAll(foodDao.searchFood(query).map { DataState.data(it) })
        else
            emit(DataState.data(listOf()))

    }.flowOn(Dispatchers.IO)
        .catch {
            emit(
                buildError(message = ERROR_UNKNOWN)
            )
        }


}