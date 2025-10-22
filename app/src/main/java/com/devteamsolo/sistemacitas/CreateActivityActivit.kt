package com.example.sistemacitas

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sistemacitas.ui.theme.SistemaCitasTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class CreateActivityActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setContent {
            SistemaCitasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateActivityScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateActivityScreen() {
        val context = LocalContext.current

        var nombre by remember { mutableStateOf("") }
        var tipo by remember { mutableStateOf("Taller") }
        var fecha by remember { mutableStateOf("") }
        var hora by remember { mutableStateOf("") }
        var lugar by remember { mutableStateOf("Sala 1 - Sede Alerce") }
        var cupo by remember { mutableStateOf("") }
        var descripcion by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(false) }

        var expandedTipo by remember { mutableStateOf(false) }
        var expandedLugar by remember { mutableStateOf(false) }
        var expandedHora by remember { mutableStateOf(false) }

        val tiposActividad = listOf("Taller", "Capacitación", "Charla", "Atención", "Operativo")
        val lugares = listOf(
            "Sala 1 - Sede Alerce",
            "Sala 2 - Sede Alerce",
            "Sala 3 - Sede Alerce"
        )
        val horas = listOf(
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
            "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00"
        )

        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                fecha = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crear Actividad") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre de la Actividad") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                )

                ExposedDropdownMenuBox(
                    expanded = expandedTipo,
                    onExpandedChange = { expandedTipo = !expandedTipo }
                ) {
                    OutlinedTextField(
                        value = tipo,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo de Actividad") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipo) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        enabled = !isLoading
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTipo,
                        onDismissRequest = { expandedTipo = false }
                    ) {
                        tiposActividad.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    tipo = item
                                    expandedTipo = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = fecha,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Fecha") },
                    placeholder = { Text("Seleccione una fecha") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() },
                    enabled = !isLoading,
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Text("📅")
                        }
                    }
                )

                ExposedDropdownMenuBox(
                    expanded = expandedHora,
                    onExpandedChange = { expandedHora = !expandedHora }
                ) {
                    OutlinedTextField(
                        value = hora,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Hora") },
                        placeholder = { Text("Seleccione una hora") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedHora) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        enabled = !isLoading
                    )
                    ExposedDropdownMenu(
                        expanded = expandedHora,
                        onDismissRequest = { expandedHora = false }
                    ) {
                        horas.forEach { time ->
                            DropdownMenuItem(
                                text = { Text(time) },
                                onClick = {
                                    hora = time
                                    expandedHora = false
                                }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = expandedLugar,
                    onExpandedChange = { expandedLugar = !expandedLugar }
                ) {
                    OutlinedTextField(
                        value = lugar,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Lugar") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLugar) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        enabled = !isLoading
                    )
                    ExposedDropdownMenu(
                        expanded = expandedLugar,
                        onDismissRequest = { expandedLugar = false }
                    ) {
                        lugares.forEach { sala ->
                            DropdownMenuItem(
                                text = { Text(sala) },
                                onClick = {
                                    lugar = sala
                                    expandedLugar = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = cupo,
                    onValueChange = {
                        if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                            cupo = it
                        }
                    },
                    label = { Text("Cupo") },
                    placeholder = { Text("30") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    maxLines = 3,
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        Log.d("CreateActivity", "Botón presionado")
                        if (nombre.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                            Toast.makeText(this@CreateActivityActivity, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("CreateActivity", "Validando disponibilidad...")
                            isLoading = true

                            checkAvailability(fecha, hora, lugar) { isAvailable, conflictingActivity ->
                                if (isAvailable) {
                                    Log.d("CreateActivity", "Horario disponible, guardando...")
                                    saveActivity(nombre, tipo, fecha, hora, lugar, cupo.toIntOrNull() ?: 0, descripcion) { success ->
                                        runOnUiThread {
                                            isLoading = false
                                            if (success) {
                                                Toast.makeText(this@CreateActivityActivity, "Actividad creada exitosamente", Toast.LENGTH_SHORT).show()
                                                finish()
                                            } else {
                                                Toast.makeText(this@CreateActivityActivity, "Error al crear actividad", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                } else {
                                    runOnUiThread {
                                        isLoading = false
                                        val message = if (conflictingActivity != null) {
                                            "⚠️ Horario ya ocupado por \"$conflictingActivity\". Por favor, elija un horario con al menos 2 horas de diferencia."
                                        } else {
                                            "⚠️ Horario ya ocupado. Por favor, elija otro horario con al menos 2 horas de diferencia."
                                        }
                                        Toast.makeText(this@CreateActivityActivity, message, Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Crear Actividad")
                    }
                }

                TextButton(
                    onClick = { finish() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text("Cancelar")
                }
            }
        }
    }

    private fun checkAvailability(
        fecha: String,
        hora: String,
        lugar: String,
        callback: (Boolean, String?) -> Unit
    ) {
        Log.d("CreateActivity", "Verificando disponibilidad para: $fecha $hora en $lugar")

        db.collection("activities")
            .whereEqualTo("fecha", fecha)
            .whereEqualTo("lugar", lugar)
            .get()
            .addOnSuccessListener { documents ->
                val selectedTime = parseTime(hora)
                var hasConflict = false
                var conflictingActivityName: String? = null

                for (document in documents) {
                    val existingHora = document.getString("hora") ?: continue
                    val existingTime = parseTime(existingHora)

                    // Calcular diferencia en minutos
                    val diffMinutes = kotlin.math.abs(selectedTime - existingTime)

                    Log.d("CreateActivity", "Comparando: $hora ($selectedTime min) vs $existingHora ($existingTime min) = $diffMinutes min de diferencia")

                    // Si la diferencia es menor a 120 minutos (2 horas), hay conflicto
                    if (diffMinutes < 120) {
                        hasConflict = true
                        conflictingActivityName = document.getString("nombre")
                        Log.d("CreateActivity", "⚠️ Conflicto detectado con: $conflictingActivityName")
                        break
                    }
                }

                if (hasConflict) {
                    Log.d("CreateActivity", "❌ Horario NO disponible")
                    callback(false, conflictingActivityName)
                } else {
                    Log.d("CreateActivity", "✅ Horario disponible")
                    callback(true, null)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("CreateActivity", "❌ Error al verificar disponibilidad: ${exception.message}", exception)
                // En caso de error, permitir crear (fail-safe)
                callback(true, null)
            }
    }

    private fun parseTime(hora: String): Int {
        // Convierte "HH:MM" a minutos totales desde medianoche
        val parts = hora.split(":")
        if (parts.size != 2) return 0

        val hours = parts[0].toIntOrNull() ?: 0
        val minutes = parts[1].toIntOrNull() ?: 0

        return hours * 60 + minutes
    }

    private fun saveActivity(
        nombre: String,
        tipo: String,
        fecha: String,
        hora: String,
        lugar: String,
        cupo: Int,
        descripcion: String,
        callback: (Boolean) -> Unit
    ) {
        Log.d("CreateActivity", "saveActivity llamado")
        Log.d("CreateActivity", "Usuario: ${auth.currentUser?.email}")

        val activity = hashMapOf(
            "nombre" to nombre,
            "tipo" to tipo,
            "fecha" to fecha,
            "hora" to hora,
            "lugar" to lugar,
            "cupo" to cupo,
            "descripcion" to descripcion,
            "createdBy" to (auth.currentUser?.email ?: ""),
            "createdAt" to System.currentTimeMillis()
        )

        Log.d("CreateActivity", "Intentando guardar en Firestore...")

        db.collection("activities")
            .add(activity)
            .addOnSuccessListener { documentReference ->
                Log.d("CreateActivity", "✅ Éxito! ID: ${documentReference.id}")
                callback(true)
            }
            .addOnFailureListener { exception ->
                Log.e("CreateActivity", "❌ Error: ${exception.message}", exception)
                callback(false)
            }
    }
}