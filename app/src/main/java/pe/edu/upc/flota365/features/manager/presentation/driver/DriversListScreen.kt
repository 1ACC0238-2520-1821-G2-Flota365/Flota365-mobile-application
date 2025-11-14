package pe.edu.upc.flota365.features.manager.presentation.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriversListScreen(
  viewModel: DriversViewModel = hiltViewModel(),
  onCreate: () -> Unit = {},
  onEdit: (Int) -> Unit = {},
  onStats: () -> Unit = {},
  onNavigateToProfile: () -> Unit = {}
) {
  val drivers = viewModel.drivers
  val stats = viewModel.stats
  val isLoading = viewModel.isLoading
  val error = viewModel.error

  LaunchedEffect(Unit) {
    viewModel.loadDrivers()
    viewModel.loadDriverStats()
  }

  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Text(
            text = "Conductores",
            fontWeight = FontWeight.SemiBold
          )
        },
        navigationIcon = {},
        actions = {
          IconButton(onClick = onNavigateToProfile) {
            Icon(
              imageVector = Icons.Default.AccountCircle,
              contentDescription = "Perfil",
              tint = MaterialTheme.colorScheme.onPrimary
            )
          }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = MaterialTheme.colorScheme.primary,
          titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
      )
    }
  ) { padding ->

    Box(
      modifier = Modifier
        .padding(padding)
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
        isLoading -> {
          CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary
          )
        }

        error != null -> {
          Text(
            text = "Error: $error",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.align(Alignment.Center)
          )
        }

        else -> {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {

            // BOTONES DE ACCIÓN
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              FilledTonalButton(
                onClick = onCreate,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp)
              ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Nuevo")
              }

              FilledTonalButton(
                onClick = onStats,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp)
              ) {
                Icon(Icons.Default.BarChart, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Estadísticas")
              }
            }

            // RESUMEN DE STATS
            stats?.let {
              Text(
                text = "Activos: ${it.activeDrivers}/${it.totalDrivers} • Experiencia promedio: ${it.averageExperience} años",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
              )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // LISTA SCROLLABLE DE CONDUCTORES
            LazyColumn(
              modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
              verticalArrangement = Arrangement.spacedBy(12.dp),
              contentPadding = PaddingValues(bottom = 12.dp)
            ) {
              items(drivers) { driver ->
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
                    // Nombre
                    Text(
                      text = "${driver.firstName} ${driver.lastName}",
                      style = MaterialTheme.typography.titleMedium,
                      fontWeight = FontWeight.SemiBold
                    )

                    // Teléfono
                    Text(
                      text = "Teléfono: ${driver.phone}",
                      style = MaterialTheme.typography.bodyMedium
                    )

                    // Licencia
                    Text(
                      text = "Licencia: ${driver.licenseNumber}",
                      style = MaterialTheme.typography.bodyMedium
                    )

                    // Estado
                    Text(
                      text = if (driver.isActive) "Activo" else "Inactivo",
                      color = if (driver.isActive) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                      style = MaterialTheme.typography.bodySmall,
                      fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.End
                    ) {
                      TextButton(onClick = { onEdit(driver.id) }) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Editar")
                      }
                      TextButton(onClick = { viewModel.deleteDriver(driver.id) }) {
                        Icon(
                          Icons.Default.Delete,
                          contentDescription = null,
                          tint = Color.Red
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Eliminar", color = Color.Red)
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
