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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CanvasRatio
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

val BACKGROUND_GRADIENTS = listOf(
    Pair(0xFF140727L, 0xFF2A1B4EL), // Dark Indigo / Violet
    Pair(0xFF0F2027L, 0xFF203A43L), // Midnight Blue
    Pair(0xFF2C3E50L, 0xFF000000L), // Charcoal Black
    Pair(0xFF4A00E0L, 0xFF8E2DE2L), // Electric Purple
    Pair(0xFFFF416CL, 0xFFFF4B2BL), // Sunset Fire
    Pair(0xFF11998EL, 0xFF38EF7DL), // Emerald Green
    Pair(0xFFF12711L, 0xFFF5AF19L), // Amber Gold
    Pair(0xFF232526L, 0xFF414345L), // Sleek Graphite
    Pair(0xFF3A1C71L, 0xFFD76D77L), // Rose Twilight
    Pair(0xFF000428L, 0xFF004E92L)  // Deep Ocean
)

@Composable
fun CanvasRatioPicker(
    selectedRatio: CanvasRatio,
    onRatioSelected: (CanvasRatio) -> Unit,
    onBackgroundSelected: (start: Long, end: Long) -> Unit,
    hasImage: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = "कैनवास अनुपात (Aspect Ratio)",
            color = TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        LazyRow(
            contentPadding = PaddingValues(vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(CanvasRatio.values()) { ratio ->
                val isSelected = ratio == selectedRatio
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) PrimaryPurple else DarkSurfaceVariant)
                        .border(
                            1.dp,
                            if (isSelected) Color.White else DarkBorder,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onRatioSelected(ratio) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = ratio.label,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else TextPrimary
                        )
                        Text(
                            text = ratio.iconLabel,
                            fontSize = 10.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.8f) else TextSecondary
                        )
                    }
                }
            }
        }

        if (!hasImage) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "पृष्ठभूमि ग्रेडिएंट (Canvas Backgrounds)",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            LazyRow(
                contentPadding = PaddingValues(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(BACKGROUND_GRADIENTS) { (start, end) ->
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(start), Color(end))
                                )
                            )
                            .border(1.dp, Color(0x44FFFFFF), CircleShape)
                            .clickable { onBackgroundSelected(start, end) }
                    )
                }
            }
        }
    }
}
