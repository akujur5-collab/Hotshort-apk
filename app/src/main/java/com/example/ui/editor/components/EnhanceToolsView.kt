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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ImageFilterType
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.SecondaryCoral
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.util.Locale

@Composable
fun EnhanceToolsView(
    brightness: Float,
    contrast: Float,
    saturation: Float,
    activeFilter: ImageFilterType,
    onBrightnessChange: (Float) -> Unit,
    onContrastChange: (Float) -> Unit,
    onSaturationChange: (Float) -> Unit,
    onFilterChange: (ImageFilterType) -> Unit,
    onAutoEnhance: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        // Quick Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onAutoEnhance,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f).height(40.dp)
            ) {
                Icon(
                    Icons.Default.AutoAwesome,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("✨ ऑटो सुधार (Auto)", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }

            OutlinedButton(
                onClick = onReset,
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                modifier = Modifier.weight(0.7f).height(40.dp)
            ) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = TextSecondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("रीसेट", fontSize = 13.sp, color = TextSecondary)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Preset Filters Row
        Text(
            text = "फ़िल्टर इफ़ेक्ट्स (Filters)",
            color = TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        LazyRow(
            contentPadding = PaddingValues(vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ImageFilterType.values()) { filter ->
                val isSelected = filter == activeFilter
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) PrimaryPurple else DarkSurfaceVariant)
                        .border(
                            1.dp,
                            if (isSelected) Color.White else DarkBorder,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onFilterChange(filter) }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = filter.hindiName,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else TextPrimary
                        )
                        Text(
                            text = filter.title,
                            fontSize = 10.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.8f) else TextSecondary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Sliders for Brightness, Contrast, Saturation
        SliderControlRow(
            label = "चमक (Brightness)",
            value = brightness,
            valueRange = 0.5f..1.5f,
            displayValue = String.format(Locale.US, "%.1fx", brightness),
            onValueChange = onBrightnessChange
        )

        SliderControlRow(
            label = "कंट्रास्ट (Contrast)",
            value = contrast,
            valueRange = 0.5f..1.5f,
            displayValue = String.format(Locale.US, "%.1fx", contrast),
            onValueChange = onContrastChange
        )

        SliderControlRow(
            label = "रंग गाढ़ापन (Saturation)",
            value = saturation,
            valueRange = 0.0f..2.0f,
            displayValue = String.format(Locale.US, "%.1fx", saturation),
            onValueChange = onSaturationChange
        )
    }
}

@Composable
fun SliderControlRow(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    displayValue: String,
    onValueChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier.width(130.dp)
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = PrimaryPurple,
                activeTrackColor = PrimaryPurple,
                inactiveTrackColor = DarkBorder
            ),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = displayValue,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            modifier = Modifier.width(42.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}
