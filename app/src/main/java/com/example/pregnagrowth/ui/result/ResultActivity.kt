package com.example.pregnagrowth.ui.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.pregnagrowth.ResultState
import com.example.pregnagrowth.ViewModelFactory
import com.example.pregnagrowth.databinding.ActivityResultBinding
import com.example.pregnagrowth.pref.UserModel
import com.example.pregnagrowth.ui.detail_intake.DetailIntakeActivity
import com.example.pregnagrowth.ui.detail_intake.DetailIntakeActivity.Companion.BREAKFAST_IMAGE
import com.example.pregnagrowth.ui.detail_intake.DetailIntakeActivity.Companion.DINNER_IMAGE
import com.example.pregnagrowth.ui.detail_intake.DetailIntakeActivity.Companion.LUNCH_IMAGE
import com.example.pregnagrowth.ui.main.MainActivity
import com.example.pregnagrowth.ui.main.MainViewModel

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private val viewModel by viewModels<ResultViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val foodName = intent.getStringExtra(FOOD_NAME)
        val foodTag = intent.getStringExtra(FOOD_TAG)
        val foodImage = intent.getStringExtra(FOOD_IMAGE)?.toUri()
        val carbohydrates = intent.getStringExtra(CARBOHYDRATES)
        val calcium = intent.getStringExtra(CALCIUM)
        val protein = intent.getStringExtra(PROTEIN)
        val fats = intent.getStringExtra(FATS)
        val vitamins = intent.getStringExtra(VITAMINS)!!.toFloat()
        val predictionId = intent.getStringExtra(PREDICTION_ID).toString()

        val vitaminsInMilligram = (vitamins * 1000F).toString()


        Glide.with(this)
            .load(foodImage)
            .into(binding.ivFoodResult)

        binding.tvFoodName.text = foodName
        binding.tvCarbohydratesAmount.text = carbohydrates
        binding.tvCalciumAmount.text = calcium
        binding.tvProteinAmount.text = protein
        binding.tvFatsAmount.text = fats
        binding.tvVitaminsAmount.text = vitaminsInMilligram

        binding.addButton.setOnClickListener {
            if (foodTag == "Breakfast") {
                viewModel.uploadBreakfast(
                    predictionId,
                    foodName,
                    calcium,
                    carbohydrates,
                    fats,
                    protein,
                    vitamins.toString()
                ).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Yeah!")
                                    setMessage("Makanan berhasil ditambahkan.")
                                    setPositiveButton("KEMBALI") { _, _ ->
                                        val intent = Intent(
                                            this@ResultActivity,
                                            DetailIntakeActivity::class.java
                                        )
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
                                    setMessage("Makanan gagal ditambahkan. Silakan coba lagi.")
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
            } else if (foodTag == "Lunch") {
                viewModel.uploadLunch(
                    predictionId,
                    foodName,
                    calcium,
                    carbohydrates,
                    fats,
                    protein,
                    vitamins.toString()
                ).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Yeah!")
                                    setMessage("Makanan berhasil ditambahkan.")
                                    setPositiveButton("KEMBALI") { _, _ ->
                                        val intent = Intent(
                                            this@ResultActivity,
                                            DetailIntakeActivity::class.java
                                        )
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
                                    setMessage("Makanan gagal ditambahkan. Silakan coba lagi.")
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
            } else if (foodTag == "Dinner") {
                viewModel.uploadDinner(
                    predictionId,
                    foodName,
                    calcium,
                    carbohydrates,
                    fats,
                    protein,
                    vitamins.toString()
                ).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Yeah!")
                                    setMessage("Makanan berhasil ditambahkan.")
                                    setPositiveButton("KEMBALI") { _, _ ->
                                        val intent = Intent(
                                            this@ResultActivity,
                                            DetailIntakeActivity::class.java
                                        )
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
                                    setMessage("Makanan gagal ditambahkan. Silakan coba lagi.")
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
            } else {
                Log.d("Result Activity", "Unknown Food Tag")
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val FOOD_NAME = "FOOD_NAME"
        const val FOOD_IMAGE = "FOOD_IMAGE"
        const val FOOD_TAG = "FOOD_TAG"
        const val CARBOHYDRATES = "CARBOHYDRATES"
        const val CALCIUM = "CALCIUM"
        const val PROTEIN = "PROTEIN"
        const val FATS = "FATS"
        const val VITAMINS = "VITAMINS"
        const val PREDICTION_ID = "ID"
    }
}