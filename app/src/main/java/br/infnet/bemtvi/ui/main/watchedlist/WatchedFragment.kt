package br.infnet.bemtvi.ui.main.watchedlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import br.infnet.bemtvi.R
import br.infnet.bemtvi.ui.MainActivityViewModel
import br.infnet.bemtvi.ui.main.tvshowslist.TvshowsViewModel
import br.infnet.bemtvi.ui.main.tvshowslist.TvshowsViewModelFactory
import br.infnet.bemtvi.ui.main.watchedlist.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class WatchedFragment : Fragment() {

    private var columnCount = 2

    private val mainActivityViewModel:
            MainActivityViewModel by activityViewModels()
    private lateinit var viewModel: TvshowsViewModel
    private lateinit var factory: TvshowsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_watched_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyWatchedRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            WatchedFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}