package com.example.juegos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.juegos.databinding.ActivityAdivNumBinding
import java.util.Random
import kotlin.properties.Delegates

class adivNum : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityAdivNumBinding
    //Variable random que nos elegira nuestro numero impar
    private lateinit var random: Random
    private var searchNum by Delegates.notNull<Int>()
    var numObt by Delegates.notNull<Int>()

    //Variable que nos contendra nuestro numero a adivinar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAdivNumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Variable random que nos elegira nuestro numero impar
        random = Random()
        //Variable que nos contendra nuestro numero a adivinar
        searchNum=(1..50).random()

        //Le asignamos la funcion a el boton adivinar
        binding.btnAdivinar.setOnClickListener {
            Adinivar()
        }
    }
    fun Adinivar(){
        numObt= Integer.parseInt(binding.edtAdivinar.text.toString())
        if (numObt==searchNum){
            //Este variable nos ayudara a hacer uso de un fragmento de diálogo
            val builder = AlertDialog.Builder(this)
            //Configuramos nuestro fragmento de diálogo
            //Agregamos un titulo
            builder.setTitle("Ganaste Crack")
            //Agregamos el cuerpo del mensage
            builder.setMessage("✨Felicidades✨ \nHas encontrado el numero 🤯 \n" +
                    "El número era $searchNum")
            //Agregamos un boton de accion
            builder.setPositiveButton("Volver a jugar") { dialogInterface, id -> adivNum() }
            //Agregamos un boton de accion
            builder.setNegativeButton("Salir") { dialogInterface, id -> finish() }
            //
            builder.show()
            binding.edtAdivinar.setText("")
            binding.txtPresionar.setText("Seguimos?")
            searchNum=(1..50).random()
        }else if (searchNum<numObt){
            binding.txtPresionar.setText("Uffff! Tranquilo 😲, te exediste \nEl numero es mas bajo")
            binding.edtAdivinar.setText("")
        }else{
            binding.txtPresionar.setText("Uffff! Echale tantitas ganas 🙄 \nEl número es mas alto")
            binding.edtAdivinar.setText("")
        }
    }
}