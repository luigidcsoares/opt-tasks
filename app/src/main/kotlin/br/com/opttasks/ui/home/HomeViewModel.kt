package br.com.opttasks.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.opttasks.data.Repository
import br.com.opttasks.data.SimulationResume

class HomeViewModel(private val repository: Repository)
    : ViewModel() {

    val simulations by lazy { MutableLiveData<List<SimulationResume>>() }

    suspend fun resumes() { simulations.value = repository.resumes().value }
}
