package com.example.tarea_3_segunda_parte

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryService {
    @GET("search.json") // Endpoint para buscar libros
    fun searchBooks(@Query("q") query: String): Call<BookResponse>
}