package com.example.pregnagrowth.ui.faq

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pregnagrowth.api.response.FaqsItem
import com.example.pregnagrowth.databinding.ItemQuestionBinding
import com.example.pregnagrowth.ui.faq.DetailFAQActivity.Companion.FAQ_ANSWER
import com.example.pregnagrowth.ui.faq.DetailFAQActivity.Companion.FAQ_QUESTION

class FAQAdapter : ListAdapter<FaqsItem, FAQAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder(private val binding: ItemQuestionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(faqs: FaqsItem) {
            binding.tvQuestion.text = faqs.question
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQAdapter.MyViewHolder {
        val binding = ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FAQAdapter.MyViewHolder, position: Int) {
        val faq = getItem(position)
        holder.bind(faq)
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailFAQActivity::class.java)
            intentDetail.putExtra(FAQ_QUESTION, faq.question)
            intentDetail.putExtra(FAQ_ANSWER, faq.answer)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FaqsItem>() {
            override fun areItemsTheSame(oldItem: FaqsItem, newItem: FaqsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FaqsItem, newItem: FaqsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}