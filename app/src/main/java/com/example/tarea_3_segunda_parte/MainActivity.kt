package com.example.tarea_3_segunda_parte

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var textViewMessage: TextView
    private lateinit var buttonGetMessage: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular el TextView y el Button
        textViewMessage = findViewById(R.id.textViewMessage)
        buttonGetMessage = findViewById(R.id.buttonGetMessage)

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://openlibrary.org/") // URL base de la API
            .addConverterFactory(GsonConverterFactory.create()) // Conversor de JSON a objetos Kotlin
            .build()

        val openLibraryService = retrofit.create(OpenLibraryService::class.java)

        // Configurar el clic del botón
        buttonGetMessage.setOnClickListener {
            // Realizar la petición HTTP
            openLibraryService.searchBooks("the lord of the rings").enqueue(object : Callback<BookResponse> {
                override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                    if (response.isSuccessful) {
                        // Procesar la respuesta JSON
                        val books = response.body()?.docs
                        if (books != null && books.isNotEmpty()) {
                            val firstBook = books[0]
                            val message = "Título: ${firstBook.title}\nAutor: ${firstBook.author_name?.joinToString()}"
                            textViewMessage.text = message
                        } else {
                            textViewMessage.text = "No se encontraron libros."
                        }
                    } else {
                        textViewMessage.text = "Error: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                    textViewMessage.text = "Error: ${t.message}"
                }
            })
        }
    }
}