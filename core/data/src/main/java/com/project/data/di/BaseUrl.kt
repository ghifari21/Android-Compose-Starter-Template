package com.project.data.di

/**
 * A wrapper for the Base URL to safely inject it via Hilt 
 * without causing KSP / BuildConfig resolution cycles in the data module.
 */
data class BaseUrl(val value: String)
