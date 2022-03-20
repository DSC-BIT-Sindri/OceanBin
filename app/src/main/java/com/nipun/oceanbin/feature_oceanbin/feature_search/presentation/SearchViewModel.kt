package com.nipun.oceanbin.feature_oceanbin.feature_search.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nipun.oceanbin.core.Constant
import com.nipun.oceanbin.core.Resource
import com.nipun.oceanbin.core.UIEvent
import com.nipun.oceanbin.feature_oceanbin.feature_search.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var job: Job? = null

    init {
        savedStateHandle.get<String>(Constant.LAT)?.let { lat ->
            savedStateHandle.get<String>(Constant.LON)?.let { lon ->
                try {
                    Log.e("Search", "Lon -> $lon \n lat -> $lat")
                    val latitude = lat.toDouble()
                    val longitude = lon.toDouble()
                    initLocation(LatLng(latitude,longitude))
                } catch (e: Exception) {
                }
            }
        }
    }

    private fun initLocation(latLng: LatLng){
        searchRepository.getInitSearch(latLng).onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _searchState.value = SearchState(
                        isLoading = true,
                        data = result.data?:searchState.value.data
                    )
                }

                is Resource.Error -> {
                    _searchState.value = SearchState(
                        isLoading = false,
                        data = result.data?:searchState.value.data
                    )
                    _eventFlow.emit(UIEvent.ShowSnackbar(
                        result.message?:"Unknown Error"
                    ))
                }

                is Resource.Success -> {
                    _searchState.value = SearchState(
                        isLoading = false,
                        data = result.data?:searchState.value.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun searchLocation(query: String) {
        _searchQuery.value = query
        job?.cancel()
        job = viewModelScope.launch {
            delay(500)
            searchRepository.getSearchResults(query = query).onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _searchState.value = SearchState(
                            isLoading = true,
                            data = result.data?:searchState.value.data
                        )
                    }

                    is Resource.Error -> {
                        _searchState.value = SearchState(
                            isLoading = false,
                            data = result.data?:searchState.value.data
                        )
                        _eventFlow.emit(UIEvent.ShowSnackbar(
                            result.message?:"Unknown Error"
                        ))
                    }

                    is Resource.Success -> {
                        _searchState.value = SearchState(
                            isLoading = false,
                            data = result.data?:searchState.value.data
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}