package pe.edu.upc.flota365

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import pe.edu.upc.flota365.presentation.navigation.AppNavHost
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      FlotaTheme {
        Surface { AppNavHost() }
      }
    }
  }
}
