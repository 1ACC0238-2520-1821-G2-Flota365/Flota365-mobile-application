package pe.edu.upc.flota365.features.manager.presentation.manager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.manager.data.remote.models.CreateFleetRequest
import pe.edu.upc.flota365.features.manager.presentation.ui.AppScaffold

@Composable
fun FleetScreen(
  onMenuClick: () -> Unit = {},
  viewModel: FleetViewModel = hiltViewModel()
) {
  val uiState = viewModel.uiState
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()
  var showDialog by remember { mutableStateOf(false) }

  // 🔹 Cargar flotas al iniciar
  LaunchedEffect(Unit) { viewModel.loadFleets() }

  // 🔹 Mostrar Snackbars automáticos
  LaunchedEffect(uiState.message, uiState.error) {
    uiState.message?.let {
      scope.launch { snackbarHostState.showSnackbar(it) }
      viewModel.clearMessages()
    }
    uiState.error?.let {
      scope.launch { snackbarHostState.showSnackbar(it) }
      viewModel.clearMessages()
    }
  }

  AppScaffold(
    title = "Gestión de Flota",
    onMenuClick = onMenuClick,
    snackbarHostState = snackbarHostState
  ) { pad ->
    Column(
      Modifier
        .fillMaxSize()
        .padding(pad)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Text("Gestión de Flota", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)

      // ---------- KPIs ----------
      if (uiState.fleets.isNotEmpty()) {
        val total = uiState.fleets.size
        val activos = uiState.fleets.count { it.isActive }
        val inactivos = total - activos

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
          FleetKpiCard("Flotas Totales", total.toString(), "100% registradas")
          FleetKpiCard("Activas", activos.toString(), "${(activos * 100 / total)}% operativas")
          FleetKpiCard("Inactivas", inactivos.toString(), "${(inactivos * 100 / total)}% inactivas")
        }
      }

      // ---------- Botón Nueva Flota ----------
      Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("Listado de Flotas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

        Button(
          onClick = { showDialog = true },
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(Icons.Filled.Add, contentDescription = null)
          Spacer(Modifier.width(4.dp))
          Text("Nueva flota")
        }
      }

      // ---------- Tabla de flotas ----------
      when {
        uiState.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        uiState.error != null -> Text("Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
        uiState.fleets.isEmpty() -> Text("No hay flotas registradas.")
        else -> {
          ElevatedCard(shape = RoundedCornerShape(16.dp)) {
            Column {
              FleetHeaderRow("Código", "Nombre", "Descripción", "Vehículos", "Estado", "Acciones")
              Divider()
              uiState.fleets.forEach { fleet ->
                FleetDataRow(
                  c1 = fleet.code ?: "-",
                  c2 = fleet.name,
                  c3 = fleet.description ?: "Sin descripción",
                  c4 = fleet.vehicleCount.toString(),
                  c5 = if (fleet.isActive) "Activa" else "Inactiva",
                  onDelete = { viewModel.deleteFleet(fleet.id) }
                )
                Divider()
              }
            }
          }
        }
      }
    }

    // ---------- Diálogo Crear Flota ----------
    if (showDialog) {
      CreateFleetDialog(
        onDismiss = { showDialog = false },
        onConfirm = { name, desc, type ->
          viewModel.createFleet(CreateFleetRequest(name, desc, type))
          showDialog = false
        }
      )
    }
  }
}

/* ----------------- DIALOG: Crear Flota ----------------- */
@Composable
fun CreateFleetDialog(
  onDismiss: () -> Unit,
  onConfirm: (String, String, Int) -> Unit
) {
  var name by remember { mutableStateOf("") }
  var description by remember { mutableStateOf("") }
  var type by remember { mutableStateOf("0") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Nueva flota") },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
        OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Tipo (ID numérico)") })
      }
    },
    confirmButton = {
      Button(onClick = { onConfirm(name, description, type.toIntOrNull() ?: 0) }) {
        Text("Crear")
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onDismiss) { Text("Cancelar") }
    }
  )
}

/* ----------------- COMPONENTES REUTILIZABLES ----------------- */
@Composable
private fun FleetKpiCard(title: String, value: String, trend: String) {
  ElevatedCard(shape = RoundedCornerShape(16.dp)) {
    Column(
      Modifier.padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Text(title, style = MaterialTheme.typography.labelLarge)
      Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
      Text(trend, style = MaterialTheme.typography.labelSmall, color = Color(0xFF2E7D32))
    }
  }
}

@Composable
private fun FleetHeaderRow(vararg titles: String) {
  Row(
    Modifier
      .fillMaxWidth()
      .background(MaterialTheme.colorScheme.primary)
      .padding(vertical = 10.dp, horizontal = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    titles.forEach {
      Text(
        text = it,
        modifier = Modifier.weight(1f),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onPrimary
      )
    }
  }
}

@Composable
private fun FleetDataRow(
  c1: String,
  c2: String,
  c3: String,
  c4: String,
  c5: String,
  onDelete: () -> Unit
) {
  Row(
    Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(c1, Modifier.weight(1f))
    Text(c2, Modifier.weight(1f))
    Text(c3, Modifier.weight(1f))
    Text(c4, Modifier.weight(1f))
    Text(
      c5,
      Modifier.weight(1f),
      color = if (c5 == "Activa") Color(0xFF2E7D32) else Color(0xFFD32F2F),
      fontWeight = FontWeight.Medium
    )
    IconButton(onClick = onDelete) {
      Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color(0xFFD32F2F))
    }
  }
}

/* ----------------- PREVIEW ----------------- */
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FleetScreenPreview() {
  FlotaTheme { FleetScreen() }
}
