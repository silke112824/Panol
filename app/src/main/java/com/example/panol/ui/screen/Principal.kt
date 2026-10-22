package com.example.panol.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Principal(
    onNavigateToCatalogo: () -> Unit,
    onNavigateToReserva: () -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido al Pañol",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button (
            onClick = onNavigateToCatalogo,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 16.dp)
        ) {
            Text("Ir al Catálogo", style = MaterialTheme.typography.titleMedium)
        }

        Button (
            onClick = onNavigateToReserva,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 16.dp)
        ) {
            Text("Realizar Reserva", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrincipalPreview() {
    Principal(
        onNavigateToCatalogo = {},
        onNavigateToReserva = {}
    )
}
