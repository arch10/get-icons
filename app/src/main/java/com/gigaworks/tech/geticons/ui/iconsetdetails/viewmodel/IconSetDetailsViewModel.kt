package com.gigaworks.tech.geticons.ui.iconsetdetails.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.gigaworks.tech.geticons.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IconSetDetailsViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    private val _iconSetId = MutableLiveData<Int>()

    val iconList = repository.getIconsByIconSet(227756).cachedIn(viewModelScope)

    fun getIconList(iconSetId: Int) {
        _iconSetId.value = iconSetId
    }

}