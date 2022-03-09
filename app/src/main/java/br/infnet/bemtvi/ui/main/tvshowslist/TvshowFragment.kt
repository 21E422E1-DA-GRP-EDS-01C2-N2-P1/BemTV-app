package br.infnet.bemtvi.ui.main.tvshowslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.infnet.bemtvi.data.model.Tvshow
import br.infnet.bemtvi.databinding.FragmentTvshowListBinding

/**
 * A fragment representing a list of Items.
 */
class TvshowFragment : Fragment() {

    private var columnCount = 2
    private lateinit var binding:FragmentTvshowListBinding
    private val viewModel: TvshowsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }
    private fun updateList(tvshows:MutableList<Tvshow>){
        with(binding.rvlistTvshows as RecyclerView) {
            adapter =MyTvshowRecyclerViewAdapter(tvshows)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        with(viewModel){
            tvshowsLiveData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    updateList(it)
                }

            })
            loadUserTvshows()
        }

        binding = FragmentTvshowListBinding.inflate(inflater,container,false)


            with(binding.rvlistTvshows as RecyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddtvshow.setOnClickListener {
            viewModel.addTvShow()
            TvshowListDialogFragment.newInstance(30).show(childFragmentManager, "criar tvshow")
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