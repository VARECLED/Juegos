package com.example.juegos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button

class LetterAdapter: BaseAdapter() {
    //Este array nos contendra nuestras letras del abecedario
    lateinit var letters: Array<String?>
    //Este comando nos servira para la vista de los botones
    lateinit var letterInf: LayoutInflater

    //Creamos un constructor
    fun AdapterLetter(context: Context){
        var c = 0;
        letters= arrayOfNulls(26)
        for (i in 'A'..'Z'){
            letters[c]= i.toString()
            c++
        }
        letterInf= LayoutInflater.from(context)
    }

    //Regresamos el tamaño de nuestra vista
    override fun getCount(): Int {
        return letters.size
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        TODO("Not yet implemented")
    }

    //Agregamos nuestra vista
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var btnLetter: Button;
        //Si no tenemos ninguna vista la vamos a crear y si existe lo agregaremos a nuestra variable
        if (p1 == null){
            btnLetter= letterInf.inflate(R.layout.letter, p2,false) as Button
        }else{
            btnLetter=p1 as Button
        }
        //Con esto crearemos cada uno de los botones con la letra del abecedario
        btnLetter.setText(letters[p0])
        return btnLetter
    }
}