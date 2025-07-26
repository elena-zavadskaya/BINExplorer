package com.example.binexplorer.di

import android.content.Context
import androidx.room.Room
import com.example.binexplorer.data.local.AppDatabase
import com.example.binexplorer.data.local.history.BinHistoryDao
import com.example.binexplorer.data.remote.ApiClient
import com.example.binexplorer.data.remote.BinApiService
import com.example.binexplorer.data.repository.BinRepositoryImpl
import com.example.binexplorer.data.repository.HistoryRepositoryImpl
import com.example.binexplorer.domain.interactor.BinInteractor
import com.example.binexplorer.domain.interactor.BinInteractorImpl
import com.example.binexplorer.domain.interactor.HistoryInteractor
import com.example.binexplorer.domain.repository.BinRepository
import com.example.binexplorer.domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideBinInteractor(
        repository: BinRepository,
        historyInteractor: HistoryInteractor
    ): BinInteractor {
        return BinInteractorImpl(repository, historyInteractor)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "binexplorer-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBinHistoryDao(database: AppDatabase): BinHistoryDao {
        return database.binHistoryDao()
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(dao: BinHistoryDao): HistoryRepository {
        return HistoryRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideHistoryInteractor(repository: HistoryRepository): HistoryInteractor {
        return HistoryInteractor(repository)
    }
}