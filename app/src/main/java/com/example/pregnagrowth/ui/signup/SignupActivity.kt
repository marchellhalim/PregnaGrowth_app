package com.example.pregnagrowth.ui.signup

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
import com.example.pregnagrowth.databinding.ActivitySignupBinding
import com.example.pregnagrowth.ui.login.LoginActivity
import com.example.pregnagrowth.ui.main.MainActivity
import com.example.pregnagrowth.utils.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SignupActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {
    private lateinit var binding: ActivitySignupBinding

    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
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
    }

    private fun setupAction() {
        binding.toLoginActivity.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signupButton.setOnClickListener {
            val name = binding.edtFullName.text.toString()
            val username = binding.edtUsername.text.toString()
            val birthdate = binding.tvBirthDate.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            viewModel.signup(name, email, password, username, birthdate).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            AlertDialog.Builder(this).apply {
                                setTitle("Yeah!")
                                setMessage("Akun sudah jadi nih. Yuk, login dahulu Moms!")
                                setPositiveButton("LANJUT") { _, _ ->
                                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
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
                                setMessage("Registrasi akun gagal. Silakan coba lagi Moms.")
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
        val title = ObjectAnimator.ofFloat(binding.signupHeading, View.ALPHA, 1f).setDuration(1000)

        val name = ObjectAnimator.ofFloat(binding.tvFullName, View.ALPHA, 1f).setDuration(1000)
        val edtName =
            ObjectAnimator.ofFloat(binding.edtLayoutFullName, View.ALPHA, 1f).setDuration(1000)

        val username = ObjectAnimator.ofFloat(binding.tvUsername, View.ALPHA, 1f).setDuration(1000)
        val edtUsername =
            ObjectAnimator.ofFloat(binding.edtLayoutUsername, View.ALPHA, 1f).setDuration(1000)

        val calendarButton =
            ObjectAnimator.ofFloat(binding.ibCalendar, View.ALPHA, 1f).setDuration(1000)
        val birthdate =
            ObjectAnimator.ofFloat(binding.tvBirthDate, View.ALPHA, 1f).setDuration(1000)

        val email = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(1000)
        val edtEmail =
            ObjectAnimator.ofFloat(binding.edtLayoutEmail, View.ALPHA, 1f).setDuration(1000)

        val password = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(1000)
        val edtPassword =
            ObjectAnimator.ofFloat(binding.edtLayoutPassword, View.ALPHA, 1f).setDuration(1000)

        val button = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(1000)
        val toLoginButton =
            ObjectAnimator.ofFloat(binding.toLoginActivity, View.ALPHA, 1f).setDuration(1000)

        val nameTogether = AnimatorSet().apply {
            playTogether(name, edtName)
        }

        val usernameTogether = AnimatorSet().apply {
            playTogether(username, edtUsername)
        }

        val birthdateTogether = AnimatorSet().apply {
            playTogether(calendarButton, birthdate)
        }

        val emailTogether = AnimatorSet().apply {
            playTogether(email, edtEmail)
        }

        val passwordTogether = AnimatorSet().apply {
            playTogether(password, edtPassword)
        }

        val buttonTogether = AnimatorSet().apply {
            playTogether(button, toLoginButton)
        }

        AnimatorSet().apply {
            playSequentially(
                title,
                nameTogether,
                usernameTogether,
                birthdateTogether,
                emailTogether,
                passwordTogether,
                buttonTogether
            )
            start()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        binding.tvBirthDate.text = dateFormat.format(calendar.time)
    }
}