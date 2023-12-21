package com.example.pregnagrowth.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.datastore.preferences.core.edit
import com.bumptech.glide.Glide
import com.example.pregnagrowth.R
import com.example.pregnagrowth.ResultState
import com.example.pregnagrowth.ViewModelFactory
import com.example.pregnagrowth.databinding.ActivityMainBinding
import com.example.pregnagrowth.pref.UserModel
import com.example.pregnagrowth.ui.detail_intake.DetailIntakeActivity
import com.example.pregnagrowth.ui.faq.FAQActivity
import com.example.pregnagrowth.ui.input_profile.InputPregnancyAgeActivity
import com.example.pregnagrowth.ui.login.LoginActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas
    private val mPaint = Paint()
    private val mPath = Path()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                setupView(user.id)
                setupAction()
            }
        }
    }

    private fun setupView(id: String) {
        supportActionBar?.hide()

        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        mBitmap = Bitmap.createBitmap(screenWidth, screenHeight/3, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)

        blueBackground()
        binding.blueBackground.setImageBitmap(mBitmap)

        viewModel.getUserInfo(id).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        binding.tvPregnancyAgeDay.visibility = View.GONE
                    }

                    is ResultState.Success -> {
                        binding.tvFullName.text = result.data.user?.name.toString()
                        val profile = result.data.user?.profile
                        binding.tvPregnancyAgeDay.visibility = View.VISIBLE

                        val ageDefaultValue = profile?.get(profile.lastIndex)?.umurJanin!!.toInt()

                        preferences = getSharedPreferences("PregnancyAgePreferences", Context.MODE_PRIVATE)
                        editor = preferences.edit()

                        var age = preferences.getInt("age", ageDefaultValue)
                        val lastUpdateMillis = preferences.getLong("lastUpdateMillis", 0)

                        binding.tvPregnancyAge.text = ageDefaultValue.toString()

                        val calendar = Calendar.getInstance()
                        val currentMillis = calendar.timeInMillis

                        if ((currentMillis - lastUpdateMillis) >= 24 * 60 * 60 * 1000) {
                            // A day has passed
                            age++
                            editor.putInt("age", age)
                            editor.putLong("lastUpdateMillis", currentMillis)
                            editor.apply()
                            viewModel.updateProfile(id, age.toString()).observe(this) {
                                Log.d("Update Pregnancy Age", result.data.success.toString())
                            }
                        }

                        viewModel.getUserFoodData(id).observe(this) { result ->
                            if (result != null) {
                                when (result) {
                                    is ResultState.Loading -> {

                                    }

                                    is ResultState.Success -> {
                                        val prediction = result.data.user?.prediction

                                        var totalCarbohydrate = 0F
                                        var totalCalcium = 0F
                                        var totalProtein = 0F
                                        var totalFats = 0F
                                        var totalVitamins = 0F

                                        val breakfast = prediction?.takeIf { it.isNotEmpty() }?.get(prediction.lastIndex)?.breakfast
                                        if (breakfast.isNullOrEmpty()) {
                                            totalCarbohydrate += 0
                                            totalCalcium += 0
                                            totalProtein += 0
                                            totalFats += 0
                                            totalVitamins += 0
                                        } else {
                                            totalCarbohydrate += breakfast.lastOrNull()?.carbohydrate.toString().toFloat()
                                            totalCalcium += breakfast.lastOrNull()?.calcium.toString().toFloat()
                                            totalProtein += breakfast.lastOrNull()?.protein.toString().toFloat()
                                            totalFats += breakfast.lastOrNull()?.fat.toString().toFloat()
                                            totalVitamins += breakfast.lastOrNull()?.vitamin.toString().toFloat()
                                        }

                                        val lunch = prediction?.takeIf { it.isNotEmpty() }?.get(prediction.lastIndex)?.lunch
                                        if (lunch.isNullOrEmpty()) {
                                            totalCarbohydrate += 0
                                            totalCalcium += 0
                                            totalProtein += 0
                                            totalFats += 0
                                            totalVitamins += 0
                                        } else {
                                            totalCarbohydrate += lunch.lastOrNull()?.carbohydrate.toString().toFloat()
                                            totalCalcium += lunch.lastOrNull()?.calcium.toString().toFloat()
                                            totalProtein += lunch.lastOrNull()?.protein.toString().toFloat()
                                            totalFats += lunch.lastOrNull()?.fat.toString().toFloat()
                                            totalVitamins += lunch.lastOrNull()?.vitamin.toString().toFloat()
                                        }

                                        val dinner = prediction?.takeIf { it.isNotEmpty() }?.get(prediction.lastIndex)?.dinner
                                        if (dinner.isNullOrEmpty()) {
                                            totalCarbohydrate += 0
                                            totalCalcium += 0
                                            totalProtein += 0
                                            totalFats += 0
                                            totalVitamins += 0
                                        } else {
                                            totalCarbohydrate += dinner.lastOrNull()?.carbohydrate.toString().toFloat()
                                            totalCalcium += dinner.lastOrNull()?.calcium.toString().toFloat()
                                            totalProtein += dinner.lastOrNull()?.protein.toString().toFloat()
                                            totalFats += dinner.lastOrNull()?.fat.toString().toFloat()
                                            totalVitamins += dinner.lastOrNull()?.vitamin.toString().toFloat()
                                        }

                                        binding.progressBarCarbohydrates.apply {
                                            progressMax = 100F
                                            progress = (totalCarbohydrate/400F) * 100F
                                        }

                                        binding.progressBarFats.apply {
                                            progressMax = 100F
                                            progress = (totalFats/62.3F) * 100F
                                        }

                                        binding.progressBarProtein.apply {
                                            progressMax = 100F
                                            progress = (totalProtein/90F) * 100F
                                        }

                                        binding.progressBarVitamins.apply {
                                            progressMax = 100F
                                            progress = (totalVitamins/100F) * 100F
                                        }

                                        binding.progressBarCalcium.apply {
                                            progressMax = 100F
                                            progress = ((totalCalcium * 1000F)/1200F) * 100F
                                        }
                                    }
                                    is ResultState.Error -> {

                                    }
                                }
                            }
                        }
                    }

                    is ResultState.Error -> {
                        AlertDialog.Builder(this).apply {
                            setTitle("Oh Tidak!")
                            setMessage("Gagal memuat data. Silakan login ulang.")
                            setPositiveButton("KEMBALI") { dialog, _ ->
                                dialog.cancel()
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }


    }

    private fun setupAction() {
        binding.ivProfile.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Keluar")
                setMessage("Apakah Anda yakin ingin keluar?")
                setPositiveButton("YA") { _, _ ->
                    editor = preferences.edit()
                    editor.clear()
                    editor.apply()
                    viewModel.logout()
                }
                setNegativeButton("TIDAK") { dialog, _ ->
                    dialog.cancel()
                }
                create()
                show()
            }
        }

        binding.faqCardView.setOnClickListener {
            val intent = Intent(this@MainActivity, FAQActivity::class.java)
            startActivity(intent)
        }

        binding.nutritionCardView.setOnClickListener {
            val intent = Intent(this@MainActivity, DetailIntakeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun blueBackground() {
        val left = 0F
        val right = mBitmap.width.toFloat()
        val top = 0F
        val bottom = mBitmap.height.toFloat()
        val cornerRadiusBottomLeft = 200f
        val cornerRadiusBottomRight = 200f

        // Move to top-left corner
        mPath.moveTo(left, top)
        // Line to top-right corner
        mPath.moveTo(right, top)
        // Line to bottom-right corner with rounded edge
        mPath.arcTo(right - 2*cornerRadiusBottomRight, bottom - 2*cornerRadiusBottomRight, right, bottom, 0f, 90f, false)
        // Line to bottom-left corner with rounded edge
        mPath.arcTo(left, bottom - 2*cornerRadiusBottomLeft, left + 2*cornerRadiusBottomLeft, bottom, 90f, 90f, false)
        // Line back to top-left corner
        mPath.lineTo(left, top)

        mPaint.color = ResourcesCompat.getColor(resources, R.color.primary_blue, null)
        mCanvas.drawPath(mPath, mPaint)

    }

}