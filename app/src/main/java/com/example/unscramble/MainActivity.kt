package com.example.unscramble

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.example.unscramble.navigation.BottomBar
import com.example.unscramble.navigation.NavHostComposable
import com.example.unscramble.navigation.NavigationDrawerSheet
import com.example.unscramble.navigation.TopBar
import com.example.unscramble.security.BiometricAuthManager
import com.example.unscramble.ui.theme.UnscrambleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject lateinit var biometricAuthManager: BiometricAuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var isAuthenticated by remember { mutableStateOf(false) }

            if(isAuthenticated) {
                val navController = rememberNavController()

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                UnscrambleTheme {
                    ModalNavigationDrawer(drawerContent = {
                        NavigationDrawerSheet {
                            scope.launch { drawerState.close() }
                        }
                    }, drawerState = drawerState) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Scaffold(
                                topBar = {
                                    TopBar(
                                        navController = navController,
                                        openDrawer = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                    )
                                },
                                bottomBar = {
                                    BottomBar { navController.navigate(it) }
                                },
                            ) { innerPadding ->
                                NavHostComposable(innerPadding, navController)
                            }
                        }
                    }
                }
            } else {
                BiometricAuthentication(
                    isAuthenticated,
                    onSuccess = { isAuthenticated = true },
                    biometricAuthManager,
                )
            }
        }
    }

    @Composable
    fun BiometricAuthentication(
        isAuthenticated: Boolean,
        onSuccess: () -> Unit,
        biometricAuthManager: BiometricAuthManager
    ) {
        val context = LocalContext.current

        val biometricManager = remember { BiometricManager.from(context) }

        val isBiometricAvailable = remember {
            biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        }
        when (isBiometricAvailable) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // Biometric features are available
                if(!isAuthenticated) {
                    biometricAuthManager.authenticate(context, {}, onSuccess, {})
                }
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                // No biometric features available on this device
                Text(text = "This phone is not prepared for biometric features")
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                // Biometric features are currently unavailable.
                Text(text = "Biometric auth is unavailable")
            }

            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                // Biometric features available but a security vulnerability has been discovered
                Text(text = "You can't use biometric auth until you have updated your security details")
            }

            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                // Biometric features are currently unavailable because the specified options are incompatible with the current Android version..
                Text(text = "You can't use biometric auth with this Android version")
            }

            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                // Unable to determine whether the user can authenticate using biometrics
                Text(text = "You can't use biometric auth")
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // The user can't authenticate because no biometric or device credential is enrolled.
                Text(text = "You can't use biometric auth")
            }
        }
    }
}