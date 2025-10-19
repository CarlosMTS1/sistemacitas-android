package com.example.sistemacitas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devteamsolo.sistemacitas.ui.theme.SistemaCitasTheme
import com.google.firebase.auth.FirebaseAuth

class CalendarActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Verificar que el usuario esté autenticado
        if (auth.currentUser == null) {
            // Si no hay usuario, volver al login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContent {
            SistemaCitasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalendarScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CalendarScreen() {
        val currentUser = auth.currentUser

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("SistemaCitas - Calendario") },
                    actions = {
                        IconButton(onClick = {
                            // Cerrar sesión
                            auth.signOut()
                            startActivity(Intent(this@CalendarActivity, LoginActivity::class.java))
                            finish()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Cerrar Sesión"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icono de calendario (emoji)
                Text(
                    text = "📅",
                    style = MaterialTheme.typography.displayLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Título
                Text(
                    text = "Calendario de Actividades",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Usuario actual
                Text(
                    text = "Bienvenido: ${currentUser?.email}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Mensaje informativo
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "🚀 Pantalla Principal",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Esta es la pantalla principal de la aplicación. " +
                                    "Aquí se mostrará el calendario con las citas programadas.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "✅ Funcionalidad completada para N3",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de ejemplo (para futuras funciones)
                OutlinedButton(
                    onClick = {
                        // Aquí irá la funcionalidad futura
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver Actividades (Próximamente)")
                }
            }
        }
    }
}