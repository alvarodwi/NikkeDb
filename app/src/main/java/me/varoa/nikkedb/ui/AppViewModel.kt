package me.varoa.nikkedb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.map
import me.varoa.nikkedb.core.data.prefs.DataStoreManager
import me.varoa.nikkedb.core.domain.model.Theme
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
  private val prefs: DataStoreManager
) : ViewModel() {
  val isDarkMode = prefs.theme.map { it == Theme.DARK.name }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}