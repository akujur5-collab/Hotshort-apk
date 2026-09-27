package com.example.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import com.example.R
import com.example.data.model.CanvasRatio
import com.example.data.model.CanvasTextAlign
import com.example.data.model.HindiFont
import com.example.data.model.ImageFilterType
import com.example.data.model.StickerLayer
import com.example.data.model.TextLayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object BitmapExporter {

    fun getFontTypeface(context: Context, font: HindiFont): Typeface {
        val resId = when (font) {
            HindiFont.POPPINS -> R.font.poppins
            HindiFont.MUKTA -> R.font.mukta
            HindiFont.KALAM -> R.font.kalam
            HindiFont.ROZHA_ONE -> R.font.rozha_one
            HindiFont.YATRA_ONE -> R.font.yatra_one
            HindiFont.SANS_SERIF -> null
            HindiFont.SERIF -> null
        }
        return if (resId != null) {
            try {
                ResourcesCompat.getFont(context, resId) ?: Typeface.DEFAULT
            } catch (e: Exception) {
                Typeface.DEFAULT
            }
        } else {
            when (font) {
                HindiFont.SERIF -> Typeface.SERIF
                else -> Typeface.SANS_SERIF
            }
        }
    }

    suspend fun renderFullResolutionBitmap(
        context: Context,
        baseBitmap: Bitmap?,
        solidBackgroundStart: Long,
        solidBackgroundEnd: Long,
        ratio: CanvasRatio,
        targetWidth: Int = 1080,
        textLayers: List<TextLayer>,
        stickerLayers: List<StickerLayer>,
        brightness: Float,
        contrast: Float,
        saturation: Float,
        filterType: ImageFilterType
    ): Bitmap = withContext(Dispatchers.Default) {
        val height = when (ratio) {
            CanvasRatio.RATIO_1_1 -> targetWidth
            CanvasRatio.RATIO_9_16 -> (targetWidth * 16f / 9f).toInt()
            CanvasRatio.RATIO_4_5 -> (targetWidth * 5f / 4f).toInt()
            CanvasRatio.RATIO_16_9 -> (targetWidth * 9f / 16f).toInt()
            CanvasRatio.RATIO_FREE -> {
                if (baseBitmap != null && baseBitmap.width > 0) {
                    (targetWidth * (baseBitmap.height.toFloat() / baseBitmap.width)).toInt()
                } else {
                    targetWidth
                }
            }
        }

        val output = Bitmap.createBitmap(targetWidth, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        // 1. Draw Background (Image or Gradient)
        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        if (baseBitmap != null) {
            // Apply image filters: brightness, contrast, saturation, and filter style
            val filterMatrix = createColorMatrix(brightness, contrast, saturation, filterType)
            bgPaint.colorFilter = ColorMatrixColorFilter(filterMatrix)

            // Scale and center-crop the bitmap to fill the canvas
            val scale = maxOf(
                targetWidth.toFloat() / baseBitmap.width,
                height.toFloat() / baseBitmap.height
            )
            val scaledWidth = baseBitmap.width * scale
            val scaledHeight = baseBitmap.height * scale
            val left = (targetWidth - scaledWidth) / 2f
            val top = (height - scaledHeight) / 2f

            canvas.drawBitmap(
                baseBitmap,
                null,
                RectF(left, top, left + scaledWidth, top + scaledHeight),
                bgPaint
            )
        } else {
            // Draw Gradient / Solid Background
            val gradient = LinearGradient(
                0f, 0f, targetWidth.toFloat(), height.toFloat(),
                solidBackgroundStart.toInt(),
                solidBackgroundEnd.toInt(),
                Shader.TileMode.CLAMP
            )
            bgPaint.shader = gradient
            canvas.drawRect(0f, 0f, targetWidth.toFloat(), height.toFloat(), bgPaint)
        }

        // 2. Draw Text Layers
        textLayers.forEach { layer ->
            drawTextLayer(context, canvas, layer, targetWidth, height)
        }

        // 3. Draw Sticker Layers
        stickerLayers.forEach { sticker ->
            drawStickerLayer(canvas, sticker, targetWidth, height)
        }

        output
    }

    private fun createColorMatrix(
        brightness: Float, // 0.5 to 1.5, default 1.0
        contrast: Float,   // 0.5 to 1.5, default 1.0
        saturation: Float, // 0.0 to 2.0, default 1.0
        filterType: ImageFilterType
    ): ColorMatrix {
        val matrix = ColorMatrix()

        // Saturation
        matrix.setSaturation(saturation)

        // Contrast and Brightness
        val scale = contrast
        val translate = (brightness - 1.0f) * 128f + (1f - scale) * 128f * 0.5f
        val contrastMatrix = ColorMatrix(
            floatArrayOf(
                scale, 0f, 0f, 0f, translate,
                0f, scale, 0f, 0f, translate,
                0f, 0f, scale, 0f, translate,
                0f, 0f, 0f, 1f, 0f
            )
        )
        matrix.postConcat(contrastMatrix)

        // Preset filter adjustments
        val filterSpecific = when (filterType) {
            ImageFilterType.NORMAL -> null
            ImageFilterType.VIVID -> ColorMatrix().apply {
                setSaturation(1.35f)
            }
            ImageFilterType.WARM_GOLD -> ColorMatrix(
                floatArrayOf(
                    1.2f, 0f, 0f, 0f, 15f,
                    0f, 1.05f, 0f, 0f, 5f,
                    0f, 0f, 0.85f, 0f, -10f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
            ImageFilterType.COOL_BLISS -> ColorMatrix(
                floatArrayOf(
                    0.85f, 0f, 0f, 0f, -10f,
                    0f, 1.0f, 0f, 0f, 5f,
                    0f, 0f, 1.25f, 0f, 20f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
            ImageFilterType.RETRO_BW -> ColorMatrix().apply {
                setSaturation(0f)
            }
            ImageFilterType.SEPIA_VINTAGE -> ColorMatrix(
                floatArrayOf(
                    0.393f, 0.769f, 0.189f, 0f, 0f,
                    0.349f, 0.686f, 0.168f, 0f, 0f,
                    0.272f, 0.534f, 0.131f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
            ImageFilterType.CYBER_NEON -> ColorMatrix(
                floatArrayOf(
                    1.3f, 0f, 0f, 0f, 25f,
                    0f, 0.9f, 0f, 0f, -15f,
                    0f, 0f, 1.4f, 0f, 35f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
            ImageFilterType.SUNSET_GLOW -> ColorMatrix(
                floatArrayOf(
                    1.25f, 0f, 0f, 0f, 30f,
                    0f, 0.95f, 0f, 0f, 0f,
                    0f, 0f, 0.8f, 0f, -20f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        }

        if (filterSpecific != null) {
            matrix.postConcat(filterSpecific)
        }

        return matrix
    }

    private fun drawTextLayer(
        context: Context,
        canvas: Canvas,
        layer: TextLayer,
        canvasWidth: Int,
        canvasHeight: Int
    ) {
        val typeface = getFontTypeface(context, layer.font)
        val style = when {
            layer.isBold && layer.isItalic -> Typeface.BOLD_ITALIC
            layer.isBold -> Typeface.BOLD
            layer.isItalic -> Typeface.ITALIC
            else -> Typeface.NORMAL
        }
        val styledTypeface = Typeface.create(typeface, style)

        // Scale factor relative to reference 1080p width
        val baseScale = canvasWidth / 400f
        val textSizePx = layer.fontSizeSp * baseScale * layer.scale

        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            this.typeface = styledTypeface
            this.textSize = textSizePx
            this.color = layer.textColor.toInt()
            this.isUnderlineText = layer.isUnderline
            this.alpha = (layer.opacity * 255).toInt().coerceIn(0, 255)
        }

        val alignment = when (layer.alignment) {
            CanvasTextAlign.START -> Layout.Alignment.ALIGN_NORMAL
            CanvasTextAlign.CENTER -> Layout.Alignment.ALIGN_CENTER
            CanvasTextAlign.END -> Layout.Alignment.ALIGN_OPPOSITE
        }

        val maxTextWidth = (canvasWidth * 0.85f).toInt()
        val staticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(layer.text, 0, layer.text.length, textPaint, maxTextWidth)
                .setAlignment(alignment)
                .setIncludePad(true)
                .build()
        } else {
            @Suppress("DEPRECATION")
            StaticLayout(
                layer.text, textPaint, maxTextWidth,
                alignment, 1.0f, 0.0f, true
            )
        }

        val textWidth = staticLayout.width.toFloat()
        val textHeight = staticLayout.height.toFloat()

        val posX = layer.x * canvasWidth
        val posY = layer.y * canvasHeight

        canvas.save()
        canvas.translate(posX, posY)
        canvas.rotate(layer.rotation)

        // Draw Background chip if configured
        val bgConfig = layer.background
        if (bgConfig != null && bgConfig.color != 0L) {
            val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = bgConfig.color.toInt()
                this.style = Paint.Style.FILL
            }
            val padX = bgConfig.paddingHorizontal * baseScale
            val padY = bgConfig.paddingVertical * baseScale
            val rect = RectF(
                -textWidth / 2f - padX,
                -textHeight / 2f - padY,
                textWidth / 2f + padX,
                textHeight / 2f + padY
            )
            canvas.drawRoundRect(rect, bgConfig.cornerRadius * baseScale, bgConfig.cornerRadius * baseScale, bgPaint)
        }

        // Draw Stroke / Outline if configured
        val strokeConfig = layer.stroke
        if (strokeConfig != null && strokeConfig.strokeWidth > 0f) {
            val strokePaint = TextPaint(textPaint).apply {
                this.style = Paint.Style.STROKE
                this.strokeWidth = strokeConfig.strokeWidth * baseScale * 2f
                this.color = strokeConfig.color.toInt()
                clearShadowLayer()
            }
            val strokeLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StaticLayout.Builder.obtain(layer.text, 0, layer.text.length, strokePaint, maxTextWidth)
                    .setAlignment(alignment)
                    .setIncludePad(true)
                    .build()
            } else {
                @Suppress("DEPRECATION")
                StaticLayout(layer.text, strokePaint, maxTextWidth, alignment, 1.0f, 0.0f, true)
            }
            canvas.save()
            canvas.translate(-textWidth / 2f, -textHeight / 2f)
            strokeLayout.draw(canvas)
            canvas.restore()
        }

        // Apply Shadow if configured
        val shadowConfig = layer.shadow
        if (shadowConfig != null && shadowConfig.blurRadius > 0f) {
            textPaint.setShadowLayer(
                shadowConfig.blurRadius * baseScale,
                shadowConfig.offsetX * baseScale,
                shadowConfig.offsetY * baseScale,
                shadowConfig.color.toInt()
            )
        }

        // Draw Main Text
        canvas.save()
        canvas.translate(-textWidth / 2f, -textHeight / 2f)
        staticLayout.draw(canvas)
        canvas.restore()

        canvas.restore()
    }

    private fun drawStickerLayer(
        canvas: Canvas,
        sticker: StickerLayer,
        canvasWidth: Int,
        canvasHeight: Int
    ) {
        val baseScale = canvasWidth / 400f
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = sticker.fontSizeSp * baseScale * sticker.scale
            textAlign = Paint.Align.CENTER
            color = android.graphics.Color.WHITE
        }

        val posX = sticker.x * canvasWidth
        val posY = sticker.y * canvasHeight

        canvas.save()
        canvas.translate(posX, posY)
        canvas.rotate(sticker.rotation)
        canvas.drawText(sticker.text, 0f, paint.textSize / 3f, paint)
        canvas.restore()
    }

    suspend fun saveBitmapToGallery(context: Context, bitmap: Bitmap, title: String): Uri? = withContext(Dispatchers.IO) {
        val filename = "ChitraLekh_${System.currentTimeMillis()}.jpg"
        var uri: Uri? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/ChitraLekh")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }

            val resolver = context.contentResolver
            uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (uri != null) {
                resolver.openOutputStream(uri)?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)
                }
                contentValues.clear()
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/ChitraLekh"
            val file = File(imagesDir)
            if (!file.exists()) file.mkdirs()
            val image = File(file, filename)
            FileOutputStream(image).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
            }
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DATA, image.absolutePath)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }
            uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        }

        uri
    }

    suspend fun saveBitmapToCacheForShare(context: Context, bitmap: Bitmap): Uri = withContext(Dispatchers.IO) {
        val cachePath = File(context.cacheDir, "images")
        if (!cachePath.exists()) cachePath.mkdirs()
        val file = File(cachePath, "chitralekh_share_${System.currentTimeMillis()}.jpg")
        val stream: OutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)
        stream.close()

        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    suspend fun saveProjectPreview(context: Context, bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        val dir = File(context.filesDir, "project_previews")
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "preview_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            val preview = Bitmap.createScaledBitmap(bitmap, 400, (400f * bitmap.height / bitmap.width).toInt(), true)
            preview.compress(Bitmap.CompressFormat.JPEG, 85, out)
        }
        file.absolutePath
    }
}
