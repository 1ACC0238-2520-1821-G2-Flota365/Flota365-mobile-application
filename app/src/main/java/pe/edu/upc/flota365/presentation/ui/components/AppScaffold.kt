package pe.edu.upc.flota365.presentation.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Scaffold base con TopAppBar que SIEMPRE muestra el ícono de menú.
 * onMenuClick abre el drawer desde el NavGraph.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
  title: String,
  onMenuClick: () -> Unit,
  content: @Composable (PaddingValues) -> Unit
) {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = { Text(title) },
        navigationIcon = {
          IconButton(onClick = onMenuClick) {
            Icon(Icons.Filled.Menu, contentDescription = "Abrir menú")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors()
      )
    }
  ) { innerPadding ->
    content(innerPadding)
  }
}
