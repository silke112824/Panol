package com.example.panol.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panol.data.local.dao.PanolDao
import com.example.panol.data.local.entity.ReservaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class ReservaViewModel(private val dao: PanolDao) : ViewModel() {

    private val _sala = MutableStateFlow("")
    val sala: StateFlow<String> = _sala.asStateFlow()

    private val _fecha = MutableStateFlow("")
    val fecha: StateFlow<String> = _fecha.asStateFlow()

    private val _horaInicio = MutableStateFlow("")
    val horaInicio: StateFlow<String> = _horaInicio.asStateFlow()

    private val _horaDevolucion = MutableStateFlow("")
    val horaDevolucion: StateFlow<String> = _horaDevolucion.asStateFlow()

    private val _mensajeError = MutableStateFlow("")
    val mensajeError: StateFlow<String> = _mensajeError.asStateFlow()

    private val _esValido = MutableStateFlow(false)
    val esValido: StateFlow<Boolean> = _esValido.asStateFlow()

    fun onSalaChange(nuevaSala: String) {
        _sala.value = nuevaSala
        validarFormulario()
    }

    // Nuevas funciones separadas
    fun onFechaChange(nuevaFecha: String) {
        _fecha.value = nuevaFecha
        validarFormulario()
    }

    fun onHoraInicioChange(nuevaHora: String) {
        _horaInicio.value = nuevaHora
        validarFormulario()
    }

    fun onHoraDevolucionChange(nuevaHora: String) {
        _horaDevolucion.value = nuevaHora
        validarFormulario()
    }

    private fun validarFormulario() {
        if (_sala.value.isBlank() || _fecha.value.isBlank() || _horaInicio.value.isBlank() || _horaDevolucion.value.isBlank()) {
            _esValido.value = false
            _mensajeError.value = "Todos los campos son obligatorios"
            return
        }

        try {
            // Unimos la fecha y la hora para armar el formato que valida la regla de las 3 horas
            val fechaHoraUnida = "${_fecha.value} ${_horaInicio.value}"
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            val fechaInicio = LocalDateTime.parse(fechaHoraUnida, formatter)
            val ahora = LocalDateTime.now()

            // Regla de anticipación crítica: mínimo 3 horas[cite: 4]
            val horasDiferencia = ChronoUnit.HOURS.between(ahora, fechaInicio)

            if (horasDiferencia < 3) {
                _esValido.value = false
                _mensajeError.value = "La reserva debe tener al menos 3 horas de anticipación"
            } else {
                _esValido.value = true
                _mensajeError.value = ""
            }
        } catch (e: Exception) {
            _esValido.value = false
            _mensajeError.value = "Formato de hora inválido. Usa HH:mm"
        }
    }

    fun enviarReserva() {
        // Ejecutamos la inserción en un hilo secundario para no bloquear la UI
        viewModelScope.launch(Dispatchers.IO) {
            val nuevaReserva = ReservaEntity(
                salaEspacio = _sala.value,
                fechaUso = _fecha.value,
                horaInicio = _horaInicio.value,
                horaDevolucion = _horaDevolucion.value,
                estado = "Pendiente" // Estado inicial requerido[cite: 3, 4]
            )
            dao.insertarReserva(nuevaReserva)
        }
    }
}