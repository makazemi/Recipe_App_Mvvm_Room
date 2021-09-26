package com.makazemi.cooking.ui.search

import androidx.lifecycle.*
import com.makazemi.cooking.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(repository: MainRepository):ViewModel(){

    private val _query=MutableLiveData<String>()

    fun search(value:String){
        _query.value=value
    }

    val foods= _query.switchMap { repository.searchFood(it).asLiveData(viewModelScope.coroutineContext) }
}