package pe.edu.upc.flota365.presentation.ui.gestor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.presentation.ui.components.SectionTitle
import pe.edu.upc.flota365.presentation.ui.components.SimpleBarChart
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme

@Composable
fun DriverStatsScreen() {
  Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
    SectionTitle("Evaluación del conductor")
    Text("Carlos Huamán", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
    ElevatedCard {
      Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Indicadores")
        SimpleBarChart(values = listOf(3f, 4.5f, 2.5f, 5f, 4f), modifier = Modifier.height(200.dp).fillMaxWidth())
      }
    }
    ElevatedCard {
      Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text("Historial de incidencias")
        Text("• 2025-09-12: Exceso de velocidad leve")
        Text("• 2025-08-30: Retraso de 15 min")
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun DriverStatsScreenPreview() {
  FlotaTheme { DriverStatsScreen() }
}
