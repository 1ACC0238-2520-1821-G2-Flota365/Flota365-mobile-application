package pe.edu.upc.flota365.features.manager.presentation.manager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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

  // Cargar flotas al iniciar
  LaunchedEffect(Unit) { viewModel.loadFleets() }

  // Snackbars automáticos
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

    Box(
      modifier = Modifier
        .padding(pad)
        .fillMaxSize()
        .background(
          Brush.verticalGradient(
            listOf(
              MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
              MaterialTheme.colorScheme.background
            )
          )
        )
    ) {

      when {
        uiState.isLoading -> {
          // Loader centrado a pantalla completa
          CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary
          )
        }

        else -> {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 16.dp, vertical = 8.dp),   // ⬅ menos espacio arriba
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {

            // Título principal, más compacto
            Text(
              text = "Gestión de Flota",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.SemiBold
            )

            // ---------- KPIs ----------
            if (uiState.fleets.isNotEmpty()) {
              val total = uiState.fleets.size
              val activos = uiState.fleets.count { it.isActive }
              val inactivos = total - activos

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
              ) {
                FleetKpiCard(
                  title = "Totales",
                  value = total.toString(),
                  trend = "100% registradas",
                  modifier = Modifier.weight(1f)
                )
                FleetKpiCard(
                  title = "Activas",
                  value = activos.toString(),
                  trend = "${(activos * 100 / total)}% operativas",
                  modifier = Modifier.weight(1f)
                )
                FleetKpiCard(
                  title = "Inactivas",
                  value = inactivos.toString(),
                  trend = "${(inactivos * 100 / total)}% inactivas",
                  modifier = Modifier.weight(1f)
                )
              }
            }

            // ---------- Encabezado + botón Nueva Flota ----------
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Tus flotas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
              )

              FilledTonalButton(
                onClick = { showDialog = true },
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                shape = RoundedCornerShape(16.dp)
              ) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(Modifier.width(4.dp))
                Text("Nueva flota")
              }
            }

            // ---------- Lista de flotas ----------
            when {
              uiState.error != null -> {
                Text(
                  text = "Error: ${uiState.error}",
                  color = MaterialTheme.colorScheme.error
                )
              }

              uiState.fleets.isEmpty() -> {
                Text("No hay flotas registradas.")
              }

              else -> {
                LazyColumn(
                  modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                  verticalArrangement = Arrangement.spacedBy(12.dp),
                  contentPadding = PaddingValues(bottom = 12.dp)
                ) {
                  items(uiState.fleets) { fleet ->
                    FleetCard(
                      code = fleet.code ?: "-",
                      name = fleet.name,
                      description = fleet.description ?: "Sin descripción",
                      vehicles = fleet.vehicleCount,
                      isActive = fleet.isActive,
                      onDelete = { viewModel.deleteFleet(fleet.id) }
                    )
                  }
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
        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("Nombre") }
        )
        OutlinedTextField(
          value = description,
          onValueChange = { description = it },
          label = { Text("Descripción") }
        )
        OutlinedTextField(
          value = type,
          onValueChange = { type = it },
          label = { Text("Tipo (ID numérico)") }
        )
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
private fun FleetKpiCard(
  title: String,
  value: String,
  trend: String,
  modifier: Modifier = Modifier
) {
  ElevatedCard(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp)
  ) {
    Column(
      modifier = Modifier.padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Text(title, style = MaterialTheme.typography.labelLarge)
      Text(
        text = value,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = trend,
        style = MaterialTheme.typography.labelSmall,
        color = Color(0xFF2E7D32)
      )
    }
  }
}

@Composable
private fun FleetCard(
  code: String,
  name: String,
  description: String,
  vehicles: Int,
  isActive: Boolean,
  onDelete: () -> Unit
) {
  ElevatedCard(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.elevatedCardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
  ) {
    Column(
      modifier = Modifier.padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = name,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.SemiBold
        )

        AssistChip(
          onClick = { },
          label = { Text(if (isActive) "Activa" else "Inactiva") },
          colors = AssistChipDefaults.assistChipColors(
            containerColor = if (isActive) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
            labelColor = if (isActive) Color(0xFF2E7D32) else Color(0xFFD32F2F)
          )
        )
      }

      Text("Código: $code", style = MaterialTheme.typography.bodySmall)
      Text("Vehículos: $vehicles", style = MaterialTheme.typography.bodySmall)
      Text(description, style = MaterialTheme.typography.bodySmall)

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        IconButton(onClick = onDelete) {
          Icon(
            Icons.Filled.Delete,
            contentDescription = "Eliminar",
            tint = Color(0xFFD32F2F)
          )
        }
      }
    }
  }
}

/* ----------------- PREVIEW ----------------- */
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FleetScreenPreview() {
  FlotaTheme { FleetScreen() }
}
