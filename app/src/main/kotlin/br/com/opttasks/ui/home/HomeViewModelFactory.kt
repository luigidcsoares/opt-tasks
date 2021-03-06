package br.com.opttasks.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.opttasks.data.Repository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            HomeViewModel(repository) as T
}