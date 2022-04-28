package com.example.soptseminar1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.soptseminar1.databinding.RepoLayoutBinding

class RepositoryRecyclerView : RecyclerView.Adapter<RepositoryRecyclerView.RepositoryViewHolder>(){
    val repositoryList = mutableListOf<RepositoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = RepoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.onBind(repositoryList[position])
    }

    override fun getItemCount(): Int = repositoryList.size

    class RepositoryViewHolder(
        private val binding: RepoLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data: RepositoryData){
            binding.repoName.text = data.repo_name
            binding.repoHomework.text = data.part_name
        }
    }
}