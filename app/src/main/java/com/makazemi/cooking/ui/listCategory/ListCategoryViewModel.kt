package com.makazemi.cooking.ui.listCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.makazemi.cooking.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListCategoryViewModel @Inject constructor(
    repository: MainRepository,
) : ViewModel(){

    val categories= repository.getAllCategory().asLiveData(viewModelScope.coroutineContext)
}