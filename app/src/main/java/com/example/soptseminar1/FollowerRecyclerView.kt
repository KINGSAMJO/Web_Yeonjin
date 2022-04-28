package com.example.soptseminar1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.soptseminar1.databinding.FollowerLayoutBinding

class FollowerRecyclerView(private val itemClick: (FollowerData) -> (Unit)) : RecyclerView.Adapter<FollowerRecyclerView.FollowerViewHolder>() {
    val followerList = mutableListOf<FollowerData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding = FollowerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(followerList[position])
    }

    override fun getItemCount(): Int = followerList.size

    class FollowerViewHolder(
        private val binding: FollowerLayoutBinding,
        private val itemClick: (FollowerData) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: FollowerData){
            binding.profName.text = data.name
            binding.profIntroduce.text = data.introduction
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }
}
