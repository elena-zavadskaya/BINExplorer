package com.example.binexplorer.di

import com.example.binexplorer.data.remote.ApiClient
import com.example.binexplorer.data.remote.BinApiService
import com.example.binexplorer.data.repository.BinRepositoryImpl
import com.example.binexplorer.domain.interactor.BinInteractor
import com.example.binexplorer.domain.interactor.BinInteractorImpl
import com.example.binexplorer.domain.repository.BinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient = ApiClient()

    @Provides
    @Singleton
    fun provideBinApiService(apiClient: ApiClient): BinApiService {
        return apiClient.binApiService
    }

    @Provides
    @Singleton
    fun provideBinRepository(apiClient: ApiClient): BinRepository {
        return BinRepositoryImpl(apiClient)
    }

    @Provides
    @Singleton
    fun provideBinInteractor(repository: BinRepository): BinInteractor {
        return BinInteractorImpl(repository)
    }
}