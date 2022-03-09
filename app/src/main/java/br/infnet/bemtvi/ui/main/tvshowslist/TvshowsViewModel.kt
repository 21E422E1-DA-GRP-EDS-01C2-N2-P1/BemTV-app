package br.infnet.bemtvi.ui.main.tvshowslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.infnet.bemtvi.data.model.Tvshow
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TvshowsViewModel : ViewModel() {

    val tvshowsLiveData = MutableLiveData<MutableList<Tvshow>>()

    val userIdBySafeArgs = "LgJKHMDiuheJ39IP3cNBfjcwXyl1"
    val users_collection = Firebase.firestore.collection("users")
    val allUserTvshowsRef = users_collection.document(userIdBySafeArgs)
        .collection("tvshows")


    val getTvshows: MutableList<Tvshow>? get() = tvshowsLiveData.value

    fun loadUserTvshows() {
        allUserTvshowsRef.get().addOnSuccessListener {
            val placeholderTvshowsList = mutableListOf<Tvshow>()
            for (doc in it.documents.iterator()) {
                val tvshow = doc.toObject(Tvshow::class.java)
                tvshow?.let { placeholderTvshowsList.add(tvshow) }

            }
            tvshowsLiveData.postValue(placeholderTvshowsList)
        }
    }

    fun addTvShow() {

        val createTvShowModel = Tvshow(null, "bab", "#http")

        val addTvshow = allUserTvshowsRef.add(createTvShowModel)
        addTvshow.addOnSuccessListener { doc ->
            val tvshows = getTvshows
            doc.get().addOnSuccessListener { tvshow ->
                val objTvshow = tvshow.toObject(Tvshow::class.java)
                if (objTvshow != null && tvshows != null) {
                    tvshows.add(objTvshow)
                    tvshowsLiveData.postValue(tvshows!!)
                }

            }
        }

    }


}