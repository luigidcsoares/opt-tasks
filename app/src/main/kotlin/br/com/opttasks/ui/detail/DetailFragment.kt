package br.com.opttasks.ui.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import br.com.opttasks.data.Simulation
import br.com.opttasks.databinding.DetailFragmentBinding
import br.com.opttasks.utils.Injector
import kotlinx.coroutines.runBlocking

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel by lazy {
        ViewModelProviders.of(this, Injector.provideDetailViewModelFactory())
            .get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get data from an asynchronous call to API service
        runBlocking { viewModel.detail(args.id) }

        return DetailFragmentBinding.inflate(inflater, container, false).let {
            it.lifecycleOwner = this

            // Set the observer to set the data
            viewModel.simulation.observe(this, Observer<Simulation> { data ->
                it.simulation = data
                android.util.Log.d("TESTE", it.simulation?.allocations.toString())
            })

           it.root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
