package com.gigaworks.tech.geticons.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gigaworks.tech.geticons.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IconsViewModel @Inject constructor(
    repository: ApiRepository
) : ViewModel() {

    private val _query = MutableLiveData("")

    val iconList = _query.switchMap {
        repository.getIconsByQuery(it ?: "").cachedIn(viewModelScope)
    }

    fun setQuery(query: String) {
        if(_query.value != query) {
            _query.value = query
        }
    }

}