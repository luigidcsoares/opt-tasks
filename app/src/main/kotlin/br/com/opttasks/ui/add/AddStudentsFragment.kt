package br.com.opttasks.ui.add

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import br.com.opttasks.R
import br.com.opttasks.data.Simulation
import br.com.opttasks.data.Skill
import br.com.opttasks.data.Student
import br.com.opttasks.databinding.AddStudentsFragmentBinding
import br.com.opttasks.utils.Injector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddStudentsFragment : Fragment() {

    companion object {
        fun newInstance() = AddStudentsFragment()
    }

    private val args by navArgs<AddStudentsFragmentArgs>()

    private val viewModel by lazy {
        ViewModelProviders.of(this, Injector.provideAddStudentsViewModelFactory())
            .get(AddStudentsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return AddStudentsFragmentBinding.inflate(inflater, container, false).run {
            studentInput = arrayListOf()
            students = arrayListOf()

            for (task in args.tasks) {
                studentInput?.add(listOf(task.name, ""))
            }

            studentAddButton.setOnClickListener {
                val skills = arrayListOf<Int>()
                for (task in studentInput as ArrayList) {
                    // Add skill for each task.
                    if (task[1].isNotEmpty()) {
                        skills.add(task[1].toInt())
                    }
                }

                if (!studentName.isNullOrEmpty() && skills.size == args.tasks.size) {
                    students?.add(Pair(studentName, skills))
                    invalidateAll()
                }
            }

            saveSimButton.setOnClickListener {
                val size = (students as ArrayList).size

                if (size > 0) {
                    val studentList = arrayListOf<Student>()

                    for (student in students as ArrayList) {
                        val skills = arrayListOf<Skill>()

                        student.second.forEachIndexed { i, value ->
                            skills.add(Skill(args.tasks[i].name!!, value))
                        }

                        studentList.add(Student(student.first, skills))
                    }

                    val simulation = Simulation(simulationName!!, args.tasks.toList(), studentList)
                    CoroutineScope(Dispatchers.Main).launch { viewModel.save(simulation) }
                    findNavController().navigate(AddStudentsFragmentDirections.actionSave())
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
