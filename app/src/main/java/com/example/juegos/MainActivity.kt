package com.example.juegos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.juegos.databinding.ActivityAhorcadoBinding
import com.example.juegos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdivina.setOnClickListener(){
            val objIntent: Intent = Intent (applicationContext, adivNum::class.java)
            startActivity(objIntent)
        }

        binding.btnAhorcado.setOnClickListener(){
            val objIntent: Intent = Intent (applicationContext, ahorcado::class.java)
            startActivity(objIntent)
        }
    }
}