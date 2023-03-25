package me.varoa.nikkedb.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.varoa.nikkedb.core.data.FavoriteRepositoryImpl
import me.varoa.nikkedb.core.data.NikkeRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {
    @Binds
    fun nikkeRepository(repo: NikkeRepositoryImpl): NikkeRepositoryImpl

    @Binds
    fun favoriteRepository(repo: FavoriteRepositoryImpl): FavoriteRepositoryImpl
}
