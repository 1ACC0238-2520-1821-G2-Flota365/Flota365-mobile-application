package pe.edu.upc.flota365.presentation.ui.conductor

import pe.edu.upc.flota365.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme

data class Ruta(
  val id: Int,
  val codigo: String,
  val estado: String,
  val peso: Double,
  val altura: Double,
  val viasRestringidas: Int
)

private val sampleRutas = List(5) {
  Ruta(
    id = it,
    codigo = "RT - 045",
    estado = "En curso",
    peso = 18.5,
    altura = 3.9,
    viasRestringidas = 1
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConductorPathScreen(
  onMenuClick: () -> Unit,
  rutas: List<Ruta>
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { /* */ },
        navigationIcon = {
          IconButton(onClick = onMenuClick) {
            Icon(
              imageVector = Icons.Default.Menu,
              contentDescription = "Abrir menú",
              tint = Color.White
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = Color(0xFF00BCD4)
        )
      )
    },
    containerColor = Color(0xFFF0F0F0)
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentPadding = PaddingValues(vertical = 16.dp)
    ) {
      item {
        Text(
          text = "Mis Rutas:",
          fontSize = 28.sp,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
      }

      // Map Placeholder
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 16.dp),
          shape = RoundedCornerShape(12.dp),
          elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
          Image(
            painter = painterResource(id = R.drawable.ic_mapa_ruta),
            contentDescription = "Vista del mapa de la ruta",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
          text = "Rutas:",
          fontSize = 18.sp,
          fontWeight = FontWeight.SemiBold,
          modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
      }

      // Path List
      items(rutas) { ruta ->
        RutaItem(
          ruta = ruta,
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
      }
    }
  }
}

@Composable
fun RutaItem(
  ruta: Ruta,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.Top
      ) {
        // Path Icon
        Box(
          modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFF9800).copy(alpha = 0.2f)), // Fondo naranja claro
          contentAlignment = Alignment.Center
        ) {
          Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_directions),
            contentDescription = "Icono de ruta",
            tint = Color(0xFFFF9800),
            modifier = Modifier.size(40.dp)
          )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(text = "Ruta: ${ruta.codigo}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
          Text(text = "Estado: ${ruta.estado}", fontSize = 14.sp, color = Color.Gray)
          Text(text = "Peso: ${ruta.peso}t", fontSize = 14.sp, color = Color.Gray)
          Text(text = "Altura: ${ruta.altura}m", fontSize = 14.sp, color = Color.Gray)
          Text(text = "Vías restringidas: ${ruta.viasRestringidas}", fontSize = 14.sp, color = Color.Gray)
        }

        IconButton(onClick = { /* Menu Navigation State */ }) {
          Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
        }
      }

      // "Submit Evidence" Button
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(end = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.End
      ) {
        Button(
          onClick = { /* Subtmit Button State  */ },
          shape = CircleShape,
          colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00BCD4).copy(alpha = 0.2f),
            contentColor = Color(0xFF00838F)
          ),
          contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
          modifier = Modifier.height(36.dp)
        ) {
          Text("Subir evidencias", fontSize = 12.sp)
        }
      }
    }
  }
}

@Preview(showBackground = true, showSystemUi = true, name="Conductor Path")
@Composable
fun ConductorPathScreenPreview() {
  FlotaTheme {
    ConductorPathScreen(
      onMenuClick = {},
      rutas = sampleRutas
    )
  }
}
