package com.example.soptseminar1.presentation.profile.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soptseminar1.data.model.response.ResponseGithubUserFollow
import com.example.soptseminar1.databinding.FollowerLayoutBinding

class FollowerAdapter(private val itemClick: (ResponseGithubUserFollow) -> (Unit)) :
    ListAdapter<ResponseGithubUserFollow, FollowerAdapter.FollowerViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding =
            FollowerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class FollowerViewHolder(
        private val binding: FollowerLayoutBinding,
        private val itemClick: (ResponseGithubUserFollow) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseGithubUserFollow) {
            Glide.with(binding.root)
                .load(data.avatar_url)
                .circleCrop()
                .into(binding.profPic)
            binding.follower = data
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ResponseGithubUserFollow>() {
            override fun areItemsTheSame(
                oldItem: ResponseGithubUserFollow,
                newItem: ResponseGithubUserFollow
            ): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(
                oldItem: ResponseGithubUserFollow,
                newItem: ResponseGithubUserFollow
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
