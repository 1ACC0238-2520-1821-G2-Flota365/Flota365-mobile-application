package pe.edu.upc.flota365.presentation.ui.conductor

import pe.edu.upc.flota365.presentation.ui.conductor.components.FormTextField
import pe.edu.upc.flota365.presentation.ui.conductor.components.CheckboxItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.flota365.ui.theme.Flota365_App_mobileTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConductorCheckInScreen(
    onMenuClick: () -> Unit,
    onCancel: () -> Unit,
    onStartJourney: () -> Unit
) {
    var hora by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var combustible by remember { mutableStateOf("") }
    var carga by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    val checkListItems = remember {
        mutableStateMapOf(
            "Luces" to true,
            "Frenos" to true,
            "Neumáticos" to true,
            "Amarre" to true
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* */ },
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
        },
        containerColor = Color.Gray
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Check - In",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    Row(Modifier.fillMaxWidth()) {
                        FormTextField(label = "Hora (auto):", value = hora, onValueChange = { hora = it }, modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(16.dp))
                        FormTextField(label = "Ubicación (GPS):", value = ubicacion, onValueChange = { ubicacion = it }, modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(Modifier.fillMaxWidth()) {
                        FormTextField(label = "Combustible (%):", value = combustible, onValueChange = { combustible = it }, keyboardType = KeyboardType.Number, modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(16.dp))
                        FormTextField(label = "Carga (t):", value = carga, onValueChange = { carga = it }, keyboardType = KeyboardType.Number, modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Check list
                    Text(text = "Check list Pre-trip:", fontWeight = FontWeight.SemiBold)
                    checkListItems.keys.forEach { item ->
                        CheckboxItem(
                            label = item,
                            checked = checkListItems[item] ?: false,
                            onCheckedChange = { isChecked ->
                                checkListItems[item] = isChecked
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Observations text-field
                    FormTextField(label = "Observaciones:", value = observaciones, onValueChange = { observaciones = it }, singleLine = false, modifier = Modifier.height(80.dp))

                    Spacer(modifier = Modifier.height(24.dp))

                    // Actions buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = onCancel,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Cancelar", color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Button(
                            onClick = onStartJourney,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Iniciar jornada", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name="Check In Screen")
@Composable
fun ConductorCheckInScreenPreview() {
    Flota365_App_mobileTheme {
        ConductorCheckInScreen(
            onMenuClick = {},
            onCancel = {},
            onStartJourney = {}
        )
    }
}