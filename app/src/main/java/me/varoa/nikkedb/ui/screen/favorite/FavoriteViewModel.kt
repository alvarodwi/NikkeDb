package me.varoa.nikkedb.ui.screen.favorite

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
import me.varoa.nikkedb.ui.common.UiState
import me.varoa.nikkedb.ui.common.UiState.Loading
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
  private val favorite: FavoriteRepository
) : ViewModel() {
  private val _uiState: MutableStateFlow<UiState<List<Nikke>>> = MutableStateFlow(Loading)
  val uiState get() = _uiState.asStateFlow()

  init {
    fetchFavorites()
  }

  fun fetchFavorites() {
    viewModelScope.launch {
      favorite.loadFavorites()
        .catch { _uiState.emit(UiState.Error(it.message.toString())) }
        .collectLatest { result ->
          _uiState.emit(UiState.Success(result))
        }
    }
  }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}
