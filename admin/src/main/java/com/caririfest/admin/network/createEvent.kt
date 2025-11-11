package com.caririfest.admin.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.caririfest.admin.model.Event
import com.google.firebase.firestore.FirebaseFirestore

fun createEvent(event: Event, context: Context) {
    val db = FirebaseFirestore.getInstance()

    db.collection("event")
        .add(event)
        .addOnSuccessListener { documentReference ->
            Toast.makeText(context, "Evento Criado com Sucesso", Toast.LENGTH_LONG).show()
            Log.d("Firestore", "Evento Criado com ID: $documentReference")
        }
        .addOnFailureListener { exception ->
            Log.w("Firestore", "Erro ao criar evento", exception)
        }

}