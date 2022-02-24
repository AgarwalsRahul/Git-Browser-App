package com.example.gitbrowser.presentation.repoDetail

import androidx.fragment.app.Fragment

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gitbrowser.presentation.branch.BranchFragment

class TabAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment {
        return BranchFragment()
    }
}