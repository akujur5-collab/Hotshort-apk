package com.example.data.model

data class PresetCategory(
    val id: String,
    val title: String,
    val icon: String
)

data class TextStylePreset(
    val id: String,
    val name: String,
    val hindiName: String,
    val font: HindiFont,
    val textColor: Long,
    val shadow: TextShadowConfig?,
    val stroke: TextStrokeConfig?,
    val background: TextBackgroundConfig?,
    val isBold: Boolean = true,
    val category: String = "royal",
    val badgeIcon: String = "✨",
    val letterSpacingSp: Float = 0f,
    val description: String = ""
)

object PresetRepository {
    val CATEGORIES = listOf(
        PresetCategory("all", "सभी 24 स्टाइल्स", "✨"),
        PresetCategory("royal", "शाही व क्लासिक", "👑"),
        PresetCategory("neon", "नियॉन व ऊर्जा", "🔥"),
        PresetCategory("devotional", "धार्मिक व भक्ति", "🛕"),
        PresetCategory("poetry", "शायरी व सुलेख", "✒️"),
        PresetCategory("festive", "उत्सव व 3D", "🎉"),
        PresetCategory("modern", "मॉडर्न व मिनिमल", "💎")
    )

    val PRESETS = listOf(
        // === 1. शाही व क्लासिक (ROYAL & LUXURY) ===
        TextStylePreset(
            id = "classic",
            name = "Classic Bold",
            hindiName = "क्लासिक बोल्ड",
            font = HindiFont.POPPINS,
            textColor = 0xFFFFFFFF,
            shadow = TextShadowConfig(color = 0xDD000000, offsetX = 3f, offsetY = 3f, blurRadius = 6f),
            stroke = null,
            background = null,
            isBold = true,
            category = "royal",
            badgeIcon = "⚪",
            letterSpacingSp = 0.2f,
            description = "सादा सफेद व गहरा ड्रॉप शैडो"
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
            isBold = false,
            category = "royal",
            badgeIcon = "👑",
            letterSpacingSp = 0.5f,
            description = "राजसी स्वर्णिम आभा व गहरा शैडो"
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
            isBold = false,
            category = "royal",
            badgeIcon = "🛕",
            letterSpacingSp = 0.8f,
            description = "शाही बैंगनी शैडो व कोमल स्वर्ण"
        ),
        TextStylePreset(
            id = "adishila_heritage",
            name = "Adishila Heritage",
            hindiName = "आदिशिला संस्कृत",
            font = HindiFont.ADISHILA,
            textColor = 0xFFFFE0B2,
            shadow = TextShadowConfig(color = 0xAA3E2723, offsetX = 2f, offsetY = 3f, blurRadius = 6f),
            stroke = null,
            background = null,
            isBold = false,
            category = "royal",
            badgeIcon = "📜",
            letterSpacingSp = 1.0f,
            description = "प्राचीन चंदन व टेराकोटा क्लासिकल"
        ),
        TextStylePreset(
            id = "noto_serif_gold",
            name = "Noto Editorial",
            hindiName = "नोटो साहित्य",
            font = HindiFont.NOTO_SERIF_DEVANAGARI,
            textColor = 0xFFFFECB3,
            shadow = TextShadowConfig(color = 0xDD311B92, offsetX = 2f, offsetY = 3f, blurRadius = 8f),
            stroke = null,
            background = null,
            isBold = false,
            category = "royal",
            badgeIcon = "✨",
            letterSpacingSp = 0.6f,
            description = "साहित्यिक शैम्पेन गोल्ड व रॉयल पर्पल"
        ),

        // === 2. नियॉन, अग्नि व ऊर्जा (NEON & ENERGY) ===
        TextStylePreset(
            id = "fire_effect",
            name = "Fire Flame",
            hindiName = "अग्नि ज्वाला",
            font = HindiFont.YATRA_ONE,
            textColor = 0xFFFF4500,
            shadow = TextShadowConfig(color = 0xFFFF9800, offsetX = 0f, offsetY = 0f, blurRadius = 18f),
            stroke = TextStrokeConfig(color = 0xFFFFEB3B, strokeWidth = 1.5f),
            background = null,
            isBold = true,
            category = "neon",
            badgeIcon = "🔥",
            letterSpacingSp = 0f,
            description = "दहकती ज्वाला व पीला नियॉन स्ट्रोक"
        ),
        TextStylePreset(
            id = "neon_cyan",
            name = "Neon Cyan",
            hindiName = "नियॉन स्यान",
            font = HindiFont.POPPINS,
            textColor = 0xFF00FFFF,
            shadow = TextShadowConfig(color = 0xFF00E5FF, offsetX = 0f, offsetY = 0f, blurRadius = 24f),
            stroke = null,
            background = null,
            isBold = true,
            category = "neon",
            badgeIcon = "💎",
            letterSpacingSp = 0.5f,
            description = "इलेक्ट्रिक स्यान ग्लो व गहरा आभास"
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
            isBold = true,
            category = "neon",
            badgeIcon = "⚡",
            letterSpacingSp = 0.4f,
            description = "फ्यूचरिस्टिक साइबरपंक पिंक व वाइट स्ट्रोक"
        ),
        TextStylePreset(
            id = "electric_purple",
            name = "Electric Ultraviolet",
            hindiName = "इलेक्ट्रिक पर्पल",
            font = HindiFont.POPPINS,
            textColor = 0xFFE040FB,
            shadow = TextShadowConfig(color = 0xFF7C4DFF, offsetX = 0f, offsetY = 0f, blurRadius = 22f),
            stroke = TextStrokeConfig(color = 0xFFEDE7F6, strokeWidth = 0.8f),
            background = null,
            isBold = true,
            category = "neon",
            badgeIcon = "🔮",
            letterSpacingSp = 0.6f,
            description = "अल्ट्रावायलेट नियॉन प्रभा व बैंगनी ऑरा"
        ),

        // === 3. धार्मिक, पावन व भक्ति (SACRED & DEVOTIONAL) ===
        TextStylePreset(
            id = "bhagwa_divine",
            name = "Saffron Divine",
            hindiName = "भगवा तेज",
            font = HindiFont.TIRO_DEVANAGARI_HINDI,
            textColor = 0xFFFF6F00,
            shadow = TextShadowConfig(color = 0xFFFFD54F, offsetX = 0f, offsetY = 2f, blurRadius = 14f),
            stroke = TextStrokeConfig(color = 0xFFFFE082, strokeWidth = 1.2f),
            background = null,
            isBold = true,
            category = "devotional",
            badgeIcon = "🚩",
            letterSpacingSp = 0.6f,
            description = "दिव्य केसरी आभा व स्वर्णिम तेज"
        ),
        TextStylePreset(
            id = "mahakal_aura",
            name = "Mahakal Ash",
            hindiName = "महाकाल भस्म",
            font = HindiFont.KHAND,
            textColor = 0xFFECEFF1,
            shadow = TextShadowConfig(color = 0xFF263238, offsetX = 3f, offsetY = 4f, blurRadius = 14f),
            stroke = TextStrokeConfig(color = 0xFF37474F, strokeWidth = 1.5f),
            background = null,
            isBold = true,
            category = "devotional",
            badgeIcon = "🔱",
            letterSpacingSp = 0.5f,
            description = "पवित्र भस्म रजत व रहस्यमयी काला धुआं"
        ),
        TextStylePreset(
            id = "tiro_classic",
            name = "Tiro Classic",
            hindiName = "तीरो पारंपरिक",
            font = HindiFont.TIRO_DEVANAGARI_HINDI,
            textColor = 0xFFFFF9C4,
            shadow = TextShadowConfig(color = 0xCC1B5E20, offsetX = 2f, offsetY = 3f, blurRadius = 6f),
            stroke = null,
            background = null,
            isBold = false,
            category = "devotional",
            badgeIcon = "🪔",
            letterSpacingSp = 0.5f,
            description = "मंदिर शिलालेख रेशमी क्रीम व हरित छाया"
        ),
        TextStylePreset(
            id = "radhe_peacock",
            name = "Vrindavan Peacock",
            hindiName = "राधे वृंदावन",
            font = HindiFont.KALAM,
            textColor = 0xFF80DEEA,
            shadow = TextShadowConfig(color = 0xFFAD1457, offsetX = 2f, offsetY = 2f, blurRadius = 10f),
            stroke = TextStrokeConfig(color = 0xFFE91E63, strokeWidth = 0.8f),
            background = null,
            isBold = true,
            category = "devotional",
            badgeIcon = "🦚",
            letterSpacingSp = 0.6f,
            description = "मोरपंखी मोरपंख नीला व कमल गुलाबी"
        ),

        // === 4. शायरी, सुलेख व साहित्य (POETRY & CALLIGRAPHY) ===
        TextStylePreset(
            id = "kalam_handwritten",
            name = "Kalam Ink",
            hindiName = "सुलेख कलम",
            font = HindiFont.KALAM,
            textColor = 0xFFFFF8E1,
            shadow = TextShadowConfig(color = 0xCC1A237E, offsetX = 2f, offsetY = 2f, blurRadius = 5f),
            stroke = null,
            background = null,
            isBold = true,
            category = "poetry",
            badgeIcon = "✒️",
            letterSpacingSp = 0.4f,
            description = "हस्तलिखित सुंदर स्याही व गहरा इंडिगो"
        ),
        TextStylePreset(
            id = "ancient_parchment",
            name = "Ancient Manuscript",
            hindiName = "प्राचीन पांडुलिपि",
            font = HindiFont.ADISHILA,
            textColor = 0xFFFFCC80,
            shadow = TextShadowConfig(color = 0xFF4E342E, offsetX = 2f, offsetY = 3f, blurRadius = 7f),
            stroke = null,
            background = null,
            isBold = false,
            category = "poetry",
            badgeIcon = "📜",
            letterSpacingSp = 1.2f,
            description = "ऐतिहासिक भोजपत्र व विंटेज वॉलनट"
        ),
        TextStylePreset(
            id = "shayari_mood",
            name = "Poetic Moonlight",
            hindiName = "शायरी खामोशी",
            font = HindiFont.NOTO_SERIF_DEVANAGARI,
            textColor = 0xFFEDE7F6,
            shadow = TextShadowConfig(color = 0xDD311B92, offsetX = 2f, offsetY = 3f, blurRadius = 12f),
            stroke = null,
            background = null,
            isBold = false,
            category = "poetry",
            badgeIcon = "🌙",
            letterSpacingSp = 0.8f,
            description = "चांदनी लैवेंडर व भावुक गहरा बैंगनी"
        ),
        TextStylePreset(
            id = "yatra_vintage",
            name = "Yatra Signage",
            hindiName = "यात्रा विंटेज",
            font = HindiFont.YATRA_ONE,
            textColor = 0xFFFFD54F,
            shadow = TextShadowConfig(color = 0xFFBF360C, offsetX = 3f, offsetY = 4f, blurRadius = 4f),
            stroke = TextStrokeConfig(color = 0xFFE65100, strokeWidth = 1.2f),
            background = null,
            isBold = true,
            category = "poetry",
            badgeIcon = "🚌",
            letterSpacingSp = 0.5f,
            description = "क्लासिक भारतीय हाइवे व साइनेज लुक"
        ),

        // === 5. उत्सव, पॉप व 3D (FESTIVE, DISPLAY & 3D) ===
        TextStylePreset(
            id = "modak_festive",
            name = "Modak Festive",
            hindiName = "मोदक उत्सव",
            font = HindiFont.MODAK,
            textColor = 0xFFFFD700,
            shadow = TextShadowConfig(color = 0xFFD84315, offsetX = 3f, offsetY = 4f, blurRadius = 10f),
            stroke = TextStrokeConfig(color = 0xFFBF360C, strokeWidth = 2f),
            background = null,
            isBold = false,
            category = "festive",
            badgeIcon = "🎨",
            letterSpacingSp = 0f,
            description = "चबी चॉकलेटी गोल्ड व उत्सव लाल शैडो"
        ),
        TextStylePreset(
            id = "khand_impact",
            name = "Khand Impact",
            hindiName = "खांड इम्पैक्ट",
            font = HindiFont.KHAND,
            textColor = 0xFFFFFFFF,
            shadow = TextShadowConfig(color = 0xFF000000, offsetX = 4f, offsetY = 4f, blurRadius = 6f),
            stroke = TextStrokeConfig(color = 0xFFD32F2F, strokeWidth = 2f),
            background = null,
            isBold = true,
            category = "festive",
            badgeIcon = "📰",
            letterSpacingSp = 0.2f,
            description = "बोल्ड न्यूज़ हेडलाइन व गहरा रेड स्ट्रोक"
        ),
        TextStylePreset(
            id = "deep_shadow",
            name = "Deep 3D Shadow",
            hindiName = "गहरा 3D छाया",
            font = HindiFont.YATRA_ONE,
            textColor = 0xFFFFC107,
            shadow = TextShadowConfig(color = 0xFF212121, offsetX = 6f, offsetY = 8f, blurRadius = 2f),
            stroke = null,
            background = null,
            isBold = true,
            category = "festive",
            badgeIcon = "🧱",
            letterSpacingSp = 0.4f,
            description = "3D गहरा उभार व कड़ा सॉलिड शैडो"
        ),
        TextStylePreset(
            id = "outline_pop",
            name = "Outline Pop",
            hindiName = "आउटलाइन पॉप",
            font = HindiFont.POPPINS,
            textColor = 0xFFFFFFFF,
            shadow = TextShadowConfig(color = 0x88000000, offsetX = 4f, offsetY = 4f, blurRadius = 6f),
            stroke = TextStrokeConfig(color = 0xFF1A1A2E, strokeWidth = 3f),
            background = null,
            isBold = true,
            category = "festive",
            badgeIcon = "🔘",
            letterSpacingSp = 0.2f,
            description = "पॉप आर्ट डबल थिक स्ट्रोक"
        ),
        TextStylePreset(
            id = "holi_gulal",
            name = "Holi Splash",
            hindiName = "होली गुलाल",
            font = HindiFont.MODAK,
            textColor = 0xFFFF4081,
            shadow = TextShadowConfig(color = 0xFFFFEA00, offsetX = 0f, offsetY = 0f, blurRadius = 16f),
            stroke = TextStrokeConfig(color = 0xFFFF6D00, strokeWidth = 1.8f),
            background = null,
            isBold = false,
            category = "festive",
            badgeIcon = "🎉",
            letterSpacingSp = 0f,
            description = "गुलाबी गुलाल, पीला ग्लो व संतरी स्ट्रोक"
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
            isBold = true,
            category = "festive",
            badgeIcon = "🌊",
            letterSpacingSp = 0.4f,
            description = "ताज़ा समुद्री लहर व टील आउटलाइन"
        ),

        // === 6. मॉडर्न, मिनिमल व हाइलाइट (MODERN & MINIMAL) ===
        TextStylePreset(
            id = "tag_chip",
            name = "Minimal Chip",
            hindiName = "हाइलाइट पट्टी",
            font = HindiFont.MUKTA,
            textColor = 0xFFFFFFFF,
            shadow = null,
            stroke = null,
            background = TextBackgroundConfig(color = 0xDD6C63FF, cornerRadius = 8f, paddingHorizontal = 16f, paddingVertical = 6f),
            isBold = true,
            category = "modern",
            badgeIcon = "🏷️",
            letterSpacingSp = 0.5f,
            description = "आधुनिक पर्पल हाइलाइटर कैप्सूल"
        ),
        TextStylePreset(
            id = "kohinoor_clean",
            name = "Kohinoor Modern",
            hindiName = "कोहिनूर मॉडर्न",
            font = HindiFont.KOHINOOR_DEVANAGARI,
            textColor = 0xFFFFFFFF,
            shadow = TextShadowConfig(color = 0x99000000, offsetX = 2f, offsetY = 2f, blurRadius = 6f),
            stroke = null,
            background = null,
            isBold = false,
            category = "modern",
            badgeIcon = "💎",
            letterSpacingSp = 0.5f,
            description = "अल्ट्रा-क्लीन मिनिमल देवनागरी"
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

data class StickerCategory(
    val id: String,
    val title: String,
    val hindiTitle: String,
    val icon: String
)

data class HindiSticker(
    val category: String,
    val icon: String,
    val isDevanagariWord: Boolean = false,
    val searchKeywords: String = ""
)

object HindiStickersRepository {
    val CATEGORIES = listOf(
        StickerCategory("all", "All", "सभी", "✨"),
        StickerCategory("devotional", "Devotional", "भक्ति व शुभ", "🕉️"),
        StickerCategory("wishes", "Wishes", "शुभकामनाएं", "🎉"),
        StickerCategory("motivation", "Motivation", "प्रेरणा व देश", "🇮🇳"),
        StickerCategory("love_family", "Love & Relations", "रिश्ते व प्रेम", "💖"),
        StickerCategory("aesthetic", "Decor & Nature", "प्रकृति व सजावट", "🌸")
    )

    val STICKERS = listOf(
        // === 1. DEVOTIONAL & SACRED (भक्ति व शुभ) ===
        HindiSticker("devotional", "ॐ", true, "om aum shiv bhakti"),
        HindiSticker("devotional", "श्री", true, "shree shri shubh"),
        HindiSticker("devotional", "शुभ लाभ", true, "shubh laabh ganesh mangal"),
        HindiSticker("devotional", "स्वास्तिक 卐", true, "swastik mangal"),
        HindiSticker("devotional", "नमस्ते 🙏", true, "namaste pranam sparsh"),
        HindiSticker("devotional", "राधे राधे", true, "radhe radhe krishna vrindavan"),
        HindiSticker("devotional", "हर हर महादेव 🔱", true, "har har mahadev shiva bholenath"),
        HindiSticker("devotional", "जय श्री राम 🚩", true, "jai shree ram hanuman ayodhya"),
        HindiSticker("devotional", "ॐ नमः शिवाय", true, "om namah shivay shiv"),
        HindiSticker("devotional", "जय माता दी", true, "jai mata di durga vaishno"),
        HindiSticker("devotional", "महाकाल 🔱", true, "mahakal shiv ujjain"),
        HindiSticker("devotional", "जय बजरंगबली 🚩", true, "hanuman bajrangbali"),
        HindiSticker("devotional", "सीताराम", true, "sita ram prabhu"),
        HindiSticker("devotional", "जय श्री कृष्णा", true, "shri krishna kanha gopal"),
        HindiSticker("devotional", "जय जगन्नाथ", true, "jagannath puri balabhadra"),
        HindiSticker("devotional", "वाहेगुरु ੴ", true, "waheguru ik onkar sikh"),
        HindiSticker("devotional", "अल्लाह ☪️", true, "allah islam mubarak khuda"),
        HindiSticker("devotional", "प्रभु येशु ✝️", true, "yeshu jesus christian bible prabhu"),
        HindiSticker("devotional", "त्रिशूल 🔱", true, "trishul shiv bholenath"),
        HindiSticker("devotional", "शंख 🐚", true, "shankh pooja arti"),
        HindiSticker("devotional", "कलश 🏺", true, "kalash mangal poojan"),
        HindiSticker("devotional", "रुद्राक्ष 📿", true, "rudraksha mala shiv"),
        HindiSticker("devotional", "दीपक 🪔", true, "diya deepak jyot"),
        HindiSticker("devotional", "चरण स्पर्श 🙏", true, "charan sparsh pranam aashirwad"),
        HindiSticker("devotional", "आरती 🕯️", true, "aarti pooja diya"),
        HindiSticker("devotional", "मोरपंख 🪶", true, "morpankh krishna kanha"),
        HindiSticker("devotional", "कमल 🪷", true, "kamal lotus laxmi"),
        HindiSticker("devotional", "ॐ तत्सत्", true, "om tat sat ved upanishad"),
        HindiSticker("devotional", "हरि ॐ", true, "hari om narayan vishnu"),
        HindiSticker("devotional", "जय भोलेनाथ", true, "bholenath shankar shiv"),
        HindiSticker("devotional", "सत्य सनातन 🚩", true, "sanatan dharma hindutva"),
        HindiSticker("devotional", "कण-कण में राम", true, "ram ishwar bhagwan"),
        HindiSticker("devotional", "सत्यमेव जयते", true, "satyameva jayate satya"),

        // === 2. WISHES & CELEBRATIONS (शुभकामनाएं व उत्सव) ===
        HindiSticker("wishes", "शुभ प्रभात 🌅", true, "shubh prabhat good morning subah"),
        HindiSticker("wishes", "शुभ रात्रि 🌙", true, "shubh ratri good night shaam"),
        HindiSticker("wishes", "जन्मदिन मुबारक 🎂", true, "happy birthday janamdin cake badhai"),
        HindiSticker("wishes", "हार्दिक बधाई 💐", true, "congratulations badhai shubhkaamna"),
        HindiSticker("wishes", "मंगलमय दिन ☀️", true, "mangalmay din shubh good day"),
        HindiSticker("wishes", "ढेरों शुभकामनाएं ✨", true, "shubhkaamnaye best wishes"),
        HindiSticker("wishes", "शुभ दीपावली 🪔", true, "shubh deepavali diwali roshni"),
        HindiSticker("wishes", "रंगों का त्योहार होली 🎨", true, "holi colors festival pichkari"),
        HindiSticker("wishes", "रक्षाबंधन की बधाई 🧵", true, "rakshabandhan rakhi bhai behen"),
        HindiSticker("wishes", "नव वर्ष मंगलमय हो 🎆", true, "happy new year nav varsh saal"),
        HindiSticker("wishes", "ईद मुबारक 🌙", true, "eid mubarak ramadan"),
        HindiSticker("wishes", "क्रिसमस की शुभकामनाएं 🎄", true, "merry christmas xmas"),
        HindiSticker("wishes", "मकर संक्रांति 🪁", true, "makar sankranti kite patang"),
        HindiSticker("wishes", "महाशिवरात्रि 🔱", true, "mahashivratri shiv parvathi"),
        HindiSticker("wishes", "गणेश चतुर्थी 🐘", true, "ganesh chaturthi bappa moriya"),
        HindiSticker("wishes", "कृष्ण जन्माष्टमी 🦚", true, "krishna janmashtami dahi handi"),
        HindiSticker("wishes", "विजयदशमी दशहरा 🏹", true, "dussehra vijayadashami raavan"),
        HindiSticker("wishes", "करवा चौथ 🌙", true, "karwa chauth suhaag chand"),
        HindiSticker("wishes", "छठ पूजा ☀️", true, "chhath puja surya dev"),
        HindiSticker("wishes", "लोहड़ी की बधाई 🔥", true, "lohri baisakhi punjab"),
        HindiSticker("wishes", "साalgirah मुबारक 💍", true, "anniversary saalgirah jodi"),
        HindiSticker("wishes", "विवाह मंगल 🎊", true, "vivah wedding shadi vivah"),
        HindiSticker("wishes", "नया सवेरा नई उम्मीद 🌄", true, "naya savera subah aasha"),
        HindiSticker("wishes", "खुशहाल जीवन 🌸", true, "khushiyan happiness jeevan anand"),

        // === 3. MOTIVATION & PATRIOTISM (प्रेरणा व देश) ===
        HindiSticker("motivation", "जय हिंद 🇮🇳", true, "jai hind tiranga bharat desh"),
        HindiSticker("motivation", "वंदे मातरम 🇮🇳", true, "vande mataram rashtra geet"),
        HindiSticker("motivation", "भारत माता की जय 🚩", true, "bharat mata desh bhakti"),
        HindiSticker("motivation", "हौसला ज़िंदा रखो 🔥", true, "hausla courage junoon"),
        HindiSticker("motivation", "सफलता की उड़ान 🏆", true, "safalta success victory jeet"),
        HindiSticker("motivation", "कर्म ही पूजा है", true, "karm work worship nishkam"),
        HindiSticker("motivation", "सकारात्मक सोच 💡", true, "positive thinking sakaratmak vichar"),
        HindiSticker("motivation", "आत्मविश्वास 🦁", true, "self confidence sher shakti"),
        HindiSticker("motivation", "मेहनत का फल ⭐", true, "hard work mehnat parishram"),
        HindiSticker("motivation", "अडिग लक्ष्य 🎯", true, "target goal lakshya focus"),
        HindiSticker("motivation", "समय मूल्यवान है ⏳", true, "samay time value waqt"),
        HindiSticker("motivation", "विजेता संकल्प 🥇", true, "winner champ champion sankalp"),
        HindiSticker("motivation", "ऊर्जा और उमंग ⚡", true, "energy positivity umang josh"),
        HindiSticker("motivation", "साहस और धैर्य 💪", true, "courage sahas dhairya patience"),
        HindiSticker("motivation", "ज्ञान ही शक्ति है 📚", true, "knowledge gyan shiksha shakti"),
        HindiSticker("motivation", "आशा की किरण 🌟", true, "hope aasha kiran roshni"),
        HindiSticker("motivation", "कर्मयोगी", true, "karmyogi gita karm"),
        HindiSticker("motivation", "संघर्ष से शिखर", true, "sangharsh struggle peak unchai"),
        HindiSticker("motivation", "सपने सच होंगे", true, "dreams come true sapne sach"),
        HindiSticker("motivation", "असंभव कुछ नहीं", true, "impossible nothing namumkin"),

        // === 4. LOVE & RELATIONS (रिश्ते, प्रेम व परिवार) ===
        HindiSticker("love_family", "दिल से ❤️", true, "dil se heart prem pyaar"),
        HindiSticker("love_family", "मेरी जान 💖", true, "meri jaan beloved prem"),
        HindiSticker("love_family", "सच्चा दोस्त 🤝", true, "true friend dosti yaar mitra"),
        HindiSticker("love_family", "प्यारा परिवार 👨‍👩‍👧‍👦", true, "family parivar ghar apno"),
        HindiSticker("love_family", "मुस्कुराते रहो 😊", true, "smile muskaan smile hansi"),
        HindiSticker("love_family", "अपना ख्याल रखना 🌸", true, "take care khayal dekhbhal"),
        HindiSticker("love_family", "सदा सुखी रहो 🌼", true, "be happy sukhi anand"),
        HindiSticker("love_family", "सुंदर यादें ✨", true, "memories yaadein lamhe"),
        HindiSticker("love_family", "दोस्ती सदाबहार 👬", true, "friendship dosti yaari"),
        HindiSticker("love_family", "माँ का प्यार 🤱", true, "maa mother mom pyaar aanchal"),
        HindiSticker("love_family", "पिता का साया 👨", true, "father papa pita baap aashirwad"),
        HindiSticker("love_family", "अटूट बंधन 💫", true, "bond rishta bandhan"),
        HindiSticker("love_family", "कोटि धन्यवाद 🙏", true, "dhanyawad shukriya thanks aabhar"),
        HindiSticker("love_family", "हार्दिक आशीर्वाद 🌟", true, "blessings aashirwad badon"),
        HindiSticker("love_family", "दुआएं हमेशा 🤲", true, "dua prayers ibadat barakat"),
        HindiSticker("love_family", "अटूट विश्वास 🤝", true, "trust vishwas bharosa"),
        HindiSticker("love_family", "सच्चा प्यार 💕", true, "true love prem mohobbat"),
        HindiSticker("love_family", "हमेशा साथ 👫", true, "always together saath sang"),

        // === 5. DECOR & NATURE (प्रकृति, सौंदर्य व सजावट) ===
        HindiSticker("aesthetic", "✨", false, "sparkles चमक तारा roshni"),
        HindiSticker("aesthetic", "⭐", false, "star सितारा tara"),
        HindiSticker("aesthetic", "🌟", false, "glowing star chamakta tara"),
        HindiSticker("aesthetic", "💫", false, "dizzy star chamak"),
        HindiSticker("aesthetic", "🪔", false, "diya deepak roshni deepawali"),
        HindiSticker("aesthetic", "🌸", false, "cherry blossom phool pushp"),
        HindiSticker("aesthetic", "🌹", false, "red rose gulab phool"),
        HindiSticker("aesthetic", "🌺", false, "hibiscus phool gudhal"),
        HindiSticker("aesthetic", "🌻", false, "sunflower surajmukhi"),
        HindiSticker("aesthetic", "🌷", false, "tulip flower pushp"),
        HindiSticker("aesthetic", "🌼", false, "daisy chameli pushp"),
        HindiSticker("aesthetic", "🪷", false, "lotus kamal pushp laxmi"),
        HindiSticker("aesthetic", "💐", false, "bouquet guldasta phool"),
        HindiSticker("aesthetic", "🍃", false, "leaves patta nature prakriti"),
        HindiSticker("aesthetic", "🌿", false, "herb paudha hara"),
        HindiSticker("aesthetic", "🍁", false, "maple leaf patta autumn"),
        HindiSticker("aesthetic", "🕊️", false, "dove shanti kabutar peace bird"),
        HindiSticker("aesthetic", "🦋", false, "butterfly titli sunder"),
        HindiSticker("aesthetic", "🦚", false, "peacock mor mayur sundar"),
        HindiSticker("aesthetic", "🦜", false, "parrot tota pakshi"),
        HindiSticker("aesthetic", "🌈", false, "rainbow indradhanush rang"),
        HindiSticker("aesthetic", "☀️", false, "sun surya ravi dhoop"),
        HindiSticker("aesthetic", "🌙", false, "moon chand chanda shaam raat"),
        HindiSticker("aesthetic", "🔥", false, "fire aag jwala agni"),
        HindiSticker("aesthetic", "💧", false, "water drop jal boond paani"),
        HindiSticker("aesthetic", "🌊", false, "ocean wave sagar leher samudra"),
        HindiSticker("aesthetic", "☕", false, "tea coffee chai pyali nashta"),
        HindiSticker("aesthetic", "🪶", false, "feather pankh morpankh lekhani"),
        HindiSticker("aesthetic", "📜", false, "scroll lekh shayari patr"),
        HindiSticker("aesthetic", "🎨", false, "art palette rang kala"),
        HindiSticker("aesthetic", "👑", false, "crown mukut raja shahi taj"),
        HindiSticker("aesthetic", "💎", false, "diamond heera ratna gem"),
        HindiSticker("aesthetic", "🎯", false, "target lakshya dart nishana"),
        HindiSticker("aesthetic", "🏹", false, "bow and arrow dhanush baan teer"),
        HindiSticker("aesthetic", "🚩", false, "flag dhwaja jhanda kesari"),
        HindiSticker("aesthetic", "🔔", false, "bell ghanti mandir temple"),
        HindiSticker("aesthetic", "🕯️", false, "candle mombatti roshni"),
        HindiSticker("aesthetic", "🎁", false, "gift uphar bhent tohfa"),
        HindiSticker("aesthetic", "🎈", false, "balloon gubbara celebration"),
        HindiSticker("aesthetic", "🎉", false, "party popper badhai utsav"),
        HindiSticker("aesthetic", "🎊", false, "confetti ball utsav jashn"),
        HindiSticker("aesthetic", "🧿", false, "evil eye nazar battu suraksha"),
        HindiSticker("aesthetic", "❤️", false, "red heart dil prem laal"),
        HindiSticker("aesthetic", "💖", false, "sparkling heart chamakta dil"),
        HindiSticker("aesthetic", "💛", false, "yellow gold heart peela dil"),
        HindiSticker("aesthetic", "🤍", false, "white peace heart safed dil"),
        HindiSticker("aesthetic", "💮", false, "white flower pushp phool"),
        HindiSticker("aesthetic", "🎖️", false, "medal samman medal padak")
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
        ),
        BackgroundTemplate(
            id = "festive_modak",
            title = "Festive Celebration",
            hindiTitle = "उत्सव मोदक",
            primaryColor = 0xFF4A0000,
            secondaryColor = 0xFF880E4F,
            sampleText = "गणपति बप्पा मोरया\nमंगल मूर्ति मोरया",
            font = HindiFont.MODAK,
            defaultPresetId = "modak_festive"
        ),
        BackgroundTemplate(
            id = "khand_banner",
            title = "Bold Announcement",
            hindiTitle = "खांड समाचार",
            primaryColor = 0xFF0D1B2A,
            secondaryColor = 0xFF1B263B,
            sampleText = "सफलता की कुंजी\nकठिन परिश्रम है",
            font = HindiFont.KHAND,
            defaultPresetId = "khand_impact"
        )
    )
}
