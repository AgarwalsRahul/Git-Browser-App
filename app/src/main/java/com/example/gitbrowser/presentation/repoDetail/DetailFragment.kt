package com.example.gitbrowser.presentation.repoDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.gitbrowser.R
import com.example.gitbrowser.databinding.FragmentDetailBinding
import com.example.gitbrowser.databinding.FragmentLandingScreenBinding
import com.example.gitbrowser.domain.data.DataState
import com.example.gitbrowser.domain.model.Repo
import com.example.gitbrowser.presentation.MainActivity
import com.example.gitbrowser.presentation.common.gone
import com.example.gitbrowser.presentation.common.visible
import com.example.gitbrowser.presentation.landingScreen.HomeViewModel
import com.example.gitbrowser.util.Constants
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private lateinit var adapter: TabAdapter

    private lateinit var repo: Repo

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewPager = binding.pager
        tabLayout = binding.tabLayout
        val bundle = arguments
        bundle?.let {
            val args = DetailFragmentArgs.fromBundle(bundle)
            repo = args.repo
            binding.detailRepoName.text = args.repo.name
            binding.detailDescription.text = args.repo.description
        }
        adapter = TabAdapter(this)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.branches)
                1 -> tab.text = getString(R.string.issues)
            }
        }.attach()
        subscribeObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        return inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_menu -> {
                viewModel.setStateEvent(DetailStateEvent.DeleteRepoEvent(repo.id))
                return true
            }
            R.id.open_browser -> {
                openBrowser()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openBrowser() {
        var url = repo.url
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url = "https://" + url;
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(Intent.createChooser(intent, "Browse with"));
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success<*> -> {
                    displayProgressBar(false)
                    (activity as MainActivity).displaySnackBar("Repo has been successfully deleted.")
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