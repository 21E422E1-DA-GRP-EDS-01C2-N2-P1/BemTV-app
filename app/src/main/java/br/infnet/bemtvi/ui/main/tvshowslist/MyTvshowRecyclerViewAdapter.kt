package br.infnet.bemtvi.ui.main.tvshowslist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import br.infnet.bemtvi.data.model.Tvshow
import br.infnet.bemtvi.databinding.FragmentTvshowBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyTvshowRecyclerViewAdapter(
    private val values: List<Tvshow>,
    private val clickCallback:(posi:Int)->Unit
) : RecyclerView.Adapter<MyTvshowRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTvshowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvshowName.text = item.name
        holder.container.setOnClickListener {
            clickCallback(position)
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvshowName: TextView = binding.tvshowitemName
        val container: LinearLayout = binding.tvshowitemContainer

        override fun toString(): String {
            return super.toString() + " todo'"
        }
    }

}