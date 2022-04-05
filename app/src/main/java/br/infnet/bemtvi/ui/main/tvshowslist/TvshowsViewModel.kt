package br.infnet.bemtvi.ui.main.tvshowslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.bemtvi.data.model.Tvshow
import br.infnet.bemtvi.services.SearchImageService
import br.infnet.bemtvi.services.SearchImageServiceListener
import br.infnet.bemtvi.services.SearchedImageURL
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TvshowsViewModelFactory(
    private val myFirestoreUserId:String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvshowsViewModel::class.java)) {
            return TvshowsViewModel(myFirestoreUserId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
class TvshowsViewModel(myFirestoreUserId:String) : ViewModel(), SearchImageServiceListener {

    val users_collection = Firebase.firestore.collection("users")
    val allUserTvshowsRef = users_collection.document(myFirestoreUserId)
        .collection("tvshows")

    val searchImageService = SearchImageService()
    init {
        searchImageService.setListener(this)
    }


    val tvshowsLiveData = MutableLiveData<MutableList<Tvshow>>()
    val getTvshows: MutableList<Tvshow>? get() = tvshowsLiveData.value

    val searchedImg = MutableLiveData<String>().apply { value = "" }

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

    fun addTvShow(tvshowName: String) {

        val createTvShowModel = Tvshow(null,
            tvshowName, "${searchedImg.value}")

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
        addTvshow.addOnFailureListener { err->
            println(err.message)
            println(err.cause)
        }

    }

    override fun whenGetImageFinished(tvshow: SearchedImageURL?) {
        tvshow?.big?.let {
            searchedImg.postValue(it)
        }
    }

    override fun whenHttpError(erro: String) {
        //TODO("Not yet implemented")
    }

    fun searchTvShowImage(tvshowName: String) {
        searchImageService.getImage(tvshowName+" tv show")
    }


}