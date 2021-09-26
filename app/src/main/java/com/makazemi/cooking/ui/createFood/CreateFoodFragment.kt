package com.makazemi.cooking.ui.createFood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.makazemi.cooking.R
import com.makazemi.cooking.databinding.FragmentCreateFoodBinding
import com.makazemi.cooking.ui.displayToast
import com.makazemi.cooking.ui.setVisibilityView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateFoodFragment : Fragment() {

    private val viewModel:CreateFoodViewModel by viewModels()

    private lateinit var customSpinAdapter:CustomSpinAdapter

    private var _binding: FragmentCreateFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initSpinner()
        subscribeObserverCategory()
        binding.btnSubmit.setOnClickListener {
            createFood()
        }
        subscribeObserverCreateFood()
    }

    private fun initSpinner() {
        customSpinAdapter = CustomSpinAdapter(
            requireContext(),
            R.layout.item_layout_spinner_category,
        ).also {
            it.setDropDownViewResource(R.layout.spinner_dropdown_item_category)
            binding.spinnerCategory.adapter = it
        }

        binding.spinnerCategory.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View,
                position: Int, id: Long
            ) {
                customSpinAdapter.getItem(position)?.let {
                    viewModel.setCategory(it)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
    }

    private fun subscribeObserverCategory(){
        viewModel.categories.observe(viewLifecycleOwner){
            it?.data?.peekContent()?.let {
                customSpinAdapter.submitList(it)
            }
        }
    }

    private fun createFood(){
        viewModel.createFood(
            name = binding.edtName.text.toString(),
            recipe = binding.edtRecipe.text.toString(),
            rawMaterial1 = binding.edtNameRaw1.text.toString(),
            rawMaterial2 = binding.edtNameRaw2.text.toString(),
            rawMaterial3 = binding.edtNameRaw3.text.toString(),
            valueRawMaterial1 = binding.edtValueRaw1.text.toString(),
            valueRawMaterial2 = binding.edtValueRaw2.text.toString(),
            valueRawMaterial3 = binding.edtValueRaw3.text.toString(),
            image = binding.edtImage.text.toString()
        )
    }

    private fun subscribeObserverCreateFood(){
        viewModel.createFoodResponse.observe(viewLifecycleOwner){
            it?.data?.getContentIfNotHandled()?.let {
                displayToast("food create!")
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
        binding.edtRecipe.text.clear()
        binding.edtNameRaw1.text.clear()
        binding.edtNameRaw2.text.clear()
        binding.edtNameRaw3.text.clear()
        binding.edtValueRaw1.text.clear()
        binding.edtValueRaw2.text.clear()
        binding.edtValueRaw3.text.clear()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}