package com.example.pregnagrowth.ui.faq

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import com.example.pregnagrowth.R
import com.example.pregnagrowth.databinding.ActivityFaqBinding
import com.example.pregnagrowth.databinding.ActivityMainBinding

class FAQActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaqBinding
    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas
    private val mPaint = Paint()
    private val mPath = Path()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        mBitmap = Bitmap.createBitmap(screenWidth, screenHeight/4, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)

        blueBackground()
        binding.blueBackground.setImageBitmap(mBitmap)
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