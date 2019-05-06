package br.com.opttasks.ui.add

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.opttasks.R
import br.com.opttasks.data.Task

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
                if (!taskName.isNullOrEmpty() && !level.isNullOrEmpty()) {
                    tasks?.add(Task(taskName, level!!.toInt()))
                    invalidateAll()
                }
            }

            navigateStudentsButton.setOnClickListener {
                val size = (tasks as ArrayList).size

                if (size > 0) {
                    val arg = arrayOfNulls<Task>(size)
                    (tasks as ArrayList).toArray(arg)

                    findNavController().navigate(
                        AddTasksFragmentDirections.actionNavigateStudents(
                            arg as Array<Task>
                        )
                    )
                } else Toast
                    .makeText(context, getString(R.string.at_least_one), Toast.LENGTH_LONG)
                    .show()
            }

            root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
