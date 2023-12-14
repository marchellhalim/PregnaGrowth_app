package com.example.pregnagrowth.ui.main

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.example.pregnagrowth.R
import com.example.pregnagrowth.ViewModelFactory
import com.example.pregnagrowth.databinding.ActivityMainBinding
import com.example.pregnagrowth.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
                setupView()
                setupAction()
            }
        }
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

        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        mBitmap = Bitmap.createBitmap(screenWidth, screenHeight/3, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)

        blueBackground()
        binding.blueBackground.setImageBitmap(mBitmap)
    }

    private fun setupAction() {
        binding.ivProfile.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Keluar")
                setMessage("Apakah Anda yakin ingin keluar?")
                setPositiveButton("YA") { _, _ ->
                    viewModel.logout()
                }
                setNegativeButton("TIDAK") { dialog, _ ->
                    dialog.cancel()
                }
                create()
                show()
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
        mPath.arcTo(right - 2*cornerRadiusBottomRight, bottom - 2*cornerRadiusBottomRight, right, bottom, 0f, 90f, false)
        // Line to bottom-left corner with rounded edge
        mPath.arcTo(left, bottom - 2*cornerRadiusBottomLeft, left + 2*cornerRadiusBottomLeft, bottom, 90f, 90f, false)
        // Line back to top-left corner
        mPath.lineTo(left, top)

        mPaint.color = ResourcesCompat.getColor(resources, R.color.primary_blue, null)
        mCanvas.drawPath(mPath, mPaint)

    }


}