package com.example.ui.editor

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.CanvasTextAlign
import com.example.data.model.PresetRepository
import com.example.ui.editor.components.CanvasRatioPicker
import com.example.ui.editor.components.CanvasView
import com.example.ui.editor.components.ColorPaletteRow
import com.example.ui.editor.components.EnhanceToolsView
import com.example.ui.editor.components.ExportSheet
import com.example.ui.editor.components.FontPickerSheet
import com.example.ui.editor.components.QuotesPickerSheet
import com.example.ui.editor.components.ShadowGlowAdjustmentView
import com.example.ui.editor.components.StickerPickerSheet
import com.example.ui.editor.components.StylePresetsRow
import com.example.ui.editor.components.TextEditDialog
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.SecondaryCoral
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    viewModel: EditorViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Photo picker launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setBaseImage(uri)
        }
    }

    BackHandler {
        onNavigateBack()
    }

    val selectedTextLayer = uiState.textLayers.find { it.id == uiState.selectedTextLayerId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "चित्रलेख एडिटर",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "${uiState.textLayers.size} टेक्स्ट परतें (Layers)",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // Undo
                    IconButton(
                        onClick = { viewModel.undo() },
                        enabled = viewModel.canUndo()
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Undo,
                            contentDescription = "Undo",
                            tint = if (viewModel.canUndo()) Color.White else Color(0x44FFFFFF)
                        )
                    }
                    // Redo
                    IconButton(
                        onClick = { viewModel.redo() },
                        enabled = viewModel.canRedo()
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Redo,
                            contentDescription = "Redo",
                            tint = if (viewModel.canRedo()) Color.White else Color(0x44FFFFFF)
                        )
                    }
                    // Export Button
                    Button(
                        onClick = { viewModel.setShowExport(true) },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .testTag("export_button")
                    ) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "सेव करें",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        },
        containerColor = DarkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Interactive Canvas (takes upper space)
            CanvasView(
                baseBitmap = uiState.baseBitmap,
                backgroundStart = uiState.backgroundStartColor,
                backgroundEnd = uiState.backgroundEndColor,
                ratio = uiState.ratio,
                textLayers = uiState.textLayers,
                stickerLayers = uiState.stickerLayers,
                selectedTextId = uiState.selectedTextLayerId,
                selectedStickerId = uiState.selectedStickerLayerId,
                brightness = uiState.brightness,
                contrast = uiState.contrast,
                saturation = uiState.saturation,
                activeFilter = uiState.activeFilter,
                onSelectText = { viewModel.selectTextLayer(it) },
                onSelectSticker = { viewModel.selectStickerLayer(it) },
                onUpdateTextPosition = { id, dx, dy, dScale, dRot ->
                    viewModel.updateSelectedTextLayer { l ->
                        l.copy(
                            x = (l.x + dx).coerceIn(0.05f, 0.95f),
                            y = (l.y + dy).coerceIn(0.05f, 0.95f),
                            scale = (l.scale * dScale).coerceIn(0.3f, 3.5f),
                            rotation = (l.rotation + dRot) % 360f
                        )
                    }
                },
                onUpdateStickerPosition = { id, dx, dy, dScale, dRot ->
                    viewModel.updateSelectedStickerLayer { s ->
                        s.copy(
                            x = (s.x + dx).coerceIn(0.05f, 0.95f),
                            y = (s.y + dy).coerceIn(0.05f, 0.95f),
                            scale = (s.scale * dScale).coerceIn(0.3f, 3.5f),
                            rotation = (s.rotation + dRot) % 360f
                        )
                    }
                },
                onEditText = { viewModel.setIsEditingText(true) },
                onDuplicateLayer = { viewModel.duplicateSelectedLayer() },
                onDeleteLayer = { viewModel.deleteSelectedLayer() },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            // Bottom Tools Container
            Surface(
                color = DarkSurface,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Tool Tabs Row
                    EditorTabsBar(
                        activeTab = uiState.activeTab,
                        onTabSelected = { tab ->
                            when (tab) {
                                EditorTab.FONTS -> viewModel.setShowFonts(true)
                                EditorTab.QUOTES -> viewModel.setShowQuotes(true)
                                EditorTab.STICKERS -> viewModel.setShowStickers(true)
                                else -> viewModel.setActiveTab(tab)
                            }
                        }
                    )

                    // Tab Content (Fixed Height Scrollable Tray)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(DarkSurface)
                    ) {
                        when (uiState.activeTab) {
                            EditorTab.TEXT -> {
                                TextToolsContent(
                                    selectedLayer = selectedTextLayer,
                                    onAddText = { viewModel.addTextLayer() },
                                    onEditText = { viewModel.setIsEditingText(true) },
                                    onFontSizeChange = { viewModel.setFontSize(it) },
                                    onTextColorChange = { viewModel.setTextColor(it) },
                                    onOpenFonts = { viewModel.setShowFonts(true) },
                                    onOpenShadow = { viewModel.setActiveTab(EditorTab.SHADOW) },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            EditorTab.STYLE -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .padding(bottom = 6.dp)
                                ) {
                                    StylePresetsRow(
                                        activePresetId = selectedTextLayer?.stylePresetId,
                                        onSelectPreset = { viewModel.applyPresetToSelected(it) }
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    ColorPaletteRow(
                                        selectedColor = selectedTextLayer?.textColor ?: 0xFFFFFFFF,
                                        onColorSelected = { viewModel.setTextColor(it) }
                                    )
                                }
                            }
                            EditorTab.SHADOW -> {
                                ShadowGlowAdjustmentView(
                                    selectedLayer = selectedTextLayer,
                                    onUpdateShadow = { viewModel.setTextShadow(it) },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            EditorTab.ENHANCE -> {
                                EnhanceToolsView(
                                    brightness = uiState.brightness,
                                    contrast = uiState.contrast,
                                    saturation = uiState.saturation,
                                    activeFilter = uiState.activeFilter,
                                    onBrightnessChange = { viewModel.setBrightness(it) },
                                    onContrastChange = { viewModel.setContrast(it) },
                                    onSaturationChange = { viewModel.setSaturation(it) },
                                    onFilterChange = { viewModel.setFilter(it) },
                                    onAutoEnhance = { viewModel.autoEnhance() },
                                    onReset = { viewModel.resetEnhance() },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            EditorTab.CANVAS -> {
                                CanvasRatioPicker(
                                    selectedRatio = uiState.ratio,
                                    onRatioSelected = { viewModel.setCanvasRatio(it) },
                                    onBackgroundSelected = { s, e -> viewModel.setBackgroundColors(s, e) },
                                    hasImage = uiState.baseBitmap != null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            else -> {
                                // Default back to text tools
                                TextToolsContent(
                                    selectedLayer = selectedTextLayer,
                                    onAddText = { viewModel.addTextLayer() },
                                    onEditText = { viewModel.setIsEditingText(true) },
                                    onFontSizeChange = { viewModel.setFontSize(it) },
                                    onTextColorChange = { viewModel.setTextColor(it) },
                                    onOpenFonts = { viewModel.setShowFonts(true) },
                                    onOpenShadow = { viewModel.setActiveTab(EditorTab.SHADOW) },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal Dialogs & Sheets
    if (uiState.isEditingText && selectedTextLayer != null) {
        TextEditDialog(
            initialText = selectedTextLayer.text,
            initialAlign = selectedTextLayer.alignment,
            isBold = selectedTextLayer.isBold,
            isItalic = selectedTextLayer.isItalic,
            isUnderline = selectedTextLayer.isUnderline,
            onSave = { txt, align, bold, italic, underline ->
                viewModel.updateSelectedTextLayer {
                    it.copy(
                        text = txt,
                        alignment = align,
                        isBold = bold,
                        isItalic = italic,
                        isUnderline = underline
                    )
                }
            },
            onDismiss = { viewModel.setIsEditingText(false) }
        )
    }

    if (uiState.showFontsSheet) {
        val currentTextLayer = selectedTextLayer
        FontPickerSheet(
            currentText = currentTextLayer?.text ?: "शुभ प्रभात आपका दिन मंगलमय हो",
            activeFont = currentTextLayer?.font ?: com.example.data.model.HindiFont.POPPINS,
            activeBold = currentTextLayer?.isBold ?: false,
            activeItalic = currentTextLayer?.isItalic ?: false,
            onApply = { chosenFont, chosenPreset, isBold, isItalic, customSizeSp ->
                viewModel.applyTypography(
                    font = chosenFont,
                    preset = chosenPreset,
                    isBold = isBold,
                    isItalic = isItalic,
                    sizeSp = customSizeSp
                )
            },
            onDismiss = { viewModel.setShowFonts(false) }
        )
    }

    if (uiState.showQuotesSheet) {
        QuotesPickerSheet(
            onQuoteSelected = { viewModel.addQuote(it) },
            onDismiss = { viewModel.setShowQuotes(false) }
        )
    }

    if (uiState.showStickersSheet) {
        StickerPickerSheet(
            onStickerSelected = { viewModel.addSticker(it) },
            onDismiss = { viewModel.setShowStickers(false) }
        )
    }

    if (uiState.showExportSheet) {
        ExportSheet(
            isExporting = uiState.isExporting,
            exportSuccessMessage = uiState.exportSuccessMessage,
            onSaveToGallery = {
                viewModel.saveToGallery { success, msg ->
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
            },
            onPrepareShare = {
                viewModel.prepareShareUri()
            },
            onDismiss = { viewModel.setShowExport(false) }
        )
    }
}

@Composable
fun EditorTabsBar(
    activeTab: EditorTab,
    onTabSelected: (EditorTab) -> Unit
) {
    val tabs = listOf(
        Pair(EditorTab.TEXT, "टेक्स्ट"),
        Pair(EditorTab.STYLE, "स्टाइल"),
        Pair(EditorTab.SHADOW, "ग्लो/शैडो"),
        Pair(EditorTab.FONTS, "फ़ॉन्ट"),
        Pair(EditorTab.QUOTES, "सुविचार"),
        Pair(EditorTab.STICKERS, "स्टिकर"),
        Pair(EditorTab.ENHANCE, "इफ़ेक्ट"),
        Pair(EditorTab.CANVAS, "कैनवास")
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(tabs) { (tab, label) ->
            val isSelected = tab == activeTab
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) PrimaryPurple else DarkSurfaceVariant)
                    .border(
                        1.dp,
                        if (isSelected) PrimaryPurple else DarkBorder,
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { onTabSelected(tab) }
                    .padding(horizontal = 14.dp, vertical = 7.dp)
            ) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color.White else TextSecondary
                )
            }
        }
    }
}

@Composable
fun TextToolsContent(
    selectedLayer: com.example.data.model.TextLayer?,
    onAddText: () -> Unit,
    onEditText: () -> Unit,
    onFontSizeChange: (Float) -> Unit,
    onTextColorChange: (Long) -> Unit,
    onOpenFonts: () -> Unit,
    onOpenShadow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Quick Action Buttons (Add Text, Edit Current, Change Font)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onAddText,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f).height(38.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("नया टेक्स्ट", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            if (selectedLayer != null) {
                Button(
                    onClick = onEditText,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceVariant),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(38.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp), tint = PrimaryPurple)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("बदलें (Edit)", fontSize = 12.sp, color = TextPrimary)
                }
            }

            Button(
                onClick = onOpenFonts,
                colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceVariant),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f).height(38.dp)
            ) {
                Icon(Icons.Default.FormatSize, contentDescription = null, modifier = Modifier.size(16.dp), tint = SecondaryCoral)
                Spacer(modifier = Modifier.width(4.dp))
                Text(selectedLayer?.font?.hindiName ?: "फ़ॉन्ट", fontSize = 12.sp, color = TextPrimary)
            }
        }

        if (selectedLayer != null) {
            Spacer(modifier = Modifier.height(8.dp))

            // Quick Glow & Shadow access button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onOpenShadow,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceVariant),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryPurple.copy(alpha = 0.5f))
                ) {
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = PrimaryPurple
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "🌟 आउटर ग्लो व 3D शैडो एडजस्ट करें",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Font Size Slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "आकार (Size): ${selectedLayer.fontSizeSp.toInt()}",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    modifier = Modifier.width(110.dp)
                )
                Slider(
                    value = selectedLayer.fontSizeSp,
                    onValueChange = onFontSizeChange,
                    valueRange = 14f..90f,
                    colors = SliderDefaults.colors(
                        thumbColor = PrimaryPurple,
                        activeTrackColor = PrimaryPurple,
                        inactiveTrackColor = DarkBorder
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            // Text Color Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(top = 2.dp)
            ) {
                items(PresetRepository.PRESET_COLORS) { colorLong ->
                    val isSelected = selectedLayer.textColor == colorLong
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(colorLong))
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) Color.White else Color(0x33FFFFFF),
                                shape = CircleShape
                            )
                            .clickable { onTextColorChange(colorLong) }
                    )
                }
            }
        }
    }
}
