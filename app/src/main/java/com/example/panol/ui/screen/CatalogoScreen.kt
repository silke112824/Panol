package com.example.panol.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panol.model.EquipoModel
import com.example.panol.ui.viewmodel.CatalogoViewModel



@Composable
fun CatalogoScreen(viewModel: CatalogoViewModel = viewModel(),
                   onNavigateToCarrito: () -> Unit,) {
    val equipos by viewModel.equipos.collectAsState()
    val filtro by viewModel.filtroSeleccionado.collectAsState()
    val categorias = listOf("Todos", "Proyector", "Robotica", "Notebook", "Alargador")


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Catalogo Pañol",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Filtro
        LazyRow(modifier = Modifier.padding(bottom = 16.dp)) {
            items(categorias) { categoria ->
                FilterChip(
                    selected = filtro == categoria,
                    onClick = { viewModel.filtrarPorCategoria(categoria) },
                    label = { Text(categoria) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        // Lista de Equipos con Animacion
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn {
                items(equipos) { equipo ->
                    EquipoTarjeta(
                        equipo = equipo,
                        onAgregarClick = { id -> viewModel.agregarAlCarrito(id) }
                    )
                }
            }
        }
        //boton para ir al carrito
        Button(
            onClick = onNavigateToCarrito,
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(top = 8.dp)
        ) {
            Text("Ver Carrito y Reservar", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun EquipoTarjeta(equipo: EquipoModel, onAgregarClick: (String) -> Unit) {
    val haptic = LocalHapticFeedback.current

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = equipo.nombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(text = equipo.marcaModelo, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Stock Disponible: ${equipo.stockDisponible}",
                    color = if (equipo.stockDisponible > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onAgregarClick(equipo.id)
                },
                enabled = equipo.stockDisponible > 0
            ) {
                Text("Agregar al Carrito")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogoPreview() {
    CatalogoScreen(
        onNavigateToCarrito = {}
    )
}