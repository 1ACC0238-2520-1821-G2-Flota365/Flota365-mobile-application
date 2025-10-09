package pe.edu.upc.flota365.presentation.ui.subscripcion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.presentation.ui.login.FlotaPrimaryButton

import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upc.flota365.ui.theme.FlotaTheme


data class SubscriptionPlan(
    val name: String,
    val price: String,
    val description: String,
    val accentColor: Color
)

@Composable
fun SubscriptionPlanScreen(
    onBack: () -> Unit,
    onSelectPlan: (SubscriptionPlan) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val plans = remember(colorScheme.primary, colorScheme.secondary) {
        listOf(
            SubscriptionPlan(
                name = "Plan Free",
                price = "S/0.00",
                description = "Acceso básico con funcionalidades limitadas.",
                accentColor = colorScheme.primary
            ),
            SubscriptionPlan(
                name = "Plan Premium",
                price = "S/67.89",
                description = "Control total, reportes inteligentes y soporte prioritario.",
                accentColor = colorScheme.secondary
            )
        )
    }

    var selectedPlan by rememberSaveable { mutableStateOf(plans.last()) }

    SubscriptionScaffold(title = "Elige tu plan", onBack = onBack) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                plans.forEach { plan ->
                    SubscriptionCard(
                        plan = plan,
                        isSelected = plan == selectedPlan,
                        onClick = { selectedPlan = plan }
                    )
                }
            }
            FlotaPrimaryButton(
                text = "Continuar",
                onClick = { onSelectPlan(selectedPlan) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SubscriptionCard(
    plan: SubscriptionPlan,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) plan.accentColor else MaterialTheme.colorScheme.outlineVariant
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        0f to plan.accentColor.copy(alpha = 0.1f),
                        1f to MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(plan.name, style = MaterialTheme.typography.titleLarge)
            Text(plan.price, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
            Text(plan.description, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

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

@Composable
private fun PlanSummary(planName: String, planPrice: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = "Plan seleccionado", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = planName, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
            Text(text = "$planPrice / mes", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SubscriptionScaffold(
    title: String,
    onBack: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Subscription Plan Screen")
@Composable
fun PreviewSubscriptionPlanScreen() {
    FlotaTheme {
        SubscriptionPlanScreen(
            onBack = {},
            onSelectPlan = {}
        )
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
