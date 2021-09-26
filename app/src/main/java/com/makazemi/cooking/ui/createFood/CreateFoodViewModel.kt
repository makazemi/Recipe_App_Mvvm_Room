package com.makazemi.cooking.ui.createFood

import androidx.lifecycle.*
import com.makazemi.cooking.model.CategoryFood
import com.makazemi.cooking.model.Food
import com.makazemi.cooking.model.RawMaterial
import com.makazemi.cooking.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreateFoodViewModel @Inject constructor(
    repository: MainRepository,
) : ViewModel() {


    val categories = repository.getAllCategory().asLiveData(viewModelScope.coroutineContext)

    private val _inputFood= MutableLiveData<Food>()

    val createFoodResponse= _inputFood.switchMap { repository.createFood(it).asLiveData(viewModelScope.coroutineContext) }


    private val _category = MutableLiveData<CategoryFood>()

    fun setCategory(item: CategoryFood) {
        _category.value = item
    }

    fun createFood(
        name: String, recipe: String, rawMaterial1: String, valueRawMaterial1: String,
        rawMaterial2: String, valueRawMaterial2: String,
        rawMaterial3: String, valueRawMaterial3: String, image: String
    ) {

        _category.value?.let { category ->
            val item = Food(
                name = name,
                recipe = recipe,
                category_key = category.id,
                category_name = category.title,
                image = image,
                raw_material = listOf(
                    RawMaterial(rawMaterial1, valueRawMaterial1),
                    RawMaterial(rawMaterial2, valueRawMaterial2),
                    RawMaterial
                        (rawMaterial3, valueRawMaterial3)
                )
            )

            _inputFood.value= item

        }


    }

}