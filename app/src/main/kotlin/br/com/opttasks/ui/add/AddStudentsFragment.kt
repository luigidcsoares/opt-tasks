package br.com.opttasks.ui.add

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import br.com.opttasks.R
import br.com.opttasks.data.task.Task
import br.com.opttasks.databinding.AddStudentsFragmentBinding
import br.com.opttasks.utils.Injector

class AddStudentsFragment : Fragment() {

    companion object {
        fun newInstance() = AddStudentsFragment()
    }

    private val args by navArgs<AddStudentsFragmentArgs>()

    private val viewModel by lazy {
        ViewModelProviders.of(this, Injector.provideAddStudentsViewModelFactory())
            .get(AddTasksViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return AddStudentsFragmentBinding.inflate(inflater, container, false).run {
            studentSkills = arrayListOf()

            for (task in args.tasks) {
                studentSkills?.add(listOf(task.name, ""))
            }

            root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
