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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.panol.model.EquipoModel
import com.example.panol.ui.viewmodel.CatalogoViewModel



@Composable
fun CatalogoScreen(viewModel: CatalogoViewModel = viewModel(), onNavigateToReserva: () -> Unit) {
    val equipos by viewModel.equipos.collectAsState()
    val filtro by viewModel.filtroSeleccionado.collectAsState()
    val categorias = listOf("Todos", "Herramientas", "Corte", "Medicion")

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
                    EquipoTarjeta (equipo, onNavigateToReserva)
                }
            }
        }
    }
}

@Composable
fun EquipoTarjeta(equipo: EquipoModel, onReservarClick: () -> Unit) {
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
                    text = "Stock Disponible: ${equipo.stockDisponible}"
                )
            }
            Button(
                onClick = onReservarClick,
                enabled = equipo.stockDisponible > 0
            ) {
                Text("Reservar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogoPreview() {
    CatalogoScreen(
        onNavigateToReserva = {}
    )
}