package me.varoa.sad.core.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import me.varoa.nikkedb.core.data.prefs.Keys
import me.varoa.nikkedb.core.domain.model.AppTheme

private val Context.dataStore by preferencesDataStore("prefs")

class DataStoreManager(
    appContext: Context
) {
    private val prefsDataStore = appContext.dataStore

    // theme
    val theme
        get() = prefsDataStore.data.map { prefs ->
            prefs[Keys.THEME_KEY] ?: AppTheme.DARK.name
        }

    suspend fun setTheme(flag: AppTheme) {
        prefsDataStore.edit { prefs -> prefs[Keys.THEME_KEY] = flag.name }
    }
}
