package me.varoa.nikkedb.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.varoa.nikkedb.core.domain.model.Nikke
import me.varoa.nikkedb.core.domain.repository.FavoriteRepository
import me.varoa.nikkedb.core.domain.repository.NikkeRepository
import me.varoa.nikkedb.ui.common.UiState
import me.varoa.nikkedb.ui.common.UiState.Loading
import me.varoa.nikkedb.ui.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  private val nikke: NikkeRepository,
  private val favorite: FavoriteRepository,
  state: SavedStateHandle
) : ViewModel() {
  private val name: String = state.get<String>(Screen.Detail.args0) ?: ""

  private val _uiState: MutableStateFlow<UiState<Nikke>> = MutableStateFlow(Loading)
  val uiState get() = _uiState.asStateFlow()

  private val _isFavorite = MutableStateFlow<Boolean>(false)
  val isFavorite get() = _isFavorite.asStateFlow()

  private lateinit var data: Nikke

  init {
    getNikkeDetail()
  }

  fun getNikkeDetail() {
    viewModelScope.launch {
      nikke.getNikke(name)
        .catch { _uiState.emit(UiState.Error(it.message.toString())) }
        .collectLatest { result ->
          if (result.isSuccess) {
            data = result.getOrThrow()
            checkInFavorite()
            _uiState.emit(UiState.Success(data))
          } else if (result.isFailure) {
            _uiState.emit(UiState.Error(result.exceptionOrNull()?.message.toString()))
          }
        }
    }
  }

  private fun checkInFavorite() {
    viewModelScope.launch {
      favorite.isNikkeInFavorites(data.name).collect {
        _isFavorite.emit(it == 1)
      }
    }
  }

  fun toggleFavorite() {
    viewModelScope.launch {
      if (isFavorite.value) {
        favorite.removeFromFavorite(data)
      } else {
        favorite.addToFavorite(data)
      }
    }
  }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}
