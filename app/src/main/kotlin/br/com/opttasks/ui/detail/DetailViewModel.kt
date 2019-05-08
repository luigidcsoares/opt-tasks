package br.com.opttasks.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.opttasks.data.Repository
import br.com.opttasks.data.Simulation


class DetailViewModel(private val repository: Repository)
    : ViewModel() {

    val simulation by lazy { MutableLiveData<Simulation>() }

    suspend fun detail(id: String) { simulation.value = repository.detail(id).value }
}
