package br.com.opttasks.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.com.opttasks.R
import br.com.opttasks.databinding.LoginFragmentBinding
import br.com.opttasks.utils.Injector

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, Injector.provideLoginViewModelFactory())
                .get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return LoginFragmentBinding.inflate(inflater, container, false).run {
            // Login action setup
            loginButton.setOnClickListener {
                // Prevent app crashing with multiple clicks
                it.isEnabled = false

                // TODO: Change to auth request
                val success = true

                if (success) findNavController()
                        .navigate(R.id.action_login)
                else Toast
                        .makeText(activity, getString(R.string.login_fail), Toast.LENGTH_LONG)
                        .show()
                        .apply { it.isEnabled = true }
            }

            root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}
