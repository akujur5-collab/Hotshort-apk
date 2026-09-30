package com.example.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.example.ui.theme.AdishilaFamily
import com.example.ui.theme.KalamFamily
import com.example.ui.theme.KhandFamily
import com.example.ui.theme.KohinoorDevanagariFamily
import com.example.ui.theme.ModakFamily
import com.example.ui.theme.MuktaFamily
import com.example.ui.theme.NotoSansDevanagariFamily
import com.example.ui.theme.NotoSerifDevanagariFamily
import com.example.ui.theme.PoppinsFamily
import com.example.ui.theme.RozhaOneFamily
import com.example.ui.theme.TiroDevanagariHindiFamily
import com.example.ui.theme.YatraOneFamily

enum class HindiFont(val title: String, val hindiName: String, val subtitle: String) {
    TIRO_DEVANAGARI_HINDI("Tiro Devanagari Hindi", "तीरो देवनागरी", "Traditional Elegant Serif"),
    KOHINOOR_DEVANAGARI("Kohinoor Devanagari", "कोहिनूर देवनागरी", "Clean Modern Sans"),
    NOTO_SERIF_DEVANAGARI("Noto Serif Devanagari", "नोटो सेरिफ़", "Classic Editorial Serif"),
    NOTO_SANS_DEVANAGARI("Noto Sans Devanagari", "नोटो सैन्स", "Universal Clean Sans"),
    ADISHILA("Adishila", "आदिशिला", "Heritage Classical Type"),
    KALAM("Kalam", "कलम", "Handwritten Calligraphy"),
    YATRA_ONE("Yatra One", "यात्रा वन", "Retro Signage Style"),
    ROZHA_ONE("Rozha One", "रोझा वन", "Royal Decorative Display"),
    KHAND("Khand", "खांड", "Bold Headline Poster"),
    MODAK("Modak", "मोदक", "Chubby Festive Display"),
    POPPINS("Poppins", "पॉपिन्स", "Bold Geometric Sans"),
    MUKTA("Mukta", "मुक्ता", "Contemporary Devanagari");

    fun toFontFamily(): FontFamily {
        return when (this) {
            TIRO_DEVANAGARI_HINDI -> TiroDevanagariHindiFamily
            KOHINOOR_DEVANAGARI -> KohinoorDevanagariFamily
            NOTO_SERIF_DEVANAGARI -> NotoSerifDevanagariFamily
            NOTO_SANS_DEVANAGARI -> NotoSansDevanagariFamily
            ADISHILA -> AdishilaFamily
            KALAM -> KalamFamily
            YATRA_ONE -> YatraOneFamily
            ROZHA_ONE -> RozhaOneFamily
            KHAND -> KhandFamily
            MODAK -> ModakFamily
            POPPINS -> PoppinsFamily
            MUKTA -> MuktaFamily
        }
    }
}

enum class CanvasTextAlign {
    START, CENTER, END
}

data class TextShadowConfig(
    val color: Long = 0xAA000000,
    val offsetX: Float = 2f,
    val offsetY: Float = 4f,
    val blurRadius: Float = 8f
)

data class TextStrokeConfig(
    val color: Long = 0xFF000000,
    val strokeWidth: Float = 0f
)

data class TextBackgroundConfig(
    val color: Long = 0x00000000,
    val cornerRadius: Float = 8f,
    val paddingHorizontal: Float = 12f,
    val paddingVertical: Float = 6f
)

data class TextLayer(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String = "नमस्ते भारत",
    val x: Float = 0.5f, // Normalized 0..1 relative to canvas width
    val y: Float = 0.5f, // Normalized 0..1 relative to canvas height
    val scale: Float = 1.0f,
    val rotation: Float = 0f,
    val fontSizeSp: Float = 36f,
    val font: HindiFont = HindiFont.POPPINS,
    val textColor: Long = 0xFFFFFFFF,
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
    val isUnderline: Boolean = false,
    val alignment: CanvasTextAlign = CanvasTextAlign.CENTER,
    val opacity: Float = 1.0f,
    val shadow: TextShadowConfig? = TextShadowConfig(),
    val stroke: TextStrokeConfig? = null,
    val background: TextBackgroundConfig? = null,
    val letterSpacingSp: Float = 0f,
    val stylePresetId: String? = null
)

data class StickerLayer(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String = "✨",
    val x: Float = 0.5f,
    val y: Float = 0.4f,
    val scale: Float = 1.0f,
    val rotation: Float = 0f,
    val fontSizeSp: Float = 44f
)

enum class CanvasRatio(val label: String, val ratio: Float, val iconLabel: String) {
    RATIO_1_1("1:1", 1.0f, "Square / Post"),
    RATIO_9_16("9:16", 9f / 16f, "Story / Reels"),
    RATIO_4_5("4:5", 4f / 5f, "Portrait / Insta"),
    RATIO_16_9("16:9", 16f / 9f, "Banner / Video"),
    RATIO_FREE("Auto", 0f, "Custom")
}

enum class ImageFilterType(val title: String, val hindiName: String) {
    NORMAL("Original", "सामान्य"),
    VIVID("Vivid", "चमकदार"),
    WARM_GOLD("Warm Gold", "स्वर्ण आभा"),
    COOL_BLISS("Cool Blue", "शीतल"),
    RETRO_BW("Monochrome", "ब्लैक & व्हाइट"),
    SEPIA_VINTAGE("Vintage", "विंटेज"),
    CYBER_NEON("Cyberpunk", "नियॉन"),
    SUNSET_GLOW("Sunset", "संध्या")
}
