package pe.edu.upc.flota365.presentation.ui.gestor

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.presentation.ui.components.AppScaffold
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme

/* =======================
   Model
   ======================= */
data class MonitoringVehicleUi(
  val plate: String,
  val model: String,
  val driver: String,
  val status: String = "Activo",
  @DrawableRes val mapImage: Int? = null
)

/* =======================
   Screen
   ======================= */
@Composable
fun MonitoringScreen(
  onMenuClick: () -> Unit = {}
) {
  // demo data
  val vehicles = remember {
    List(6) {
      MonitoringVehicleUi(
        plate = "Placa: ABC - 123",
        model = "Modelo: Toyota Hilux 2023",
        driver = "Conductor: Carlos Méndez"
      )
    }
  }

  AppScaffold(
    title = "Monitoreo",
    onMenuClick = onMenuClick
  ) { pad ->
    Column(
      Modifier
        .fillMaxSize()
        .padding(pad)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

      // título grande
      Text(
        "Monitoreo de Flota:",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.SemiBold
      )

      // Tarjeta con mapa (imagen o placeholder)
      ElevatedCard(shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(12.dp)) {
          MapPreview(
            // si tienes un drawable del mapa, pásalo aquí:
            // imageRes = R.drawable.mock_map
            imageRes = null,
            modifier = Modifier
              .fillMaxWidth()
              .height(220.dp)
          )
          Spacer(Modifier.height(8.dp))
          Text(
            "Estado de vehículos:",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      // Lista de vehículos
      ElevatedCard(shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
          vehicles.forEachIndexed { index, v ->
            VehicleMonitorRow(
              plate = v.plate,
              model = v.model,
              driver = v.driver,
              status = v.status,
              onMore = { /* abrir menú contextual si quieres */ }
            )
            if (index != vehicles.lastIndex) Divider()
          }
        }
      }
    }
  }
}

/* =======================
   Building blocks
   ======================= */

@Composable
private fun MapPreview(
  @DrawableRes imageRes: Int?,
  modifier: Modifier = Modifier
) {
  if (imageRes != null) {
    Image(
      painter = painterResource(id = imageRes),
      contentDescription = "Mapa",
      modifier = modifier.clip(RoundedCornerShape(12.dp))
    )
  } else {
    // Placeholder con degradado (se parece al mock)
    Box(
      modifier
        .clip(RoundedCornerShape(12.dp))
        .background(
          Brush.linearGradient(
            listOf(
              MaterialTheme.colorScheme.primary.copy(alpha = .20f),
              MaterialTheme.colorScheme.secondary.copy(alpha = .20f)
            )
          )
        ),
      contentAlignment = Alignment.Center
    ) {
      Text("Mapa (visual)", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
  }
}

@Composable
private fun VehicleMonitorRow(
  plate: String,
  model: String,
  driver: String,
  status: String,
  onMore: () -> Unit
) {
  Row(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 6.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // ícono del vehículo
    Surface(
      tonalElevation = 2.dp,
      shape = MaterialTheme.shapes.medium,
      color = MaterialTheme.colorScheme.primary.copy(alpha = .12f)
    ) {
      Box(Modifier.size(44.dp), contentAlignment = Alignment.Center) {
        Icon(
          imageVector = Icons.Filled.DirectionsCar,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary
        )
      }
    }

    Spacer(Modifier.width(12.dp))

    // texto + pill de estado
    Column(Modifier.weight(1f)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(plate, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.width(8.dp))
        StatusPill(status)
      }
      Text(model, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      Text(driver, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }

    // menú ⋮
    var expanded by remember { mutableStateOf(false) }
    Box {
      IconButton(onClick = { expanded = true }) {
        Icon(Icons.Filled.MoreVert, contentDescription = "Más opciones")
      }
      DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(text = { Text("Ver detalles") }, onClick = { expanded = false })
        DropdownMenuItem(text = { Text("Reportar incidencia") }, onClick = { expanded = false })
      }
    }
  }
}

@Composable
private fun StatusPill(label: String) {
  AssistChip(
    onClick = {},
    label = { Text(label) },
    enabled = false,
    colors = AssistChipDefaults.assistChipColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer,
      labelColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
  )
}

/* =======================
   Preview
   ======================= */
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MonitoringScreenPreview() {
  FlotaTheme { MonitoringScreen() }
}
