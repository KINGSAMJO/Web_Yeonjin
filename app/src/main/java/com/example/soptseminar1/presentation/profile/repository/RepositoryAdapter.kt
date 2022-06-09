package com.example.soptseminar1.presentation.profile.repository

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.soptseminar1.data.model.response.ResponseGithubUserRepo
import com.example.soptseminar1.databinding.RepoLayoutBinding

class RepositoryAdapter : ListAdapter<ResponseGithubUserRepo, RepositoryAdapter.RepositoryViewHolder>(
    diffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = RepoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class RepositoryViewHolder(
        private val binding: RepoLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseGithubUserRepo) {
            binding.repoName.text = data.name
            binding.repoDescription.text = data.description
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ResponseGithubUserRepo>(){
            override fun areItemsTheSame(
                oldItem: ResponseGithubUserRepo,
                newItem: ResponseGithubUserRepo
            ): Boolean {
                return oldItem.name == newItem.name || oldItem.description == newItem.description
            }

            override fun areContentsTheSame(
                oldItem: ResponseGithubUserRepo,
                newItem: ResponseGithubUserRepo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}