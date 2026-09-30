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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HindiFont
import com.example.data.model.PresetRepository
import com.example.data.model.TextStylePreset
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.SecondaryCoral
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

private data class FontCategory(
    val id: String,
    val title: String,
    val icon: String,
    val fonts: Set<HindiFont>
)

private val FONT_CATEGORIES = listOf(
    FontCategory(
        id = "all",
        title = "सभी फ़ॉन्ट्स",
        icon = "✨",
        fonts = HindiFont.values().toSet()
    ),
    FontCategory(
        id = "heritage",
        title = "पारंपरिक व शास्त्रीय",
        icon = "🛕",
        fonts = setOf(
            HindiFont.ADISHILA,
            HindiFont.TIRO_DEVANAGARI_HINDI,
            HindiFont.ROZHA_ONE
        )
    ),
    FontCategory(
        id = "calligraphy",
        title = "सुलेख व कलम",
        icon = "✒️",
        fonts = setOf(
            HindiFont.KALAM,
            HindiFont.YATRA_ONE
        )
    ),
    FontCategory(
        id = "festive",
        title = "उत्सव व हेडलाइन",
        icon = "🎉",
        fonts = setOf(
            HindiFont.MODAK,
            HindiFont.KHAND
        )
    ),
    FontCategory(
        id = "modern",
        title = "क्लीन व आधुनिक",
        icon = "📰",
        fonts = setOf(
            HindiFont.KOHINOOR_DEVANAGARI,
            HindiFont.NOTO_SERIF_DEVANAGARI,
            HindiFont.NOTO_SANS_DEVANAGARI,
            HindiFont.POPPINS,
            HindiFont.MUKTA
        )
    )
)

private val PREVIEW_BACKGROUNDS = listOf(
    0xFF12121E to "डार्क वेलवेट",
    0xFF2A1C0E to "रॉयल गोल्ड",
    0xFF081C24 to "डीप टील",
    0xFF050505 to "चारकोल ब्लैक"
)

private val SAMPLE_PROMPTS = listOf(
    "शुभ प्रभात 🌅",
    "ॐ नमः शिवाय 🔱",
    "राधे राधे 🦚",
    "सत्यमेव जयते 🚩",
    "हौसले की उड़ान 🌟"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontPickerSheet(
    currentText: String = "शुभ प्रभात आपका दिन मंगलमय हो",
    activeFont: HindiFont,
    activeBold: Boolean = false,
    activeItalic: Boolean = false,
    onApply: (HindiFont, TextStylePreset?, Boolean, Boolean, Float) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val focusManager = LocalFocusManager.current

    var selectedFont by remember { mutableStateOf(activeFont) }
    var selectedPreset by remember { mutableStateOf<TextStylePreset?>(null) }
    var isBold by remember { mutableStateOf(activeBold) }
    var isItalic by remember { mutableStateOf(activeItalic) }
    var previewFontSizeSp by remember { mutableFloatStateOf(30f) }
    var previewText by remember { mutableStateOf(if (currentText.isBlank()) "सुंदर विचार और कलात्मक रचना" else currentText) }
    var isEditingCustomText by remember { mutableStateOf(false) }
    var selectedCategoryId by remember { mutableStateOf("all") }
    var bgThemeIndex by remember { mutableStateOf(0) }

    val currentCategory = FONT_CATEGORIES.first { it.id == selectedCategoryId }
    val displayedFonts = HindiFont.values().filter { it in currentCategory.fonts }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = DarkSurface,
        scrimColor = Color(0x99000000)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 1. TOP HEADER BAR (With persistent OK / Apply button!)
            Surface(
                color = DarkSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "फ़ॉन्ट व टाइपोग्राफी",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(PrimaryPurple.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "12 फ़ॉन्ट्स",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryPurple
                                )
                            }
                        }
                        Text(
                            text = "चुना गया: ${selectedFont.hindiName} (${selectedFont.title})",
                            fontSize = 12.sp,
                            color = PrimaryPurple,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // TOP OK / APPLY BUTTON (Always visible at the top!)
                        Button(
                            onClick = {
                                onApply(
                                    selectedFont,
                                    selectedPreset,
                                    isBold,
                                    isItalic,
                                    previewFontSizeSp
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "OK / चुनें",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                        }
                    }
                }
            }

            // 2. SCROLLABLE CONTENT BODY (Scrolls smoothly on all screen sizes!)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // HERO LIVE PREVIEW CARD
                val activeBg = PREVIEW_BACKGROUNDS[bgThemeIndex].first
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(activeBg))
                        .border(1.5.dp, PrimaryPurple.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                        .padding(12.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Preview top info bar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = selectedFont.hindiName,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryPurple
                                )
                                Text(
                                    text = " • ${selectedFont.subtitle}",
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            // Theme switcher pill
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.Black.copy(alpha = 0.4f))
                                    .clickable {
                                        bgThemeIndex = (bgThemeIndex + 1) % PREVIEW_BACKGROUNDS.size
                                    }
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    Icons.Default.Palette,
                                    contentDescription = null,
                                    tint = PrimaryPurple,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = PREVIEW_BACKGROUNDS[bgThemeIndex].second,
                                    fontSize = 10.sp,
                                    color = TextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // The Text Render Area
                        val effectiveTextColor = if (selectedPreset != null) {
                            Color(selectedPreset!!.textColor)
                        } else {
                            Color.White
                        }

                        val effectiveShadow = if (selectedPreset?.shadow != null) {
                            Shadow(
                                color = Color(selectedPreset!!.shadow!!.color),
                                offset = androidx.compose.ui.geometry.Offset(
                                    selectedPreset!!.shadow!!.offsetX,
                                    selectedPreset!!.shadow!!.offsetY
                                ),
                                blurRadius = selectedPreset!!.shadow!!.blurRadius
                            )
                        } else {
                            Shadow(
                                color = Color(0x99000000),
                                offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                                blurRadius = 6f
                            )
                        }

                        val effectiveBold = selectedPreset?.isBold ?: isBold

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(84.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (selectedPreset?.background != null) {
                                        Color(selectedPreset!!.background!!.color)
                                    } else {
                                        Color.Transparent
                                    }
                                )
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = previewText,
                                fontFamily = selectedFont.toFontFamily(),
                                fontSize = previewFontSizeSp.sp,
                                fontWeight = if (effectiveBold) FontWeight.Bold else FontWeight.Normal,
                                fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal,
                                textAlign = TextAlign.Center,
                                color = effectiveTextColor,
                                style = TextStyle(shadow = effectiveShadow),
                                maxLines = 2,
                                lineHeight = (previewFontSizeSp * 1.25f).sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Toolbar under preview: Bold, Italic, Font Size controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isBold) PrimaryPurple else Color(0x33FFFFFF))
                                        .clickable { isBold = !isBold },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.FormatBold,
                                        contentDescription = "Bold",
                                        tint = Color.White,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isItalic) PrimaryPurple else Color(0x33FFFFFF))
                                        .clickable { isItalic = !isItalic },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.FormatItalic,
                                        contentDescription = "Italic",
                                        tint = Color.White,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "${previewFontSizeSp.toInt()}sp",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                                Box(
                                    modifier = Modifier
                                        .size(26.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0x33FFFFFF))
                                        .clickable {
                                            if (previewFontSizeSp > 20f) previewFontSizeSp -= 4f
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("A-", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                                Box(
                                    modifier = Modifier
                                        .size(26.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0x33FFFFFF))
                                        .clickable {
                                            if (previewFontSizeSp < 56f) previewFontSizeSp += 4f
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("A+", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // SAMPLE PREVIEW TEXT CHIPS
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterChip(
                        selected = !isEditingCustomText && previewText == currentText,
                        onClick = {
                            previewText = currentText
                            isEditingCustomText = false
                        },
                        modifier = Modifier.height(28.dp),
                        label = { Text("मूल टेक्स्ट", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = DarkBackground,
                            selectedContainerColor = PrimaryPurple,
                            labelColor = TextSecondary,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )

                    SAMPLE_PROMPTS.forEach { sample ->
                        FilterChip(
                            selected = !isEditingCustomText && previewText == sample,
                            onClick = {
                                previewText = sample
                                isEditingCustomText = false
                            },
                            modifier = Modifier.height(28.dp),
                            label = { Text(sample, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = DarkBackground,
                                selectedContainerColor = PrimaryPurple,
                                labelColor = TextSecondary,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(14.dp)
                        )
                    }

                    FilterChip(
                        selected = isEditingCustomText,
                        onClick = { isEditingCustomText = !isEditingCustomText },
                        modifier = Modifier.height(28.dp),
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(11.dp))
                                Spacer(modifier = Modifier.width(3.dp))
                                Text("कस्टम टाइप", fontSize = 11.sp)
                            }
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = DarkBackground,
                            selectedContainerColor = SecondaryCoral,
                            labelColor = TextSecondary,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                }

                if (isEditingCustomText) {
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = previewText,
                        onValueChange = { previewText = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("यहाँ अपना टेक्स्ट लिखें...", fontSize = 12.sp) },
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryPurple,
                            unfocusedBorderColor = DarkBorder,
                            focusedContainerColor = DarkBackground,
                            unfocusedContainerColor = DarkBackground,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ARTISTIC STYLE PRESETS CAROUSEL
                Text(
                    text = "कलात्मक शैली (Artistic Styles):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ArtisticStyleChip(
                        title = "सादा मूल",
                        icon = "✨",
                        isSelected = selectedPreset == null,
                        onClick = { selectedPreset = null }
                    )

                    PresetRepository.PRESETS.forEach { preset ->
                        ArtisticStyleChip(
                            title = preset.hindiName,
                            icon = preset.badgeIcon,
                            isSelected = selectedPreset?.id == preset.id,
                            onClick = {
                                selectedPreset = preset
                                selectedFont = preset.font
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // FONT CATEGORY CHIPS
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FONT_CATEGORIES.forEach { category ->
                        val isSelected = category.id == selectedCategoryId
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategoryId = category.id },
                            modifier = Modifier.height(28.dp),
                            label = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(category.icon, fontSize = 11.sp)
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(category.title, fontSize = 11.sp)
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = DarkBackground,
                                selectedContainerColor = PrimaryPurple,
                                labelColor = TextSecondary,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // FONT CARDS LIST (With direct OK action on each card!)
                Text(
                    text = "फ़ॉन्ट चुनें (${displayedFonts.size} उपलब्ध):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    displayedFonts.forEach { font ->
                        val isSelected = font == selectedFont
                        FontTypographyCard(
                            font = font,
                            sampleText = previewText,
                            isSelected = isSelected,
                            preset = selectedPreset,
                            onClick = {
                                selectedFont = font
                            },
                            onApplyDirect = {
                                selectedFont = font
                                onApply(
                                    font,
                                    selectedPreset,
                                    isBold,
                                    isItalic,
                                    previewFontSizeSp
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // 3. BOTTOM STICKY ACTION FOOTER (Always visible pinned at the bottom!)
            Surface(
                color = DarkSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
                    ) {
                        Text("रद्द करें", color = TextSecondary, fontSize = 14.sp)
                    }

                    Button(
                        onClick = {
                            onApply(
                                selectedFont,
                                selectedPreset,
                                isBold,
                                isItalic,
                                previewFontSizeSp
                            )
                        },
                        modifier = Modifier
                            .weight(2f)
                            .height(46.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "✓ ${selectedFont.hindiName} लागू करें (OK)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArtisticStyleChip(
    title: String,
    icon: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) PrimaryPurple.copy(alpha = 0.25f) else DarkBackground,
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) PrimaryPurple else DarkBorder
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) PrimaryPurple else TextSecondary
            )
        }
    }
}

@Composable
private fun FontTypographyCard(
    font: HindiFont,
    sampleText: String,
    isSelected: Boolean,
    preset: TextStylePreset?,
    onClick: () -> Unit,
    onApplyDirect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) DarkSurfaceVariant else DarkBackground)
            .border(
                width = if (isSelected) 1.8.dp else 1.dp,
                color = if (isSelected) PrimaryPurple else DarkBorder,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = font.hindiName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) PrimaryPurple else TextPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "(${font.title})",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                // The text sample previewed in this font
                val textColor = if (preset != null) Color(preset.textColor) else Color(0xFFE2E0EE)
                Text(
                    text = sampleText,
                    fontFamily = font.toFontFamily(),
                    fontSize = 16.sp,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Direct "OK / चुनें" button right on the card!
            Button(
                onClick = onApplyDirect,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) PrimaryPurple else Color(0x336C63FF)
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Icon(
                    Icons.Default.Done,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = if (isSelected) "OK" else "चुनें",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
