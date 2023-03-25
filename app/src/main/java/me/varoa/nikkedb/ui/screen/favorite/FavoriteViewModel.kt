package me.varoa.nikkedb.ui.screen.favorite

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.varoa.nikkedb.core.domain.repository.NikkeRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repo: NikkeRepository
) : ViewModel()
