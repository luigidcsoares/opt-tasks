package br.com.opttasks.ui.add

import androidx.lifecycle.ViewModel
import br.com.opttasks.data.Repository
import br.com.opttasks.data.Simulation

class AddStudentsViewModel(private val repository: Repository)
    : ViewModel() {

    suspend fun save(simulation: Simulation) { repository.save(simulation) }
}
