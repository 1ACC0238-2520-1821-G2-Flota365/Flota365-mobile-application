package pe.edu.upc.flota365.features.manager.presentation.report

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.manager.presentation.ui.AppScaffold

@Composable
fun ReportsScreen(
  onMenuClick: () -> Unit = {},
  viewModel: ReportsViewModel = hiltViewModel()
) {
  val state = viewModel.uiState

  // 🔁 Llamamos a la carga de reportes solo una vez al entrar
  LaunchedEffect(Unit) {
    viewModel.fetchReports()
  }

  AppScaffold(
    title = "Reportes",
    onMenuClick = onMenuClick
  ) { pad ->
    Column(
      Modifier
        .fillMaxSize()
        .padding(pad)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Text(
        text = "Reportes",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.SemiBold
      )

      // --- Filtros (mantenemos tu diseño original) ---
      ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
      ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
          Text(
            "Filtros de reporte",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
          ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
              LabeledDropdown(label = "Tipos de reporte")
              LabeledDropdown(label = "Vehículos")
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
              LabeledDateRange(label = "Rango de fechas")
              LabeledDropdown(label = "Formato de salida")
            }
          }

          Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            Button(onClick = { /* TODO: Generar reporte */ }) { Text("Generar reporte") }
            Spacer(Modifier.width(10.dp))
            OutlinedButton(onClick = { /* TODO: Limpiar filtros */ }) { Text("Limpiar filtros") }
          }
        }
      }

      // --- Lista de reportes ---
      Text(
        "Reportes recientes",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Medium
      )

      when {
        state.isLoading -> {
          CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        }
        state.error != null -> {
          Text(
            text = "Error: ${state.error}",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.align(Alignment.CenterHorizontally)
          )
        }
        state.reports.isEmpty() -> {
          Text("No hay reportes disponibles")
        }
        else -> {
          ElevatedCard(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
          ) {
            val scroll = rememberScrollState()

            Column(Modifier.horizontalScroll(scroll)) {
              // Encabezado
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                TableHeaderCell("Nombre", 0.28f)
                TableHeaderCell("Tipo", 0.18f)
                TableHeaderCell("Fecha de creación", 0.27f)
                TableHeaderCell("Creado por", 0.27f)
              }

              Divider(thickness = 0.dp, color = MaterialTheme.colorScheme.primary)

              state.reports.forEach { report ->
                ReportRow(
                  nombre = report.title,
                  tipo = report.type,
                  fecha = report.generatedAt,
                  autor = report.createdBy
                )
                Divider()
              }
            }
          }
        }
      }
    }
  }
}

/* ---------- Reusables ---------- */

@Composable
private fun LabeledDropdown(label: String) {
  OutlinedTextField(
    value = "",
    onValueChange = {},
    label = { Text(label) },
    readOnly = true,
    enabled = false,
    trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = null) },
    modifier = Modifier.fillMaxWidth()
  )
}

@Composable
private fun LabeledDateRange(label: String) {
  OutlinedTextField(
    value = "",
    onValueChange = {},
    label = { Text(label) },
    readOnly = true,
    enabled = false,
    trailingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
    modifier = Modifier.fillMaxWidth()
  )
}

@Composable
private fun TableHeaderCell(text: String, weight: Float) {
  Box(Modifier.padding(end = 8.dp)) {
    Text(
      text = text,
      style = MaterialTheme.typography.labelLarge,
      color = MaterialTheme.colorScheme.onPrimary,
      modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
        .padding(horizontal = 8.dp, vertical = 8.dp)
    )
  }
}

@Composable
private fun ReportRow(
  nombre: String?,
  tipo: String?,
  fecha: String?,
  autor: String?
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    BodyCell(nombre ?:"", 0.28f)
    BodyCell(tipo ?:"", 0.18f)
    BodyCell(fecha ?:"", 0.27f)
    BodyCell(autor ?:"" , 0.27f)
  }
}

@Composable
private fun BodyCell(text: String, weight: Float) {
  Box(Modifier.padding(end = 8.dp)) {
    Text(text = text, style = MaterialTheme.typography.bodyMedium)
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ReportsScreenPreview() {
  FlotaTheme { ReportsScreen() }
}
