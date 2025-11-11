package com.caririfest.admin.network

import android.util.Log
import com.caririfest.admin.model.Event
import com.google.firebase.firestore.FirebaseFirestore

fun createEvent(event: Event) {
    val db = FirebaseFirestore.getInstance()

    db.collection("event")
        .add(event)
        .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "Evento Criado com ID: $documentReference")
        }
        .addOnFailureListener { exception ->
            Log.w("Firestore", "Erro ao criar evento", exception)
        }

}