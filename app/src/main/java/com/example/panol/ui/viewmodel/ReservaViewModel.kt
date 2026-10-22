package com.example.panol.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ReservaViewModel : ViewModel() {
    val sala = MutableStateFlow("")
    val fechaHoraInicio = MutableStateFlow("")
    val horaFin = MutableStateFlow("")

    private val _mensaje = MutableStateFlow("")
    val mensaje = _mensaje.asStateFlow()

    fun intentarReservar() {
        if (sala.value.isBlank() || fechaHoraInicio.value.isBlank() || horaFin.value.isBlank()) {
            _mensaje.value = "Llena todos los datos, porfa."
            return
        }

        try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            val inicio = LocalDateTime.parse(fechaHoraInicio.value, formatter)
            val horas = ChronoUnit.HOURS.between(LocalDateTime.now(), inicio)

            if (horas < 3) {
                _mensaje.value = "Error: Mínimo 3 horas de anticipación requeridas." // Regla crítica del caso
            } else {
                _mensaje.value = "¡Reserva aprobada y enviada!"
                // Aquí llamas a tu DAO para guardar en Room
            }
        } catch (e: Exception) {
            _mensaje.value = "Formato de fecha malo. Usa: dd/MM/yyyy HH:mm"
        }
    }
}