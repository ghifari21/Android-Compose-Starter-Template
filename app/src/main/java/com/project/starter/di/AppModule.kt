package com.project.starter.di

import com.project.data.di.BaseUrl
import com.project.starter.BuildConfig
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
    fun provideBaseUrl(): BaseUrl = BaseUrl(BuildConfig.BASE_URL)
}
