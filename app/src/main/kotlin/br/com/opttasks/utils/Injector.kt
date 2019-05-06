package br.com.opttasks.utils

import br.com.opttasks.data.Repository
import br.com.opttasks.ui.add.AddStudentsViewModelFactory
import br.com.opttasks.ui.add.AddTasksViewModelFactory
import br.com.opttasks.ui.home.HomeViewModelFactory
import br.com.opttasks.ui.login.LoginViewModelFactory

object Injector {

    fun provideLoginViewModelFactory() = LoginViewModelFactory()
    fun provideHomeViewModelFactory() = HomeViewModelFactory()
    fun provideAddTasksViewModelFactory() = AddTasksViewModelFactory()
    fun provideAddStudentsViewModelFactory() = AddStudentsViewModelFactory(Repository)
}