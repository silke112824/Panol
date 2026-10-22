package com.example.panol.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panol.data.database.AppDatabase
import com.example.panol.ui.viewmodel.ReservaViewModel
import com.example.panol.utils.mostrarNotificacionReserva

@Composable
fun ReservaScreen(
    onReservaExitosa: () -> Unit
) {
    val database = AppDatabase.getDatabase(LocalContext.current)
    // Instancia del ViewModel con la factory para proveer el DAO
    val viewModel: ReservaViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ReservaViewModel(database.panolDao()) as T
            }
        }
    )
    val sala by viewModel.sala.collectAsState()
    val fecha by viewModel.fecha.collectAsState()
    val horaInicio by viewModel.horaInicio.collectAsState()
    val horaDevolucion by viewModel.horaDevolucion.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()
    val esValido by viewModel.esValido.collectAsState()
    val context = LocalContext.current

    var mostrarDialogo by remember { mutableStateOf(false) }
    var mostrarCalendario by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Recurso nativo del calendario
    if (mostrarCalendario) {
        DatePickerDialog(
            onDismissRequest = { mostrarCalendario = false },
            confirmButton = {
                TextButton(onClick = {
                    mostrarCalendario = false
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formato = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
                        viewModel.onFechaChange(formato.format(java.util.Date(millis)))
                    }
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarCalendario = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Nueva Reserva",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Campo para la sala
        OutlinedTextField(
            value = sala,
            onValueChange = { viewModel.onSalaChange(it) },
            label = { Text("Sala o espacio de destino") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            singleLine = true
        )

        // Campo para la fecha con el DatePicker nativo
        OutlinedTextField(
            value = fecha,
            onValueChange = { },
            label = { Text("Fecha de Uso") },
            placeholder = { Text("Toca para seleccionar") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clickable { mostrarCalendario = true },
            enabled = false, // Bloquea escritura manual para forzar el uso del calendario
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        // Campo para la hora de inicio
        OutlinedTextField(
            value = horaInicio,
            onValueChange = { viewModel.onHoraInicioChange(it) },
            label = { Text("Hora de Inicio (HH:mm)") },
            placeholder = { Text("Ej: 10:00") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true
        )

        // Campo para la hora de devolución
        OutlinedTextField(
            value = horaDevolucion,
            onValueChange = { viewModel.onHoraDevolucionChange(it) },
            label = { Text("Hora de Devolución Estimada (HH:mm)") },
            placeholder = { Text("Ej: 12:30") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            singleLine = true
        )

        // Mensaje de error para validación de las 3 horas
        AnimatedVisibility(visible = mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { mostrarDialogo = true },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = esValido
        ) {
            Text("Confirmar Reserva", style = MaterialTheme.typography.titleMedium)
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                title = { Text("Confirmación") },
                text = { Text("¿Deseas enviar esta solicitud al pañol?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.enviarReserva()
                        mostrarDialogo = false

                        //recurso nativo 2 notificacion---------------------------------------------------------
                        mostrarNotificacionReserva(context, sala)

                        onReservaExitosa()
                    }) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewReservaScreen() {
    MaterialTheme {
        ReservaScreen(
            onReservaExitosa = {}
        )
    }
}