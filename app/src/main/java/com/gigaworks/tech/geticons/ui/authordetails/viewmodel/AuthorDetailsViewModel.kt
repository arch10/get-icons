package com.gigaworks.tech.geticons.ui.authordetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigaworks.tech.geticons.domain.IconSet
import com.gigaworks.tech.geticons.repository.ApiRepository
import com.gigaworks.tech.geticons.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthorDetailsViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    var authorId: Int = -1

    private val _iconSetList = MutableLiveData<Response<List<IconSet>>>()
    val iconSetList: LiveData<Response<List<IconSet>>>
        get() = _iconSetList

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun getIconSetList(authorId: Int) {
        if(this.authorId != authorId) {
            this.authorId = authorId
            viewModelScope.launch {
                _loading.value = true
                _iconSetList.value = repository.getIconSetByAuthor(authorId)
                _loading.value = false
            }
        }
    }

}