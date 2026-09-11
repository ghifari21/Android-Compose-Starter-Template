package com.project.starter.ui.auth

import com.project.domain.repository.SessionManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AuthEntryPoint {
    fun sessionManager(): SessionManager
}
