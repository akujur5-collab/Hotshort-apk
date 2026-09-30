package com.example

import com.example.data.model.CanvasRatio
import com.example.data.model.HindiFont
import com.example.data.model.HindiQuotesRepository
import com.example.data.model.HindiStickersRepository
import com.example.data.model.PresetRepository
import com.example.data.model.TemplateRepository
import com.example.data.model.TextLayer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testTextPresetsLoaded() {
        val presets = PresetRepository.PRESETS
        assertTrue(presets.isNotEmpty())
        assertTrue("Expected 24+ presets, found ${presets.size}", presets.size >= 24)

        val classic = presets.find { it.id == "classic" }
        assertNotNull(classic)
        assertEquals("Classic Bold", classic?.name)

        val neon = presets.find { it.id == "neon_cyan" }
        assertNotNull(neon)

        val gold = presets.find { it.id == "gold_luxury" }
        assertNotNull(gold)

        val bhagwa = presets.find { it.id == "bhagwa_divine" }
        assertNotNull(bhagwa)

        val mahakal = presets.find { it.id == "mahakal_aura" }
        assertNotNull(mahakal)

        val categories = PresetRepository.CATEGORIES
        assertTrue(categories.size >= 6)
        assertTrue(categories.any { it.id == "royal" })
        assertTrue(categories.any { it.id == "devotional" })
        assertTrue(categories.any { it.id == "neon" })
    }

    @Test
    fun testHindiQuotesCategories() {
        val categories = HindiQuotesRepository.CATEGORIES
        assertTrue(categories.size >= 5)

        val morning = categories.find { it.id == "morning" }
        assertNotNull(morning)
        assertTrue(morning!!.quotes.isNotEmpty())

        val motivation = categories.find { it.id == "motivation" }
        assertNotNull(motivation)
        assertTrue(motivation!!.quotes.isNotEmpty())
    }

    @Test
    fun testHindiStickersAvailable() {
        val stickers = HindiStickersRepository.STICKERS
        assertTrue("Expected 120+ stickers, found ${stickers.size}", stickers.size >= 120)
        assertTrue(stickers.any { it.icon.contains("ॐ") })
        assertTrue(stickers.any { it.icon.contains("शुभ लाभ") })
        assertTrue(stickers.any { it.icon.contains("जय श्री राम") })
        assertTrue(stickers.any { it.icon.contains("जन्मदिन मुबारक") })
        assertTrue(stickers.any { it.icon.contains("जय हिंद") })
        assertTrue(stickers.any { it.icon.contains("दिल से") })
        assertTrue(stickers.any { it.icon.contains("🪔") })

        val categories = HindiStickersRepository.CATEGORIES
        assertTrue(categories.size >= 5)
        assertTrue(categories.any { it.id == "devotional" })
        assertTrue(categories.any { it.id == "wishes" })
        assertTrue(categories.any { it.id == "motivation" })
        assertTrue(categories.any { it.id == "love_family" })
        assertTrue(categories.any { it.id == "aesthetic" })
    }

    @Test
    fun testStarterTemplates() {
        val templates = TemplateRepository.STARTER_TEMPLATES
        assertTrue(templates.isNotEmpty())
        templates.forEach {
            assertFalse(it.sampleText.isBlank())
            assertNotNull(it.font)
        }
    }

    @Test
    fun testTextLayerDefaults() {
        val layer = TextLayer()
        assertEquals(0.5f, layer.x)
        assertEquals(0.5f, layer.y)
        assertEquals(HindiFont.POPPINS, layer.font)
        assertFalse(layer.text.isBlank())
    }

    @Test
    fun testCanvasRatios() {
        assertEquals(1.0f, CanvasRatio.RATIO_1_1.ratio)
        assertEquals(9f / 16f, CanvasRatio.RATIO_9_16.ratio)
    }

    @Test
    fun test4KExportDimensions() {
        val (w1, h1) = com.example.util.BitmapExporter.get4KDimensions(CanvasRatio.RATIO_1_1, null)
        assertEquals(3840, w1)
        assertEquals(3840, h1)

        val (w916, h916) = com.example.util.BitmapExporter.get4KDimensions(CanvasRatio.RATIO_9_16, null)
        assertEquals(2160, w916)
        assertEquals(3840, h916)

        val (w169, h169) = com.example.util.BitmapExporter.get4KDimensions(CanvasRatio.RATIO_16_9, null)
        assertEquals(3840, w169)
        assertEquals(2160, h169)

        val (w45, h45) = com.example.util.BitmapExporter.get4KDimensions(CanvasRatio.RATIO_4_5, null)
        assertEquals(3072, w45)
        assertEquals(3840, h45)
    }

    @Test
    fun testTextLayerPositionBounds() {
        val text = "शुभ प्रभात आपका दिन मंगलमय हो"
        val layer = TextLayer(text = text, x = 0.25f, y = 0.2f)
        assertTrue(layer.x in 0.05f..0.95f)
        assertTrue(layer.y in 0.05f..0.95f)
        assertEquals(text, layer.text)
    }

    @Test
    fun testAllRequestedHindiFontsAvailable() {
        val allFonts = HindiFont.values()
        val expectedFontTitles = listOf(
            "Tiro Devanagari Hindi",
            "Kohinoor Devanagari",
            "Noto Serif Devanagari",
            "Noto Sans Devanagari",
            "Adishila",
            "Kalam",
            "Yatra One",
            "Rozha One",
            "Khand",
            "Modak"
        )
        for (expected in expectedFontTitles) {
            val found = allFonts.any { it.title.equals(expected, ignoreCase = true) }
            assertTrue("Expected font $expected to be available", found)
        }
        allFonts.forEach { font ->
            assertTrue(font.title.isNotBlank())
            assertTrue(font.hindiName.isNotBlank())
            assertTrue(font.subtitle.isNotBlank())
            assertNotNull(font.toFontFamily())
        }
    }
}
