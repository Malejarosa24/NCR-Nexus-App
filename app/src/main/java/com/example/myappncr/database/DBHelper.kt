package com.example.myappncr.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "MyAppNCR.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_USERS = "usuarios"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "nombre"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"

        // TABLA SOLICITUDES
        const val TABLE_SOLICITUDES = "solicitudes"

        const val COLUMN_CLIENTE = "cliente"
        const val COLUMN_EMPRESA = "empresa"
        const val COLUMN_ASUNTO = "asunto"
        const val COLUMN_DESCRIPCION = "descripcion"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTable = """
            CREATE TABLE $TABLE_USERS(
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_EMAIL TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()

        db.execSQL(createTable)

        val createTableSolicitudes = """
    CREATE TABLE $TABLE_SOLICITUDES(
        $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_CLIENTE TEXT,
        $COLUMN_EMPRESA TEXT,
        $COLUMN_ASUNTO TEXT,
        $COLUMN_DESCRIPCION TEXT
    )
""".trimIndent()

        db.execSQL(createTableSolicitudes)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")

        db.execSQL("DROP TABLE IF EXISTS $TABLE_SOLICITUDES")

        onCreate(db)

    }

    // Registrar usuario
    fun insertarUsuario(user: User): Boolean {

        val db = writableDatabase

        val values = ContentValues()

        values.put(COLUMN_NAME, user.nombre)
        values.put(COLUMN_EMAIL, user.email)
        values.put(COLUMN_PASSWORD, user.password)

        val resultado = db.insert(TABLE_USERS, null, values)

        db.close()

        return resultado != -1L
    }

    // Verificar si un correo ya existe
    fun existeCorreo(email: String): Boolean {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ?",
            arrayOf(email)
        )

        val existe = cursor.count > 0

        cursor.close()
        db.close()

        return existe
    }

    // Validar login (la dejamos por ahora)
    fun login(email: String, password: String): Boolean {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password)
        )

        val existe = cursor.count > 0

        cursor.close()
        db.close()

        return existe
    }
    fun insertarSolicitud(solicitud: Solicitud): Boolean {

        val db = writableDatabase

        val values = ContentValues()

        values.put(COLUMN_CLIENTE, solicitud.cliente)
        values.put(COLUMN_EMPRESA, solicitud.empresa)
        values.put(COLUMN_ASUNTO, solicitud.asunto)
        values.put(COLUMN_DESCRIPCION, solicitud.descripcion)

        val resultado = db.insert(
            TABLE_SOLICITUDES,
            null,
            values
        )

        db.close()

        return resultado != -1L
    }

    fun obtenerSolicitudes(): ArrayList<Solicitud> {

        val lista = ArrayList<Solicitud>()

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_SOLICITUDES",
            null
        )

        if (cursor.moveToFirst()) {

            do {

                val solicitud = Solicitud(

                    id = cursor.getInt(0),

                    cliente = cursor.getString(1),

                    empresa = cursor.getString(2),

                    asunto = cursor.getString(3),

                    descripcion = cursor.getString(4)

                )

                lista.add(solicitud)

            } while (cursor.moveToNext())

        }

        cursor.close()
        db.close()

        return lista
    }

    // Obtener el usuario completo
    fun obtenerUsuario(
        email: String,
        password: String
    ): User? {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password)
        )

        var usuario: User? = null

        if (cursor.moveToFirst()) {

            usuario = User(

                id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COLUMN_ID)
                ),

                nombre = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NAME)
                ),

                email = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_EMAIL)
                ),

                password = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)
                )

            )
        }

        cursor.close()
        db.close()

        return usuario
    }

    // Actualizar contraseña
    fun actualizarPassword(
        email: String,
        nuevaPassword: String
    ): Boolean {

        val db = writableDatabase

        val values = ContentValues()

        values.put(COLUMN_PASSWORD, nuevaPassword)

        val filas = db.update(
            TABLE_USERS,
            values,
            "$COLUMN_EMAIL = ?",
            arrayOf(email)
        )

        db.close()

        return filas > 0

    }
}
