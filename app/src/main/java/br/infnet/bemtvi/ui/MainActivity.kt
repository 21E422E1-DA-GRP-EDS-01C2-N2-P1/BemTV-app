package br.infnet.bemtvi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.infnet.bemtvi.R
import br.infnet.bemtvi.databinding.ActivityLoginBinding

import br.infnet.bemtvi.data.model.Tvshow
import br.infnet.bemtvi.services.MyFirebaseLibrary
import br.infnet.bemtvi.ui.login.LoginFragment
import br.infnet.bemtvi.ui.login.SignInDialog
import br.infnet.bemtvi.ui.login.SignUpDialog
import br.infnet.bemtvi.ui.main.LoggedinFragment
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    val activityViewModel: MainActivityViewModel by viewModels()


    private fun goToPage(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainactivity_maincontainer, fragment)
            .commitNow()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityViewModel.isLoggedIn.observe(this@MainActivity, Observer {loggedIn->
            loggedIn?.let{
                if(loggedIn) goToPage(LoggedinFragment.newInstance())
                else  goToPage(LoginFragment.newInstance())
            }
        })
    }



}

