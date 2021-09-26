package com.makazemi.cooking.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.makazemi.cooking.databinding.FragmentListFoodBinding
import com.makazemi.cooking.db.FoodDao
import com.makazemi.cooking.model.Food
import com.makazemi.cooking.ui.displayToast
import com.makazemi.cooking.ui.setVisibilityView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListFoodFragment : Fragment() {


    private val viewModel:ListFoodViewModel by viewModels()

    private lateinit var foodAdapter: FoodAdapter

    @Inject
    lateinit var requestManager: RequestManager

    private val args:ListFoodFragmentArgs by navArgs()

    private var _binding: FragmentListFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding.txtTitle.text= args.categoryArg.title
        binding.imgBack.setOnClickListener{
            findNavController().popBackStack()
        }
        initRcy()
        viewModel.setCategory(args.categoryArg)
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
                foodAdapter.submitList(it)
            }
            it.error?.getContentIfNotHandled()?.message?.let {
                displayToast(it)
            }
            binding.progressbar.setVisibilityView(it.loading.isLoading)
        }
    }

    private fun goToDetailFood(item: Food){
        val action= ListFoodFragmentDirections.actionListFoodFragmentToDetailFoodFragment(item)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}