package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

// Local Static Fonts for Hindi / Devanagari typography
val TiroDevanagariHindiFamily = FontFamily(
    Font(R.font.tiro_devanagari_hindi, FontWeight.Normal)
)

val KohinoorDevanagariFamily = FontFamily(
    Font(R.font.kohinoor_devanagari, FontWeight.Normal)
)

val NotoSerifDevanagariFamily = FontFamily(
    Font(R.font.noto_serif_devanagari, FontWeight.Normal)
)

val NotoSansDevanagariFamily = FontFamily(
    Font(R.font.noto_sans_devanagari, FontWeight.Normal)
)

val AdishilaFamily = FontFamily(
    Font(R.font.adishila, FontWeight.Normal)
)

val KalamFamily = FontFamily(
    Font(R.font.kalam, FontWeight.Normal),
    Font(R.font.kalam, FontWeight.Bold)
)

val YatraOneFamily = FontFamily(
    Font(R.font.yatra_one, FontWeight.Normal)
)

val RozhaOneFamily = FontFamily(
    Font(R.font.rozha_one, FontWeight.Normal)
)

val KhandFamily = FontFamily(
    Font(R.font.khand, FontWeight.Normal),
    Font(R.font.khand, FontWeight.Bold)
)

val ModakFamily = FontFamily(
    Font(R.font.modak, FontWeight.Normal)
)

val PoppinsFamily = FontFamily(
    Font(R.font.poppins, FontWeight.Normal),
    Font(R.font.poppins, FontWeight.Bold)
)

val MuktaFamily = FontFamily(
    Font(R.font.mukta, FontWeight.Normal),
    Font(R.font.mukta, FontWeight.Bold)
)

// App Default Typography
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp
    ),
    displayMedium = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    titleMedium = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MuktaFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = MuktaFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = PoppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )
)
