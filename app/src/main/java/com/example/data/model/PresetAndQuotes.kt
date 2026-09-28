package com.example.data.model

data class TextStylePreset(
    val id: String,
    val name: String,
    val hindiName: String,
    val font: HindiFont,
    val textColor: Long,
    val shadow: TextShadowConfig?,
    val stroke: TextStrokeConfig?,
    val background: TextBackgroundConfig?,
    val isBold: Boolean = true
)

object PresetRepository {
    val PRESETS = listOf(
        TextStylePreset(
            id = "classic",
            name = "Classic Bold",
            hindiName = "क्लासिक बोल्ड",
            font = HindiFont.POPPINS,
            textColor = 0xFFFFFFFF,
            shadow = TextShadowConfig(color = 0xDD000000, offsetX = 3f, offsetY = 3f, blurRadius = 6f),
            stroke = null,
            background = null,
            isBold = true
        ),
        TextStylePreset(
            id = "neon_cyan",
            name = "Neon Cyan",
            hindiName = "नियॉन ग्लो",
            font = HindiFont.POPPINS,
            textColor = 0xFF00FFFF,
            shadow = TextShadowConfig(color = 0xFF00E5FF, offsetX = 0f, offsetY = 0f, blurRadius = 24f),
            stroke = null,
            background = null,
            isBold = true
        ),
        TextStylePreset(
            id = "gold_luxury",
            name = "Gold Luxury",
            hindiName = "शाही गोल्ड",
            font = HindiFont.ROZHA_ONE,
            textColor = 0xFFFFD700,
            shadow = TextShadowConfig(color = 0xEE8B6508, offsetX = 2f, offsetY = 4f, blurRadius = 8f),
            stroke = TextStrokeConfig(color = 0xAA5B3A00, strokeWidth = 1.5f),
            background = null,
            isBold = false
        ),
        TextStylePreset(
            id = "fire_effect",
            name = "Fire Effect",
            hindiName = "अग्नि ज्वाला",
            font = HindiFont.YATRA_ONE,
            textColor = 0xFFFF4500,
            shadow = TextShadowConfig(color = 0xFFFF9800, offsetX = 0f, offsetY = 0f, blurRadius = 18f),
            stroke = TextStrokeConfig(color = 0xFFFFEB3B, strokeWidth = 1.5f),
            background = null,
            isBold = true
        ),
        TextStylePreset(
            id = "water_style",
            name = "Ocean Wave",
            hindiName = "सागर तरंग",
            font = HindiFont.POPPINS,
            textColor = 0xFFE0F7FA,
            shadow = TextShadowConfig(color = 0xFF0288D1, offsetX = 0f, offsetY = 2f, blurRadius = 14f),
            stroke = TextStrokeConfig(color = 0xFF0097A7, strokeWidth = 1.2f),
            background = null,
            isBold = true
        ),
        TextStylePreset(
            id = "royal_devanagari",
            name = "Royal Devanagari",
            hindiName = "राजसी देवनागरी",
            font = HindiFont.ROZHA_ONE,
            textColor = 0xFFFFE082,
            shadow = TextShadowConfig(color = 0xFF4A148C, offsetX = 3f, offsetY = 5f, blurRadius = 10f),
            stroke = null,
            background = null,
            isBold = false
        ),
        TextStylePreset(
            id = "kalam_handwritten",
            name = "Kalam Ink",
            hindiName = "सुलेख कलम",
            font = HindiFont.KALAM,
            textColor = 0xFFFFF8E1,
            shadow = TextShadowConfig(color = 0xCC1A237E, offsetX = 2f, offsetY = 2f, blurRadius = 5f),
            stroke = null,
            background = null,
            isBold = true
        ),
        TextStylePreset(
            id = "outline_pop",
            name = "Outline Pop",
            hindiName = "आउटलाइन स्टाइल",
            font = HindiFont.POPPINS,
            textColor = 0xFFFFFFFF,
            shadow = TextShadowConfig(color = 0x88000000, offsetX = 4f, offsetY = 4f, blurRadius = 6f),
            stroke = TextStrokeConfig(color = 0xFF1A1A2E, strokeWidth = 3f),
            background = null,
            isBold = true
        ),
        TextStylePreset(
            id = "deep_shadow",
            name = "Deep 3D Shadow",
            hindiName = "गहरा छाया प्रभाव",
            font = HindiFont.YATRA_ONE,
            textColor = 0xFFFFC107,
            shadow = TextShadowConfig(color = 0xFF212121, offsetX = 6f, offsetY = 8f, blurRadius = 2f),
            stroke = null,
            background = null,
            isBold = true
        ),
        TextStylePreset(
            id = "tag_chip",
            name = "Minimal Chip",
            hindiName = "हाइलाइट पट्टी",
            font = HindiFont.MUKTA,
            textColor = 0xFFFFFFFF,
            shadow = null,
            stroke = null,
            background = TextBackgroundConfig(color = 0xDD6C63FF, cornerRadius = 8f, paddingHorizontal = 16f, paddingVertical = 6f),
            isBold = true
        ),
        TextStylePreset(
            id = "retro_sunset",
            name = "Sunset Retro",
            hindiName = "संध्या लालिमा",
            font = HindiFont.POPPINS,
            textColor = 0xFFFF6E40,
            shadow = TextShadowConfig(color = 0xFFFFD740, offsetX = -2f, offsetY = 2f, blurRadius = 10f),
            stroke = TextStrokeConfig(color = 0xFFD50000, strokeWidth = 1.5f),
            background = null,
            isBold = true
        ),
        TextStylePreset(
            id = "cyber_magenta",
            name = "Cyber Magenta",
            hindiName = "साइबर पिंक",
            font = HindiFont.POPPINS,
            textColor = 0xFFFF1744,
            shadow = TextShadowConfig(color = 0xFFFF4081, offsetX = 0f, offsetY = 0f, blurRadius = 20f),
            stroke = TextStrokeConfig(color = 0xFFFFFFFF, strokeWidth = 1f),
            background = null,
            isBold = true
        )
    )

    val PRESET_COLORS = listOf(
        0xFFFFFFFF, // Pure White
        0xFF1A1A1A, // Dark Charcoal
        0xFFFF3B30, // Coral Red
        0xFFFF9500, // Vibrant Orange
        0xFFFFD600, // Warm Gold
        0xFF34C759, // Fresh Mint
        0xFF00E5FF, // Cyan Glow
        0xFF007AFF, // Deep Sky Blue
        0xFF7C69EF, // Royal Indigo
        0xFFFF2E63, // Hot Pink
        0xFFFF7597, // Soft Rose
        0xFFFFEAA7, // Cream Sunset
        0xFF00B894, // Emerald
        0xFFE056FD  // Neon Purple
    )
}

data class HindiQuoteCategory(
    val id: String,
    val icon: String,
    val title: String,
    val hindiTitle: String,
    val quotes: List<String>
)

object HindiQuotesRepository {
    val CATEGORIES = listOf(
        HindiQuoteCategory(
            id = "morning",
            icon = "🌅",
            title = "Good Morning",
            hindiTitle = "शुभ प्रभात",
            quotes = listOf(
                "शुभ प्रभात! आपका दिन मंगलमय एवं ऊर्जावान हो।",
                "हर सुबह एक नया अवसर लेकर आती है, मुस्कुराइए और शुरुआत कीजिए।",
                "सवेरा वही है जो आपके मन के अंधकार को मिटा दे। सुप्रभात!",
                "उठिए, नई उम्मीद और नए संकल्प के साथ दिन की शुरुआत करें।",
                "रिश्ते पैसों के मोहताज नहीं होते, बस एक मीठी मुस्कान और सच्चे बोल काफी हैं।"
            )
        ),
        HindiQuoteCategory(
            id = "motivation",
            icon = "🚀",
            title = "Motivation",
            hindiTitle = "प्रेरणादायक",
            quotes = listOf(
                "कोशिश करने वालों की कभी हार नहीं होती।",
                "समय और मेहनत वो चाबी है, जो सफलता का हर ताला खोल सकती है।",
                "मंजिल उन्हीं को मिलती है, जिनके सपनों में जान होती है।",
                "परिंदों को मंजिल मिलेगी यकीनन, ये फैले हुए उनके पंख बोलते हैं।",
                "कठिन रास्तों से घबराना मत, क्योंकि कठिन रास्ते ही खूबसूरत मंजिल की ओर ले जाते हैं।"
            )
        ),
        HindiQuoteCategory(
            id = "bhakti",
            icon = "🕉️",
            title = "Spiritual",
            hindiTitle = "भक्ति व ईश्वर",
            quotes = listOf(
                "ॐ नमः शिवाय। हर हर महादेव!",
                "जय श्री राम! प्रभु की कृपा आप पर सदा बनी रहे।",
                "ईश्वर पर विश्वास रखिए, जो होता है अच्छे के लिए ही होता है।",
                "राधे राधे! जीवन में प्रेम और शांति की वर्षा हो।",
                "सच्ची प्रार्थना वही है जो हृदय की गहराइयों से निकले।"
            )
        ),
        HindiQuoteCategory(
            id = "shayari",
            icon = "📜",
            title = "Poetry / Shayari",
            hindiTitle = "शायरी व विचार",
            quotes = listOf(
                "हौसले के तरकश में कोशिश का वो तीर ज़िंदा रखो।",
                "ज़िंदगी की हर सुबह कुछ शर्तें लेके आती है, और शाम कुछ तजुर्बे देके जाती है।",
                "लफ्ज़ कम पड़ जाते हैं जब अहसास गहरे होते हैं।",
                "ख्वाहिशों से नहीं गिरते हैं फूल झोली में, कर्म की शाख को हिलाना होगा।",
                "सादगी में जो खूबसूरती है, वो किसी दिखावे में नहीं।"
            )
        ),
        HindiQuoteCategory(
            id = "love_friendship",
            icon = "💖",
            title = "Love & Friends",
            hindiTitle = "प्रेम व मित्रता",
            quotes = listOf(
                "सच्चा दोस्त वही है जो आपकी खामोशी को भी समझ ले।",
                "प्यार वो नहीं जो दुनिया को दिखाया जाए, प्यार वो है जो दिल से निभाया जाए।",
                "दोस्ती में दूरियां मायने नहीं रखतीं, जब दिल के रिश्ते पक्के हों।",
                "मुस्कान आपकी रहे, खुशियाँ हमारे हिस्से की भी आपको मिलें।"
            )
        ),
        HindiQuoteCategory(
            id = "wishes",
            icon = "🎉",
            title = "Celebrations",
            hindiTitle = "शुभकामनाएं व बधाई",
            quotes = listOf(
                "जन्मदिन की हार्दिक शुभकामनाएं! आप सदा मुस्कुराते रहें।",
                "शुभ दीपावली! आपके जीवन में सुख, शांति और समृद्धि का प्रकाश फैले।",
                "आपको और आपके परिवार को पावन पर्व की मंगलमय बधाई।",
                "नई शुरुआत की ढेरों शुभकामनाएं!"
            )
        ),
        HindiQuoteCategory(
            id = "bible",
            icon = "✝️",
            title = "Bible Verses",
            hindiTitle = "बाइबिल वचन",
            quotes = listOf(
                "जो मुझे सामर्थ्य देता है, उसमें मैं सब कुछ कर सकता हूँ।\n- फिलिप्पियों 4:13",
                "यहोवा मेरा चरवाहा है; मुझे कुछ घटी न होगी।\n- भजन संहिता 23:1",
                "तेरा वचन मेरे पांव के लिये दीपक, और मेरे मार्ग के लिये उजियाला है।\n- भजन संहिता 119:105",
                "परमेश्वर हमारा शरणस्थान और बल है, संकट में अति सहज से मिलने वाला सहायक।\n- भजन संहिता 46:1",
                "हे सब परिश्रम करने वालों और बोझ से दबे हुए लोगो, मेरे पास आओ; मैं तुम्हें विश्राम दूँगा।\n- मत्ती 11:28",
                "यहोवा तुझे आशीष दे और तेरी रक्षा करे, और तुझे शान्ति दे।\n- गिनती 6:24-26",
                "विश्वास आशा की हुई वस्तुओं का निश्चय, और अनदेखी वस्तुओं का प्रमाण है।\n- इब्रानियों 11:1"
            )
        )
    )
}

data class HindiSticker(
    val category: String,
    val icon: String,
    val isDevanagariWord: Boolean = false
)

object HindiStickersRepository {
    val STICKERS = listOf(
        // Auspicious Hindi Devanagari Badges
        HindiSticker("Hindi", "ॐ", true),
        HindiSticker("Hindi", "श्री", true),
        HindiSticker("Hindi", "शुभ लाभ", true),
        HindiSticker("Hindi", "स्वास्तिक 卐", true),
        HindiSticker("Hindi", "नमस्ते 🙏", true),
        HindiSticker("Hindi", "राधे राधे", true),
        HindiSticker("Hindi", "हर हर महादेव 🔱", true),
        HindiSticker("Hindi", "जय श्री राम 🚩", true),
        HindiSticker("Hindi", "सत्यमेव जयते", true),
        HindiSticker("Hindi", "जय हिंद 🇮🇳", true),
        // Emojis & Symbols
        HindiSticker("Aesthetic", "✨"),
        HindiSticker("Aesthetic", "⭐"),
        HindiSticker("Aesthetic", "🪔"),
        HindiSticker("Aesthetic", "🌸"),
        HindiSticker("Aesthetic", "🌹"),
        HindiSticker("Aesthetic", "❤️"),
        HindiSticker("Aesthetic", "🔥"),
        HindiSticker("Aesthetic", "👑"),
        HindiSticker("Aesthetic", "💫"),
        HindiSticker("Aesthetic", "🎯"),
        HindiSticker("Aesthetic", "🕊️"),
        HindiSticker("Aesthetic", "☕")
    )
}

data class BackgroundTemplate(
    val id: String,
    val title: String,
    val hindiTitle: String,
    val primaryColor: Long,
    val secondaryColor: Long,
    val sampleText: String,
    val font: HindiFont,
    val defaultPresetId: String
)

object TemplateRepository {
    val STARTER_TEMPLATES = listOf(
        BackgroundTemplate(
            id = "suvichar_gold",
            title = "Morning Suvichar",
            hindiTitle = "स्वर्ण सुविचार",
            primaryColor = 0xFF2B1055,
            secondaryColor = 0xFF7597DE,
            sampleText = "शुभ प्रभात\nआपका दिन मंगलमय हो",
            font = HindiFont.ROZHA_ONE,
            defaultPresetId = "gold_luxury"
        ),
        BackgroundTemplate(
            id = "fire_motivate",
            title = "Fiery Motivation",
            hindiTitle = "अग्नि संकल्प",
            primaryColor = 0xFF140700,
            secondaryColor = 0xFF541200,
            sampleText = "कोशिश करने वालों की\nकभी हार नहीं होती",
            font = HindiFont.YATRA_ONE,
            defaultPresetId = "fire_effect"
        ),
        BackgroundTemplate(
            id = "ocean_calm",
            title = "Peaceful Wisdom",
            hindiTitle = "शांत ज्ञान",
            primaryColor = 0xFF001E3D,
            secondaryColor = 0xFF004E92,
            sampleText = "सादगी में ही सच्चा\nसौंदर्य और शांति है",
            font = HindiFont.MUKTA,
            defaultPresetId = "water_style"
        ),
        BackgroundTemplate(
            id = "royal_bhakti",
            title = "Devotional Mystic",
            hindiTitle = "भक्ति भाव",
            primaryColor = 0xFF31004A,
            secondaryColor = 0xFF150024,
            sampleText = "ॐ नमः शिवाय\nहर हर महादेव",
            font = HindiFont.ROZHA_ONE,
            defaultPresetId = "royal_devanagari"
        ),
        BackgroundTemplate(
            id = "kalam_shayari",
            title = "Vintage Poetry",
            hindiTitle = "कलम शायरी",
            primaryColor = 0xFF1F1C18,
            secondaryColor = 0xFF3E362C,
            sampleText = "हौसले के तरकश में\nकोशिश का तीर ज़िंदा रखो",
            font = HindiFont.KALAM,
            defaultPresetId = "kalam_handwritten"
        ),
        BackgroundTemplate(
            id = "neon_modern",
            title = "Modern Cyber",
            hindiTitle = "आधुनिक नियॉन",
            primaryColor = 0xFF0A0A1A,
            secondaryColor = 0xFF1C0A35,
            sampleText = "सपनों को सच करने का\nसमय अभी है",
            font = HindiFont.POPPINS,
            defaultPresetId = "neon_cyan"
        ),
        BackgroundTemplate(
            id = "bible_divine_peace",
            title = "Divine Grace",
            hindiTitle = "ईश्वरीय कृपा",
            primaryColor = 0xFF14052B,
            secondaryColor = 0xFF3B1566,
            sampleText = "यहोवा मेरा चरवाहा है;\nमुझे कुछ घटी न होगी।\n- भजन संहिता 23:1",
            font = HindiFont.ROZHA_ONE,
            defaultPresetId = "gold_luxury"
        ),
        BackgroundTemplate(
            id = "bible_strength",
            title = "Inner Strength",
            hindiTitle = "आत्मिक शक्ति",
            primaryColor = 0xFF0A2342,
            secondaryColor = 0xFF1E5288,
            sampleText = "जो मुझे सामर्थ्य देता है,\nउसमें मैं सब कुछ कर सकता हूँ।\n- फिलिप्पियों 4:13",
            font = HindiFont.POPPINS,
            defaultPresetId = "classic"
        )
    )
}
