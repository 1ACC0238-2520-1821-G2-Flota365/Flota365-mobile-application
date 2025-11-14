package pe.edu.upc.flota365.features.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.auth.presentation.login.FlotaPrimaryButton

@Composable
fun PaymentInformationScreen(
  onSubscriptionConfirmed: () -> Unit,
  planName: String,
  planPrice: String
) {
  var cardHolder by rememberSaveable { mutableStateOf("") }
  var cardNumber by rememberSaveable { mutableStateOf("") }
  var expiration by rememberSaveable { mutableStateOf("") }
  var cvv by rememberSaveable { mutableStateOf("") }
  var email by rememberSaveable { mutableStateOf("") }
  var showDialog by rememberSaveable { mutableStateOf(false) }

  val isFormValid = listOf(cardHolder, cardNumber, expiration, cvv, email)
    .all { it.isNotBlank() }

  val colorScheme = MaterialTheme.colorScheme

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          listOf(
            colorScheme.primary.copy(alpha = 0.08f),
            colorScheme.background,
            colorScheme.surface
          )
        )
      )
      .padding(horizontal = 24.dp, vertical = 32.dp),
    verticalArrangement = Arrangement.SpaceBetween
  ) {

    // CARD con contenido
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 16.dp)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {

        // RESUMEN DEL PLAN
        Text(
          text = "Resumen del plan",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.SemiBold
          ),
          color = colorScheme.primary
        )

        Text(
          text = planName,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold
          )
        )

        Text(
          text = "$planPrice / mes",
          style = MaterialTheme.typography.bodyMedium,
          color = colorScheme.onSurfaceVariant
        )

        Text(
          text = "Tu suscripción se renovará automáticamente cada mes. Puedes cancelarla desde Configuración.",
          style = MaterialTheme.typography.bodySmall,
          color = colorScheme.onSurfaceVariant
        )

        // FORMULARIO
        Text(
          text = "Datos de la tarjeta",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
          )
        )

        OutlinedTextField(
          value = cardHolder,
          onValueChange = { cardHolder = it },
          label = { Text("Nombre del titular") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          singleLine = true
        )

        OutlinedTextField(
          value = cardNumber,
          onValueChange = { cardNumber = it },
          label = { Text("Número de tarjeta") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          singleLine = true
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          OutlinedTextField(
            value = expiration,
            onValueChange = { expiration = it },
            label = { Text("MM/AA") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            singleLine = true
          )
          OutlinedTextField(
            value = cvv,
            onValueChange = { cvv = it },
            label = { Text("CVC") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            singleLine = true
          )
        }

        Text(
          text = "Correo de facturación",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.SemiBold
          )
        )

        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("Correo de recibo") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          singleLine = true
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // BOTÓN Y TEXTO DE SEGURIDAD
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      FlotaPrimaryButton(
        text = "Confirmar",
        onClick = { showDialog = true },
        modifier = Modifier.fillMaxWidth(),
        enabled = isFormValid
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Tus datos se procesan de forma segura.\nNunca almacenamos la información de tu tarjeta.",
        style = MaterialTheme.typography.bodySmall,
        color = colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }

  // ALERT DIALOG
  if (showDialog) {
    AlertDialog(
      onDismissRequest = { showDialog = false },
      title = {
        Text(
          text = "Confirmar suscripción",
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
          )
        )
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Text("Plan: $planName")
          Text("Precio: $planPrice / mes")
          Text("Tarjeta terminada en ${cardNumber.takeLast(4)}")
          Text("Se enviará un comprobante a $email")
        }
      },
      confirmButton = {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
        ) {
          FlotaPrimaryButton(
            text = "Aceptar",
            onClick = {
              showDialog = false
              onSubscriptionConfirmed()
            },
            modifier = Modifier.fillMaxWidth()
          )
        }
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPaymentInformationScreen() {
  FlotaTheme {
    PaymentInformationScreen(
      onSubscriptionConfirmed = {},
      planName = "Plan Premium",
      planPrice = "S/67.89"
    )
  }
}
