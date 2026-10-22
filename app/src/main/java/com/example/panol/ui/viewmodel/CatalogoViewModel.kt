package com.example.panol.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.panol.model.EquipoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ItemCarrito(val equipo: EquipoModel, var cantidad: Int)

class CatalogoViewModel : ViewModel() {

    private val listaInicial = listOf(
        EquipoModel("1", "Proyector Epson", "Epson PowerLite", "Proyector", 3, "Disponible"),
        EquipoModel("2", "Kit Robótica", "LEGO WeDo 2.0", "Robótica", 5, "Disponible"),
        EquipoModel("3", "Notebook HP", "HP ProBook", "Notebook", 0, "Prestado"),
        EquipoModel("4", "Placa ESP32", "Espressif", "Robótica", 10, "Disponible"),
        EquipoModel("5", "Alargador 5m", "Kendal", "Alargador", 2, "Disponible")
    )

    private val _equipos = MutableStateFlow(listaInicial)
    val equipos: StateFlow<List<EquipoModel>> = _equipos.asStateFlow()

    private val _filtroSeleccionado = MutableStateFlow("Todos")
    val filtroSeleccionado: StateFlow<String> = _filtroSeleccionado.asStateFlow()

    // Estado del Carrito
    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito: StateFlow<List<ItemCarrito>> = _carrito.asStateFlow()

    fun filtrarPorCategoria(categoria: String) {
        _filtroSeleccionado.value = categoria
        if (categoria == "Todos") {
            _equipos.value = _equipos.value
        }
    }

    fun agregarAlCarrito(equipoId: String) {
        val listaActualizada = _equipos.value.map { equipo ->
            if (equipo.id == equipoId && equipo.stockDisponible > 0) {
                // Descuenta 1 del stock visualmente
                equipo.copy(stockDisponible = equipo.stockDisponible - 1)
            } else {
                equipo
            }
        }
        _equipos.value = listaActualizada

        // Agregar al carrito
        val equipoAgregado = listaInicial.find { it.id == equipoId } ?: return
        val carritoActual = _carrito.value.toMutableList()
        val itemExistente = carritoActual.find { it.equipo.id == equipoId }

        if (itemExistente != null) {
            itemExistente.cantidad += 1
        } else {
            carritoActual.add(ItemCarrito(equipoAgregado, 1))
        }
        _carrito.value = carritoActual
    }
}