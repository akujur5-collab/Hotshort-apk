package com.example.ui.editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Flare
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TextLayer
import com.example.data.model.TextShadowConfig
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.SecondaryCoral
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

private enum class AdjustmentMode(val title: String, val icon: String) {
    OUTER_GLOW("आउटर ग्लो", "🌟"),
    DROP_SHADOW("3D शैडो", "🌑"),
    QUICK_POP("त्वरित प्रभाव", "⚡")
}

private data class QuickShadowPreset(
    val id: String,
    val name: String,
    val icon: String,
    val config: TextShadowConfig?
)

private val QUICK_PRESETS = listOf(
    QuickShadowPreset(
        id = "gold_glow",
        name = "गोल्डन ऑरा",
        icon = "🌟",
        config = TextShadowConfig(color = 0xFFFFD700, offsetX = 0f, offsetY = 0f, blurRadius = 22f)
    ),
    QuickShadowPreset(
        id = "cyan_glow",
        name = "इलेक्ट्रिक स्यान",
        icon = "💎",
        config = TextShadowConfig(color = 0xFF00E5FF, offsetX = 0f, offsetY = 0f, blurRadius = 24f)
    ),
    QuickShadowPreset(
        id = "fire_glow",
        name = "ज्वाला प्रभा",
        icon = "🔥",
        config = TextShadowConfig(color = 0xFFFF5722, offsetX = 0f, offsetY = 0f, blurRadius = 20f)
    ),
    QuickShadowPreset(
        id = "pink_glow",
        name = "साइबर पिंक",
        icon = "🌸",
        config = TextShadowConfig(color = 0xFFFF4081, offsetX = 0f, offsetY = 0f, blurRadius = 22f)
    ),
    QuickShadowPreset(
        id = "purple_glow",
        name = "इलेक्ट्रिक पर्पल",
        icon = "🔮",
        config = TextShadowConfig(color = 0xFF7C4DFF, offsetX = 0f, offsetY = 0f, blurRadius = 22f)
    ),
    QuickShadowPreset(
        id = "white_glow",
        name = "दिव्य श्वेत",
        icon = "⚪",
        config = TextShadowConfig(color = 0xFFFFFFFF, offsetX = 0f, offsetY = 0f, blurRadius = 18f)
    ),
    QuickShadowPreset(
        id = "drop_natural",
        name = "नेचुरल शैडो",
        icon = "🌑",
        config = TextShadowConfig(color = 0xCC000000, offsetX = 3f, offsetY = 4f, blurRadius = 8f)
    ),
    QuickShadowPreset(
        id = "hard_3d",
        name = "3D गहरा उभार",
        icon = "🧱",
        config = TextShadowConfig(color = 0xFF111111, offsetX = 5f, offsetY = 7f, blurRadius = 1.5f)
    ),
    QuickShadowPreset(
        id = "none",
        name = "हटाएं",
        icon = "🚫",
        config = null
    )
)

private val GLOW_COLORS = listOf(
    0xFFFFD700L to "स्वर्ण",
    0xFFFF9800L to "अम्बर",
    0xFF00E5FFL to "स्यान",
    0xFF00E676L to "लाइम",
    0xFFFF4081L to "पिंक",
    0xFFE040FBL to "मैजेंटा",
    0xFF7C4DFFL to "पर्पल",
    0xFFFFFFFFL to "श्वेत"
)

private val SHADOW_COLORS = listOf(
    0xFF000000L to "काला",
    0xFF212121L to "चारकोल",
    0xFFBF360CL to "ज्वाला",
    0xFF311B92L to "रॉयल पर्पल",
    0xFF0D47A1L to "नेवी",
    0xFF3E2723L to "चॉकलेट",
    0xFF1B5E20L to "हरित",
    0xFF616161L to "धूसर"
)

@Composable
fun ShadowGlowAdjustmentView(
    selectedLayer: TextLayer?,
    onUpdateShadow: (TextShadowConfig?) -> Unit,
    modifier: Modifier = Modifier
) {
    if (selectedLayer == null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "कैनवास पर किसी टेक्स्ट पर टैप करें",
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ताकि उसमें आउटर ग्लो या 3D शैडो प्रभाव जोड़ सकें",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }
        return
    }

    var mode by remember { mutableStateOf(AdjustmentMode.OUTER_GLOW) }

    val currentShadow = selectedLayer.shadow
    val blurRadius = currentShadow?.blurRadius ?: 0f
    val offsetX = currentShadow?.offsetX ?: 0f
    val offsetY = currentShadow?.offsetY ?: 0f
    val shadowColor = currentShadow?.color ?: 0xFFFFD700L

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        // Mode Selector Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                AdjustmentMode.values().forEach { m ->
                    val isSelected = m == mode
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            mode = m
                            if (m == AdjustmentMode.OUTER_GLOW) {
                                // Reset offsets to 0 for radial glow
                                val existing = currentShadow ?: TextShadowConfig(color = 0xFFFFD700L, blurRadius = 20f)
                                onUpdateShadow(existing.copy(offsetX = 0f, offsetY = 0f, blurRadius = if (existing.blurRadius == 0f) 20f else existing.blurRadius))
                            } else if (m == AdjustmentMode.DROP_SHADOW) {
                                val existing = currentShadow ?: TextShadowConfig(color = 0xDD000000L, offsetX = 3f, offsetY = 4f, blurRadius = 8f)
                                if (existing.offsetX == 0f && existing.offsetY == 0f) {
                                    onUpdateShadow(existing.copy(offsetX = 3f, offsetY = 4f, blurRadius = 8f))
                                }
                            }
                        },
                        modifier = Modifier.height(30.dp),
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(m.icon, fontSize = 11.sp)
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(m.title, fontSize = 11.sp)
                            }
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = DarkSurfaceVariant,
                            selectedContainerColor = PrimaryPurple,
                            labelColor = TextSecondary,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                }
            }

            // Quick Clear button if shadow is active
            if (currentShadow != null && currentShadow.blurRadius > 0f) {
                Surface(
                    onClick = { onUpdateShadow(null) },
                    shape = RoundedCornerShape(8.dp),
                    color = DarkBackground,
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = null, tint = SecondaryCoral, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("हटाएं", color = SecondaryCoral, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (mode) {
            AdjustmentMode.QUICK_POP -> {
                // Quick Pop FX Presets list
                Text(
                    text = "त्वरित पॉप व ग्लो प्रभाव (One-Tap Presets):",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 2.dp)
                ) {
                    items(QUICK_PRESETS) { preset ->
                        val isSelected = currentShadow == preset.config
                        Surface(
                            onClick = { onUpdateShadow(preset.config) },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) PrimaryPurple.copy(alpha = 0.25f) else DarkSurfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) PrimaryPurple else DarkBorder
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(84.dp)
                                    .padding(vertical = 8.dp, horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(preset.icon, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = preset.name,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) PrimaryPurple else TextPrimary,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }

            AdjustmentMode.OUTER_GLOW -> {
                // Glow Spread Slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🌟 ग्लो फैलाव (Spread):",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    Text(
                        text = "${blurRadius.toInt()}dp",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryPurple
                    )
                }

                Slider(
                    value = blurRadius,
                    onValueChange = { newBlur ->
                        val existing = currentShadow ?: TextShadowConfig(color = 0xFFFFD700L)
                        onUpdateShadow(existing.copy(offsetX = 0f, offsetY = 0f, blurRadius = newBlur))
                    },
                    valueRange = 0f..40f,
                    colors = SliderDefaults.colors(
                        thumbColor = PrimaryPurple,
                        activeTrackColor = PrimaryPurple,
                        inactiveTrackColor = DarkBorder
                    ),
                    modifier = Modifier.height(28.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Glow Color Selection
                Text(
                    text = "ग्लो रंग (Aura Colors):",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GLOW_COLORS.forEach { (colorHex, name) ->
                        val isSelected = currentShadow != null && currentShadow.color == colorHex
                        ColorDot(
                            colorHex = colorHex,
                            name = name,
                            isSelected = isSelected,
                            onClick = {
                                val existing = currentShadow ?: TextShadowConfig(offsetX = 0f, offsetY = 0f, blurRadius = 20f)
                                onUpdateShadow(existing.copy(color = colorHex, blurRadius = if (existing.blurRadius == 0f) 20f else existing.blurRadius))
                            }
                        )
                    }
                }
            }

            AdjustmentMode.DROP_SHADOW -> {
                // 3D Shadow Sliders: Blur, X, Y
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🌑 छाया धुंधलापन (Blur):", fontSize = 11.sp, color = TextSecondary)
                    Text("${blurRadius.toInt()}dp", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryPurple)
                }
                Slider(
                    value = blurRadius,
                    onValueChange = { newBlur ->
                        val existing = currentShadow ?: TextShadowConfig(color = 0xDD000000L, offsetX = 3f, offsetY = 4f)
                        onUpdateShadow(existing.copy(blurRadius = newBlur))
                    },
                    valueRange = 0f..30f,
                    colors = SliderDefaults.colors(
                        thumbColor = PrimaryPurple,
                        activeTrackColor = PrimaryPurple,
                        inactiveTrackColor = DarkBorder
                    ),
                    modifier = Modifier.height(24.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("↔️ क्षैतिज दिशा (X Offset):", fontSize = 11.sp, color = TextSecondary)
                    Text("${offsetX.toInt()}dp", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryPurple)
                }
                Slider(
                    value = offsetX,
                    onValueChange = { newX ->
                        val existing = currentShadow ?: TextShadowConfig(color = 0xDD000000L, blurRadius = 6f)
                        onUpdateShadow(existing.copy(offsetX = newX))
                    },
                    valueRange = -20f..20f,
                    colors = SliderDefaults.colors(
                        thumbColor = PrimaryPurple,
                        activeTrackColor = PrimaryPurple,
                        inactiveTrackColor = DarkBorder
                    ),
                    modifier = Modifier.height(24.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("↕️ ऊर्ध्वाधर दिशा (Y Offset):", fontSize = 11.sp, color = TextSecondary)
                    Text("${offsetY.toInt()}dp", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryPurple)
                }
                Slider(
                    value = offsetY,
                    onValueChange = { newY ->
                        val existing = currentShadow ?: TextShadowConfig(color = 0xDD000000L, blurRadius = 6f)
                        onUpdateShadow(existing.copy(offsetY = newY))
                    },
                    valueRange = -20f..20f,
                    colors = SliderDefaults.colors(
                        thumbColor = PrimaryPurple,
                        activeTrackColor = PrimaryPurple,
                        inactiveTrackColor = DarkBorder
                    ),
                    modifier = Modifier.height(24.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Shadow Colors
                Text("छाया रंग (Shadow Colors):", fontSize = 11.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SHADOW_COLORS.forEach { (colorHex, name) ->
                        val isSelected = currentShadow != null && currentShadow.color == colorHex
                        ColorDot(
                            colorHex = colorHex,
                            name = name,
                            isSelected = isSelected,
                            onClick = {
                                val existing = currentShadow ?: TextShadowConfig(offsetX = 3f, offsetY = 4f, blurRadius = 6f)
                                onUpdateShadow(existing.copy(color = colorHex))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorDot(
    colorHex: Long,
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Color(colorHex))
                .border(
                    width = if (isSelected) 2.5.dp else 1.dp,
                    color = if (isSelected) Color.White else DarkBorder,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = name,
            fontSize = 9.sp,
            color = if (isSelected) PrimaryPurple else TextSecondary,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
