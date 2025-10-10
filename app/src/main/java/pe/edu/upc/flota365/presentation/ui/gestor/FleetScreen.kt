package pe.edu.upc.flota365.presentation.ui.gestor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.presentation.ui.components.AppScaffold
import pe.edu.upc.flota365.ui.theme.FlotaTheme

@Composable
fun FleetScreen(
  onMenuClick: () -> Unit = {},
  onNewFleet: () -> Unit = {}
) {
  AppScaffold(
    title = "Gestión de Flota",
    onMenuClick = onMenuClick
  ) { pad ->
    Column(
      Modifier
        .fillMaxSize()
        .padding(pad)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

      // Título grande
      Text(
        "Gestión de Flota:",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.SemiBold
      )

      // -------- KPIs (2 x 2) --------
      ElevatedCard(shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
          Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            FleetKpiCard(
              modifier = Modifier.weight(1f),
              title = "Total de flotas:",
              value = "41",
              trend = "+ 1% esta semana"
            )
            FleetKpiCard(
              modifier = Modifier.weight(1f),
              title = "Flota Principal:",
              value = "38",
              trend = "12% operativo"
            )
          }
          Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            FleetKpiCard(
              modifier = Modifier.weight(1f),
              title = "Flota Secundaria:",
              value = "3",
              trend = "12% operativo"
            )
            FleetKpiCard(
              modifier = Modifier.weight(1f),
              title = "Flota Externa:",
              value = "41",
              trend = "100% operativo"
            )
          }
        }
      }

      // -------- Gestión de Flotas (tabla) --------
      Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          "Gestión de Flotas:",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.SemiBold
        )
        // Botón tipo "pill" a la derecha
        Button(
          onClick = onNewFleet,
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          shape = RoundedCornerShape(12.dp)
        ) { Text("+ Nueva flota") }
      }

      ElevatedCard(shape = RoundedCornerShape(16.dp)) {
        Column {
          FleetHeaderRow(
            "ID" to 0.14f,
            "Nombre" to 0.22f,
            "Descripción" to 0.32f,
            "Vehículos" to 0.16f,
            "Estado" to 0.16f
          )
          Divider()
          FleetDataRow("FL-001", "Flota Principal", "Unidades urbanas", "38", "Operativa")
          Divider()
          FleetDataRow("FL-002", "Flota secundaria", "Soporte interprovincial", "12", "Operativa")
          Divider()
          FleetDataRow("FL-003", "Flota externa", "Tercerizada para picos", "7", "Parcial")
        }
      }

      // -------- Flota Principal (tabla vehículos) --------
      Text(
        "Flota Principal:",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
      )

      ElevatedCard(shape = RoundedCornerShape(16.dp)) {
        Column {
          FleetHeaderRow(
            "Placa" to 0.18f,
            "Modelo" to 0.32f,
            "Conductor" to 0.22f,
            "Vehículos" to 0.14f,
            "Estado" to 0.14f
          )
          Divider()
          FleetDataRow("ABC-123", "Toyota Hilux 2023", "Carlos Méndez", "1", "Activo")
          Divider()
          FleetDataRow("XYZ-789", "Ford Ranger 2022", "Carlos Méndez", "1", "Activo")
          Divider()
          FleetDataRow("DEF-456", "Mitsubishi L200 2023", "Carlos Méndez", "1", "En ruta")
        }
      }
    }
  }
}

/* ---------- Reusables ---------- */

@Composable
private fun FleetKpiCard(
  title: String,
  value: String,
  trend: String,
  modifier: Modifier = Modifier
) {
  ElevatedCard(modifier = modifier, shape = RoundedCornerShape(16.dp)) {
    Row(
      Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Ícono/ilustración
      Surface(
        color = MaterialTheme.colorScheme.primary.copy(alpha = .12f),
        shape = RoundedCornerShape(12.dp)
      ) {
        Box(Modifier.size(48.dp), contentAlignment = Alignment.Center) {
          Icon(
            imageVector = Icons.Filled.DirectionsCar,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
          )
        }
      }
      Column(Modifier.weight(1f)) {
        Text(title, style = MaterialTheme.typography.labelLarge)
        Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(
          "↑ $trend",
          style = MaterialTheme.typography.labelSmall,
          color = Color(0xFF2E7D32) // verde
        )
      }
    }
  }
}

@Composable
private fun FleetHeaderRow(vararg cells: Pair<String, Float>) {
  // Cabecera con fondo teal y texto blanco
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
      .padding(horizontal = 12.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    cells.forEach { (title, weight) ->
      Box(
        Modifier
          .weight(weight)
          .padding(end = 8.dp)
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelLarge,
          color = MaterialTheme.colorScheme.onPrimary
        )
      }
    }
  }
}

@Composable
private fun FleetDataRow(
  c1: String,
  c2: String,
  c3: String,
  c4: String,
  c5: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    BodyCell(c1, 0.14f)
    BodyCell(c2, 0.22f)
    BodyCell(c3, 0.32f)
    BodyCell(c4, 0.16f)
    BodyCell(c5, 0.16f)
  }
}

@Composable
private fun BodyCell(text: String, weight: Float) {
  Box(
    Modifier
      .padding(end = 8.dp)
  ) {
    Text(text, style = MaterialTheme.typography.bodyMedium)
  }
}

/* ---------- Preview ---------- */

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FleetScreenPreview() {
  FlotaTheme { FleetScreen() }
}
