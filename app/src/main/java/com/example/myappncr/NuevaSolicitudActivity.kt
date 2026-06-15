package com.example.myappncr

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myappncr.database.DBHelper
import com.example.myappncr.database.Solicitud

class NuevaSolicitudActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_solicitud)

        dbHelper = DBHelper(this)

        val etCliente = findViewById<EditText>(R.id.etCliente)
        val etEmpresa = findViewById<EditText>(R.id.etEmpresa)
        val etAsunto = findViewById<EditText>(R.id.etAsunto)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)

        val btnGuardarSolicitud =
            findViewById<Button>(R.id.btnGuardarSolicitud)

        val btnVolver =
            findViewById<Button>(R.id.btnVolver)

        btnGuardarSolicitud.setOnClickListener {

            val cliente = etCliente.text.toString().trim()
            val empresa = etEmpresa.text.toString().trim()
            val asunto = etAsunto.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()

            if (
                cliente.isEmpty() ||
                empresa.isEmpty() ||
                asunto.isEmpty() ||
                descripcion.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val solicitud = Solicitud(

                cliente = cliente,

                empresa = empresa,

                asunto = asunto,

                descripcion = descripcion

            )

            val guardado =
                dbHelper.insertarSolicitud(solicitud)

            if (guardado) {

                Toast.makeText(
                    this,
                    "Solicitud registrada correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                etCliente.text.clear()
                etEmpresa.text.clear()
                etAsunto.text.clear()
                etDescripcion.text.clear()

            } else {

                Toast.makeText(
                    this,
                    "No fue posible guardar la solicitud",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }

        btnVolver.setOnClickListener {

            finish()

        }

    }

}