package com.example.ui.editor.components

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CanvasRatio
import com.example.data.model.CanvasTextAlign
import com.example.data.model.ImageFilterType
import com.example.data.model.StickerLayer
import com.example.data.model.TextLayer
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.SecondaryCoral
import kotlin.math.roundToInt

@Composable
fun CanvasView(
    baseBitmap: Bitmap?,
    backgroundStart: Long,
    backgroundEnd: Long,
    ratio: CanvasRatio,
    textLayers: List<TextLayer>,
    stickerLayers: List<StickerLayer>,
    selectedTextId: String?,
    selectedStickerId: String?,
    brightness: Float,
    contrast: Float,
    saturation: Float,
    activeFilter: ImageFilterType,
    onSelectText: (String?) -> Unit,
    onSelectSticker: (String?) -> Unit,
    onUpdateTextPosition: (String, Float, Float, Float, Float) -> Unit, // id, dxNorm, dyNorm, scaleDelta, rotDelta
    onUpdateStickerPosition: (String, Float, Float, Float, Float) -> Unit,
    onEditText: () -> Unit,
    onDuplicateLayer: () -> Unit,
    onDeleteLayer: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Aspect ratio calculation
    val aspect = when (ratio) {
        CanvasRatio.RATIO_1_1 -> 1.0f
        CanvasRatio.RATIO_9_16 -> 9f / 16f
        CanvasRatio.RATIO_4_5 -> 4f / 5f
        CanvasRatio.RATIO_16_9 -> 16f / 9f
        CanvasRatio.RATIO_FREE -> {
            if (baseBitmap != null && baseBitmap.height > 0) {
                baseBitmap.width.toFloat() / baseBitmap.height
            } else {
                1.0f
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .aspectRatio(aspect)
                .shadow(16.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            onSelectText(null)
                            onSelectSticker(null)
                        }
                    )
                }
                .testTag("editor_canvas")
        ) {
            val canvasWidthPx = constraints.maxWidth.toFloat()
            val canvasHeightPx = constraints.maxHeight.toFloat()

            // 1. Canvas Background Layer
            if (baseBitmap != null) {
                val colorFilter = remember(brightness, contrast, saturation, activeFilter) {
                    createComposeColorFilter(brightness, contrast, saturation, activeFilter)
                }
                androidx.compose.foundation.Image(
                    bitmap = baseBitmap.asImageBitmap(),
                    contentDescription = "Background Photo",
                    contentScale = ContentScale.Crop,
                    colorFilter = colorFilter,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(backgroundStart), Color(backgroundEnd))
                            )
                        )
                )
            }

            // 2. Sticker Layers
            stickerLayers.forEach { sticker ->
                val isSelected = sticker.id == selectedStickerId
                StickerItemView(
                    sticker = sticker,
                    isSelected = isSelected,
                    canvasWidth = canvasWidthPx,
                    canvasHeight = canvasHeightPx,
                    onSelect = { onSelectSticker(sticker.id) },
                    onTransform = { dX, dY, dScale, dRot ->
                        onUpdateStickerPosition(
                            sticker.id,
                            dX / canvasWidthPx,
                            dY / canvasHeightPx,
                            dScale,
                            dRot
                        )
                    },
                    onDelete = onDeleteLayer,
                    onDuplicate = onDuplicateLayer
                )
            }

            // 3. Text Layers
            textLayers.forEach { textLayer ->
                val isSelected = textLayer.id == selectedTextId
                TextItemView(
                    layer = textLayer,
                    isSelected = isSelected,
                    canvasWidth = canvasWidthPx,
                    canvasHeight = canvasHeightPx,
                    onSelect = { onSelectText(textLayer.id) },
                    onTransform = { dX, dY, dScale, dRot ->
                        onUpdateTextPosition(
                            textLayer.id,
                            dX / canvasWidthPx,
                            dY / canvasHeightPx,
                            dScale,
                            dRot
                        )
                    },
                    onEdit = onEditText,
                    onDelete = onDeleteLayer,
                    onDuplicate = onDuplicateLayer
                )
            }
        }
    }
}

@Composable
private fun TextItemView(
    layer: TextLayer,
    isSelected: Boolean,
    canvasWidth: Float,
    canvasHeight: Float,
    onSelect: () -> Unit,
    onTransform: (dx: Float, dy: Float, scale: Float, rotation: Float) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onDuplicate: () -> Unit
) {
    val posX = (layer.x * canvasWidth).roundToInt()
    val posY = (layer.y * canvasHeight).roundToInt()

    val textAlign = when (layer.alignment) {
        CanvasTextAlign.START -> TextAlign.Start
        CanvasTextAlign.CENTER -> TextAlign.Center
        CanvasTextAlign.END -> TextAlign.End
    }

    val shadow = if (layer.shadow != null && layer.shadow.blurRadius > 0f) {
        Shadow(
            color = Color(layer.shadow.color),
            offset = Offset(layer.shadow.offsetX, layer.shadow.offsetY),
            blurRadius = layer.shadow.blurRadius
        )
    } else null

    Box(
        modifier = Modifier
            .offset { IntOffset(posX, posY) }
            .rotate(layer.rotation)
            .scale(layer.scale)
            .pointerInput(layer.id) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    onTransform(pan.x, pan.y, zoom, rotation)
                }
            }
            .pointerInput(layer.id + "_tap") {
                detectTapGestures(
                    onTap = { onSelect() },
                    onDoubleTap = {
                        onSelect()
                        onEdit()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Text Container
        Box(
            modifier = Modifier
                .offset(x = (-50).dp, y = (-20).dp) // Center roughly
                .then(
                    if (layer.background != null && layer.background.color != 0L) {
                        Modifier.background(
                            Color(layer.background.color),
                            RoundedCornerShape(layer.background.cornerRadius.dp)
                        ).padding(
                            horizontal = layer.background.paddingHorizontal.dp,
                            vertical = layer.background.paddingVertical.dp
                        )
                    } else Modifier
                )
                .then(
                    if (isSelected) {
                        Modifier
                            .border(
                                1.5.dp,
                                PrimaryPurple,
                                RoundedCornerShape(6.dp)
                            )
                            .padding(4.dp)
                    } else Modifier.padding(4.dp)
                )
        ) {
            Text(
                text = layer.text,
                fontFamily = layer.font.toFontFamily(),
                fontSize = layer.fontSizeSp.sp,
                fontWeight = if (layer.isBold) FontWeight.Bold else FontWeight.Normal,
                fontStyle = if (layer.isItalic) FontStyle.Italic else FontStyle.Normal,
                textDecoration = if (layer.isUnderline) TextDecoration.Underline else TextDecoration.None,
                color = Color(layer.textColor).copy(alpha = layer.opacity),
                textAlign = textAlign,
                style = androidx.compose.ui.text.TextStyle(
                    shadow = shadow,
                    letterSpacing = layer.letterSpacingSp.sp
                )
            )

            // Quick Actions Bubble when selected
            if (isSelected) {
                FloatingActionStrip(
                    onEdit = onEdit,
                    onDuplicate = onDuplicate,
                    onDelete = onDelete,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(y = (-40).dp)
                )
            }
        }
    }
}

@Composable
private fun StickerItemView(
    sticker: StickerLayer,
    isSelected: Boolean,
    canvasWidth: Float,
    canvasHeight: Float,
    onSelect: () -> Unit,
    onTransform: (dx: Float, dy: Float, scale: Float, rotation: Float) -> Unit,
    onDelete: () -> Unit,
    onDuplicate: () -> Unit
) {
    val posX = (sticker.x * canvasWidth).roundToInt()
    val posY = (sticker.y * canvasHeight).roundToInt()

    Box(
        modifier = Modifier
            .offset { IntOffset(posX, posY) }
            .rotate(sticker.rotation)
            .scale(sticker.scale)
            .pointerInput(sticker.id) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    onTransform(pan.x, pan.y, zoom, rotation)
                }
            }
            .pointerInput(sticker.id + "_tap") {
                detectTapGestures(onTap = { onSelect() })
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset(x = (-20).dp, y = (-20).dp)
                .then(
                    if (isSelected) {
                        Modifier
                            .border(1.5.dp, PrimaryPurple, RoundedCornerShape(8.dp))
                            .padding(4.dp)
                    } else Modifier.padding(4.dp)
                )
        ) {
            Text(
                text = sticker.text,
                fontSize = sticker.fontSizeSp.sp,
                color = Color.White
            )

            if (isSelected) {
                FloatingActionStrip(
                    onEdit = null,
                    onDuplicate = onDuplicate,
                    onDelete = onDelete,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(y = (-36).dp)
                )
            }
        }
    }
}

@Composable
private fun FloatingActionStrip(
    onEdit: (() -> Unit)?,
    onDuplicate: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = CircleShape,
        color = Color(0xEE1E1A33),
        shadowElevation = 8.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onEdit != null) {
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit Text",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            IconButton(
                onClick = onDuplicate,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.ContentCopy,
                    contentDescription = "Duplicate Layer",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Delete Layer",
                    tint = SecondaryCoral,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

private fun createComposeColorFilter(
    brightness: Float,
    contrast: Float,
    saturation: Float,
    filterType: ImageFilterType
): ColorFilter {
    val cm = android.graphics.ColorMatrix()
    cm.setSaturation(saturation)

    val scale = contrast
    val translate = (brightness - 1.0f) * 128f + (1f - scale) * 128f * 0.5f
    val contrastMatrix = android.graphics.ColorMatrix(
        floatArrayOf(
            scale, 0f, 0f, 0f, translate,
            0f, scale, 0f, 0f, translate,
            0f, 0f, scale, 0f, translate,
            0f, 0f, 0f, 1f, 0f
        )
    )
    cm.postConcat(contrastMatrix)

    when (filterType) {
        ImageFilterType.VIVID -> cm.postConcat(android.graphics.ColorMatrix().apply { setSaturation(1.35f) })
        ImageFilterType.WARM_GOLD -> cm.postConcat(
            android.graphics.ColorMatrix(
                floatArrayOf(
                    1.2f, 0f, 0f, 0f, 15f,
                    0f, 1.05f, 0f, 0f, 5f,
                    0f, 0f, 0.85f, 0f, -10f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        )
        ImageFilterType.COOL_BLISS -> cm.postConcat(
            android.graphics.ColorMatrix(
                floatArrayOf(
                    0.85f, 0f, 0f, 0f, -10f,
                    0f, 1.0f, 0f, 0f, 5f,
                    0f, 0f, 1.25f, 0f, 20f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        )
        ImageFilterType.RETRO_BW -> cm.postConcat(android.graphics.ColorMatrix().apply { setSaturation(0f) })
        ImageFilterType.SEPIA_VINTAGE -> cm.postConcat(
            android.graphics.ColorMatrix(
                floatArrayOf(
                    0.393f, 0.769f, 0.189f, 0f, 0f,
                    0.349f, 0.686f, 0.168f, 0f, 0f,
                    0.272f, 0.534f, 0.131f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        )
        ImageFilterType.CYBER_NEON -> cm.postConcat(
            android.graphics.ColorMatrix(
                floatArrayOf(
                    1.3f, 0f, 0f, 0f, 25f,
                    0f, 0.9f, 0f, 0f, -15f,
                    0f, 0f, 1.4f, 0f, 35f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        )
        ImageFilterType.SUNSET_GLOW -> cm.postConcat(
            android.graphics.ColorMatrix(
                floatArrayOf(
                    1.25f, 0f, 0f, 0f, 30f,
                    0f, 0.95f, 0f, 0f, 0f,
                    0f, 0f, 0.8f, 0f, -20f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        )
        ImageFilterType.NORMAL -> Unit
    }

    return ColorFilter.colorMatrix(ColorMatrix(cm.array))
}
