package com.example.gitbrowser.presentation.addRepo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gitbrowser.R
import com.example.gitbrowser.databinding.FragmentAddBinding
import com.example.gitbrowser.databinding.FragmentLandingScreenBinding
import com.example.gitbrowser.domain.data.DataState
import com.example.gitbrowser.domain.model.Repo
import com.example.gitbrowser.presentation.MainActivity
import com.example.gitbrowser.presentation.common.gone
import com.example.gitbrowser.presentation.common.hideKeyboard
import com.example.gitbrowser.presentation.common.visible
import com.example.gitbrowser.presentation.landingScreen.HomeViewModel
import com.example.gitbrowser.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFragment : Fragment() {

    private val viewModel: AddRepoViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObserver()
        initOwnerEditTextListener()
        initRepoNameEditTextListener()
        binding.addButton.setOnClickListener {
            view.hideKeyboard()
            viewModel.setStateEvent(
                AddRepoEvent.AddRepoIntoCacheEvent(
                    binding.ownerEditText.text.toString(),
                    binding.repoNameEditText.text.toString()
                )
            )
        }
    }

    private fun initOwnerEditTextListener() {
        binding.ownerEditText.apply {
            addTextChangedListener {
                viewModel.setOwner(it.toString())
                if (binding.ownerLayout.isErrorEnabled) {
                    binding.ownerLayout.error =
                        if (viewModel.validate(this.text.toString())) null else "This field is mandatory."
                }
            }
            onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    viewModel.ownerFieldFocus = false
                    binding.ownerLayout.isErrorEnabled =
                        !viewModel.validate(this.text.toString())
                    binding.ownerLayout.error =
                        if (viewModel.validate(this.text.toString())) null else "This field is mandatory."


                } else {
                    binding.ownerLayout.isErrorEnabled = !viewModel.ownerFieldFocus
                }
            }
        }
    }

    private fun initRepoNameEditTextListener() {
        binding.repoNameEditText.apply {
            addTextChangedListener {
                viewModel.setRepo(it.toString())
                if (binding.repoNameLayout.isErrorEnabled) {
                    binding.repoNameLayout.error =
                        if (viewModel.validate(this.text.toString())) null else "This field is mandatory."
                }
            }
            onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    viewModel.repoFieldFocus = false
                    binding.repoNameLayout.isErrorEnabled =
                        !viewModel.validate(this.text.toString())
                    binding.repoNameLayout.error =
                        if (viewModel.validate(this.text.toString())) null else "This field is mandatory."


                } else {
                    binding.repoNameLayout.isErrorEnabled = !viewModel.ownerFieldFocus
                }
            }
        }
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success<Repo> -> {
                    displayProgressBar(false)
                    (requireActivity() as MainActivity).displaySnackBar("Repo added successfully")
                    findNavController().popBackStack()
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }

        }
        lifecycleScope.launch {
            viewModel.isAddEnable.collect { value ->
                binding.addButton.isEnabled = value
            }
            viewModel.owner.collect {
                binding.ownerEditText.setText(it)
            }

            viewModel.repo.collect {
                binding.repoNameEditText.setText(it)
            }
        }
    }

    private fun displayProgressBar(isVisible: Boolean) {
        (requireActivity() as MainActivity).displayProgressBar(isVisible)
    }

    private fun displayError(message: String?) {
        (requireActivity() as MainActivity).displaySnackBar(
            message ?: "Unknown Error. Please try again."
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}