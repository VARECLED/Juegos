package com.example.juegos

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.juegos.databinding.ActivityAhorcadoBinding
import java.util.*
import kotlin.properties.Delegates


class ahorcado : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityAhorcadoBinding

                            //VARIABLES
    //La variable words nos contendra nuestras palabras y nuestras pistas
    private lateinit var words: Array<String>
    //Esta variable nos servira para obtener un numero aleatorio
    private lateinit var random: Random
    //Esta variable contendra la palabra que ya se uso
    private lateinit var prevWord : String
    //Esta variable contendra las letras de la palabra
    private lateinit var charViews: Array<TextView?>
    //Esta variable nos contendra las imagenes de nuestro monito
    private lateinit var parts: Array<ImageView?>
    //Creamos una variable que nos servira para saber el numero de palabras correctas
    private var numCorr by Delegates.notNull<Int>()

    //Esta variable nos servira para saber el numero de partes de nuestro monito
    private var partMon by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAhorcadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializamos parts y asignamos valores
        parts= arrayOfNulls(6)
        parts[0] = binding.head
        parts[1] = binding.body
        parts[2] = binding.armLeft
        parts[3] = binding.armRight
        parts[4] = binding.legLeft
        parts[5] = binding.legRight

        //Inizializamos variables
        random= Random()
        //Con este comando asignamos los valores de nuestro archivo contenido a words
        words=resources.getStringArray(R.array.words)

        //Llamado a función
        playGame()
    }

                                            //Creamos una funcion simple
    fun playGame() {
                                            //Asignacion de palabra, separación por letras y validación

        //La variable newWord es una lista que contendra la palabra y la pista
        var newWord = words[random.nextInt(words.size)].split(",")
        //Esta impresion en pantalla sirve para ver que realmente contiene la palabra y la pista
        //println(newWord[0] + " pista "+  newWord[1])
        //Imprimimos nuestra pista en nuestro TextView
        binding.txtHelp.setText(newWord[1])

        //Verificamos que la palabra no se repita cuando lo ejecutemos nuevamente
        prevWord = newWord[0].uppercase()

        while (newWord.equals(prevWord))
            newWord= words[random.nextInt(words.size)].split(",")

        //Separamos la palabra en letras para poder imprimir el numero de textView
        charViews = arrayOfNulls(prevWord.length)
        //Limpiamos los cuadros de texto
        binding.word.removeAllViews()

        //El for nos permitira modificar cada uno de los TextView
        for (i in 0..(prevWord.length-1)){
                                            //Configuración de el TextView que contendra las letras
            //Creamos nuestro textView
            charViews[i] = TextView(this).apply {
                //Le asignamos la primera letra de nuestra palabra
                text = prevWord[i].toString()
            }
            //Especificamos el ancho y alto de nuestro TextView
            charViews[i]!!.layoutParams =
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            //Centramos el texto
            charViews[i]!!.gravity = Gravity.CENTER
            //Agregamos el colore del texto
            charViews[i]!!.setTextColor(Color.WHITE)
            //Agregamos el fondo del texto
            charViews[i]!!.setBackgroundResource(R.drawable.letter_bg)

                                            //Asignacion de TextView a nuestro Layout donde se imprimira
            binding.word.addView(charViews[i])

        }

        //Creamos una instancia a nuestra clase
        var adapter = LetterAdapter()
        adapter.AdapterLetter(this)
        binding.keyboard.adapter = adapter

        //Esta funcion desaparecere las partes de nuestro monito
        for(i in 0 .. 5){
            parts[i]!!.visibility=View.INVISIBLE
        }
        // Inicializamos variables
        numCorr=0
        partMon=0
    }

    //Esta función se encargara de validar cuando se pulse una letra de nuestro teclado
    fun letterPressed(view: View){
        //Esta variable nos servira para validar si ya esta correcta toda la palabra
        var correct = false

        //Primero obtendremos la letra de nuestos botones en nuestro teclado
        val letter: String = (view as TextView).text.toString()
        val letterChar: Char = letter[0]
        //Desactivamos el boton una vez que se le de clic
        view.isEnabled=false
        //Creamos un for para ver si coincide la letra con nuestra palabra
        for (i in 0 .. (prevWord.length-1)){
            if (prevWord[i]==letterChar){
                correct=true
                numCorr++
                charViews[i]!!.setTextColor(Color.BLACK)
            }
        }
        //Esta comparacion nos servira para saber si ya adivino la palabra
        if (correct){
            if (numCorr==prevWord.length){
                //Este variable nos ayudara a hacer uso de un fragmento de diálogo
                val builder = AlertDialog.Builder(this)
                //Configuramos nuestro fragmento de diálogo
                //Agregamos un titulo
                builder.setTitle("Ganaste Crack")
                //Agregamos el cuerpo del mensage
                builder.setMessage("✨Felicidades✨ \nHas encontrado la respuesta 🤯 \n" +
                        "La palabra era $prevWord ")
                //Agregamos un boton de accion
                builder.setPositiveButton("Volver a jugar", { dialogInterface, id -> playGame() })
                //Agregamos un boton de accion
                builder.setNegativeButton("Salir", { dialogInterface, id -> finish() })
                //
                builder.show()
            }
        }//En caso de que el usuario se equivoque hara lo siguiente:
        else if (partMon<6) {
            //Imprimimos una parte del monito
            parts[partMon]!!.visibility=View.VISIBLE
            partMon++
        }else{
            //Este variable nos ayudara a hacer uso de un fragmento de diálogo
            val builder = AlertDialog.Builder(this)
            //Configuramos nuestro fragmento de diálogo
            //Agregamos un titulo
            builder.setTitle("Perdiste")
            //Agregamos el cuerpo del mensage
            builder.setMessage("Has perdido 😯 \nNo has encontrado la respuesta 😐 \n" +
                    "La palabra era $prevWord ")
            //Agregamos un boton de accion
            builder.setPositiveButton("Volver a jugar", { dialogInterface, id -> playGame() })
            //Agregamos un boton de accion
            builder.setNegativeButton("Salir", { dialogInterface, id -> finish() })
            //
            builder.show()
        }
    }
}