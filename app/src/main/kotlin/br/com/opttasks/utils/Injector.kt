package br.com.opttasks.utils

import br.com.opttasks.data.Repository
import br.com.opttasks.ui.add.AddStudentsViewModelFactory
import br.com.opttasks.ui.add.AddTasksViewModelFactory
import br.com.opttasks.ui.detail.DetailViewModelFactory
import br.com.opttasks.ui.home.HomeViewModelFactory
import br.com.opttasks.ui.login.LoginViewModelFactory

object Injector {

    fun provideLoginViewModelFactory() = LoginViewModelFactory()
    fun provideHomeViewModelFactory() = HomeViewModelFactory(Repository)
    fun provideDetailViewModelFactory() = DetailViewModelFactory(Repository)
    fun provideAddTasksViewModelFactory() = AddTasksViewModelFactory()
    fun provideAddStudentsViewModelFactory() = AddStudentsViewModelFactory(Repository)
}