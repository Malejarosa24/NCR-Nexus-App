package com.example.myappncr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class activity_login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val btnLoginSubmit = findViewById<Button>(R.id.btnLoginSubmit)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        btnLoginSubmit.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(
                            this,
                            "¡Bienvenido a NCR Nexus!",
                            Toast.LENGTH_SHORT
                        ).show()

                        val usuario = auth.currentUser

                        val intent = Intent(
                            this,
                            ActivitySelection::class.java
                        )

                        intent.putExtra(
                            "nombreUsuario",
                            usuario?.displayName ?: "Usuario"
                        )

                        startActivity(intent)

                        finish()

                    } else {

                        Toast.makeText(
                            this,
                            "Correo o contraseña incorrectos",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }

        }

        tvForgotPassword.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ForgotPasswordActivity::class.java
                )
            )

        }

    }

}