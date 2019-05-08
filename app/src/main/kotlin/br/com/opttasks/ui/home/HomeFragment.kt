package br.com.opttasks.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import br.com.opttasks.R
import br.com.opttasks.data.SimulationResume
import br.com.opttasks.databinding.HomeFragmentBinding
import br.com.opttasks.utils.Injector
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, Injector.provideHomeViewModelFactory())
                .get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get data from an asynchronous call to API service
        runBlocking { viewModel.resumes() }

        return HomeFragmentBinding.inflate(inflater, container, false).let {

            // Setting up adapter to show list of simulations (resume)
            it.adapter = HomeAdapter(mutableListOf(), object : HomeAdapter.OnItemClickListener {
                override fun onItemClick(id: String) {
                    findNavController().navigate(HomeFragmentDirections.actionDetail(id))
                }
            })

            // Set the observer to set the data
            viewModel.simulations.observe(this, Observer<List<SimulationResume>> {
                data -> it.adapter?.setData(data)
            })

            it.lifecycleOwner = this

            // Add new opt simulation.
            it.addButton.setOnClickListener { findNavController().navigate(R.id.action_add) }

            it.root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}
