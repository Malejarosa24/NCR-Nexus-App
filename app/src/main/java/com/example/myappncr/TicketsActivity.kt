package com.example.myappncr

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.myappncr.database.DBHelper

class TicketsActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets)

        dbHelper = DBHelper(this)

        val layoutSolicitudes =
            findViewById<LinearLayout>(R.id.layoutSolicitudes)

        val btnVolver =
            findViewById<Button>(R.id.btnVolver)

        val solicitudes =
            dbHelper.obtenerSolicitudes()

        if (solicitudes.isEmpty()) {

            val mensaje = TextView(this)

            mensaje.text = "No existen solicitudes registradas."

            mensaje.textSize = 16f

            mensaje.gravity = Gravity.CENTER

            mensaje.setPadding(20,60,20,60)

            layoutSolicitudes.addView(mensaje)

        } else {

            for (solicitud in solicitudes) {

                val card = CardView(this)

                val params = LinearLayout.LayoutParams(

                    LinearLayout.LayoutParams.MATCH_PARENT,

                    LinearLayout.LayoutParams.WRAP_CONTENT

                )

                params.setMargins(0,0,0,25)

                card.layoutParams = params

                card.radius = 20f

                card.cardElevation = 8f

                val contenido = LinearLayout(this)

                contenido.orientation = LinearLayout.VERTICAL

                contenido.setPadding(35,35,35,35)

                fun agregarTitulo(
                    titulo:String,
                    valor:String
                ){

                    val tv = TextView(this)

                    tv.text = "$titulo\n$valor"

                    tv.textSize = 16f

                    tv.setTextColor(Color.BLACK)

                    tv.setPadding(0,12,0,12)

                    contenido.addView(tv)

                }

                agregarTitulo(
                    "👤 Cliente",
                    solicitud.cliente
                )

                agregarTitulo(
                    "🏢 Empresa",
                    solicitud.empresa
                )

                agregarTitulo(
                    "📌 Asunto",
                    solicitud.asunto
                )

                agregarTitulo(
                    "📝 Descripción",
                    solicitud.descripcion
                )

                card.addView(contenido)

                layoutSolicitudes.addView(card)

            }

        }

        btnVolver.setOnClickListener {

            finish()

        }

    }

}