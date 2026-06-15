package com.example.myappncr

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)

        val btnResetSubmit =
            findViewById<Button>(R.id.btnResetSubmit)

        val tvCancel =
            findViewById<TextView>(R.id.tvCancel)

        btnResetSubmit.setOnClickListener {

            val email = etEmail.text.toString().trim()

            if (email.isEmpty()) {

                Toast.makeText(
                    this,
                    "Ingrese su correo electrónico",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Se envió un enlace para restablecer la contraseña.",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()

                    } else {

                        Toast.makeText(
                            this,
                            "No fue posible enviar el correo.",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }

        }

        tvCancel.setOnClickListener {

            finish()

        }

    }

}