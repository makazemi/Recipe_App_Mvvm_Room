package com.makazemi.cooking.ui.createCategory

import androidx.lifecycle.*
import com.makazemi.cooking.model.CategoryFood
import com.makazemi.cooking.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateCategoryViewModel @Inject constructor(
    repository: MainRepository,
) : ViewModel() {

    private val _categoryInput = MutableLiveData<CategoryFood>()

    fun createCategory(image: String, name: String) {
        _categoryInput.value = CategoryFood(title = name, image = image)
    }

    val createCategoryResponse = _categoryInput.switchMap {
        repository.createCategory(it).asLiveData(viewModelScope.coroutineContext)
    }

}