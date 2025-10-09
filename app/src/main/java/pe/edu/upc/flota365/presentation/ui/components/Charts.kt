package pe.edu.upc.flota365.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp

@Composable
fun SimpleBarChart(values: List<Float>, modifier: Modifier) {
  val max = (values.maxOrNull() ?: 1f).coerceAtLeast(1f)
  val barColor = MaterialTheme.colorScheme.primary

  Canvas(modifier) {
    val barWidth = size.width / (values.size * 2f)
    values.forEachIndexed { i, v ->
      val left = i * 2f * barWidth + barWidth * 0.5f
      val top = size.height - (v / max) * size.height
      drawRect(
        color = barColor,
        topLeft = Offset(left, top),
        size = Size(barWidth, size.height - top)
      )
    }
  }
  Spacer(Modifier.height(6.dp))
}
