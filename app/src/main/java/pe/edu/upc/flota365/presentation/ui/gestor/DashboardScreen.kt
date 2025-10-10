package pe.edu.upc.flota365.presentation.ui.gestor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.presentation.ui.components.AppScaffold
import pe.edu.upc.flota365.ui.theme.FlotaTheme

@Composable
fun DashboardScreen(
  onMenuClick: () -> Unit = {},
) {
  AppScaffold(
    title = "Flota365",
    onMenuClick = onMenuClick
  ) { padding ->

    var query by remember { mutableStateOf("") }

    val filtered = remember(query) {
      sampleVehicles.filter { v ->
        val q = query.trim().lowercase()
        if (q.isEmpty()) true
        else v.plate.lowercase().contains(q) ||
          v.model.lowercase().contains(q) ||
          v.driver.lowercase().contains(q)
      }
    }

    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(
        start = 16.dp, end = 16.dp,
        top = 12.dp, bottom = 16.dp
      ),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Título grande
      item {
        Text(
          text = "Flota365",
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.SemiBold
        )
      }

      // KPIs 2x2
      item {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SmallKpiCard(
              modifier = Modifier.weight(1f).height(92.dp),
              icon = { Icon(Icons.Filled.DirectionsCar, null) },
              title = "En el estrecho",
              value = "41",
              subtitle = ""
            )
            SmallKpiCard(
              modifier = Modifier.weight(1f).height(92.dp),
              icon = { Icon(Icons.Filled.Speed, null) },
              title = "Velocidades en riesgo",
              value = "41",
              subtitle = ""
            )
          }
          Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SmallKpiCard(
              modifier = Modifier.weight(1f).height(92.dp),
              icon = { Icon(Icons.Filled.Build, null) },
              title = "En mantenimiento",
              value = "41",
              subtitle = ""
            )
            SmallKpiCard(
              modifier = Modifier.weight(1f).height(92.dp),
              icon = { Icon(Icons.Filled.Settings, null) },
              title = "Líderes de flota",
              value = "41",
              subtitle = ""
            )
          }
        }
      }

      // Header sección
      item {
        Row(
          Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            "Vehículos activos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
          )
          AssistChip(onClick = {}, label = { Text("Activos") }, enabled = false)
        }
      }

      // Buscador funcional
      item {
        OutlinedTextField(
          value = query,
          onValueChange = { query = it },
          placeholder = { Text("Search") },
          trailingIcon = { Icon(Icons.Filled.Search, null) },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
      }

      // Lista filtrada
      items(filtered) { item ->
        VehicleListRow(
          plate = item.plate,
          model = item.model,
          driver = item.driver,
          status = "Activo",
          onMenu = {}
        )
        Divider()
      }
    }
  }
}

private data class VehicleUi(val plate: String, val model: String, val driver: String)
private val sampleVehicles = listOf(
  VehicleUi("Placa: ABC - 123", "Modelo: Toyota Hilux 2022", "Conductor: Carlos Méndez"),
  VehicleUi("Placa: DEF - 456", "Modelo: Toyota Hilux 2023", "Conductor: Carlos Méndez"),
  VehicleUi("Placa: GHI - 789", "Modelo: Toyota Hilux 2022", "Conductor: Carlos Méndez"),
  VehicleUi("Placa: JKL - 321", "Modelo: Toyota Hilux 2021", "Conductor: Carlos Méndez")
)

@Composable
private fun SmallKpiCard(
  icon: @Composable () -> Unit,
  title: String,
  value: String,
  subtitle: String,
  modifier: Modifier = Modifier
) {
  ElevatedCard(modifier = modifier) {
    Row(
      Modifier
        .fillMaxSize()
        .padding(horizontal = 12.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Surface(
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primary.copy(alpha = .12f)
      ) {
        Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) { icon() }
      }
      Column(Modifier.weight(1f)) {
        Text(title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        if (subtitle.isNotBlank()) {
          Text(subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      }
    }
  }
}

@Composable
private fun VehicleListRow(
  plate: String,
  model: String,
  driver: String,
  status: String,
  onMenu: () -> Unit
) {
  Row(
    Modifier
      .fillMaxWidth()
      .padding(vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Surface(
      tonalElevation = 1.dp,
      shape = MaterialTheme.shapes.medium,
      color = MaterialTheme.colorScheme.primary.copy(alpha = .12f)
    ) {
      Box(Modifier.size(44.dp), contentAlignment = Alignment.Center) {
        Icon(Icons.Filled.DirectionsCar, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
      }
    }
    Spacer(Modifier.width(12.dp))

    Column(Modifier.weight(1f)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(plate, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.width(8.dp))
        StatusChip(text = status)
      }
      Text(model, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      Text(driver, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }

    IconButton(onClick = onMenu) { Icon(Icons.Filled.MoreVert, contentDescription = null) }
  }
}

@Composable
private fun StatusChip(text: String) {
  AssistChip(
    onClick = {},
    label = { Text(text) },
    enabled = false,
    colors = AssistChipDefaults.assistChipColors(
      labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
      containerColor = MaterialTheme.colorScheme.primaryContainer
    )
  )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DashboardPreview() { FlotaTheme { DashboardScreen() } }
