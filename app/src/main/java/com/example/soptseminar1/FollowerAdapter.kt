package com.example.soptseminar1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soptseminar1.databinding.FollowerLayoutBinding

class FollowerAdapter(private val itemClick: (ResponseGithubUserFollow) -> (Unit)) :
    RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {
    val followerList = mutableListOf<ResponseGithubUserFollow>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding =
            FollowerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(followerList[position])
    }

    override fun getItemCount(): Int = followerList.size

    class FollowerViewHolder(
        private val binding: FollowerLayoutBinding,
        private val itemClick: (ResponseGithubUserFollow) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseGithubUserFollow) {

            Glide.with(binding.root)
                .load(data.avatar_url)
                .circleCrop()
                .into(binding.profPic)
            binding.profName.text = data.userId

            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }
}
