package pe.edu.upc.flota365.features.auth.presentation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import pe.edu.upc.flota365.features.auth.presentation.components.SubscriptionCard
import pe.edu.upc.flota365.features.auth.presentation.components.SubscriptionScaffold
import pe.edu.upc.flota365.features.auth.presentation.login.FlotaPrimaryButton

data class SubscriptionPlan(
  val name: String,
  val price: String,
  val description: String,
  val accentColor: Color
)

val SubscriptionPlanSaver: Saver<SubscriptionPlan, Any> = listSaver(
  save = { listOf(it.name, it.price, it.description, it.accentColor.toArgb()) },
  restore = {
    SubscriptionPlan(
      name = it[0] as String,
      price = it[1] as String,
      description = it[2] as String,
      accentColor = Color(it[3] as Int)
    )
  }
)

@Composable
fun SubscriptionPlanScreen(
  onBack: () -> Unit,
  onSelectPlan: (SubscriptionPlan) -> Unit
) {
  val colorScheme = MaterialTheme.colorScheme

  val plans = listOf(
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

  var selectedPlan by rememberSaveable(stateSaver = SubscriptionPlanSaver) {
    mutableStateOf(plans.last())
  }

  SubscriptionScaffold(
    title = "Elige tu plan",
    onBack = onBack
  ) {
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
        .padding(horizontal = 24.dp, vertical = 24.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {

      // Parte superior: título + descripción + cards
      Column(
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "Planes para tu flota",
          style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.SemiBold
          )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "Elige el plan que mejor se adapte a las necesidades de tu empresa. Puedes modificarlo en cualquier momento.",
          style = MaterialTheme.typography.bodyMedium,
          color = colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
          modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          plans.forEach { plan ->
            SubscriptionCard(
              plan = plan,
              isSelected = plan == selectedPlan,
              onClick = { selectedPlan = plan }
            )
          }
        }
      }

      // Parte inferior: resumen + botón
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "Seleccionado: ${selectedPlan.name} (${selectedPlan.price})",
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Medium
          ),
          color = colorScheme.onSurfaceVariant,
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
          textAlign = TextAlign.Center
        )

        FlotaPrimaryButton(
          text = "Continuar",
          onClick = { onSelectPlan(selectedPlan) },
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Podrás cambiar tu plan luego desde Configuración.",
          style = MaterialTheme.typography.bodySmall,
          color = colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSubscriptionPlanScreen() {
  FlotaTheme {
    SubscriptionPlanScreen(
      onBack = {},
      onSelectPlan = {}
    )
  }
}
