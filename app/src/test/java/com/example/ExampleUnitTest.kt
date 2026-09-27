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
        assertTrue(presets.size >= 10)

        val classic = presets.find { it.id == "classic" }
        assertNotNull(classic)
        assertEquals("Classic Bold", classic?.name)

        val neon = presets.find { it.id == "neon_cyan" }
        assertNotNull(neon)

        val gold = presets.find { it.id == "gold_luxury" }
        assertNotNull(gold)
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
        assertTrue(stickers.isNotEmpty())
        assertTrue(stickers.any { it.icon.contains("ॐ") })
        assertTrue(stickers.any { it.icon.contains("शुभ लाभ") })
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
}
