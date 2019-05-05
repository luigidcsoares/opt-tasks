package br.com.opttasks.ui.home

import  androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import br.com.opttasks.R
import br.com.opttasks.databinding.HomeFragmentBinding
import br.com.opttasks.utils.Injector

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, Injector.provideHomeViewModelFactory())
                .get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return HomeFragmentBinding.inflate(inflater, container, false).run {
            // Add new opt simulation.
            addButton.setOnClickListener { findNavController().navigate(R.id.action_add) }
            root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}
