package br.infnet.bemtvi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import br.infnet.bemtvi.databinding.ActivityLoginBinding

import br.infnet.bemtvi.data.model.Tvshow
import br.infnet.bemtvi.services.MyFirebaseLibrary
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    fun snackAlert(text: String) {

        val up_layout: View = binding.upLayout as View
        Snackbar.make(up_layout, "  ${text}", Snackbar.LENGTH_LONG + 4242).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

    }


    fun firestoreNestedGetterListener() {
        val mfb = MyFirebaseLibrary()
        mfb.firestoreNestedGetter().addOnSuccessListener {
            it?.let {
                val tvshows = it.toObjects(Tvshow::class.java)
                snackAlert("$tvshows")
            }
        }
    }
}

