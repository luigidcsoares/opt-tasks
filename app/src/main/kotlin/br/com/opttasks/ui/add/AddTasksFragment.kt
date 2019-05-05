package br.com.opttasks.ui.add

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.opttasks.R

import br.com.opttasks.data.task.Task
import br.com.opttasks.databinding.AddTasksFragmentBinding
import br.com.opttasks.utils.Injector

class AddTasksFragment : Fragment() {

    companion object {
        fun newInstance() = AddTasksFragment()
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, Injector.provideAddTasksViewModelFactory())
            .get(AddTasksViewModel::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return AddTasksFragmentBinding.inflate(inflater, container, false).run {
            tasks = arrayListOf()

            taskAddButton.setOnClickListener {
                if (name.isNullOrEmpty() || level.isNullOrEmpty()) {
                    taskNameLayout.error = if (name.isNullOrEmpty()) getString(R.string.not_empty) else null
                    taskValueLayout.error = if (level.isNullOrEmpty()) getString(R.string.not_empty) else null
                } else {
                    tasks?.add(Task(name, level!!.toInt()))
                    invalidateAll()
                    taskNameLayout.error = null
                    taskValueLayout.error = null
                }
            }

            navigateStudentsButton.setOnClickListener {
                val arg = arrayOfNulls<Task>(tasks?.size!!)
                (tasks as ArrayList).toArray(arg)

                findNavController().navigate(AddTasksFragmentDirections.actionNavigateStudents(
                    arg as Array<Task>
                ))
            }

            root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
