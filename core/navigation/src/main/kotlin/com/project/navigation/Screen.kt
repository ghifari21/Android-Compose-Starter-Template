package com.project.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a screen configuration, typically used for Bottom Navigation items.
 * Does not contain route strings anymore, as routes are defined as @Serializable objects.
 */
sealed class Screen(
    @StringRes val label: Int? = null,
    val activeIcon: ImageVector? = null,
    val inactiveIcon: ImageVector? = null
)