package br.infnet.bemtvi.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.setupWithNavController
import br.infnet.bemtvi.R
import br.infnet.bemtvi.databinding.LoggedinFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoggedinFragment : Fragment() {

    companion object {
        fun newInstance() = LoggedinFragment()
    }

    private lateinit var viewModel: LoggedinViewModel
    private lateinit var binding:LoggedinFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LoggedinViewModel::class.java)
        binding = LoggedinFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigation(view)
    }
    private fun setupBottomNavigation(view:View){
        val bottomBtns: BottomNavigationView = binding.bottomNavBtns
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.loggedmain_navhost) as NavHostFragment
        val navController = findNavController(navHostFragment)
        bottomBtns.setupWithNavController(navController)

    }

}