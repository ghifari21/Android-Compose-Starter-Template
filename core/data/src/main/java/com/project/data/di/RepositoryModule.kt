package com.project.data.di

import com.project.data.repository.ExampleRepositoryImpl
import com.project.domain.repository.ExampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExampleRepository(
        exampleRepositoryImpl: ExampleRepositoryImpl
    ): ExampleRepository

    @Binds
    @Singleton
    abstract fun bindSyncRepository(
        workManagerSyncRepository: com.project.data.repository.WorkManagerSyncRepository
    ): com.project.domain.repository.SyncRepository
}
