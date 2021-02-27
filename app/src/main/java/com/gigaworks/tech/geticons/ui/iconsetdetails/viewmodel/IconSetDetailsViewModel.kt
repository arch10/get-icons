package com.gigaworks.tech.geticons.ui.iconsetdetails.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.gigaworks.tech.geticons.domain.Icon
import com.gigaworks.tech.geticons.repository.ApiRepository
import com.gigaworks.tech.geticons.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IconSetDetailsViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    var iconSetId: Int = -1

    private val _iconList = MutableLiveData<Response<List<Icon>>>()
    val iconList: LiveData<Response<List<Icon>>>
        get() = _iconList

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun getIconList(iconSetId: Int) {
        if(this.iconSetId != iconSetId) {
            this.iconSetId = iconSetId
            viewModelScope.launch {
                _loading.value = true
                _iconList.value = repository.getIconsInIconSet(iconSetId)
                _loading.value = false
            }
        }
    }

}