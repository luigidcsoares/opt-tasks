package br.com.opttasks.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.opttasks.data.Repository

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val repository: Repository)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        DetailViewModel(repository) as T

}
