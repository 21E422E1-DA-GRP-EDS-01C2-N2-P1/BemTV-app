package br.infnet.bemtvi.ui.main.tvshowslist

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.infnet.bemtvi.R
import br.infnet.bemtvi.databinding.FragmentTvshowBottomdialogBinding
import br.infnet.bemtvi.ui.login.afterTextChanged
import com.squareup.picasso.Picasso


/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    TvshowListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class TvshowListDialogFragment : BottomSheetDialogFragment() {
// TODO: Customize parameter argument names
 val ARG_ITEM_COUNT = "item_count"

    private val viewModel  by viewModels<TvshowsViewModel>({
        requireParentFragment()
    })
    private var _binding: FragmentTvshowBottomdialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTvshowBottomdialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.searchedImg.observe(viewLifecycleOwner, Observer {bigThumbnalUrl->
            val isLinkOk = bigThumbnalUrl.contains("http")
            if(isLinkOk){
                val dimen = R.id.bottomsheet_imgview
                Picasso.get().load(bigThumbnalUrl)
                    .fit()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.bottomsheetImgview)
            }

        })
        binding.bottomsheetTxtTvshowName.afterTextChanged {
            viewModel.searchTvShowImage(it)
        }
        binding.bottomsheetBtnSavetvshow.setOnClickListener {
            val tvshowName = "${binding
                .bottomsheetTxtTvshowName.text.toString()}"
            viewModel.addTvShow(tvshowName)
            dismiss()
        }
    }


    companion object {

        // TODO: Customize parameters
        fun newInstance(itemCount: Int): TvshowListDialogFragment =
            TvshowListDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}