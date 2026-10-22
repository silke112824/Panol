package com.example.panol.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.panol.ui.viewmodel.CatalogoViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import com.example.panol.ui.theme.PanolTheme

@Composable
fun CarritoScreen(
    viewModel: CatalogoViewModel,
    onNavigateToReserva: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val carrito by viewModel.carrito.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Carrito de Solicitudes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (carrito.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("El carrito está vacio", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(carrito) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(item.equipo.nombre, fontWeight = FontWeight.Bold)
                                Text("Cantidad solicitada: ${item.cantidad}")
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = onNavigateToReserva,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = carrito.isNotEmpty()
        ) {
            Text("Proceder a la Reserva", style = MaterialTheme.typography.titleMedium)
        }
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth().height(56.dp),

        ) {
            Text("Volver")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CarritoScreenPreview() {
    PanolTheme() {
        val previewViewModel = CatalogoViewModel()

        CarritoScreen (
            viewModel = previewViewModel,
            onNavigateToReserva = { },
            onNavigateBack = {}
        )
    }
}