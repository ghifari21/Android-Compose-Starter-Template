package com.project.common.notification

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/**
 * A headless Composable that automatically requests the POST_NOTIFICATIONS permission
 * on Android 13 (API 33) and above.
 *
 * @param onPermissionGranted Callback invoked when permission is granted or already granted.
 * @param onPermissionDenied Callback invoked when permission is denied.
 */
@Composable
fun NotificationPermissionEffect(
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {}
) {
    val context = LocalContext.current

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val isGranted = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                if (granted) {
                    onPermissionGranted()
                } else {
                    onPermissionDenied()
                }
            }
        )

        LaunchedEffect(Unit) {
            if (isGranted) {
                onPermissionGranted()
            } else {
                launcher.launch(permission)
            }
        }
    } else {
        // Below Android 13, permission is granted at install time
        LaunchedEffect(Unit) {
            onPermissionGranted()
        }
    }
}
