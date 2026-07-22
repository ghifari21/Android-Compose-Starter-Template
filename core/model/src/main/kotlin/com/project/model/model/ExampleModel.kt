package com.project.model.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExampleModel(
    val id: String?
) : Parcelable
