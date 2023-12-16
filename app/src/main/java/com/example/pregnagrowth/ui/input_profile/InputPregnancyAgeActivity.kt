package com.example.pregnagrowth.ui.input_profile

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.pregnagrowth.R
import com.example.pregnagrowth.ResultState
import com.example.pregnagrowth.ViewModelFactory
import com.example.pregnagrowth.databinding.ActivityInputPregnancyAgeBinding
import com.example.pregnagrowth.databinding.ActivityInputWeightBinding
import com.example.pregnagrowth.ui.login.LoginActivity
import com.example.pregnagrowth.ui.main.MainActivity

class InputPregnancyAgeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputPregnancyAgeBinding

    private val viewModel by viewModels<InputProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val animationDuration: Long = 6000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputPregnancyAgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()

        Glide.with(this)
            .load(R.drawable.input_activity_image)
            .into(binding.imageView)

        binding.pregnancyAgeNumberPicker.maxValue = 280
        binding.pregnancyAgeNumberPicker.minValue = 0
    }

    private fun setupAction() {
        val userId = intent.getStringExtra(USER_ID)?.toInt()
        val weight = intent.getStringExtra(USER_WEIGHT)?.toInt()

        binding.continueButton.setOnClickListener {
            val pregnancyAge = binding.pregnancyAgeNumberPicker.value
            viewModel.profile(null, weight, pregnancyAge, null, userId).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            AlertDialog.Builder(this).apply {
                                setTitle("Berhasil memasukkan data profil!")
                                setMessage("Yuk, sekarang login dahulu Moms.")
                                setPositiveButton("LANJUT") { _, _ ->
                                    val intent = Intent(this@InputPregnancyAgeActivity, LoginActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }

                        is ResultState.Error -> {
                            AlertDialog.Builder(this).apply {
                                setTitle("Oh Tidak!")
                                setMessage("Input gagal. Silakan coba lagi Moms.")
                                setPositiveButton("KEMBALI") { dialog, _ ->
                                    dialog.cancel()
                                }
                                create()
                                show()
                            }
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = animationDuration
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.inputTitle, View.ALPHA, 1f).setDuration(1000)
        val description = ObjectAnimator.ofFloat(binding.tvInputDescription, View.ALPHA, 1f).setDuration(1000)
        val tvInputPregnancyAge = ObjectAnimator.ofFloat(binding.tvInputPregnancyAge, View.ALPHA, 1f).setDuration(1000)
        val pregnancyAgePicker = ObjectAnimator.ofFloat(binding.pregnancyAgeNumberPicker, View.ALPHA, 1f).setDuration(1000)
        val tvDays = ObjectAnimator.ofFloat(binding.tvDays, View.ALPHA, 1f).setDuration(1000)
        val button = ObjectAnimator.ofFloat(binding.continueButton, View.ALPHA, 1f).setDuration(1000)

        AnimatorSet().apply {
            playTogether(
                title,
                description,
                tvInputPregnancyAge,
                pregnancyAgePicker,
                tvDays,
                button
            )
            start()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val USER_ID = "USER_ID"
        const val USER_WEIGHT = "USER_WEIGHT"
    }
}