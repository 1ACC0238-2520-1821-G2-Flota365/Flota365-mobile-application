package pe.edu.upc.flota365.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors: ColorScheme = lightColorScheme(
  primary = FlotaPrimary,
  onPrimary = FlotaBgLight,
  secondary = FlotaPrimaryAlt,
  background = FlotaBgLight,
  onBackground = FlotaTextDark,
  surface = FlotaBgLight,
  onSurface = FlotaTextDark,
  error = FlotaError,
  onError = FlotaBgLight
)

private val DarkColors: ColorScheme = darkColorScheme(
  primary = FlotaPrimary,
  onPrimary = FlotaBlack,
  secondary = FlotaPrimaryAlt,
  background = FlotaBlack,
  onBackground = FlotaGrayLight,
  surface = FlotaBlack,
  onSurface = FlotaGrayLight,
  error = FlotaError,
  onError = FlotaBlack
)

@Composable
fun FlotaTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = if (darkTheme) DarkColors else LightColors,
    typography = Typography(),
    content = content
  )
}
