package com.makazemi.cooking.ui.recipe

import androidx.lifecycle.*
import com.makazemi.cooking.model.CategoryFood
import com.makazemi.cooking.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ListFoodViewModel @Inject constructor(
    repository: MainRepository,
) : ViewModel(){

    private val _category=MutableLiveData<CategoryFood>()

    fun setCategory(item:CategoryFood){
        _category.value= item
    }

    val foods= _category.switchMap {
        repository.getFoodsByCategory(it).asLiveData(viewModelScope.coroutineContext)
    }

}