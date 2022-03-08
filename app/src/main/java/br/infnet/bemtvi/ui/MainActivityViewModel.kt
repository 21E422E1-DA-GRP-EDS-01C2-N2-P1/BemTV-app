package br.infnet.bemtvi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.infnet.bemtvi.data.model.MyFirestoreUser
import br.infnet.bemtvi.data.model.Tvshow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
    private fun createUserByAuth(mUser: FirebaseUser){

        val users_collection = Firebase.firestore.collection("users")
        //val tvShowSample = Tvshow(21, "friends", "#http")
        val newuser:MyFirestoreUser? = mUser.email?.let {
            MyFirestoreUser(mUser.uid,mUser.displayName, it,null) }

        val taskPush = newuser?.let { users_collection.add(it) }
        taskPush?.addOnSuccessListener {
            val ldf =  it
            val dd = ""
        }

    }
    fun verifyCurrentUser(){
        mUserLiveData?.value?.let{
            val db = Firebase.firestore
            val users_collection = db.collection("users")
            val test = users_collection.whereEqualTo("id",it.uid)
            test.addSnapshotListener { value, error ->
                val vw = value?.toMutableList()
                if (vw?.size?.equals(0) == true){
                    val d = 4
                    val dd = 4
                    createUserByAuth(it)
                }
            }
            //val taskCompare = users_collection.whereGreaterThan("friends",21)
            val taskGet = users_collection.get()
            taskGet.addOnSuccessListener { it ->
                it?.let {
                    val tvshows = it.toObjects(Tvshow::class.java)
                    println(tvshows)
                }

            }

        }

    }

}