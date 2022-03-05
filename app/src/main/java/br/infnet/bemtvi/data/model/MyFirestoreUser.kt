package br.infnet.bemtvi.data.model

import com.google.firebase.firestore.DocumentId

data class MyFirestoreUser(
    @DocumentId
    val idUser:String? = null,
    val name:String? = null,
    val email:String,
    val tvshows:List<Tvshow>?=null
)
