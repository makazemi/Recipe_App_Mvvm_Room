package com.makazemi.cooking.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.makazemi.cooking.db.AppDatabase
import com.makazemi.cooking.db.CategoryFoodDao
import com.makazemi.cooking.db.FoodDao
import com.makazemi.cooking.repository.MainRepository
import com.makazemi.cooking.repository.MainRepositoryImp
import com.makazemi.cooking.util.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun provideGlideInstance(@ApplicationContext application: Context): RequestManager {
        return Glide.with(application)
    }

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext application: Context): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideFoodDao(db: AppDatabase): FoodDao {
        return db.getFoodDao()
    }

    @Singleton
    @Provides
    fun provideCategoryFoodDao(db: AppDatabase): CategoryFoodDao {
        return db.getCategoryFoodDao()
    }


    @Singleton
    @Provides
    fun provideMainRepository(foodDao:FoodDao,categoryFoodDao: CategoryFoodDao):MainRepository{
        return MainRepositoryImp(foodDao,categoryFoodDao)
    }
}