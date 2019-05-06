package br.com.opttasks.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.opttasks.data.Repository

@Suppress("UNCHECKED_CAST")
class AddStudentsViewModelFactory(private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        AddStudentsViewModel(repository) as T
}
