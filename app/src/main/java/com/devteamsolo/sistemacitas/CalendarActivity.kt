package com.example.sistemacitas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sistemacitas.ui.theme.SistemaCitasTheme
import com.google.firebase.auth.FirebaseAuth

class CalendarActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
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

    @Composable
    fun CalendarScreen() {
        val currentUser = auth.currentUser

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "📅",
                style = MaterialTheme.typography.displayLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Calendario de Actividades",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Bienvenido: ${currentUser?.email}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Ver Actividades
            Button(
                onClick = {
                    startActivity(Intent(this@CalendarActivity, ActivityListActivity::class.java))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("📋 Ver Actividades")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Crear Actividad
            Button(
                onClick = {
                    startActivity(Intent(this@CalendarActivity, CreateActivityActivity::class.java))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("➕ Crear Actividad")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Cerrar Sesión
            OutlinedButton(
                onClick = {
                    auth.signOut()
                    startActivity(Intent(this@CalendarActivity, LoginActivity::class.java))
                    finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar Sesión")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "✅ Sistema completo funcionando",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}