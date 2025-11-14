package pe.edu.upc.flota365.features.auth.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PlanSummary(planName: String, planPrice: String) {
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
