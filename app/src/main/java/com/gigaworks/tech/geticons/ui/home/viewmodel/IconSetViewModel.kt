package com.gigaworks.tech.geticons.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gigaworks.tech.geticons.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IconSetViewModel @Inject constructor(
    repository: ApiRepository
) : ViewModel() {

    val iconSetList = repository.getIconSets().cachedIn(viewModelScope)

}