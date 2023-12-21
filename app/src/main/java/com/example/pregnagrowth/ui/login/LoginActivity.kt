package com.example.pregnagrowth.ui.login

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
import com.example.pregnagrowth.api.response.LoginResponse
import com.example.pregnagrowth.databinding.ActivityLoginBinding
import com.example.pregnagrowth.pref.UserModel
import com.example.pregnagrowth.ui.main.MainActivity
import com.example.pregnagrowth.ui.signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    private val animationDuration: Long = 6000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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
            .load(R.drawable.motion_layout_image_3)
            .into(binding.imageView)
    }

    private fun setupAction() {
        binding.toSignupActivity.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            viewModel.login(email, password).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            val token = result.data.login?.token!!
                            val id = result.data.login.id.toString()
                            viewModel.saveSession(user = UserModel(email, token, id, true))

                            AlertDialog.Builder(this).apply {
                                setTitle("Yeah!")
                                setMessage("Anda berhasil login.")
                                setPositiveButton("LANJUT") { _, _ ->
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }

                            showLoading(false)
                        }

                        is ResultState.Error -> {
                            AlertDialog.Builder(this).apply {
                                setTitle("Oh Tidak!")
                                setMessage("Login gagal. Silakan coba lagi.")
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

        val title = ObjectAnimator.ofFloat(binding.loginTitle, View.ALPHA, 1f).setDuration(1000)
        val message = ObjectAnimator.ofFloat(binding.loginDescription, View.ALPHA, 1f).setDuration(1000)
        val loginHeading = ObjectAnimator.ofFloat(binding.loginHeading, View.ALPHA, 1f).setDuration(1000)
        val edtEmail = ObjectAnimator.ofFloat(binding.edtLayoutEmail, View.ALPHA, 1f).setDuration(1000)
        val edtPassword = ObjectAnimator.ofFloat(binding.edtLayoutPassword, View.ALPHA, 1f).setDuration(1000)
        val button = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(1000)
        val toSignupButton = ObjectAnimator.ofFloat(binding.toSignupActivity, View.ALPHA, 1f).setDuration(1000)

        val buttonTogether = AnimatorSet().apply {
            playTogether(button, toSignupButton)
        }

        AnimatorSet().apply {
            playSequentially(title, message, loginHeading, edtEmail, edtPassword, buttonTogether)
            start()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}