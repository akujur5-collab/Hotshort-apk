package com.example.ui.editor

import android.app.Application
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.db.ProjectEntity
import com.example.data.db.ProjectRepository
import com.example.data.model.BibleVerse
import com.example.data.model.CanvasRatio
import com.example.data.model.CanvasTextAlign
import com.example.data.model.HindiFont
import com.example.data.model.ImageFilterType
import com.example.data.model.PresetRepository
import com.example.data.model.StickerLayer
import com.example.data.model.TextLayer
import com.example.data.model.TextStylePreset
import com.example.util.BitmapExporter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EditorUiState(
    val baseImageUri: Uri? = null,
    val baseBitmap: Bitmap? = null,
    val backgroundStartColor: Long = 0xFF140727,
    val backgroundEndColor: Long = 0xFF2A1B4E,
    val ratio: CanvasRatio = CanvasRatio.RATIO_1_1,
    val textLayers: List<TextLayer> = listOf(
        TextLayer(
            text = "नमस्ते भारत",
            fontSizeSp = 42f,
            font = HindiFont.POPPINS,
            textColor = 0xFFFFFFFF,
            isBold = true
        )
    ),
    val stickerLayers: List<StickerLayer> = emptyList(),
    val selectedTextLayerId: String? = null,
    val selectedStickerLayerId: String? = null,
    // Enhance & Filters
    val brightness: Float = 1.0f,
    val contrast: Float = 1.0f,
    val saturation: Float = 1.0f,
    val activeFilter: ImageFilterType = ImageFilterType.NORMAL,
    // Active Bottom Tab
    val activeTab: EditorTab = EditorTab.TEXT,
    // Export and Modal state
    val isExporting: Boolean = false,
    val exportSuccessMessage: String? = null,
    val exportedImageUri: Uri? = null,
    val isEditingText: Boolean = false,
    val showQuotesSheet: Boolean = false,
    val showStickersSheet: Boolean = false,
    val showFontsSheet: Boolean = false,
    val showExportSheet: Boolean = false
)

enum class EditorTab(val title: String, val hindiTitle: String) {
    TEXT("Text", "टेक्स्ट"),
    STYLE("Styles", "स्टाइल"),
    FONTS("Fonts", "फ़ॉन्ट"),
    QUOTES("Quotes", "सुविचार"),
    STICKERS("Stickers", "स्टिकर"),
    ENHANCE("Enhance", "इफ़ेक्ट"),
    CANVAS("Canvas", "कैनवास")
}

class EditorViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProjectRepository(AppDatabase.getDatabase(application).projectDao())

    private val _uiState = MutableStateFlow(EditorUiState())
    val uiState: StateFlow<EditorUiState> = _uiState.asStateFlow()

    // Undo / Redo history stacks
    private val undoStack = mutableListOf<List<TextLayer>>()
    private val redoStack = mutableListOf<List<TextLayer>>()

    init {
        // Select initial text layer
        _uiState.update { it.copy(selectedTextLayerId = it.textLayers.firstOrNull()?.id) }
    }

    private fun pushUndo() {
        undoStack.add(_uiState.value.textLayers)
        redoStack.clear()
        if (undoStack.size > 20) undoStack.removeAt(0)
    }

    fun undo() {
        if (undoStack.isNotEmpty()) {
            val previous = undoStack.removeAt(undoStack.lastIndex)
            redoStack.add(_uiState.value.textLayers)
            _uiState.update { it.copy(textLayers = previous) }
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val next = redoStack.removeAt(redoStack.lastIndex)
            undoStack.add(_uiState.value.textLayers)
            _uiState.update { it.copy(textLayers = next) }
        }
    }

    fun canUndo(): Boolean = undoStack.isNotEmpty()
    fun canRedo(): Boolean = redoStack.isNotEmpty()

    fun setBaseImage(uri: Uri?) {
        if (uri == null) {
            _uiState.update { it.copy(baseImageUri = null, baseBitmap = null) }
            return
        }
        viewModelScope.launch {
            try {
                val context = getApplication<Application>()
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                        decoder.isMutableRequired = true
                    }
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                }
                _uiState.update {
                    it.copy(
                        baseImageUri = uri,
                        baseBitmap = bitmap,
                        ratio = CanvasRatio.RATIO_FREE
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun applyStarterTemplate(
        sampleText: String,
        font: HindiFont,
        startColor: Long,
        endColor: Long,
        presetId: String
    ) {
        val preset = PresetRepository.PRESETS.find { it.id == presetId } ?: PresetRepository.PRESETS[0]
        val newLayer = TextLayer(
            text = sampleText,
            font = font,
            textColor = preset.textColor,
            shadow = preset.shadow,
            stroke = preset.stroke,
            background = preset.background,
            isBold = preset.isBold,
            stylePresetId = preset.id,
            fontSizeSp = 38f
        )
        _uiState.update {
            it.copy(
                baseImageUri = null,
                baseBitmap = null,
                backgroundStartColor = startColor,
                backgroundEndColor = endColor,
                textLayers = listOf(newLayer),
                selectedTextLayerId = newLayer.id,
                ratio = CanvasRatio.RATIO_1_1
            )
        }
    }

    fun applyBibleVerse(
        verse: BibleVerse,
        language: String = "hi"
    ) {
        val preset = PresetRepository.PRESETS.find { it.id == verse.presetId } ?: PresetRepository.PRESETS[0]
        val formattedText = when (language) {
            "en" -> "${verse.verseEnglish}\n\n— ${verse.referenceEnglish}"
            "both" -> "${verse.verseHindi}\n\n\"${verse.verseEnglish}\"\n\n— ${verse.referenceHindi}"
            else -> "${verse.verseHindi}\n\n— ${verse.referenceHindi}"
        }
        val newLayer = TextLayer(
            text = formattedText,
            font = HindiFont.ROZHA_ONE,
            textColor = preset.textColor,
            shadow = preset.shadow,
            stroke = preset.stroke,
            background = preset.background,
            isBold = false,
            stylePresetId = preset.id,
            fontSizeSp = if (formattedText.length > 80) 28f else 34f,
            alignment = CanvasTextAlign.CENTER
        )
        _uiState.update {
            it.copy(
                baseImageUri = null,
                baseBitmap = null,
                backgroundStartColor = verse.bgStartColor,
                backgroundEndColor = verse.bgEndColor,
                textLayers = listOf(newLayer),
                selectedTextLayerId = newLayer.id,
                ratio = CanvasRatio.RATIO_1_1
            )
        }
    }

    fun setActiveTab(tab: EditorTab) {
        _uiState.update { it.copy(activeTab = tab) }
    }

    fun setCanvasRatio(ratio: CanvasRatio) {
        _uiState.update { it.copy(ratio = ratio) }
    }

    fun setBackgroundColors(start: Long, end: Long) {
        _uiState.update {
            it.copy(
                backgroundStartColor = start,
                backgroundEndColor = end
            )
        }
    }

    fun addTextLayer(initialText: String = "नया टेक्स्ट") {
        pushUndo()
        val newLayer = TextLayer(
            text = initialText,
            x = 0.5f,
            y = 0.5f,
            fontSizeSp = 36f,
            font = HindiFont.POPPINS,
            textColor = 0xFFFFFFFF,
            isBold = true
        )
        _uiState.update {
            it.copy(
                textLayers = it.textLayers + newLayer,
                selectedTextLayerId = newLayer.id,
                selectedStickerLayerId = null
            )
        }
    }

    fun addQuote(quoteText: String) {
        pushUndo()
        val selectedFont = HindiFont.ROZHA_ONE
        val newLayer = TextLayer(
            text = quoteText,
            x = 0.5f,
            y = 0.5f,
            fontSizeSp = 32f,
            font = selectedFont,
            textColor = 0xFFFFFFFF,
            shadow = PresetRepository.PRESETS[0].shadow,
            isBold = false
        )
        _uiState.update {
            it.copy(
                textLayers = it.textLayers + newLayer,
                selectedTextLayerId = newLayer.id,
                showQuotesSheet = false
            )
        }
    }

    fun addSticker(stickerEmojiOrWord: String) {
        val newSticker = StickerLayer(
            text = stickerEmojiOrWord,
            x = 0.5f,
            y = 0.35f,
            fontSizeSp = if (stickerEmojiOrWord.length > 2) 36f else 46f
        )
        _uiState.update {
            it.copy(
                stickerLayers = it.stickerLayers + newSticker,
                selectedStickerLayerId = newSticker.id,
                selectedTextLayerId = null,
                showStickersSheet = false
            )
        }
    }

    fun selectTextLayer(id: String?) {
        _uiState.update {
            it.copy(
                selectedTextLayerId = id,
                selectedStickerLayerId = null
            )
        }
    }

    fun selectStickerLayer(id: String?) {
        _uiState.update {
            it.copy(
                selectedStickerLayerId = id,
                selectedTextLayerId = null
            )
        }
    }

    fun updateSelectedTextLayer(update: (TextLayer) -> TextLayer) {
        val activeId = _uiState.value.selectedTextLayerId ?: return
        _uiState.update { state ->
            val updated = state.textLayers.map { layer ->
                if (layer.id == activeId) update(layer) else layer
            }
            state.copy(textLayers = updated)
        }
    }

    fun updateSelectedStickerLayer(update: (StickerLayer) -> StickerLayer) {
        val activeId = _uiState.value.selectedStickerLayerId ?: return
        _uiState.update { state ->
            val updated = state.stickerLayers.map { sticker ->
                if (sticker.id == activeId) update(sticker) else sticker
            }
            state.copy(stickerLayers = updated)
        }
    }

    fun applyPresetToSelected(preset: TextStylePreset) {
        pushUndo()
        updateSelectedTextLayer { current ->
            current.copy(
                font = preset.font,
                textColor = preset.textColor,
                shadow = preset.shadow,
                stroke = preset.stroke,
                background = preset.background,
                isBold = preset.isBold,
                stylePresetId = preset.id
            )
        }
    }

    fun setFont(font: HindiFont) {
        pushUndo()
        updateSelectedTextLayer { it.copy(font = font) }
        _uiState.update { it.copy(showFontsSheet = false) }
    }

    fun setTextColor(color: Long) {
        updateSelectedTextLayer { it.copy(textColor = color) }
    }

    fun setFontSize(sizeSp: Float) {
        updateSelectedTextLayer { it.copy(fontSizeSp = sizeSp) }
    }

    fun setTextAlignment(align: CanvasTextAlign) {
        updateSelectedTextLayer { it.copy(alignment = align) }
    }

    fun toggleBold() {
        updateSelectedTextLayer { it.copy(isBold = !it.isBold) }
    }

    fun toggleItalic() {
        updateSelectedTextLayer { it.copy(isItalic = !it.isItalic) }
    }

    fun toggleUnderline() {
        updateSelectedTextLayer { it.copy(isUnderline = !it.isUnderline) }
    }

    fun duplicateSelectedLayer() {
        pushUndo()
        val textId = _uiState.value.selectedTextLayerId
        if (textId != null) {
            val layer = _uiState.value.textLayers.find { it.id == textId } ?: return
            val dup = layer.copy(
                id = java.util.UUID.randomUUID().toString(),
                x = (layer.x + 0.05f).coerceIn(0.1f, 0.9f),
                y = (layer.y + 0.05f).coerceIn(0.1f, 0.9f)
            )
            _uiState.update {
                it.copy(
                    textLayers = it.textLayers + dup,
                    selectedTextLayerId = dup.id
                )
            }
            return
        }

        val stickerId = _uiState.value.selectedStickerLayerId
        if (stickerId != null) {
            val sticker = _uiState.value.stickerLayers.find { it.id == stickerId } ?: return
            val dup = sticker.copy(
                id = java.util.UUID.randomUUID().toString(),
                x = (sticker.x + 0.05f).coerceIn(0.1f, 0.9f),
                y = (sticker.y + 0.05f).coerceIn(0.1f, 0.9f)
            )
            _uiState.update {
                it.copy(
                    stickerLayers = it.stickerLayers + dup,
                    selectedStickerLayerId = dup.id
                )
            }
        }
    }

    fun deleteSelectedLayer() {
        pushUndo()
        val textId = _uiState.value.selectedTextLayerId
        if (textId != null) {
            _uiState.update {
                val list = it.textLayers.filterNot { l -> l.id == textId }
                it.copy(
                    textLayers = list,
                    selectedTextLayerId = list.lastOrNull()?.id
                )
            }
            return
        }

        val stickerId = _uiState.value.selectedStickerLayerId
        if (stickerId != null) {
            _uiState.update {
                val list = it.stickerLayers.filterNot { s -> s.id == stickerId }
                it.copy(
                    stickerLayers = list,
                    selectedStickerLayerId = list.lastOrNull()?.id
                )
            }
        }
    }

    // Enhance adjustments
    fun setBrightness(value: Float) = _uiState.update { it.copy(brightness = value) }
    fun setContrast(value: Float) = _uiState.update { it.copy(contrast = value) }
    fun setSaturation(value: Float) = _uiState.update { it.copy(saturation = value) }
    fun setFilter(filter: ImageFilterType) = _uiState.update { it.copy(activeFilter = filter) }

    fun autoEnhance() {
        _uiState.update {
            it.copy(
                brightness = 1.08f,
                contrast = 1.15f,
                saturation = 1.2f,
                activeFilter = ImageFilterType.VIVID
            )
        }
    }

    fun resetEnhance() {
        _uiState.update {
            it.copy(
                brightness = 1.0f,
                contrast = 1.0f,
                saturation = 1.0f,
                activeFilter = ImageFilterType.NORMAL
            )
        }
    }

    // Modal Sheet controls
    fun setShowQuotes(show: Boolean) = _uiState.update { it.copy(showQuotesSheet = show) }
    fun setShowStickers(show: Boolean) = _uiState.update { it.copy(showStickersSheet = show) }
    fun setShowFonts(show: Boolean) = _uiState.update { it.copy(showFontsSheet = show) }
    fun setShowExport(show: Boolean) = _uiState.update { it.copy(showExportSheet = show) }
    fun setIsEditingText(show: Boolean) = _uiState.update { it.copy(isEditingText = show) }

    fun saveToGallery(onComplete: (Boolean, String) -> Unit) {
        val state = _uiState.value
        _uiState.update { it.copy(isExporting = true) }

        viewModelScope.launch {
            try {
                val context = getApplication<Application>()
                val fullResBitmap = BitmapExporter.renderFullResolutionBitmap(
                    context = context,
                    baseBitmap = state.baseBitmap,
                    solidBackgroundStart = state.backgroundStartColor,
                    solidBackgroundEnd = state.backgroundEndColor,
                    ratio = state.ratio,
                    targetWidth = 3840,
                    textLayers = state.textLayers,
                    stickerLayers = state.stickerLayers,
                    brightness = state.brightness,
                    contrast = state.contrast,
                    saturation = state.saturation,
                    filterType = state.activeFilter
                )

                val uri = BitmapExporter.saveBitmapToGallery(context, fullResBitmap, "ChitraLekh Artwork")

                // Save thumbnail to Room Database projects
                val previewPath = BitmapExporter.saveProjectPreview(context, fullResBitmap)
                val primaryTitle = state.textLayers.firstOrNull()?.text?.take(30) ?: "हिंदी कलाकृति"
                repository.insert(
                    ProjectEntity(
                        title = primaryTitle,
                        previewPath = previewPath,
                        textCount = state.textLayers.size
                    )
                )

                _uiState.update {
                    it.copy(
                        isExporting = false,
                        exportedImageUri = uri,
                        exportSuccessMessage = "सफलतापूर्वक 4K Ultra HD में गैलरी में सेव हो गया! (Saved in 4K UHD)"
                    )
                }
                onComplete(true, "4K Ultra HD में गैलरी में सेव हो गया!")
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isExporting = false) }
                onComplete(false, "सेव करने में त्रुटि: ${e.message}")
            }
        }
    }

    suspend fun prepareShareUri(): Uri? {
        val state = _uiState.value
        val context = getApplication<Application>()
        return try {
            val fullResBitmap = BitmapExporter.renderFullResolutionBitmap(
                context = context,
                baseBitmap = state.baseBitmap,
                solidBackgroundStart = state.backgroundStartColor,
                solidBackgroundEnd = state.backgroundEndColor,
                ratio = state.ratio,
                targetWidth = 3840,
                textLayers = state.textLayers,
                stickerLayers = state.stickerLayers,
                brightness = state.brightness,
                contrast = state.contrast,
                saturation = state.saturation,
                filterType = state.activeFilter
            )
            BitmapExporter.saveBitmapToCacheForShare(context, fullResBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
