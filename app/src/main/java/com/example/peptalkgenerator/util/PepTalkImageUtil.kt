package com.example.peptalkgenerator.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.net.Uri
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun createPepTalkShareUri(context: Context, pepTalkText: String): Uri {
    val width = 1080
    val height = 1080
    val padding = 80f
    val cornerRadius = 40f

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Background — purple (Purple40: 0xFF6650A4)
    val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF6650A4.toInt()
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

    // Card background — white with rounded corners
    val cardPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }
    val cardRect = RectF(padding, padding, width - padding, height - padding)
    canvas.drawRoundRect(cardRect, cornerRadius, cornerRadius, cardPaint)

    // App name at top of card
    val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF6650A4.toInt()
        textSize = 52f
        typeface = Typeface.DEFAULT_BOLD
        textAlign = Paint.Align.CENTER
    }
    canvas.drawText("Pep Talk Generator", width / 2f, padding + 80f, titlePaint)

    // Divider line
    val dividerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFFD0BCFF.toInt()
        strokeWidth = 3f
    }
    val dividerY = padding + 110f
    canvas.drawLine(padding + 40f, dividerY, width - padding - 40f, dividerY, dividerPaint)

    // PepTalk text — wrapped inside the card
    val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF1C1B1F.toInt()
        textSize = 72f
        typeface = Typeface.DEFAULT
    }
    val textWidth = (width - padding * 2 - 80).toInt()
    val staticLayout = StaticLayout.Builder
        .obtain(pepTalkText, 0, pepTalkText.length, textPaint, textWidth)
        .setAlignment(Layout.Alignment.ALIGN_CENTER)
        .setLineSpacing(8f, 1f)
        .build()

    // Center the text block vertically within the card (below divider)
    val textAreaTop = dividerY + 40f
    val textAreaBottom = height - padding - 80f
    val textAreaHeight = textAreaBottom - textAreaTop
    val textBlockHeight = staticLayout.height.toFloat()
    val textY = textAreaTop + (textAreaHeight - textBlockHeight) / 2f

    canvas.save()
    canvas.translate(padding + 40f, textY.coerceAtLeast(textAreaTop))
    staticLayout.draw(canvas)
    canvas.restore()

    // Save to cache and return URI
    val file = File(context.cacheDir, "pep_talk_share.png")
    FileOutputStream(file).use { fos ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
    }
    bitmap.recycle()

    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}
