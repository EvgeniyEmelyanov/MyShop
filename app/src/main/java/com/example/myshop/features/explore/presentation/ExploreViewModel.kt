package com.example.myshop.features.explore.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExploreViewModel : ViewModel() {

    private val provider = ExploreCategoriesProvider

    private val _state = MutableLiveData(ExploreUiState())
    val state: LiveData<ExploreUiState> = _state

    init {
        _state.value = buildState()
    }

    private fun buildState(): ExploreUiState {

        val exploreCategories = provider.getCategories()

        return ExploreUiState(
            categories = exploreCategories
        )
    }


}

