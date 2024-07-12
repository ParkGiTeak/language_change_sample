package com.example.languagechange.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.languagechange.Listener.OnLanguageChangeListener
import com.example.languagechange.databinding.LanguageItemBinding
import com.example.languagechange.model.LanguageModel

class LanguageChangeAdapter(val languageList: List<LanguageModel>) : RecyclerView.Adapter<LanguageChangeAdapter.LanguageChangeViewHolder>() {

    private var itemClickListener: OnLanguageChangeListener? = null

    fun setItemClickListener(onItemClickListener: OnLanguageChangeListener) {
        itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageChangeViewHolder {
        val binding = LanguageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageChangeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageChangeViewHolder, position: Int) {
        val safePosition = holder.adapterPosition
        if(safePosition != RecyclerView.NO_POSITION) {
            holder.bind(safePosition)
        }
    }

    override fun getItemCount(): Int {
        return languageList.size
    }

    inner class LanguageChangeViewHolder(private val binding: LanguageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(safePosition: Int) {
            if (languageList[safePosition].isLanguageChecked) {
                binding.tvLanguageBottom.setTextColor(Color.parseColor("#009dff"))
            } else {
                binding.tvLanguageBottom.setTextColor(Color.parseColor("#8d8d8d"))
            }

            binding.tvLanguageBottom.text = languageList[safePosition].language
            binding.root.setOnClickListener {
                itemClickListener?.onChangeClick(safePosition)
            }
        }
    }
}