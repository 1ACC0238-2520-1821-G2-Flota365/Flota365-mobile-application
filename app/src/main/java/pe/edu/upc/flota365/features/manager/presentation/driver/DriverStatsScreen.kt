package pe.edu.upc.flota365.features.manager.presentation.driver

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pe.edu.upc.flota365.features.manager.presentation.ui.SectionTitle
import pe.edu.upc.flota365.features.manager.presentation.ui.SimpleBarChart
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme

@Composable
fun DriverStatsScreen(
  viewModel: DriverStatsViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsState()

  Column(
    Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    SectionTitle("Estadísticas de Conductores")

    when {
      uiState.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
      }

      uiState.error != null -> Text(
        "Error: ${uiState.error}",
        color = MaterialTheme.colorScheme.error
      )

      uiState.stats != null -> {
        val stats = uiState.stats!!

        // 🧾 Tarjeta resumen
        ElevatedCard {
          Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Resumen General", fontWeight = FontWeight.SemiBold)
            Text("Total de conductores: ${stats.totalDrivers}")
            Text("Activos: ${stats.activeDrivers}")
            Text("Inactivos: ${stats.inactiveDrivers}")
            Text("Licencias vencidas: ${stats.driversWithExpiredLicense}")
            Text("Promedio de experiencia: ${stats.averageExperience} años")
          }
        }

        // 📊 Gráfico simple
        ElevatedCard {
          Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Distribución", fontWeight = FontWeight.SemiBold)
            SimpleBarChart(
              values = listOf(
                stats.totalDrivers.toFloat(),
                stats.activeDrivers.toFloat(),
                stats.inactiveDrivers.toFloat(),
                stats.averageExperience.toFloat()
              ),
              modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
            )
          }
        }
      }

      else -> Text("No hay datos disponibles.")
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun DriverStatsScreenPreview() {
  FlotaTheme { DriverStatsScreen() }
}
