package com.example.myappncr

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val cbMostrarPassword = findViewById<CheckBox>(R.id.cbMostrarPassword)
        val btnRegisterSubmit = findViewById<Button>(R.id.btnRegisterSubmit)

        // Mostrar u ocultar contraseña
        cbMostrarPassword.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                etPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }

            etPassword.setSelection(etPassword.text.length)
        }

        // Registrar usuario
        btnRegisterSubmit.setOnClickListener {

            val nombre = etNombre.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (password.length < 6) {

                Toast.makeText(
                    this,
                    "La contraseña debe tener mínimo 6 caracteres",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        // Guardar el nombre del usuario
                        val user = auth.currentUser

                        user?.updateProfile(
                            com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                .setDisplayName(nombre)
                                .build()
                        )

                        Toast.makeText(
                            this,
                            "Usuario registrado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this, SuccessActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {

                        Toast.makeText(
                            this,
                            task.exception?.message ?: "No fue posible registrar el usuario",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }

        }

    }
}