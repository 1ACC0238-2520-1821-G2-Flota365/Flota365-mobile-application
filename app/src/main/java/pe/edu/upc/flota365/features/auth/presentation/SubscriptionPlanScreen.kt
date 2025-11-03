package pe.edu.upc.flota365.features.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.features.auth.presentation.login.FlotaPrimaryButton
import pe.edu.upc.flota365.core.ui.theme.FlotaTheme
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upc.flota365.features.auth.presentation.components.SubscriptionCard
import pe.edu.upc.flota365.features.auth.presentation.components.SubscriptionScaffold

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
