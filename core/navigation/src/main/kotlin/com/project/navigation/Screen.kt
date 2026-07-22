package com.project.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    @StringRes val label: Int? = null,
    val activeIcon: ImageVector? = null,
    val inactiveIcon: ImageVector? = null
) {

}