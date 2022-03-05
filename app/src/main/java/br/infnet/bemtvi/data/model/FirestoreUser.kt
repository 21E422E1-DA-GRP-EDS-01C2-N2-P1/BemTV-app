package br.infnet.bemtvi.data.model

import com.google.firebase.firestore.DocumentId

data class FirestoreUser(
    @DocumentId
    val idUser:Long? = null,
    val name:String? = null
)
