package br.infnet.bemtvi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivityViewModel: ViewModel() {
    var mAuth: FirebaseAuth?= null

    val mUserLiveData = MutableLiveData<FirebaseUser?>().apply { value=null }

    fun getLiveDataWhenOtherLiveDataChange(
        changedLiveData:MutableLiveData<FirebaseUser?>,functionWhichReturnsNewValue:(FirebaseUser?)->Boolean): LiveData<Boolean> {
        return Transformations.map(changedLiveData,functionWhichReturnsNewValue)
    }
    val isUserLoggedIn:LiveData<Boolean> = getLiveDataWhenOtherLiveDataChange(mUserLiveData,{user->
        user!=null
    })

    init{
        mAuth = FirebaseAuth.getInstance()
        mAuth?.let {
            mUserLiveData.postValue(it.currentUser)
        }
    }
    fun logout(){
        mAuth?.signOut()
        mUserLiveData.postValue(null)
    }

}