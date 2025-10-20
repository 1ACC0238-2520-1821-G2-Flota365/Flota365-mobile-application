package pe.edu.upc.flota365.presentation.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme

@Composable
fun ProfileScreen() {
  Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Text("Perfil")
  }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
  FlotaTheme { ProfileScreen() }
}
