package pe.edu.upc.flota365.presentation.ui.conductor

import pe.edu.upc.flota365.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainConductorScreen(
  onMenuClick: () -> Unit,
  onCheckInClicked: () -> Unit,
  onCheckOutClicked: () -> Unit
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { /* ... */ },
        navigationIcon = {
          IconButton(onClick = onMenuClick) {
            Icon(
              imageVector = Icons.Default.Menu,
              contentDescription = "Menú de navegación",
              tint = Color.White
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = Color(0xFF00BCD4)
        )
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
        .background(Color(0xFFF0F0F0))
    ) {
      Text(
        text = "¡Bienvenido!",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
      )

      // Mapa de Ruta
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .height(250.dp)
          .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
      ) {
        Image(
          painter = painterResource(id = R.drawable.ic_mapa_ruta),
          contentDescription = "Vista del mapa",
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize()
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      MiJornadaCard(
        onCheckInClick = onCheckInClicked,
        onCheckOutClick = onCheckOutClicked
      )

      Spacer(modifier = Modifier.height(24.dp))

      MiVehiculoCard()

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun MiJornadaCard(
  onCheckInClick: () -> Unit,
  onCheckOutClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = "Mi Jornada", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
      Spacer(modifier = Modifier.height(16.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          InfoRow("Estado:")
          InfoRow("Hora de inicio:")
          InfoRow("Vehículo:")
        }
        Image(
          painter = painterResource(id = R.drawable.ic_menu_directions_car),
          contentDescription = "Icono de coche",
          modifier = Modifier.size(64.dp)
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        Button(
          onClick = onCheckInClick,
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
        ) {
          Text("CHECK IN", color = Color.White)
        }
        Button(
          onClick = onCheckOutClick,
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
        ) {
          Text("CHECK OUT", color = Color.White)
        }
      }
    }
  }
}

@Composable
fun MiVehiculoCard() {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = "Mi Vehiculo", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
      Spacer(modifier = Modifier.height(16.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          InfoRow("Placa:")
          InfoRow("Modelo:")
          InfoRow("Peso:")
          InfoRow("Altura:")
        }
        Image(
          painter = painterResource(id = R.drawable.ic_menu_directions_car),
          contentDescription = "Icono de coche",
          modifier = Modifier.size(64.dp)
        )
      }
    }
  }
}

@Composable
fun InfoRow(label: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(vertical = 6.dp)
  ) {
    Text(text = label, modifier = Modifier.width(100.dp))
    Box(
      modifier = Modifier
        .height(30.dp)
        .weight(1f)
        .clip(RoundedCornerShape(4.dp))
        .background(Color(0xFFF0F0F0))
    )
  }
}

@Preview(showBackground = true, showSystemUi = true, name="Main Coductor Screen")
@Composable
fun MainConductorScreenPreview() {
  FlotaTheme {
    MainConductorScreen(
      onMenuClick = {},
      onCheckInClicked = {},
      onCheckOutClicked = {}
    )
  }
}
