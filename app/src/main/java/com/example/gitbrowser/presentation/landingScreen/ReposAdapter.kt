package com.example.gitbrowser.presentation.landingScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gitbrowser.R
import com.example.gitbrowser.domain.model.Repo

class ReposListAdapter(
    private val interaction: Interaction? = null,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {

        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.repo_list_item_layout,
                parent,
                false
            ),
            interaction
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RepoViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Repo>) {
        val commitCallback = Runnable {
            // if process died must restore list position
            // very annoying
            interaction?.restoreListPosition()
        }
        differ.submitList(list, commitCallback)
    }

    fun getRepo(index: Int): Repo? {
        return try {
            differ.currentList[index]
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            null
        }
    }

    class RepoViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?,


        ) : RecyclerView.ViewHolder(itemView) {


        private lateinit var repo: Repo

        fun bind(item: Repo) = with(itemView) {
            setOnClickListener {
                interaction?.onItemSelected(adapterPosition, repo)
            }

            repo = item
            findViewById<TextView>(R.id.repo_name_edit_text).setText(item.name)
            findViewById<TextView>(R.id.description).setText(item.description)

        }
    }

    class EmptyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Interaction {

        fun onItemSelected(position: Int, item: Repo)

        fun restoreListPosition()

    }

}