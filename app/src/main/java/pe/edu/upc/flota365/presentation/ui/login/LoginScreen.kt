package pe.edu.upc.flota365.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import pe.edu.upc.flota365.R
import pe.edu.upc.flota365.ui.theme.FlotaTheme

@Composable
fun OnboardingScreen(onStart: () -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.surface)
      .padding(24.dp),
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Column {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(320.dp)
          .background(
            brush = Brush.linearGradient(
              listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.primaryContainer
              )
            ),
            shape = RoundedCornerShape(24.dp)
          ),
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = painterResource(id = R.drawable.logo365),
          contentDescription = null,
          modifier = Modifier.size(270.dp),
          contentScale = ContentScale.Fit
        )
      }
      Spacer(modifier = Modifier.height(24.dp))
      Text(
        text = "Flota365",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
      )
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = "El equilibrio móvil más allá de una opción",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
    FlotaPrimaryButton(
      text = "Comenzar",
      onClick = onStart,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
fun LoginWelcomeScreen(
  onLogin: () -> Unit,
  onCreateAccount: () -> Unit,
  onBack: () -> Unit
) {
  ScaffoldContainer(onBack = onBack, title = "Inicio Sesión") {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
          text = "Bienvenido a Flota365",
          style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
          textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = "Gestiona tu flota con herramientas inteligentes y un entorno pensado para conductores y gestores.",
          textAlign = TextAlign.Center,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Column(modifier = Modifier.fillMaxWidth()) {
        FlotaPrimaryButton(
          text = "Iniciar sesión",
          onClick = onLogin,
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        FilledTonalButton(
          onClick = onCreateAccount,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
          )
        ) {
          Text("Crear cuenta")
        }
      }
    }
  }
}

@Composable
fun LoginFormScreen(
  onBack: () -> Unit,
  onContinue: () -> Unit,
  onGoToSubscription: () -> Unit
) {
  ScaffoldContainer(onBack = onBack, title = "Acceso a tu cuenta") {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        Text(
          text = "Ingrese sus credenciales",
          style = MaterialTheme.typography.titleMedium,
          modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
          value = email,
          onValueChange = { email = it },
          label = { Text("Correo electrónico") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
          )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
          value = password,
          onValueChange = { password = it },
          label = { Text("Contraseña") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
          )
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
          onClick = onGoToSubscription,
          colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
          )
        ) {
          Text("¿Aún no tienes suscripción? Conoce nuestros planes")
        }
      }

      FlotaPrimaryButton(
        text = "Continuar",
        onClick = onContinue,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

@Composable
fun RoleSelectionScreen(
  onBack: () -> Unit,
  onDriverSelected: () -> Unit,
  onManagerSelected: () -> Unit
) {
  ScaffoldContainer(onBack = onBack, title = "Selecciona tu rol") {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "¿Quién eres?",
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(vertical = 24.dp)
      )

      RoleCard(
        title = "Conductor",
        description = "Accede a tus rutas, incidencias y novedades.",
        onClick = onDriverSelected
      )
      Spacer(modifier = Modifier.height(16.dp))
      RoleCard(
        title = "Gestor",
        description = "Administra flotas, pagos y reportes en tiempo real.",
        onClick = onManagerSelected
      )
    }
  }
}

@Composable
private fun RoleCard(
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

@Composable
fun ScaffoldContainer(
  onBack: () -> Unit,
  title: String,
  content: @Composable () -> Unit
) {
  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = onBack) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = title,
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
      }
      Spacer(modifier = Modifier.height(8.dp))
      content()
    }
  }
}

@Composable
fun FlotaPrimaryButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Button(
    onClick = onClick,
    modifier = modifier,
    colors = ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary
    ),
    shape = RoundedCornerShape(18.dp)
  ) {
    Text(text = text, style = MaterialTheme.typography.titleMedium)
  }
}



@Preview(showBackground = true, showSystemUi = true, name = "Onboarding")
@Composable
fun PreviewOnboardingScreen() {
  FlotaTheme {
    OnboardingScreen(onStart = {})
  }
}

@Preview(showBackground = true, showSystemUi = true, name = "Login Welcome")
@Composable
fun PreviewLoginWelcomeScreen() {
  FlotaTheme {
    LoginWelcomeScreen(
      onLogin = {},
      onCreateAccount = {},
      onBack = {}
    )
  }
}

@Preview(showBackground = true, showSystemUi = true, name = "Login Form Themed")
@Composable
fun PreviewLoginFormScreenThemed() {
  FlotaTheme {
    LoginFormScreen(
      onBack = {},
      onContinue = {},
      onGoToSubscription = {}
    )
  }
}

@Preview(showBackground = true, showSystemUi = true, name = "Role Selection")
@Composable
fun PreviewRoleSelectionScreen() {
  FlotaTheme {
    RoleSelectionScreen(
      onBack = {},
      onDriverSelected = {},
      onManagerSelected = {}
    )
  }
}
