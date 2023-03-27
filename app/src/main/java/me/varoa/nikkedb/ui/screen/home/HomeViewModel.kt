package me.varoa.nikkedb.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.varoa.nikkedb.core.data.prefs.DataStoreManager
import me.varoa.nikkedb.core.domain.model.Nikke
import me.varoa.nikkedb.core.domain.model.Theme
import me.varoa.nikkedb.core.domain.repository.NikkeRepository
import me.varoa.nikkedb.ui.common.UiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val nikke: NikkeRepository,
  val prefs: DataStoreManager
) : ViewModel() {
  private val _uiState: MutableStateFlow<UiState<List<Nikke>>> =
    MutableStateFlow(UiState.Loading)
  val uiState
    get() = _uiState

  val isDarkMode = prefs.theme.map { it == Theme.DARK.name }

  private val _query = MutableStateFlow("")
  val query get() = _query.asStateFlow()

  private var data: List<Nikke> = emptyList()

  init {
    getAllNikkes()
  }

  fun getAllNikkes() {
    viewModelScope.launch {
      _uiState.emit(UiState.Loading)
      nikke.getNikkes()
        .catch { _uiState.emit(UiState.Error(it.message.toString())) }
        .collect { result ->
          if (result.isSuccess) {
            data = result.getOrThrow()
              .sortedBy { it.name }
            _uiState.emit(UiState.Success(data))
          } else if (result.isFailure) {
            _uiState.emit(UiState.Error(result.exceptionOrNull()?.message.toString()))
          }
        }
    }
  }

  fun setQuery(q : String){
    viewModelScope.launch {
      _query.emit(q)
    }
  }

  fun searchNikke() {
    viewModelScope.launch {
      _uiState.emit(
        UiState.Success(
          data
            .filter { it.name.lowercase().startsWith(query.value.lowercase()) }
        )
      )
    }
  }

  fun toggleTheme() {
    viewModelScope.launch {
      prefs.setTheme(
        if (isDarkMode.first())
          Theme.LIGHT
        else
          Theme.DARK
      )
    }
  }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}
