package com.example.ui.editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PresetRepository
import com.example.data.model.TextStylePreset
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun StylePresetsRow(
    activePresetId: String?,
    onSelectPreset: (TextStylePreset) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "टेक्स्ट स्टाइल प्रीसेट (Text Styles)",
                color = TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "12 स्टाइल्स",
                color = PrimaryPurple,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(PresetRepository.PRESETS) { preset ->
                val isSelected = preset.id == activePresetId
                PresetCard(
                    preset = preset,
                    isSelected = isSelected,
                    onClick = { onSelectPreset(preset) }
                )
            }
        }
    }
}

@Composable
fun PresetCard(
    preset: TextStylePreset,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val shadow = if (preset.shadow != null && preset.shadow.blurRadius > 0f) {
        Shadow(
            color = Color(preset.shadow.color),
            offset = Offset(preset.shadow.offsetX, preset.shadow.offsetY),
            blurRadius = preset.shadow.blurRadius
        )
    } else null

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(DarkSurfaceVariant)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) PrimaryPurple else DarkBorder,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "नमस्ते",
                fontFamily = preset.font.toFontFamily(),
                fontSize = 20.sp,
                color = Color(preset.textColor),
                fontWeight = if (preset.isBold) FontWeight.Bold else FontWeight.Normal,
                style = TextStyle(shadow = shadow)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = preset.hindiName,
                fontSize = 11.sp,
                color = if (isSelected) PrimaryPurple else TextSecondary,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
fun ColorPaletteRow(
    selectedColor: Long,
    onColorSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            text = "टेक्स्ट रंग (Colors)",
            color = TextSecondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(PresetRepository.PRESET_COLORS) { colorLong ->
                val isSelected = colorLong == selectedColor
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(colorLong))
                        .border(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) Color.White else Color(0x33FFFFFF),
                            shape = CircleShape
                        )
                        .clickable { onColorSelected(colorLong) }
                )
            }
        }
    }
}
