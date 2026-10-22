package com.example.panol.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.panol.model.EquipoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CatalogoViewModel : ViewModel() {

    // Lista ficticia depurada para pruebas académicas

    private val listaEquipos = listOf(
        EquipoModel("1", "Alicate", "Stanley", "Herramientas", 10),
        EquipoModel("2", "Destornillador", "Truper", "Herramientas", 10),
        EquipoModel("3", "Martillo", "Surtek", "Herramientas", 10),
        EquipoModel("4", "Sierra", "Bosch", "Corte", 10),
        EquipoModel("5", "Huincha de medir", "Stanley", "Medicion", 10)
    )

    private val _equipos = MutableStateFlow(listaEquipos)
    val equipos: StateFlow<List<EquipoModel>> = _equipos.asStateFlow()

    private val _filtroSeleccionado = MutableStateFlow("Todos")
    val filtroSeleccionado: StateFlow<String> = _filtroSeleccionado.asStateFlow()

    fun filtrarPorCategoria(categoria: String) {
        _filtroSeleccionado.value = categoria
        if (categoria == "Todos") {
            _equipos.value = listaEquipos
        } else {
            _equipos.value = listaEquipos.filter { it.categoria == categoria }
        }
    }
}