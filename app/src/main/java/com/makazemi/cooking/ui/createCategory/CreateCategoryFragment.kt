package com.makazemi.cooking.ui.createCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.makazemi.cooking.databinding.FragmentCreateCategoryBinding
import com.makazemi.cooking.ui.displayToast
import com.makazemi.cooking.ui.setVisibilityView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateCategoryFragment : Fragment() {

    private val viewModel: CreateCategoryViewModel by viewModels()


    private var _binding: FragmentCreateCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.btnSubmit.setOnClickListener {
            viewModel.createCategory(
                binding.edtImage.text.toString(),
                binding.edtName.text.toString()
            )
        }

        viewModel.createCategoryResponse.observe(viewLifecycleOwner) {
            it?.data?.getContentIfNotHandled()?.let {
              displayToast("category create!")
                clearEditText()

            }
            it.error?.getContentIfNotHandled()?.message?.let {
                displayToast(it)
            }

            binding.progressbar.setVisibilityView(it.loading.isLoading)

        }
    }


    private fun clearEditText() {
        binding.edtImage.text.clear()
        binding.edtName.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}