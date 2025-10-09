@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package pe.edu.upc.flota365.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppScaffold(
  title: String,
  onMenuClick: () -> Unit = {},
  floatingActionButton: (@Composable () -> Unit)? = null,
  content: @Composable (PaddingValues) -> Unit
) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(title, style = MaterialTheme.typography.titleLarge) },
        navigationIcon = {
          IconButton(onClick = onMenuClick) {
            Icon(Icons.Filled.Menu, contentDescription = "Abrir menú")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primary,
          titleContentColor = MaterialTheme.colorScheme.onPrimary,
          navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
          actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
      )
    },
    floatingActionButton = {
      if (floatingActionButton != null) floatingActionButton()
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      content(innerPadding)
    }
  }
}
