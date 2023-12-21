package com.example.pregnagrowth.ui.detail_intake

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.pregnagrowth.R
import com.example.pregnagrowth.ResultState
import com.example.pregnagrowth.ViewModelFactory
import com.example.pregnagrowth.databinding.ActivityDetailIntakeBinding
import com.example.pregnagrowth.ui.result.ResultActivity
import com.example.pregnagrowth.ui.result.ResultActivity.Companion.CALCIUM
import com.example.pregnagrowth.ui.result.ResultActivity.Companion.CARBOHYDRATES
import com.example.pregnagrowth.ui.result.ResultActivity.Companion.FATS
import com.example.pregnagrowth.ui.result.ResultActivity.Companion.FOOD_IMAGE
import com.example.pregnagrowth.ui.result.ResultActivity.Companion.FOOD_NAME
import com.example.pregnagrowth.ui.result.ResultActivity.Companion.FOOD_TAG
import com.example.pregnagrowth.ui.result.ResultActivity.Companion.PREDICTION_ID
import com.example.pregnagrowth.ui.result.ResultActivity.Companion.PROTEIN
import com.example.pregnagrowth.ui.result.ResultActivity.Companion.VITAMINS
import com.example.pregnagrowth.utils.formatFloat
import com.example.pregnagrowth.utils.getImageUri
import com.example.pregnagrowth.utils.reduceFileImage
import com.example.pregnagrowth.utils.uriToFile

class DetailIntakeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailIntakeBinding

    private val viewModel by viewModels<DetailIntakeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var currentImageUri: Uri? = null

    private var foodTag: String? = null

    private var predictionId: String = ""

    private val launcher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            val foodTag = foodTag ?: "defaultTag"
            uploadImage(foodTag)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailIntakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel.getSession().observe(this) { user ->
            val userId = user.id
            viewModel.getUserFoodData(userId).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            showLoading(false)

                            val prediction = result.data.user?.prediction
                            predictionId = prediction?.takeIf { it.isNotEmpty() }?.get(prediction.lastIndex)?.id.toString()

                            var totalCarbohydrate = 0F
                            var totalCalcium = 0F
                            var totalProtein = 0F
                            var totalFats = 0F
                            var totalVitamins = 0F

                            val breakfast = prediction?.takeIf { it.isNotEmpty() }?.get(prediction.lastIndex)?.breakfast
                            val breakfastName = if (breakfast.isNullOrEmpty()) {
                                ""
                            } else {
                                breakfast.lastOrNull()?.foodName ?: ""
                            }
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

                            if (breakfast.isNullOrEmpty()) {
                                Glide.with(this)
                                    .load(R.drawable.ic_camera_24)
                                    .into(binding.ivTakeBreakfastPhoto)
                            } else {
                                Glide.with(this)
                                    .load(R.drawable.ic_edit_24)
                                    .into(binding.ivTakeBreakfastPhoto)
                            }

                            val lunch = prediction?.takeIf { it.isNotEmpty() }?.get(prediction.lastIndex)?.lunch
                            val lunchName = if (lunch.isNullOrEmpty()) {
                                ""
                            } else {
                                lunch.lastOrNull()?.foodName ?: ""
                            }
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

                            if (lunch.isNullOrEmpty()) {
                                Glide.with(this)
                                    .load(R.drawable.ic_camera_24)
                                    .into(binding.ivTakeLunchPhoto)
                            } else {
                                Glide.with(this)
                                    .load(R.drawable.ic_edit_24)
                                    .into(binding.ivTakeLunchPhoto)
                            }

                            val dinner = prediction?.takeIf { it.isNotEmpty() }?.get(prediction.lastIndex)?.dinner
                            val dinnerName = if (dinner.isNullOrEmpty()) {
                                ""
                            } else {
                                dinner.lastOrNull()?.foodName ?: ""
                            }
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

                            if (dinner.isNullOrEmpty()) {
                                Glide.with(this)
                                    .load(R.drawable.ic_camera_24)
                                    .into(binding.ivTakeDinnerPhoto)
                            } else {
                                Glide.with(this)
                                    .load(R.drawable.ic_edit_24)
                                    .into(binding.ivTakeDinnerPhoto)
                            }

                            binding.tvBreakfastName.text = breakfastName
                            binding.tvLunchName.text = lunchName
                            binding.tvDinnerName.text = dinnerName


                            binding.tvCarbohydratesAmount.text = formatFloat(totalCarbohydrate)
                            binding.tvCalciumAmount.text = formatFloat(totalCalcium * 1000F)
                            binding.tvFatsAmount.text = formatFloat(totalFats)
                            binding.tvProteinAmount.text = formatFloat(totalProtein)
                            binding.tvVitaminsAmount.text = formatFloat(totalVitamins)

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

                            if ((prediction.isNullOrEmpty()) /*|| ((breakfast != null) && (lunch != null) && (dinner != null))*/) {
                                viewModel.uploadNewPrediction(userId).observe(this) {
                                    if (it is ResultState.Success) {
                                        setupAction()
                                    }
                                }
                            } else {
                                setupAction()

                            }
                        }
                        is ResultState.Error -> {
                            AlertDialog.Builder(this@DetailIntakeActivity).apply {
                                setTitle("Oh Tidak!")
                                setMessage("Gagal memuat data makanan Moms. Silakan coba lagi.")
                                setPositiveButton("KEMBALI") { dialog, _ ->
                                    dialog.cancel()
                                }
                                create()
                                show()
                            }
                            showToast(result.error)
                            showLoading(false)
                        }
                    }
                }
            }
        }

    }

    private fun setupAction() {
        binding.ivTakeBreakfastPhoto.setOnClickListener {
            foodTag = "Breakfast"
            currentImageUri = getImageUri(this)
            launcher.launch(currentImageUri)
        }
        binding.ivTakeLunchPhoto.setOnClickListener {
            foodTag = "Lunch"
            currentImageUri = getImageUri(this)
            launcher.launch(currentImageUri)
        }
        binding.ivTakeDinnerPhoto.setOnClickListener {
            foodTag = "Dinner"
            currentImageUri = getImageUri(this)
            launcher.launch(currentImageUri)
        }
    }


    private fun uploadImage(foodTag: String) {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this@DetailIntakeActivity).reduceFileImage()

            Log.d("Image File", "showImage: ${imageFile.path}")

            viewModel.uploadPhoto(imageFile).observe(this@DetailIntakeActivity) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            val intent =
                                Intent(this@DetailIntakeActivity, ResultActivity::class.java)
                            intent.putExtra(FOOD_IMAGE, uri.toString())
                            intent.putExtra(FOOD_NAME, result.data.data?.foodName.toString())
                            intent.putExtra(
                                CARBOHYDRATES,
                                result.data.data?.carbohydrates.toString()
                            )
                            intent.putExtra(CALCIUM, result.data.data?.calcium.toString())
                            intent.putExtra(PROTEIN, result.data.data?.protein.toString())
                            intent.putExtra(FATS, result.data.data?.fat.toString())
                            intent.putExtra(VITAMINS, result.data.data?.vitamins.toString())
                            intent.putExtra(FOOD_TAG, foodTag)
                            intent.putExtra(PREDICTION_ID, predictionId)
                            startActivity(intent)
                            finish()
                            showLoading(false)

                        }

                        is ResultState.Error -> {
                            AlertDialog.Builder(this@DetailIntakeActivity).apply {
                                setTitle("Oh Tidak!")
                                setMessage("Gagal mendeteksi makanan. Silakan coba lagi.")
                                setPositiveButton("KEMBALI") { dialog, _ ->
                                    dialog.cancel()
                                }
                                create()
                                show()
                            }
                            showToast(result.error)
                            showLoading(false)
                        }
                    }
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.statusProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val BREAKFAST_IMAGE = "BREAKFAST_IMAGE"
        const val LUNCH_IMAGE = "LUNCH_IMAGE"
        const val DINNER_IMAGE = "DINNER_IMAGE"
    }
}