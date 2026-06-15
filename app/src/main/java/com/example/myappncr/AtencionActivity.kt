package com.example.myappncr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class AtencionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atencion)

        val cardNuevaSolicitud =
            findViewById<CardView>(R.id.cardNuevaSolicitud)

        val cardTickets =
            findViewById<CardView>(R.id.cardTickets)

        val cardBuscarCliente =
            findViewById<CardView>(R.id.cardBuscarCliente)

        val cardEquipos =
            findViewById<CardView>(R.id.cardEquipos)

        val btnVolver =
            findViewById<Button>(R.id.btnVolver)

        // Nueva Solicitud
        cardNuevaSolicitud.setOnClickListener {

            val intent = Intent(
                this,
                NuevaSolicitudActivity::class.java
            )

            startActivity(intent)

        }

        // Tickets Activos
        cardTickets.setOnClickListener {

            val intent = Intent(
                this,
                TicketsActivity::class.java
            )

            startActivity(intent)

        }

        // Buscar Cliente
        cardBuscarCliente.setOnClickListener {

            Toast.makeText(
                this,
                "Módulo en desarrollo",
                Toast.LENGTH_SHORT
            ).show()

        }

        // Equipos del Cliente
        cardEquipos.setOnClickListener {

            Toast.makeText(
                this,
                "Módulo en desarrollo",
                Toast.LENGTH_SHORT
            ).show()

        }

        btnVolver.setOnClickListener {

            finish()

        }

    }

}