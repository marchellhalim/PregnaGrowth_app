package com.example.pregnagrowth.ui.faq

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pregnagrowth.R
import com.example.pregnagrowth.ResultState
import com.example.pregnagrowth.ViewModelFactory
import com.example.pregnagrowth.databinding.ActivityFaqBinding
import com.example.pregnagrowth.databinding.ActivityMainBinding
import com.example.pregnagrowth.pref.UserModel
import com.example.pregnagrowth.ui.main.MainActivity

class FAQActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaqBinding
    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas
    private val mPaint = Paint()
    private val mPath = Path()

    private val viewModel by viewModels<FAQViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        mBitmap = Bitmap.createBitmap(screenWidth, screenHeight / 4, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)

        blueBackground()
        binding.blueBackground.setImageBitmap(mBitmap)
        setupView()
    }

    private fun setupView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvQuestionsList.layoutManager = layoutManager

        val adapter = FAQAdapter()
        binding.rvQuestionsList.adapter = adapter
        viewModel.FAQs().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        val faqs = result.data.faqs
                        adapter.submitList(faqs)
                        showLoading(false)
                    }

                    is ResultState.Error -> {
                        Toast.makeText(this, "Gagal menampilkan rekomendasi.", LENGTH_SHORT).show()
                        showLoading(false)
                    }
                }
            }
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
        mPath.arcTo(
            right - 2 * cornerRadiusBottomRight,
            bottom - 2 * cornerRadiusBottomRight,
            right,
            bottom,
            0f,
            90f,
            false
        )
        // Line to bottom-left corner with rounded edge
        mPath.arcTo(
            left,
            bottom - 2 * cornerRadiusBottomLeft,
            left + 2 * cornerRadiusBottomLeft,
            bottom,
            90f,
            90f,
            false
        )
        // Line back to top-left corner
        mPath.lineTo(left, top)

        mPaint.color = ResourcesCompat.getColor(resources, R.color.primary_blue, null)
        mCanvas.drawPath(mPath, mPaint)

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}