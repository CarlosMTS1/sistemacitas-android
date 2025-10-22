package com.example.sistemacitas

data class Activity(
    val id: String = "",
    val nombre: String = "",
    val tipo: String = "",
    val fecha: String = "",
    val hora: String = "",
    val lugar: String = "",
    val cupo: Int = 0,
    val descripcion: String = "",
    val createdBy: String = "",
    val createdAt: Long = System.currentTimeMillis()
)