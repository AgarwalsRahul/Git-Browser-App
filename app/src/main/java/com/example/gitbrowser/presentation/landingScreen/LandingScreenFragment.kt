package com.example.gitbrowser.presentation.landingScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitbrowser.R
import com.example.gitbrowser.databinding.FragmentLandingScreenBinding
import com.example.gitbrowser.domain.data.DataState
import com.example.gitbrowser.domain.model.Repo
import com.example.gitbrowser.presentation.MainActivity
import com.example.gitbrowser.presentation.common.TopSpacingItemDecoration
import com.example.gitbrowser.presentation.common.gone
import com.example.gitbrowser.presentation.common.visible
import com.example.gitbrowser.util.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LandingScreenFragment : Fragment(), ReposListAdapter.Interaction {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentLandingScreenBinding? = null

    private var listAdapter: ReposListAdapter? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLandingScreenBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreInstanceState(savedInstanceState)
        subscribeObserver()
        setUpRecyclerView()
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingScreenFragment_to_addFragment)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        return inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_menu -> {
                findNavController().navigate(R.id.action_landingScreenFragment_to_addFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let { inState ->
            (inState["page"] as Int?)?.let { page ->
                viewModel.setPage(page)
            }
            (inState["isQueryExhausted"] as Boolean?)?.let { isQueryExhausted ->
                viewModel.setQueryExhausted(isQueryExhausted)
            }
            (inState["layoutManagerState"] as Parcelable?)?.let { lmState ->
                viewModel.layoutManagerState = lmState
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun subscribeObserver() {
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success<List<Repo>> -> {
                    displayProgressBar(false)
                    if ((dataState.data.size < viewModel.getPage() * Constants.REPO_PAGINATION_PAGE_SIZE)
                        && !viewModel.isQueryExhausted()
                    ) {
                        viewModel.setQueryExhausted(true)
                    }
                    Log.d("LandingScreen","${dataState.data}")
                    listAdapter?.submitList(dataState.data)
                    listAdapter?.notifyDataSetChanged()
                    if (dataState.data.isEmpty()) {
                        binding.recyclerView.gone()
                        binding.addButton.visible()
                        binding.textView.visible()
                    } else {
                        binding.addButton.gone()
                        binding.textView.gone()
                        binding.recyclerView.visible()
                    }
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


    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(TopSpacingItemDecoration(20))
            listAdapter = ReposListAdapter(this@LandingScreenFragment)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == listAdapter?.itemCount?.minus(1)) {
//                        viewModel.nextPage()
                    }
                }
            })
            adapter = listAdapter
        }
    }

    private fun saveLayoutManagerState() {
        binding.recyclerView.layoutManager?.onSaveInstanceState()?.let { lmState ->
            viewModel.layoutManagerState = lmState
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.refreshQuery()
    }

    override fun onPause() {
        super.onPause()
        saveLayoutManagerState()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadFirstPage()
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

    override fun onItemSelected(position: Int, item: Repo) {

    }

    override fun restoreListPosition() {
        viewModel.layoutManagerState.let { lmState ->
            binding.recyclerView.layoutManager?.onRestoreInstanceState(
                lmState
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("page", viewModel.getPage())
        outState.putParcelable("layoutManagerState", viewModel.layoutManagerState)
        outState.putBoolean("isQueryExhausted", viewModel.isQueryExhausted())
        super.onSaveInstanceState(outState)
    }
}