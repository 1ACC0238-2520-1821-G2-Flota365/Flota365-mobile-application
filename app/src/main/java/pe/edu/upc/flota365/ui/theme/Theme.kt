package pe.edu.upc.flota365.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryTealDarkMode,
    onPrimary = Color(0xFF00363D),
    primaryContainer = PrimaryTealContainerDark,
    onPrimaryContainer = PrimaryTealContainer,
    secondary = SecondaryAmberDark,
    onSecondary = Color(0xFF332600),
    secondaryContainer = SecondaryAmberContainerDark,
    onSecondaryContainer = SecondaryAmberContainer,
    tertiary = AccentTeal,
    onTertiary = Color(0xFF00363D),
    background = BackgroundDark,
    onBackground = TextPrimaryLight,
    surface = SurfaceDark,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryLight,
    outline = OutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryTeal,
    onPrimary = Color.White,
    primaryContainer = PrimaryTealContainer,
    onPrimaryContainer = PrimaryTealDark,
    secondary = SecondaryAmber,
    onSecondary = Color(0xFF1C1B1F),
    secondaryContainer = SecondaryAmberContainer,
    onSecondaryContainer = Color(0xFF271900),
    tertiary = AccentTeal,
    onTertiary = Color.White,
    background = BackgroundLight,
    onBackground = TextPrimaryDark,
    surface = SurfaceLight,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryDark,
    outline = OutlineLight
)

@Composable
fun Flota365_App_mobileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}