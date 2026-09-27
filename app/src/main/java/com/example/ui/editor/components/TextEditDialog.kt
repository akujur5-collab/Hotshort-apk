package com.example.ui.editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.CanvasTextAlign
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.PrimaryPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

val QUICK_HINDI_PHRASES = listOf(
    "शुभ प्रभात 🌅",
    "जय श्री राम 🚩",
    "हर हर महादेव 🔱",
    "जन्मदिन मुबारक 🎂",
    "नमस्ते भारत 🙏",
    "शुभ विचार ✨",
    "राधे राधे 🌸",
    "सत्यमेव जयते 🇮🇳",
    "बहुत बहुत बधाई 🎉",
    "धन्यवाद 💐"
)

@Composable
fun TextEditDialog(
    initialText: String,
    initialAlign: CanvasTextAlign,
    isBold: Boolean,
    isItalic: Boolean,
    isUnderline: Boolean,
    onSave: (text: String, align: CanvasTextAlign, bold: Boolean, italic: Boolean, underline: Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(initialText) }
    var align by remember { mutableStateOf(initialAlign) }
    var bold by remember { mutableStateOf(isBold) }
    var italic by remember { mutableStateOf(isItalic) }
    var underline by remember { mutableStateOf(isUnderline) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = DarkSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "टेक्स्ट संपादित करें (Edit Text)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = {
                        Text("यहाँ हिंदी या अंग्रेज़ी में लिखें...", color = TextSecondary)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .testTag("text_input_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPurple,
                        unfocusedBorderColor = DarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        cursorColor = PrimaryPurple
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Quick Hindi Phrase Chips
                Text(
                    text = "त्वरित हिंदी वाक्यांश (Quick Phrases):",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(QUICK_HINDI_PHRASES) { phrase ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(DarkSurfaceVariant)
                                .border(1.dp, DarkBorder, RoundedCornerShape(20.dp))
                                .clickable {
                                    text = if (text.isBlank()) phrase else "$text $phrase"
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(text = phrase, fontSize = 12.sp, color = Color(0xFFDDD8F5))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Text format tools row (Alignment + Bold + Italic + Underline)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Alignment buttons
                    Row(
                        modifier = Modifier
                            .background(DarkBackground, RoundedCornerShape(10.dp))
                            .padding(4.dp)
                    ) {
                        IconButton(
                            onClick = { align = CanvasTextAlign.START },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.FormatAlignLeft,
                                contentDescription = "Align Left",
                                tint = if (align == CanvasTextAlign.START) PrimaryPurple else TextSecondary
                            )
                        }
                        IconButton(
                            onClick = { align = CanvasTextAlign.CENTER },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.FormatAlignCenter,
                                contentDescription = "Align Center",
                                tint = if (align == CanvasTextAlign.CENTER) PrimaryPurple else TextSecondary
                            )
                        }
                        IconButton(
                            onClick = { align = CanvasTextAlign.END },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.FormatAlignRight,
                                contentDescription = "Align Right",
                                tint = if (align == CanvasTextAlign.END) PrimaryPurple else TextSecondary
                            )
                        }
                    }

                    // Style toggles
                    Row(
                        modifier = Modifier
                            .background(DarkBackground, RoundedCornerShape(10.dp))
                            .padding(4.dp)
                    ) {
                        IconButton(
                            onClick = { bold = !bold },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.FormatBold,
                                contentDescription = "Bold",
                                tint = if (bold) PrimaryPurple else TextSecondary
                            )
                        }
                        IconButton(
                            onClick = { italic = !italic },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.FormatItalic,
                                contentDescription = "Italic",
                                tint = if (italic) PrimaryPurple else TextSecondary
                            )
                        }
                        IconButton(
                            onClick = { underline = !underline },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.FormatUnderlined,
                                contentDescription = "Underline",
                                tint = if (underline) PrimaryPurple else TextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        onSave(text, align, bold, italic, underline)
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("save_text_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "संपन्न (Done)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
