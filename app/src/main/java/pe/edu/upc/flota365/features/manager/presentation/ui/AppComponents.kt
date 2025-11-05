package pe.edu.upc.flota365.features.manager.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pe.edu.upc.flota365.R

@Composable
fun AppDrawerHeader() {
  Column(Modifier.fillMaxWidth().padding(16.dp)) {
    Box(Modifier.size(48.dp).clip(RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
      Image(painterResource(id = R.mipmap.ic_launcher_round), contentDescription = null)
    }
    Spacer(Modifier.height(10.dp))
    Text("Flota365", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
    Text("Gestores de flota", color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(Modifier.height(8.dp))
    Divider()
  }
}

@Composable
fun SectionTitle(text: String) {
  Text(text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
}

@Composable
fun KpiCard(title: String, value: String, footer: String? = null, modifier: Modifier = Modifier) {
  ElevatedCard(modifier = modifier) {
    Column(Modifier.padding(16.dp)) {
      Text(title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
      Spacer(Modifier.height(6.dp))
      Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
      if (!footer.isNullOrBlank()) {
        Spacer(Modifier.height(4.dp))
        Text(footer, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }
  }
}

@Composable
fun DrawerItemVisual(
  label: String,
  selected: Boolean,
  icon: @Composable () -> Unit,
  onClick: () -> Unit
) {
  NavigationDrawerItem(
    label = { Text(label) },
    selected = selected,
    onClick = onClick,
    icon = icon,
    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
  )
}
