package pe.edu.upc.flota365.features.auth.presentation.components

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
import androidx.compose.ui.unit.dp

@Composable
fun RoleCard(
  title: String,
  description: String,
  onClick: () -> Unit
) {
  Card(
    onClick = onClick,
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    shape = RoundedCornerShape(20.dp)
  ) {
    Column(modifier = Modifier.padding(24.dp)) {
      Text(text = title, style = MaterialTheme.typography.titleLarge)
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = description, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
  }
}
