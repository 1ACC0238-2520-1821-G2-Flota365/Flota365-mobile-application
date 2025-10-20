package pe.edu.upc.flota365.features.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.R
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.auth.presentation.login.FlotaPrimaryButton

@Composable
fun OnboardingScreen(onStart: () -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.surface)
      .padding(24.dp),
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Column {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(320.dp)
          .background(
            brush = Brush.linearGradient(
              listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.primaryContainer
              )
            ),
            shape = RoundedCornerShape(24.dp)
          ),
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = painterResource(id = R.drawable.logo365),
          contentDescription = null,
          modifier = Modifier.size(270.dp),
          contentScale = ContentScale.Fit
        )
      }
      Spacer(modifier = Modifier.height(24.dp))
      Text(
        text = "Flota365",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = "El equilibrio móvil más allá de una opción",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
    FlotaPrimaryButton(
      text = "Comenzar",
      onClick = onStart,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Preview(showBackground = true, showSystemUi = true, name = "Onboarding")
@Composable
fun PreviewOnboardingScreen() {
  FlotaTheme {
    OnboardingScreen(onStart = {})
  }
}
