package com.example.sistemacitas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sistemacitas.ui.theme.SistemaCitasTheme
import com.google.firebase.firestore.FirebaseFirestore

class ActivityListActivity : ComponentActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = FirebaseFirestore.getInstance()

        setContent {
            SistemaCitasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ActivityListScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ActivityListScreen() {
        var activities by remember { mutableStateOf<List<Pair<String, Map<String, Any>>>>(emptyList()) }
        var isLoading by remember { mutableStateOf(true) }
        var showDeleteDialog by remember { mutableStateOf(false) }
        var activityToDelete by remember { mutableStateOf<Pair<String, String>?>(null) } // ID y nombre
        var isDeleting by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            loadActivities { loadedActivities ->
                activities = loadedActivities
                isLoading = false
            }
        }

        // Diálogo de confirmación para eliminar
        if (showDeleteDialog && activityToDelete != null) {
            AlertDialog(
                onDismissRequest = {
                    if (!isDeleting) {
                        showDeleteDialog = false
                    }
                },
                title = { Text("Eliminar Actividad") },
                text = {
                    Text("¿Estás seguro de que deseas eliminar la actividad \"${activityToDelete?.second}\"?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            isDeleting = true
                            activityToDelete?.let { (id, _) ->
                                deleteActivity(id) { success ->
                                    runOnUiThread {
                                        isDeleting = false
                                        if (success) {
                                            activities = activities.filter { it.first != id }
                                            Toast.makeText(
                                                this@ActivityListActivity,
                                                "Actividad eliminada exitosamente",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                this@ActivityListActivity,
                                                "Error al eliminar la actividad",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        showDeleteDialog = false
                                        activityToDelete = null
                                    }
                                }
                            }
                        },
                        enabled = !isDeleting
                    ) {
                        if (isDeleting) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp
                                )
                                Text("Eliminando...")
                            }
                        } else {
                            Text("Eliminar", color = MaterialTheme.colorScheme.error)
                        }
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            activityToDelete = null
                        },
                        enabled = !isDeleting
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Actividades") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        startActivity(Intent(this@ActivityListActivity, CreateActivityActivity::class.java))
                    }
                ) {
                    Text("+", style = MaterialTheme.typography.headlineMedium)
                }
            }
        ) { paddingValues ->
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (activities.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay actividades creadas")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(activities, key = { it.first }) { activity ->
                        ActivityCard(
                            activityId = activity.first,
                            activity = activity.second,
                            onDelete = {
                                activityToDelete = Pair(
                                    activity.first,
                                    activity.second["nombre"] as? String ?: "esta actividad"
                                )
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ActivityCard(
        activityId: String,
        activity: Map<String, Any>,
        onDelete: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Abrir detalle */ },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = activity["nombre"] as? String ?: "",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tipo: ${activity["tipo"] as? String ?: ""}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "📅 ${activity["fecha"] as? String ?: ""} - ${activity["hora"] as? String ?: ""}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "📍 ${activity["lugar"] as? String ?: ""}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    val descripcion = activity["descripcion"] as? String
                    if (!descripcion.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = descripcion,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(
                    onClick = onDelete,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar actividad"
                    )
                }
            }
        }
    }

    private fun loadActivities(callback: (List<Pair<String, Map<String, Any>>>) -> Unit) {
        Log.d("ActivityList", "Cargando actividades...")

        db.collection("activities")
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val activitiesList = documents.map { document ->
                    Pair(document.id, document.data)
                }
                Log.d("ActivityList", "✅ Actividades cargadas: ${activitiesList.size}")
                callback(activitiesList)
            }
            .addOnFailureListener { exception ->
                Log.e("ActivityList", "❌ Error al cargar: ${exception.message}", exception)
                callback(emptyList())
            }
    }

    private fun deleteActivity(activityId: String, callback: (Boolean) -> Unit) {
        Log.d("ActivityList", "Eliminando actividad: $activityId")

        db.collection("activities")
            .document(activityId)
            .delete()
            .addOnSuccessListener {
                Log.d("ActivityList", "✅ Actividad eliminada exitosamente")
                callback(true)
            }
            .addOnFailureListener { exception ->
                Log.e("ActivityList", "❌ Error al eliminar: ${exception.message}", exception)
                callback(false)
            }
    }

    override fun onResume() {
        super.onResume()
        // Recargar actividades cuando volvemos a esta pantalla
        setContent {
            SistemaCitasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ActivityListScreen()
                }
            }
        }
    }
}