package pe.edu.upc.flota365.features.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.auth.presentation.components.SubscriptionScaffold
import pe.edu.upc.flota365.features.auth.presentation.login.FlotaPrimaryButton

@Composable
fun PaymentInformationScreen(
  onBack: () -> Unit,
  planName: String,
  planPrice: String,
  onSubscriptionConfirmed: () -> Unit
) {
  var cardHolder by rememberSaveable { mutableStateOf("") }
  var cardNumber by rememberSaveable { mutableStateOf("") }
  var expiration by rememberSaveable { mutableStateOf("") }
  var cvv by rememberSaveable { mutableStateOf("") }
  var email by rememberSaveable { mutableStateOf("") }
  var showDialog by rememberSaveable { mutableStateOf(false) }

  SubscriptionScaffold(title = "Información de Pago", onBack = onBack) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        PlanSummary(planName = planName, planPrice = planPrice)
        Text(
          text = "Introduce los datos de tu tarjeta",
          style = MaterialTheme.typography.titleMedium
        )
        OutlinedTextField(
          value = cardHolder,
          onValueChange = { cardHolder = it },
          label = { Text("Nombre del titular") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp)
        )
        OutlinedTextField(
          value = cardNumber,
          onValueChange = { cardNumber = it },
          label = { Text("Número de tarjeta") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
          OutlinedTextField(
            value = expiration,
            onValueChange = { expiration = it },
            label = { Text("MM/AA") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp)
          )
          OutlinedTextField(
            value = cvv,
            onValueChange = { cvv = it },
            label = { Text("CVC") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp)
          )
        }
        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("Correo de recibo") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp)
        )
      }

      FlotaPrimaryButton(
        text = "Confirmar",
        onClick = { showDialog = true },
        modifier = Modifier.fillMaxWidth()
      )
    }

    if (showDialog) {
      AlertDialog(
        onDismissRequest = { showDialog = false },
        title = {
          Text(text = "Confirmar suscripción", textAlign = TextAlign.Center)
        },
        text = {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Plan seleccionado: $planName")
            Text("Precio: $planPrice / mes")
            Text("Fecha de facturación: 30 de octubre de 2025")
            Text("Método de pago: Tarjeta terminada en ${'$'}{cardNumber.takeLast(4)}")
          }
        },
        confirmButton = {
          FlotaPrimaryButton(
            text = "Aceptar",
            onClick = {
              showDialog = false
              onSubscriptionConfirmed()
            },
            modifier = Modifier.fillMaxWidth()
          )
        },
        dismissButton = {
          TextButton(onClick = { showDialog = false }) {
            Text("Cancelar")
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp)
      )
    }
  }
}

@Preview(showBackground = true, showSystemUi = true, name = "Payment Information Screen")
@Composable
fun PreviewPaymentInformationScreen() {
  FlotaTheme {
    PaymentInformationScreen(
      onBack = {},
      planName = "Plan Premium",
      planPrice = "S/67.89",
      onSubscriptionConfirmed = {}
    )
  }
}
