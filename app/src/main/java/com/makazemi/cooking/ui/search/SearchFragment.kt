package com.makazemi.cooking.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.makazemi.cooking.databinding.FragmentSearchBinding
import com.makazemi.cooking.model.Food
import com.makazemi.cooking.ui.displayToast
import com.makazemi.cooking.ui.recipe.FoodAdapter
import com.makazemi.cooking.ui.setVisibilityView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var foodAdapter: FoodAdapter

    private val viewModel:SearchViewModel by viewModels()

    @Inject
    lateinit var requestManager: RequestManager

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        initRcy()

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query= binding.edtSearch.text.toString().trim()
                viewModel.search(query)
            }

        })

        subscribeObserverFood()
    }

    private fun initRcy(){
        foodAdapter= FoodAdapter(requestManager)
        binding.rcyItem.apply {
            layoutManager= GridLayoutManager(requireContext(),2)
            adapter=foodAdapter
        }
        foodAdapter.setClickListener {
            goToDetailFood(it)
        }
    }

    private fun subscribeObserverFood(){
        viewModel.foods.observe(viewLifecycleOwner){
            it?.data?.peekContent()?.let {
                foodAdapter.submitNewList(it)
                Timber.d("list=$it")
            }
            it.error?.getContentIfNotHandled()?.message?.let {
                displayToast(it)
            }
            binding.progressbar.setVisibilityView(it.loading.isLoading)

        }
    }

    private fun goToDetailFood(item:Food){
        val action=SearchFragmentDirections.actionSearchFragmentToDetailFoodFragment2(item)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}