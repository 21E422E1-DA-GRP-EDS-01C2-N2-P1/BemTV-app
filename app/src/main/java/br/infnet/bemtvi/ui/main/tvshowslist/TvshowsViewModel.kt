package br.infnet.bemtvi.ui.main.tvshowslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.infnet.bemtvi.data.model.Tvshow

class TvshowsViewModel:ViewModel() {

    val tvshows = MutableLiveData<Tvshow>()
    fun addTvShow(){

    }
}