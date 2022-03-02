package br.infnet.bemtvi.services

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import br.infnet.bemtvi.data.model.Tvshow
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class MyEncryptionService(private val context: Context, private val filesDir: File) {
    val mymasterkey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    val testfile = File(filesDir, "Anyfile.txt")
    val testFileEncrypted = EncryptedFile.Builder(
        context, testfile, mymasterkey,
        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
    ).build()


    fun encriptOld(whatToWrite: String): String? {
        val keyToOpenOrCloseSharedPref = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        val sharedPreferencs = EncryptedSharedPreferences.create(
            "passencrip.sp",
            keyToOpenOrCloseSharedPref,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        with(sharedPreferencs.edit()) {
            this.putString("bemtvpass", whatToWrite)
            this.commit()

        }
        val bemtvpass = sharedPreferencs.getString("bemtvpass", "")
        return bemtvpass
    }

    fun encriptBuilder(whatToWrite: String): String {
        if (testfile.exists()) {
            testfile.delete()
        }
        val outputToFile = testFileEncrypted.openFileOutput()
        outputToFile.write(whatToWrite.toByteArray())
        outputToFile.close()
        return testfile.absolutePath


    }

    fun decriptBuilder(whatToWrite: String): String {
        val inputStream = testfile.inputStream()
        inputStream.close()
        return testfile.absolutePath


    }
}

class MyFirebaseLibrary() {
    fun firestoreRead() {
        val db_storage = Firebase.storage
        val taskLs = db_storage.reference.listAll()
        taskLs.addOnSuccessListener {
        }

    }

    fun firestorePost() {
        val users_collection = Firebase.firestore.collection("users")
        val tvShowSample = Tvshow(21, "friends", "#http")

        /*val taskPush = users_collection.add(hashMapOf(
            "friends" to tvShowSample,
            "none" to "danone",
        ))*/
        val taskOverrider = users_collection.document("Vb1shcfnWqNxl2kOKntN")
            .set(
                hashMapOf(
                    "friends" to tvShowSample,
                    "none" to "danone",
                )
            )
        taskOverrider.addOnSuccessListener {
        }
    }

    fun firestoreGetter() {
        val users_collection = Firebase.firestore.collection("users")
        //val taskCompare = users_collection.whereGreaterThan("friends",21)
        val taskGet = users_collection.get()
        taskGet.addOnSuccessListener { it ->
            it?.let {
                val tvshows = it.toObjects(Tvshow::class.java)
            }

        }

    }

    fun firestoreNestedGetter(): Task<QuerySnapshot> {
        val comedyTvshows_collection = Firebase.firestore.collection("users")
            .document("tvshows").collection("comedy")
        //val taskCompare = users_collection.whereGreaterThan("friends",21)
        val taskGet = comedyTvshows_collection.get()
        return taskGet
    }


}
class MyMediaStore(private val applicationContext:Context){
    fun contentProviderGetAllImages(){
        val imagesLocationUser = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val resolverQuery = applicationContext.contentResolver
            .query(imagesLocationUser,arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME),
                null,null,null)
        resolverQuery.use {
                cursor->
            val idColumn = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            println(cursor.count)
            while(cursor.moveToNext()){
                val n = cursor.getString(nameColumn)
                val cUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,cursor.getLong(idColumn))
                println("$n")
            }
        }

    }
}