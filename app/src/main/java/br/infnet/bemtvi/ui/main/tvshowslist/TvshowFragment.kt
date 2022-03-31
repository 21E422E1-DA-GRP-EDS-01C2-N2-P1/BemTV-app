package br.infnet.bemtvi.ui.main.tvshowslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import br.infnet.bemtvi.data.model.Tvshow
import br.infnet.bemtvi.databinding.FragmentTvshowListBinding
import br.infnet.bemtvi.ui.MainActivityViewModel
import com.google.firebase.auth.FirebaseUser

/**
 * A fragment representing a list of Items.
 */
class TvshowFragment : Fragment() {

    private var columnCount = 2
    private lateinit var binding:FragmentTvshowListBinding


    /*private val viewModel: TvshowsViewModel by viewModels(){
        //TvshowsViewModelFactory()
    }*/
    private val mainActivityViewModel:
            MainActivityViewModel by activityViewModels()
    private lateinit var viewModel: TvshowsViewModel
    private lateinit var factory: TvshowsViewModelFactory
    private var myFirestoreUserId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }
    private fun updateList(tvshows:MutableList<Tvshow>){
        with(binding.rvlistTvshows as RecyclerView) {
            val callbackClick = { position:Int->
                TvshowListDialogFragment.newInstance(position)
                    .show(childFragmentManager, "editar tvshow")

            }
            adapter =MyTvshowRecyclerViewAdapter(tvshows,callbackClick)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myFirestoreUserId = mainActivityViewModel.mUserLiveData
            .value?.let { it.uid }?: ""

        factory = TvshowsViewModelFactory(myFirestoreUserId)
        viewModel = ViewModelProvider(this,factory)
            .get(TvshowsViewModel::class.java)



        binding = FragmentTvshowListBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel){
            tvshowsLiveData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    updateList(it)
                }

            })
            loadUserTvshows()
        }
        with(binding.rvlistTvshows as RecyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }

        binding.fabAddtvshow.setOnClickListener {
            TvshowListDialogFragment.newInstance(30)
                .show(childFragmentManager, "criar tvshow")
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TvshowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}