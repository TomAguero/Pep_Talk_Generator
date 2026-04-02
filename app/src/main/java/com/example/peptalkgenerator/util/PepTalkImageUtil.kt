package com.example.peptalkgenerator.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import androidx.core.graphics.createBitmap
import androidx.core.graphics.withTranslation

fun createPepTalkShareUri(context: Context, pepTalkText: String): Uri {
    val width = 1080
    val horizontalPadding = 120f

    // Measure text first so we can size the canvas to 1.5x the text height
    val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFFFFFFFF.toInt()
        textSize = 80f
        typeface = Typeface.DEFAULT_BOLD
    }
    val textWidth = (width - horizontalPadding * 2).toInt()
    val staticLayout = StaticLayout.Builder
        .obtain(pepTalkText, 0, pepTalkText.length, textPaint, textWidth)
        .setAlignment(Layout.Alignment.ALIGN_CENTER)
        .setLineSpacing(12f, 1f)
        .build()

    // Canvas height = 1.5x text height (25% vertical padding on each side)
    val verticalPadding = staticLayout.height * 0.25f
    val height = (staticLayout.height + verticalPadding * 2).toInt()

    val bitmap = createBitmap(width, height)
    val canvas = Canvas(bitmap)

    // Background — purple (Purple40: 0xFF6650A4)
    val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF6A3FD1.toInt()
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

    canvas.withTranslation(horizontalPadding, verticalPadding) {
        staticLayout.draw(this)
    }

    // Save to cache and return URI
    val file = File(context.cacheDir, "pep_talk_share.png")
    FileOutputStream(file).use { fos ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
    }
    bitmap.recycle()

    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}
