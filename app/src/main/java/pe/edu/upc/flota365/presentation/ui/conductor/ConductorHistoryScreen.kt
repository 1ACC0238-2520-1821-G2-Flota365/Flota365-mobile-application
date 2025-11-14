package pe.edu.upc.flota365.presentation.ui.conductor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme

data class Estadistica(
  val titulo: String,
  val valor: String,
  val cambio: String,
  val icono: Int,
  val colorIcono: Color
)

data class ViajeHistorial(
  val id: Int,
  val ruta: String,
  val descripcion: String,
  val descripcionExtra: String,
  val distancia: String,
  val duracion: String,
  val estado: String
)

private val sampleEstadisticas = listOf(
  //Estadistica("Total de viajes:", "41", "+1% esta semana", android.R.drawable.ic_menu_directions_car, Color(0xFF42A5F5)),
  //Estadistica("Velocidad promedio:", "3km", "+1% esta semana", android.R.drawable.ic_dashboard, Color(0xFF66BB6A)),
  Estadistica("Promedio de entrega:", "1h 30min", "+1% esta semana", android.R.drawable.ic_menu_my_calendar, Color(0xFFAB47BC)),
  Estadistica("Rendimiento personal:", "41 %", "+1% esta semana", android.R.drawable.ic_menu_sort_by_size, Color(0xFF26A69A))
)

private val sampleViajes = listOf(
  ViajeHistorial(1, "RT-045", "Lima → Callao", "(8 paradas)", "36.4 km", "1 h 12 min", "Completado"),
  ViajeHistorial(2, "RT-046", "Ate → Ventanilla", "(ADR clase 3)", "52.8 km", "1 h 55 min", "Completado"),
  ViajeHistorial(3, "RT-032", "Lurín → Chorrillos", "(franja AM)", "24.1 km", "58 min", "Completado")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConductorHistoryScreen(
  onMenuClick: () -> Unit,
  estadisticas: List<Estadistica>,
  viajes: List<ViajeHistorial>
) {
  var searchQuery by remember { mutableStateOf("") }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { /* Sin título */ },
        navigationIcon = {
          IconButton(onClick = onMenuClick) {
            Icon(Icons.Default.Menu, "Abrir menú", tint = Color.White)
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF00BCD4))
      )
    },
    containerColor = Color(0xFFF5F5F5)
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      item {
        Text(
          text = "Historial de viajes",
          style = MaterialTheme.typography.headlineLarge,
          fontWeight = FontWeight.Bold
        )
      }

      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          estadisticas.forEach { estadistica ->
            EstadisticaCard(
              estadistica = estadistica,
              modifier = Modifier.weight(1f)
            )
          }
        }
      }

      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Viajes realizados",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
          )
          Button(
            onClick = { /* Lógica para exportar */ },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFF00BCD4).copy(alpha = 0.2f),
              contentColor = Color(0xFF00838F)
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
          ) {
            Text("Exportar", fontSize = 12.sp)
          }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("Search") },
          leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp)
        )
      }

      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(2.dp)
        ) {
          Column {
            TablaHeader()
            HorizontalDivider()
            viajes.forEach { viaje ->
              TablaRow(viaje)
              HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
          }
        }
      }
    }
  }
}

@Composable
fun EstadisticaCard(estadistica: Estadistica, modifier: Modifier = Modifier) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(2.dp)
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Text(estadistica.titulo, fontSize = 12.sp, color = Color.Gray)
      Spacer(modifier = Modifier.height(8.dp))
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = estadistica.valor,
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold
        )
        Image(
          painter = painterResource(id = estadistica.icono),
          contentDescription = null,
          modifier = Modifier.size(32.dp)
        )
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = estadistica.cambio,
        fontSize = 12.sp,
        color = Color(0xFF4CAF50)
      )
    }
  }
}

@Composable
fun TablaHeader() {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color(0xFF00BCD4))
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text("Ruta", Modifier.weight(0.15f), color = Color.White, fontWeight = FontWeight.Bold)
    Text("Descripción", Modifier.weight(0.3f), color = Color.White, fontWeight = FontWeight.Bold)
    Text("Distancia", Modifier.weight(0.2f), color = Color.White, fontWeight = FontWeight.Bold)
    Text("Duración", Modifier.weight(0.2f), color = Color.White, fontWeight = FontWeight.Bold)
    Text("Estado", Modifier.weight(0.2f), color = Color.White, fontWeight = FontWeight.Bold)
  }
}

@Composable
fun TablaRow(viaje: ViajeHistorial) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(viaje.ruta, Modifier.weight(0.15f), fontSize = 14.sp)
    Column(Modifier.weight(0.3f)) {
      Text(viaje.descripcion, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
      Text(viaje.descripcionExtra, fontSize = 12.sp, color = Color.Gray)
    }
    TextWithUnit(text = viaje.distancia, Modifier.weight(0.2f))
    TextWithUnit(text = viaje.duracion, Modifier.weight(0.2f))
    Text(viaje.estado, Modifier.weight(0.2f), fontSize = 14.sp)
  }
}

@Composable
fun TextWithUnit(text: String, modifier: Modifier = Modifier) {
  val parts = text.split(" ")
  Text(
    buildAnnotatedString {
      withStyle(style = SpanStyle(fontSize = 14.sp)) {
        append(parts.getOrElse(0) { "" })
      }
      if (parts.size > 1) {
        append(" ")
        withStyle(style = SpanStyle(fontSize = 12.sp, color = Color.Gray)) {
          append(parts.getOrElse(1) { "" })
        }
      }
    },
    modifier = modifier
  )
}

@Preview(showBackground = true, showSystemUi = true, name="Conductor History ")
@Composable
fun ConductorHistoryScreenPreview() {
  FlotaTheme {
    ConductorHistoryScreen(
      onMenuClick = {},
      estadisticas = sampleEstadisticas,
      viajes = sampleViajes
    )
  }
}
