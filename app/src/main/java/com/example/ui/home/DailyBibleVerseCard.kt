package com.example.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BibleVerse
import com.example.data.model.BibleVerseRepository
import com.example.ui.theme.AccentGold
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.RozhaOneFamily
import com.example.ui.theme.SecondaryCoral
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DailyBibleVerseCard(
    onDesignVerse: (BibleVerse, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var verseOffset by remember { mutableIntStateOf(0) }
    var selectedLang by remember { mutableStateOf("hi") } // "hi", "en", "both"
    var showVerseLibrarySheet by remember { mutableStateOf(false) }

    // Today's base verse + offset for browsing next/prev verses
    val totalVerses = BibleVerseRepository.VERSES.size
    val todayVerse = remember { BibleVerseRepository.getVerseOfTheDay() }
    val currentVerseIndex = remember(verseOffset) {
        val baseIndex = BibleVerseRepository.VERSES.indexOfFirst { it.id == todayVerse.id }
        val safeBase = if (baseIndex >= 0) baseIndex else 0
        (safeBase + verseOffset).mod(totalVerses)
    }
    val currentVerse = BibleVerseRepository.VERSES[currentVerseIndex]

    val dateStr = remember {
        SimpleDateFormat("EEEE, d MMMM", Locale("hi", "IN")).format(Date())
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(
                1.dp,
                Brush.horizontalGradient(
                    listOf(AccentGold.copy(alpha = 0.6f), PrimaryPurple.copy(alpha = 0.8f))
                ),
                RoundedCornerShape(22.dp)
            )
            .testTag("daily_bible_verse_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF281044),
                            Color(0xFF140726),
                            Color(0xFF1B0F30)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(AccentGold.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "✝️", fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "आज का वचन",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AccentGold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "• VERSE OF THE DAY",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextSecondary
                                )
                            }
                            Text(
                                text = dateStr,
                                fontSize = 11.sp,
                                color = Color(0xFFC7C3E8)
                            )
                        }
                    }

                    // Language Selector
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0x33000000))
                            .border(0.8.dp, DarkBorder, RoundedCornerShape(20.dp))
                            .padding(2.dp)
                    ) {
                        LanguagePill(
                            label = "हिंदी",
                            isSelected = selectedLang == "hi",
                            onClick = { selectedLang = "hi" }
                        )
                        LanguagePill(
                            label = "ENG",
                            isSelected = selectedLang == "en",
                            onClick = { selectedLang = "en" }
                        )
                        LanguagePill(
                            label = "दोनों",
                            isSelected = selectedLang == "both",
                            onClick = { selectedLang = "both" }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Verse Display with Animation
                AnimatedContent(
                    targetState = Pair(currentVerse, selectedLang),
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "verse_anim"
                ) { (verse, lang) ->
                    Column {
                        // Decorative open quote
                        Text(
                            text = "“",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentGold.copy(alpha = 0.5f),
                            lineHeight = 24.sp
                        )

                        when (lang) {
                            "hi" -> {
                                Text(
                                    text = verse.verseHindi,
                                    fontSize = 17.sp,
                                    lineHeight = 26.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                    fontFamily = RozhaOneFamily
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "— ${verse.referenceHindi}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AccentGold
                                )
                            }
                            "en" -> {
                                Text(
                                    text = verse.verseEnglish,
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Italic,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "— ${verse.referenceEnglish}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AccentGold
                                )
                            }
                            else -> {
                                Text(
                                    text = verse.verseHindi,
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                    fontFamily = RozhaOneFamily
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "\"${verse.verseEnglish}\"",
                                    fontSize = 13.sp,
                                    lineHeight = 20.sp,
                                    fontStyle = FontStyle.Italic,
                                    color = Color(0xFFD4CCEC)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "— ${verse.referenceHindi} (${verse.referenceEnglish})",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AccentGold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Category tag
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x334A148C))
                                .border(0.5.dp, PrimaryPurple.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "✨ ${verse.categoryHindi} (${verse.category})",
                                fontSize = 11.sp,
                                color = Color(0xFFE0C3FC),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Actions Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Main "Design Poster" Button
                    Button(
                        onClick = { onDesignVerse(currentVerse, selectedLang) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(44.dp)
                            .testTag("design_bible_verse_button")
                    ) {
                        Icon(
                            Icons.Default.Brush,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "पोस्टर बनाएं",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Copy Action
                    IconButton(
                        onClick = {
                            val clipboard =
                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clipText = when (selectedLang) {
                                "en" -> "${currentVerse.verseEnglish}\n\n— ${currentVerse.referenceEnglish}"
                                "both" -> "${currentVerse.verseHindi}\n\n\"${currentVerse.verseEnglish}\"\n\n— ${currentVerse.referenceHindi}"
                                else -> "${currentVerse.verseHindi}\n\n— ${currentVerse.referenceHindi}"
                            }
                            clipboard.setPrimaryClip(ClipData.newPlainText("Bible Verse", clipText))
                            Toast.makeText(context, "वचन कॉपी हो गया!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0x33FFFFFF))
                            .testTag("copy_verse_button")
                    ) {
                        Icon(
                            Icons.Default.ContentCopy,
                            contentDescription = "Copy verse",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Share Action
                    IconButton(
                        onClick = {
                            val shareText = when (selectedLang) {
                                "en" -> "✝️ Daily Bible Verse:\n\n${currentVerse.verseEnglish}\n\n— ${currentVerse.referenceEnglish}\n\n(ChitraLekh)"
                                "both" -> "✝️ दैनिक बाइबिल वचन:\n\n${currentVerse.verseHindi}\n\n\"${currentVerse.verseEnglish}\"\n\n— ${currentVerse.referenceHindi}\n\n(ChitraLekh)"
                                else -> "✝️ दैनिक बाइबिल वचन:\n\n${currentVerse.verseHindi}\n\n— ${currentVerse.referenceHindi}\n\n(ChitraLekh)"
                            }
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "आज का बाइबिल वचन")
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            context.startActivity(Intent.createChooser(intent, "वचन साझा करें"))
                        },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0x33FFFFFF))
                            .testTag("share_verse_button")
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Share verse",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Next / Shuffle Action
                    IconButton(
                        onClick = { verseOffset++ },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0x33FFFFFF))
                            .testTag("next_verse_button")
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Next verse",
                            tint = AccentGold,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Browse Library Button
                    IconButton(
                        onClick = { showVerseLibrarySheet = true },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0x33FFFFFF))
                            .testTag("all_verses_button")
                    ) {
                        Icon(
                            Icons.Default.LibraryBooks,
                            contentDescription = "All verses library",
                            tint = Color(0xFFC7C3E8),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }

    if (showVerseLibrarySheet) {
        BibleVersesLibrarySheet(
            onSelectVerse = { verse ->
                showVerseLibrarySheet = false
                onDesignVerse(verse, selectedLang)
            },
            onDismiss = { showVerseLibrarySheet = false }
        )
    }
}

@Composable
private fun LanguagePill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) PrimaryPurple else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else TextSecondary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BibleVersesLibrarySheet(
    onSelectVerse: (BibleVerse) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredVerses = remember(selectedCategory, searchQuery) {
        BibleVerseRepository.getFilteredVerses(selectedCategory, searchQuery)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = DarkSurface,
        scrimColor = Color(0x99000000)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "✝️", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "पवित्र बाइबिल वचन (Bible Verses)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "${BibleVerseRepository.VERSES.size} प्रेरणादायक वचन • हिंदी व अंग्रेज़ी",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Search input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "वचन या संदर्भ खोजें (उदा. फिलिप्पियों, प्रेम, शांति)...",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPurple,
                    unfocusedBorderColor = DarkBorder,
                    focusedContainerColor = DarkSurfaceVariant,
                    unfocusedContainerColor = DarkSurfaceVariant,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Categories Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(BibleVerseRepository.CATEGORIES) { (catId, catHindi) ->
                    val isSelected = selectedCategory == catId
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = catId },
                        label = {
                            Text(
                                text = catHindi,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryPurple,
                            selectedLabelColor = Color.White,
                            containerColor = DarkSurfaceVariant,
                            labelColor = TextSecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = DarkBorder,
                            selectedBorderColor = PrimaryPurple
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Verses List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
            ) {
                items(filteredVerses) { verse ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, DarkBorder, RoundedCornerShape(14.dp)),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            // Category Badge
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(PrimaryPurple.copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "${verse.categoryHindi} • ${verse.category}",
                                        fontSize = 10.sp,
                                        color = Color(0xFFD4CCEC),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Hindi verse
                            Text(
                                text = verse.verseHindi,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary,
                                fontFamily = RozhaOneFamily,
                                lineHeight = 22.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "— ${verse.referenceHindi}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentGold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // English verse
                            Text(
                                text = "\"${verse.verseEnglish}\"",
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color(0xFFB3AFD3),
                                lineHeight = 18.sp
                            )
                            Text(
                                text = "— ${verse.referenceEnglish}",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Actions
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    // Copy
                                    OutlinedButton(
                                        onClick = {
                                            val clipboard =
                                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clip = "${verse.verseHindi}\n\n— ${verse.referenceHindi}"
                                            clipboard.setPrimaryClip(
                                                ClipData.newPlainText("Bible Verse", clip)
                                            )
                                            Toast.makeText(context, "वचन कॉपी हो गया!", Toast.LENGTH_SHORT).show()
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        border = androidx.compose.foundation.BorderStroke(0.8.dp, DarkBorder)
                                    ) {
                                        Icon(
                                            Icons.Default.ContentCopy,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp),
                                            tint = TextSecondary
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("कॉपी", fontSize = 11.sp, color = TextSecondary)
                                    }

                                    // Share
                                    OutlinedButton(
                                        onClick = {
                                            val shareText = "✝️ बाइबिल वचन:\n\n${verse.verseHindi}\n\n— ${verse.referenceHindi}\n\n(ChitraLekh)"
                                            val intent = Intent(Intent.ACTION_SEND).apply {
                                                type = "text/plain"
                                                putExtra(Intent.EXTRA_SUBJECT, "बाइबिल वचन")
                                                putExtra(Intent.EXTRA_TEXT, shareText)
                                            }
                                            context.startActivity(Intent.createChooser(intent, "साझा करें"))
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        border = androidx.compose.foundation.BorderStroke(0.8.dp, DarkBorder)
                                    ) {
                                        Icon(
                                            Icons.Default.Share,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp),
                                            tint = TextSecondary
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("शेयर", fontSize = 11.sp, color = TextSecondary)
                                    }
                                }

                                // Design
                                Button(
                                    onClick = { onSelectVerse(verse) },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
                                ) {
                                    Icon(
                                        Icons.Default.Brush,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp),
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("पोस्टर बनाएं", fontSize = 12.sp, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
