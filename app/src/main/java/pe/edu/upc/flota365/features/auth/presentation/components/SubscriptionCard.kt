package pe.edu.upc.flota365.features.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.features.auth.presentation.SubscriptionPlan

@Composable
fun SubscriptionCard(
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
