package com.example.myappncr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class ActivitySelection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)

        val cardAtencion = findViewById<CardView>(R.id.cardAtencion)
        val cardVentas = findViewById<CardView>(R.id.cardVentas)
        val cardSoporte = findViewById<CardView>(R.id.cardSoporte)

        val btnVolver = findViewById<Button>(R.id.btnVolver)

        val usuario = FirebaseAuth.getInstance().currentUser

        val nombreUsuario = usuario?.displayName ?: "Usuario"

        val hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        val saludo = when {

            hora < 12 -> "Buenos días"

            hora < 18 -> "Buenas tardes"

            else -> "Buenas noches"

        }

        tvGreeting.text =
            "$saludo, $nombreUsuario 👋\n\nNos alegra verte nuevamente."

        cardAtencion.setOnClickListener {

            startActivity(
                Intent(this, AtencionActivity::class.java)
            )

        }

        cardVentas.setOnClickListener {

            Toast.makeText(
                this,
                "Esta área estará disponible próximamente.",
                Toast.LENGTH_SHORT
            ).show()

        }

        cardSoporte.setOnClickListener {

            Toast.makeText(
                this,
                "Esta área estará disponible próximamente.",
                Toast.LENGTH_SHORT
            ).show()

        }

        btnVolver.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            finish()

        }

    }

}