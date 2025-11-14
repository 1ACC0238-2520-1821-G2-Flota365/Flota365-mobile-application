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
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.core.ui.theme.*
import pe.edu.upc.flota365.features.manager.presentation.ui.AppScaffold

@Composable
fun ReportsScreen(
  onMenuClick: () -> Unit = {},
  onNavigateToCreate: () -> Unit = {},
  viewModel: ReportsViewModel = hiltViewModel()
) {
  val state = viewModel.uiState
  val snackbarHostState = remember { SnackbarHostState() }

  // Carga inicial
  LaunchedEffect(Unit) {
    viewModel.fetchReports()
  }

  AppScaffold(
    title = "Reportes",
    onMenuClick = onMenuClick,
    snackbarHostState = snackbarHostState
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
        fontWeight = FontWeight.SemiBold,
        color = FlotaTextDark
      )

      /* ----------------- FILTROS ----------------- */
      ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = FlotaBgLight)
      ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

          Text(
            "Filtros de reporte",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = FlotaTextDark
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
          ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
              DropdownMenuWithLabel(
                label = "Tipo de reporte",
                options = listOf("Flota", "Conductores", "Asignaciones", "Mantenimiento"),
                selected = viewModel.selectedType,
                onSelected = { viewModel.selectedType = it }
              )
              DropdownMenuWithLabel(
                label = "Formato de salida",
                options = listOf("PDF", "Excel", "CSV"),
                selected = viewModel.selectedFormat,
                onSelected = { viewModel.selectedFormat = it }
              )
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
              DateRangeField(
                label = "Desde",
                value = viewModel.fromDate ?: "",
                onValueChange = { viewModel.fromDate = it }
              )
              DateRangeField(
                label = "Hasta",
                value = viewModel.toDate ?: "",
                onValueChange = { viewModel.toDate = it }
              )
            }
          }

          Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            Button(
              onClick = { viewModel.applyFilters() },
              colors = ButtonDefaults.buttonColors(containerColor = FlotaPrimary)
            ) {
              Text("Aplicar filtros", color = FlotaBgLight)
            }
            Spacer(Modifier.width(10.dp))
            OutlinedButton(onClick = { viewModel.clearFilters() }) {
              Text("Limpiar filtros")
            }
          }
        }
      }

      /* ----------------- LISTA DE REPORTES ----------------- */
      Text(
        "Reportes recientes",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Medium,
        color = FlotaTextDark
      )

      when {
        state.isLoading -> CircularProgressIndicator(
          Modifier.align(Alignment.CenterHorizontally),
          color = FlotaPrimary
        )

        state.error != null -> Text(
          text = "Error: ${state.error}",
          color = FlotaError,
          modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        state.reports.isEmpty() -> Text(
          "No hay reportes disponibles",
          color = FlotaTextDark,
          modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        else -> {
          ElevatedCard(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
            colors = CardDefaults.cardColors(containerColor = FlotaBgLight)
          ) {
            val scroll = rememberScrollState()
            Column(Modifier.horizontalScroll(scroll)) {
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

              Divider(thickness = 0.dp, color = FlotaPrimary)

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

/* ----------------- REUSABLES ----------------- */

@Composable
fun DropdownMenuWithLabel(label: String, options: List<String>, selected: String?, onSelected: (String?) -> Unit) {
  var expanded by remember { mutableStateOf(false) }

  Column {
    OutlinedTextField(
      value = selected ?: "",
      onValueChange = {},
      label = { Text(label) },
      readOnly = true,
      trailingIcon = {
        IconButton(onClick = { expanded = !expanded }) {
          Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
        }
      },
      modifier = Modifier.fillMaxWidth()
    )

    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
      options.forEach { option ->
        DropdownMenuItem(
          text = { Text(option) },
          onClick = {
            onSelected(option)
            expanded = false
          }
        )
      }
    }
  }
}

@Composable
fun DateRangeField(label: String, value: String, onValueChange: (String) -> Unit) {
  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(label) },
    trailingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
    modifier = Modifier.fillMaxWidth()
  )
}

@Composable
fun TableHeaderCell(text: String, weight: Float) {
  Box(Modifier.padding(end = 8.dp)) {
    Text(
      text = text,
      style = MaterialTheme.typography.labelLarge,
      color = FlotaBgLight,
      modifier = Modifier
        .fillMaxWidth()
        .background(FlotaPrimary, RoundedCornerShape(8.dp))
        .padding(horizontal = 8.dp, vertical = 8.dp)
    )
  }
}

@Composable
fun ReportRow(nombre: String?, tipo: String?, fecha: String?, autor: String?) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    BodyCell(nombre ?: "", 0.28f)
    BodyCell(tipo ?: "", 0.18f)
    BodyCell(fecha ?: "", 0.27f)
    BodyCell(autor ?: "", 0.27f)
  }
}

@Composable
fun BodyCell(text: String, weight: Float) {
  Box(Modifier.padding(end = 8.dp)) {
    Text(text = text, style = MaterialTheme.typography.bodyMedium, color = FlotaTextDark)
  }
}
