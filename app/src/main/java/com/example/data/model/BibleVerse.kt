package com.example.data.model

import java.util.Calendar

data class BibleVerse(
    val id: String,
    val verseHindi: String,
    val referenceHindi: String,
    val verseEnglish: String,
    val referenceEnglish: String,
    val category: String,
    val categoryHindi: String,
    val bgStartColor: Long = 0xFF1A0933,
    val bgEndColor: Long = 0xFF3D155F,
    val presetId: String = "gold_luxury"
)

object BibleVerseRepository {

    val CATEGORIES = listOf(
        "All" to "सभी",
        "Strength" to "साहस व शक्ति",
        "Peace" to "शांति व सुकून",
        "Faith" to "विश्वास व आशा",
        "Love" to "प्रेम व अनुग्रह",
        "Wisdom" to "मार्गदर्शन व ज्ञान",
        "Blessing" to "स्तुति व आशीष"
    )

    val VERSES = listOf(
        BibleVerse(
            id = "phil_4_13",
            verseHindi = "जो मुझे सामर्थ्य देता है, उसमें मैं सब कुछ कर सकता हूँ।",
            referenceHindi = "फिलिप्पियों 4:13",
            verseEnglish = "I can do all things through Christ who strengthens me.",
            referenceEnglish = "Philippians 4:13",
            category = "Strength",
            categoryHindi = "साहस व शक्ति",
            bgStartColor = 0xFF14052B,
            bgEndColor = 0xFF3B1566,
            presetId = "gold_luxury"
        ),
        BibleVerse(
            id = "psalm_23_1",
            verseHindi = "यहोवा मेरा चरवाहा है; मुझे कुछ घटी न होगी।",
            referenceHindi = "भजन संहिता 23:1",
            verseEnglish = "The Lord is my shepherd; I shall not want.",
            referenceEnglish = "Psalm 23:1",
            category = "Peace",
            categoryHindi = "शांति व सुकून",
            bgStartColor = 0xFF0A2342,
            bgEndColor = 0xFF1E5288,
            presetId = "water_style"
        ),
        BibleVerse(
            id = "jer_29_11",
            verseHindi = "क्योंकि यहोवा की यह वाणी है, कि जो कल्पनाएं मैं तुम्हारे विषय करता हूँ उन्हें मैं जानता हूँ, वे भलाई ही की हैं, हानि की नहीं, कि अन्त में तुम्हारी आशा पूरी करूँ।",
            referenceHindi = "यिर्मयाह 29:11",
            verseEnglish = "For I know the thoughts that I think toward you, says the Lord, thoughts of peace and not of evil, to give you a future and a hope.",
            referenceEnglish = "Jeremiah 29:11",
            category = "Faith",
            categoryHindi = "विश्वास व आशा",
            bgStartColor = 0xFF2D0E3E,
            bgEndColor = 0xFF652378,
            presetId = "royal_devanagari"
        ),
        BibleVerse(
            id = "prov_3_5_6",
            verseHindi = "तू अपनी समझ का सहारा न लेना, वरन सम्पूर्ण मन से यहोवा पर भरोसा रखना। उसी को स्मरण करके सब काम करना, तब वह तेरे लिये सीधा मार्ग निकालेगा।",
            referenceHindi = "नीतिवचन 3:5-6",
            verseEnglish = "Trust in the Lord with all your heart, and lean not on your own understanding; in all your ways acknowledge Him, and He shall direct your paths.",
            referenceEnglish = "Proverbs 3:5-6",
            category = "Wisdom",
            categoryHindi = "मार्गदर्शन व ज्ञान",
            bgStartColor = 0xFF1C1A27,
            bgEndColor = 0xFF393452,
            presetId = "classic"
        ),
        BibleVerse(
            id = "john_3_16",
            verseHindi = "क्योंकि परमेश्वर ने जगत से ऐसा प्रेम रखा कि उसने अपना एकलौता पुत्र दे दिया, ताकि जो कोई उस पर विश्वास करे, वह नाश न हो, परन्तु अनन्त जीवन पाए।",
            referenceHindi = "यूहन्ना 3:16",
            verseEnglish = "For God so loved the world that He gave His only begotten Son, that whoever believes in Him should not perish but have everlasting life.",
            referenceEnglish = "John 3:16",
            category = "Love",
            categoryHindi = "प्रेम व अनुग्रह",
            bgStartColor = 0xFF350A24,
            bgEndColor = 0xFF701E45,
            presetId = "gold_luxury"
        ),
        BibleVerse(
            id = "isaiah_40_31",
            verseHindi = "परन्तु जो यहोवा की बाट जोहते हैं, वे नया बल प्राप्त करते जाएंगे, वे उकाबों के समान पंखों से उड़ेंगे; वे दौड़ेंगे और थकित न होंगे, चलेंगे और शिथिल न होंगे।",
            referenceHindi = "यशायाह 40:31",
            verseEnglish = "Those who wait on the Lord shall renew their strength; they shall mount up with wings like eagles, they shall run and not be weary, they shall walk and not faint.",
            referenceEnglish = "Isaiah 40:31",
            category = "Strength",
            categoryHindi = "साहस व शक्ति",
            bgStartColor = 0xFF112233,
            bgEndColor = 0xFF225577,
            presetId = "neon_cyan"
        ),
        BibleVerse(
            id = "matt_6_33",
            verseHindi = "इसलिए पहले तुम परमेश्वर के राज्य और उसके धर्म की खोज करो, तो ये सब वस्तुएं भी तुम्हें मिल जाएंगी।",
            referenceHindi = "मत्ती 6:33",
            verseEnglish = "Seek first the kingdom of God and His righteousness, and all these things shall be added to you.",
            referenceEnglish = "Matthew 6:33",
            category = "Faith",
            categoryHindi = "विश्वास व आशा",
            bgStartColor = 0xFF1F1235,
            bgEndColor = 0xFF432371,
            presetId = "gold_luxury"
        ),
        BibleVerse(
            id = "joshua_1_9",
            verseHindi = "क्या मैंने तुझे आज्ञा नहीं दी? हियाव बान्ध और दृढ़ हो जा; भय न खा, और तेरा मन कच्चा न हो; क्योंकि जहां जहां तू जाएगा वहां वहां तेरा परमेश्वर यहोवा तेरे संग रहेगा।",
            referenceHindi = "यहोशू 1:9",
            verseEnglish = "Be strong and of good courage; do not be afraid, nor be dismayed, for the Lord your God is with you wherever you go.",
            referenceEnglish = "Joshua 1:9",
            category = "Strength",
            categoryHindi = "साहस व शक्ति",
            bgStartColor = 0xFF2B0A0D,
            bgEndColor = 0xFF6B1D24,
            presetId = "fire_effect"
        ),
        BibleVerse(
            id = "psalm_46_1",
            verseHindi = "परमेश्वर हमारा शरणस्थान और बल है, संकट में अति सहज से मिलने वाला सहायक।",
            referenceHindi = "भजन संहिता 46:1",
            verseEnglish = "God is our refuge and strength, a very present help in trouble.",
            referenceEnglish = "Psalm 46:1",
            category = "Peace",
            categoryHindi = "शांति व सुकून",
            bgStartColor = 0xFF0D2538,
            bgEndColor = 0xFF194C72,
            presetId = "water_style"
        ),
        BibleVerse(
            id = "romans_8_28",
            verseHindi = "हम जानते हैं, कि जो लोग परमेश्वर से प्रेम रखते हैं, उनके लिये सब बातें मिलकर भलाई ही को उत्पन्न करती हैं।",
            referenceHindi = "रोमियों 8:28",
            verseEnglish = "And we know that all things work together for good to those who love God, to those who are the called according to His purpose.",
            referenceEnglish = "Romans 8:28",
            category = "Faith",
            categoryHindi = "विश्वास व आशा",
            bgStartColor = 0xFF24143D,
            bgEndColor = 0xFF512E85,
            presetId = "royal_devanagari"
        ),
        BibleVerse(
            id = "psalm_91_1_2",
            verseHindi = "जो परमप्रधान के छाए हुए स्थान में बैठा रहे, वह सर्वशक्तिमान की छाया में ठिकाना पाएगा। मैं यहोवा के विषय कहूँगा, 'वह मेरा शरणस्थान और मेरा गढ़ है; वह मेरा परमेश्वर है, मैं उस पर भरोसा रखूँगा।'",
            referenceHindi = "भजन संहिता 91:1-2",
            verseEnglish = "He who dwells in the secret place of the Most High shall abide under the shadow of the Almighty. I will say of the Lord, 'He is my refuge and my fortress; my God, in Him I will trust.'",
            referenceEnglish = "Psalm 91:1-2",
            category = "Peace",
            categoryHindi = "शांति व सुकून",
            bgStartColor = 0xFF121B28,
            bgEndColor = 0xFF254163,
            presetId = "classic"
        ),
        BibleVerse(
            id = "matt_11_28",
            verseHindi = "हे सब परिश्रम करने वालों और बोझ से दबे हुए लोगो, मेरे पास आओ; मैं तुम्हें विश्राम दूँगा।",
            referenceHindi = "मत्ती 11:28",
            verseEnglish = "Come to Me, all you who labor and are heavy laden, and I will give you rest.",
            referenceEnglish = "Matthew 11:28",
            category = "Peace",
            categoryHindi = "शांति व सुकून",
            bgStartColor = 0xFF1B263B,
            bgEndColor = 0xFF415A77,
            presetId = "water_style"
        ),
        BibleVerse(
            id = "1cor_13_4_7",
            verseHindi = "प्रेम धीरजवन्त है, और कृपालु है; प्रेम डाह नहीं करता; प्रेम अपनी बड़ाई नहीं करता, और फूलता नहीं... वह सब बातें सह लेता है, सब बातों पर विश्वास करता है।",
            referenceHindi = "1 कुरिन्थियों 13:4-7",
            verseEnglish = "Love suffers long and is kind; love does not envy; love does not parade itself... bears all things, believes all things, hopes all things, endures all things.",
            referenceEnglish = "1 Corinthians 13:4-7",
            category = "Love",
            categoryHindi = "प्रेम व अनुग्रह",
            bgStartColor = 0xFF3D0C2E,
            bgEndColor = 0xFF831A62,
            presetId = "gold_luxury"
        ),
        BibleVerse(
            id = "psalm_119_105",
            verseHindi = "तेरा वचन मेरे पांव के लिये दीपक, और मेरे मार्ग के लिये उजियाला है।",
            referenceHindi = "भजन संहिता 119:105",
            verseEnglish = "Your word is a lamp to my feet and a light to my path.",
            referenceEnglish = "Psalm 119:105",
            category = "Wisdom",
            categoryHindi = "मार्गदर्शन व ज्ञान",
            bgStartColor = 0xFF261A05,
            bgEndColor = 0xFF66440C,
            presetId = "gold_luxury"
        ),
        BibleVerse(
            id = "2tim_1_7",
            verseHindi = "क्योंकि परमेश्वर ने हमें भय की नहीं, पर सामर्थ्य, और प्रेम, और संयम की आत्मा दी है।",
            referenceHindi = "2 तीमुथियुस 1:7",
            verseEnglish = "For God has not given us a spirit of fear, but of power and of love and of a sound mind.",
            referenceEnglish = "2 Timothy 1:7",
            category = "Strength",
            categoryHindi = "साहस व शक्ति",
            bgStartColor = 0xFF1E0F38,
            bgEndColor = 0xFF482087,
            presetId = "neon_cyan"
        ),
        BibleVerse(
            id = "gal_5_22_23",
            verseHindi = "आत्मा का फल प्रेम, आनन्द, मेल, धीरज, कृपा, भलाई, विश्वास, नम्रता, और संयम हैं।",
            referenceHindi = "गलतियों 5:22-23",
            verseEnglish = "The fruit of the Spirit is love, joy, peace, longsuffering, kindness, goodness, faithfulness, gentleness, self-control.",
            referenceEnglish = "Galatians 5:22-23",
            category = "Blessing",
            categoryHindi = "स्तुति व आशीष",
            bgStartColor = 0xFF0E2E1F,
            bgEndColor = 0xFF1E5E40,
            presetId = "classic"
        ),
        BibleVerse(
            id = "eph_2_8_9",
            verseHindi = "क्योंकि विश्वास के द्वारा अनुग्रह ही से तुम्हारा उद्धार हुआ है, और यह तुम्हारी ओर से नहीं, वरन परमेश्वर का दान है।",
            referenceHindi = "इफिसियों 2:8-9",
            verseEnglish = "For by grace you have been saved through faith, and that not of yourselves; it is the gift of God.",
            referenceEnglish = "Ephesians 2:8-9",
            category = "Love",
            categoryHindi = "प्रेम व अनुग्रह",
            bgStartColor = 0xFF2A103D,
            bgEndColor = 0xFF5B2384,
            presetId = "royal_devanagari"
        ),
        BibleVerse(
            id = "num_6_24_26",
            verseHindi = "यहोवा तुझे आशीष दे और तेरी रक्षा करे: यहोवा तुझ पर अपने मुख का प्रकाश चमकाए, और तुझ पर अनुग्रह करे: यहोवा अपना मुख तेरी ओर करे, और तुझे शान्ति दे।",
            referenceHindi = "गिनती 6:24-26",
            verseEnglish = "The Lord bless you and keep you; the Lord make His face shine upon you, and be gracious to you; the Lord lift up His countenance upon you, and give you peace.",
            referenceEnglish = "Numbers 6:24-26",
            category = "Blessing",
            categoryHindi = "स्तुति व आशीष",
            bgStartColor = 0xFF191238,
            bgEndColor = 0xFF3F2D87,
            presetId = "gold_luxury"
        ),
        BibleVerse(
            id = "lam_3_22_23",
            verseHindi = "यह यहोवा की महाकृपा का फल है कि हम मिट नहीं गए, क्योंकि उसकी दया कभी समाप्त नहीं होती; वह प्रति भोर नई होती रहती है; तेरी सच्चाई महान है।",
            referenceHindi = "विलापगीत 3:22-23",
            verseEnglish = "Through the Lord's mercies we are not consumed, because His compassions fail not. They are new every morning; great is Your faithfulness.",
            referenceEnglish = "Lamentations 3:22-23",
            category = "Blessing",
            categoryHindi = "स्तुति व आशीष",
            bgStartColor = 0xFF291500,
            bgEndColor = 0xFF633400,
            presetId = "gold_luxury"
        ),
        BibleVerse(
            id = "psalm_121_1_2",
            verseHindi = "मैं अपनी आंखें पर्वतों की ओर लगाऊंगा। मुझे सहायता कहां से मिलेगी? मेरी सहायता यहोवा की ओर से आती है, जो आकाश और पृथ्वी का कर्ता है।",
            referenceHindi = "भजन संहिता 121:1-2",
            verseEnglish = "I will lift up my eyes to the hills—from whence comes my help? My help comes from the Lord, who made heaven and earth.",
            referenceEnglish = "Psalm 121:1-2",
            category = "Faith",
            categoryHindi = "विश्वास व आशा",
            bgStartColor = 0xFF0E1A2B,
            bgEndColor = 0xFF1E3A60,
            presetId = "water_style"
        ),
        BibleVerse(
            id = "phil_4_6_7",
            verseHindi = "किसी भी बात की चिन्ता मत करो: वरन हर एक बात में तुम्हारे निवेदन, प्रार्थना और विन्ती के द्वारा धन्यवाद के साथ परमेश्वर के सम्मुख उपस्थित किए जाएं। तब परमेश्वर की शान्ति तुम्हारे हृदय और विचारों को सुरक्षित रखेगी।",
            referenceHindi = "फिलिप्पियों 4:6-7",
            verseEnglish = "Be anxious for nothing, but in everything by prayer and supplication, with thanksgiving, let your requests be made known to God; and the peace of God, which surpasses all understanding, will guard your hearts and minds.",
            referenceEnglish = "Philippians 4:6-7",
            category = "Peace",
            categoryHindi = "शांति व सुकून",
            bgStartColor = 0xFF152238,
            bgEndColor = 0xFF2C4977,
            presetId = "classic"
        ),
        BibleVerse(
            id = "john_14_6",
            verseHindi = "यीशु ने उससे कहा, 'मार्ग और सच्चाई और जीवन मैं ही हूँ; बिना मेरे द्वारा कोई पिता के पास नहीं पहुँच सकता।'",
            referenceHindi = "यूहन्ना 14:6",
            verseEnglish = "Jesus said to him, 'I am the way, the truth, and the life. No one comes to the Father except through Me.'",
            referenceEnglish = "John 14:6",
            category = "Wisdom",
            categoryHindi = "मार्गदर्शन व ज्ञान",
            bgStartColor = 0xFF210933,
            bgEndColor = 0xFF4D1776,
            presetId = "gold_luxury"
        ),
        BibleVerse(
            id = "psalm_37_4",
            verseHindi = "यहोवा को अपने सुख का मूल जान, और वह तेरे मनोरथों को पूरा करेगा।",
            referenceHindi = "भजन संहिता 37:4",
            verseEnglish = "Delight yourself also in the Lord, and He shall give you the desires of your heart.",
            referenceEnglish = "Psalm 37:4",
            category = "Blessing",
            categoryHindi = "स्तुति व आशीष",
            bgStartColor = 0xFF251A07,
            bgEndColor = 0xFF5C4012,
            presetId = "gold_luxury"
        ),
        BibleVerse(
            id = "heb_11_1",
            verseHindi = "विश्वास आशा की हुई वस्तुओं का निश्चय, और अनदेखी वस्तुओं का प्रमाण है।",
            referenceHindi = "इब्रानियों 11:1",
            verseEnglish = "Now faith is the substance of things hoped for, the evidence of things not seen.",
            referenceEnglish = "Hebrews 11:1",
            category = "Faith",
            categoryHindi = "विश्वास व आशा",
            bgStartColor = 0xFF1A1231,
            bgEndColor = 0xFF3D2A72,
            presetId = "royal_devanagari"
        ),
        BibleVerse(
            id = "psalm_27_1",
            verseHindi = "यहोवा मेरी ज्योति और मेरा उद्धार है; मैं किस से डरूँ? यहोवा मेरे जीवन का दृढ़ गढ़ है; मैं किस का भय खाऊं?",
            referenceHindi = "भजन संहिता 27:1",
            verseEnglish = "The Lord is my light and my salvation; whom shall I fear? The Lord is the strength of my life; of whom shall I be afraid?",
            referenceEnglish = "Psalm 27:1",
            category = "Strength",
            categoryHindi = "साहस व शक्ति",
            bgStartColor = 0xFF261014,
            bgEndColor = 0xFF5E2732,
            presetId = "fire_effect"
        ),
        BibleVerse(
            id = "1pet_5_7",
            verseHindi = "अपनी सारी चिन्ता उसी पर डाल दो, क्योंकि उसको तुम्हारा ध्यान है।",
            referenceHindi = "1 पतरस 5:7",
            verseEnglish = "Casting all your care upon Him, for He cares for you.",
            referenceEnglish = "1 Peter 5:7",
            category = "Peace",
            categoryHindi = "शांति व सुकून",
            bgStartColor = 0xFF0D2833,
            bgEndColor = 0xFF1D5268,
            presetId = "water_style"
        ),
        BibleVerse(
            id = "rom_12_2",
            verseHindi = "और इस संसार के सदृश न बनो; परन्तु तुम्हारे मन के नए हो जाने से तुम्हारा चाल-चलन भी बदलता जाए, जिस से तुम परमेश्वर की भली, और भावती, और सिद्ध इच्छा मालूम करते रहो।",
            referenceHindi = "रोमियों 12:2",
            verseEnglish = "And do not be conformed to this world, but be transformed by the renewing of your mind, that you may prove what is that good and acceptable and perfect will of God.",
            referenceEnglish = "Romans 12:2",
            category = "Wisdom",
            categoryHindi = "मार्गदर्शन व ज्ञान",
            bgStartColor = 0xFF191A2E,
            bgEndColor = 0xFF353761,
            presetId = "classic"
        ),
        BibleVerse(
            id = "rev_21_4",
            verseHindi = "वह उन की आंखों से सब आंसू पोंछ डालेगा; और इसके बाद मृत्यु न रहेगी, और न शोक, न विलाप, न पीड़ा रहेगी; पहली बातें बीत गईं।",
            referenceHindi = "प्रकाशितवाक्य 21:4",
            verseEnglish = "And God will wipe away every tear from their eyes; there shall be no more death, nor sorrow, nor crying. There shall be no more pain, for the former things have passed away.",
            referenceEnglish = "Revelation 21:4",
            category = "Peace",
            categoryHindi = "शांति व सुकून",
            bgStartColor = 0xFF1C0A33,
            bgEndColor = 0xFF45197D,
            presetId = "neon_cyan"
        ),
        BibleVerse(
            id = "col_3_23",
            verseHindi = "जो कुछ तुम करते हो, तन-मन से करो, यह समझकर कि मनुष्यों के लिये नहीं परन्तु प्रभु के लिये करते हो।",
            referenceHindi = "कुलुस्सियों 3:23",
            verseEnglish = "And whatever you do, do it heartily, as to the Lord and not to men.",
            referenceEnglish = "Colossians 3:23",
            category = "Wisdom",
            categoryHindi = "मार्गदर्शन व ज्ञान",
            bgStartColor = 0xFF231308,
            bgEndColor = 0xFF542E13,
            presetId = "kalam_handwritten"
        ),
        BibleVerse(
            id = "james_1_5",
            verseHindi = "पर यदि तुम में से किसी को बुद्धि की घटी हो, तो परमेश्वर से मांगे, जो बिना उलाहना दिए सब को उदारता से देता है; और उसको दी जाएगी।",
            referenceHindi = "याकूब 1:5",
            verseEnglish = "If any of you lacks wisdom, let him ask of God, who gives to all liberally and without reproach, and it will be given to him.",
            referenceEnglish = "James 1:5",
            category = "Wisdom",
            categoryHindi = "मार्गदर्शन व ज्ञान",
            bgStartColor = 0xFF171926,
            bgEndColor = 0xFF323753,
            presetId = "classic"
        )
    )

    /**
     * Deterministically returns the Verse of the Day according to the calendar day of the year.
     */
    fun getVerseOfTheDay(calendar: Calendar = Calendar.getInstance()): BibleVerse {
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val index = (dayOfYear - 1).coerceAtLeast(0) % VERSES.size
        return VERSES[index]
    }

    /**
     * Get verses filtered by category and optional query
     */
    fun getFilteredVerses(category: String = "All", query: String = ""): List<BibleVerse> {
        return VERSES.filter { verse ->
            val matchCategory = category == "All" || verse.category == category
            val matchQuery = query.isBlank() ||
                    verse.verseHindi.contains(query, ignoreCase = true) ||
                    verse.referenceHindi.contains(query, ignoreCase = true) ||
                    verse.verseEnglish.contains(query, ignoreCase = true) ||
                    verse.referenceEnglish.contains(query, ignoreCase = true)
            matchCategory && matchQuery
        }
    }
}
