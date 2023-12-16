package com.example.pregnagrowth.ui.faq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pregnagrowth.R
import com.example.pregnagrowth.databinding.ActivityDetailFaqBinding
import com.example.pregnagrowth.databinding.ActivityFaqBinding

class DetailFAQActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFaqBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val question = intent.getStringExtra(FAQ_QUESTION)
        val answer = intent.getStringExtra(FAQ_ANSWER)

        binding.tvFaqQuestion.text = question
        binding.tvFaqAnswer.text = answer
    }

    companion object {
        const val FAQ_QUESTION = "FAQ_QUESTION"
        const val FAQ_ANSWER = "FAQ_ANSWER"
    }
}