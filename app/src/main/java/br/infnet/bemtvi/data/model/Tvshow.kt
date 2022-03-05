package br.infnet.bemtvi.data.model

import com.google.firebase.firestore.DocumentId
import java.io.Serializable

data class Tvshow(
    @DocumentId
    val idTvshow:Long? = null,
    val name:String? = null,
    val urlThumbnail:String? = null
)
