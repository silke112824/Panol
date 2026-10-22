package com.example.panol.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipos")
data class EquipoEntity(
    @PrimaryKey
    val id: String,
    val nombre: String,
    val marcaModelo: String,
    val categoria: String,
    val stockDisponible: Int,
    val estado: String
)
