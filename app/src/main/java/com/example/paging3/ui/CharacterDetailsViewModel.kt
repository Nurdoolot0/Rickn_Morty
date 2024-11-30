package com.example.paging3.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paging3.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: CharacterDetailsRepository
) : ViewModel() {

    private val _characterDetails = MutableLiveData<Character?>()
    val characterDetails: LiveData<Character?> get() = _characterDetails

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    fun fetchCharacterDetails(id: Int) {
        _characterDetails.postValue(null)
        _loadingState.postValue(true)

        viewModelScope.launch {
            try {
                val character = repository.getCharacterDetails(id)
                _characterDetails.postValue(character)
            } catch (e: Exception) {
            } finally {
                _loadingState.postValue(false)
            }
        }
    }
}