package br.com.opttasks.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.opttasks.data.SimulationResume
import br.com.opttasks.databinding.SimListItemBinding

class HomeAdapter(
    private val simulations: MutableList<SimulationResume>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<HomeAdapter.SimViewHolder>() {

    class SimViewHolder(private val binding: SimListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(simulation: SimulationResume, onItemClickListener: OnItemClickListener) {
            binding.simulation = simulation
            binding.executePendingBindings()

            itemView.setOnClickListener { onItemClickListener.onItemClick(simulation.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SimViewHolder(SimListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))

    override fun getItemCount() = simulations.size

    override fun onBindViewHolder(holder: SimViewHolder, position: Int) {
        holder.bind(simulations[position], onItemClickListener)
    }

    fun setData(list: List<SimulationResume>) {
        simulations.apply {
            clear()
            addAll(list)
        }

        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }
}