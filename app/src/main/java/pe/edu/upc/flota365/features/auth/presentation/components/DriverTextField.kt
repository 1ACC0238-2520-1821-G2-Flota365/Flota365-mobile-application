package pe.edu.upc.flota365.features.auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DriverTextField(
  value: String,
  onValueChange: (String) -> Unit,
  label: String
) {
  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(label) },
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 16.dp),
    shape = RoundedCornerShape(16.dp)
  )
}
