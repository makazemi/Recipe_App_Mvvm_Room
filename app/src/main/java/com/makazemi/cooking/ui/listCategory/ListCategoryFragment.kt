package com.makazemi.cooking.ui.listCategory

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.google.android.material.navigation.NavigationView
import com.makazemi.cooking.databinding.FragmentListCategoryBinding
import com.makazemi.cooking.model.CategoryFood
import com.makazemi.cooking.ui.displayToast
import com.makazemi.cooking.ui.setVisibilityView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ListCategoryFragment : Fragment() , NavigationView.OnNavigationItemSelectedListener {

    private val viewModel:ListCategoryViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter

    @Inject
    lateinit var requestManager: RequestManager

    private var _binding: FragmentListCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        initDrawer()
        initRcy()
        subscribeObserverCategory()
    }

    private fun initRcy(){
        categoryAdapter= CategoryAdapter(requestManager)
        binding.rcyItem.apply {
            layoutManager= LinearLayoutManager(requireContext())
            adapter= categoryAdapter
        }
        categoryAdapter.setClickListener {
            goToListFood(it)
        }
    }

    private fun subscribeObserverCategory(){
        viewModel.categories.observe(viewLifecycleOwner){
            it?.data?.peekContent()?.let{
                categoryAdapter.submitList(it)
                Timber.d("category=$it")
            }
            it.error?.getContentIfNotHandled()?.message?.let {
                displayToast(it)
            }
            binding.progressbar.setVisibilityView(it.loading.isLoading)
        }
    }

    private fun goToListFood(item:CategoryFood){
        val action=ListCategoryFragmentDirections.actionListCategoryFragmentToListFoodFragment(item)
        findNavController().navigate(action)
    }

    private fun initDrawer() {
        AppBarConfiguration(this.findNavController().graph, binding.drawerLayout)
        binding.navView.setupWithNavController(this.findNavController())
        binding.navView.setNavigationItemSelectedListener(this)
        binding.layMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.RIGHT)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isCheckable = true
        binding.drawerLayout.closeDrawer(Gravity.RIGHT)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}