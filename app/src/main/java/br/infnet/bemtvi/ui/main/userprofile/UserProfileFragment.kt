package br.infnet.bemtvi.ui.main.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import br.infnet.bemtvi.databinding.FragmentUserProfileBinding
import br.infnet.bemtvi.ui.MainActivityViewModel

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    val activityViewModel: MainActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener { activityViewModel.logout() }

    }
}