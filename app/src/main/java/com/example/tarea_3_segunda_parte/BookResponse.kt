package com.example.tarea_3_segunda_parte

data class BookResponse(
    val docs: List<Book>
)

data class Book(
    val title: String,
    val author_name: List<String>?,
    val cover_i: Long?
)