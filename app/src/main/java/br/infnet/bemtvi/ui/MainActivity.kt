package br.infnet.bemtvi.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.infnet.bemtvi.R
import br.infnet.bemtvi.databinding.ActivityLoginBinding
import br.infnet.bemtvi.ui.login.LoginFragment
import br.infnet.bemtvi.ui.main.LoggedinFragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    val activityViewModel: MainActivityViewModel by viewModels()


    private fun goToPage(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainactivity_maincontainer, fragment)
            .commitNow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityViewModel.isUserLoggedIn.observe(this@MainActivity, Observer { loggedIn ->
            loggedIn?.let {
                if (loggedIn) goToPage(LoggedinFragment.newInstance())
                else goToPage(LoginFragment.newInstance())
            }
        })

    }
    private fun adsPubBind(){
        MobileAds.initialize(this)
        val adView = binding.adView
        val adBuild = AdRequest.Builder().build()

        adView.loadAd(adBuild)


        adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                println("onAdFailedToLoad::: $adError")
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }


}

