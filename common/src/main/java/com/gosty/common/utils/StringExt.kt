package com.gosty.common.utils

import android.util.Patterns

/**
 * Validates if the string is a valid email address.
 */
fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Alternative for [isNullOrEmpty] with inverse logic.
 */
fun String?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}
