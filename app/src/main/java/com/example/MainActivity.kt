package com.example

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.db.AppDatabase
import com.example.data.db.ProjectRepository
import com.example.data.model.BackgroundTemplate
import com.example.data.model.BibleVerse
import com.example.ui.editor.EditorScreen
import com.example.ui.editor.EditorViewModel
import com.example.ui.home.HomeScreen
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

enum class Screen {
    HOME, EDITOR
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = ProjectRepository(AppDatabase.getDatabase(this).projectDao())

        setContent {
            MyApplicationTheme {
                MainApp(repository = repository)
            }
        }
    }
}

@Composable
fun MainApp(
    repository: ProjectRepository,
    editorViewModel: EditorViewModel = viewModel()
) {
    var currentScreen by remember { mutableStateOf(Screen.HOME) }
    val savedProjects by repository.allProjects.collectAsStateWithLifecycle(initialValue = emptyList())
    val coroutineScope = rememberCoroutineScope()

    when (currentScreen) {
        Screen.HOME -> {
            HomeScreen(
                savedProjects = savedProjects,
                onPickImage = { uri: Uri ->
                    editorViewModel.setBaseImage(uri)
                    currentScreen = Screen.EDITOR
                },
                onCreateBlank = {
                    editorViewModel.setBaseImage(null)
                    currentScreen = Screen.EDITOR
                },
                onSelectTemplate = { template: BackgroundTemplate ->
                    editorViewModel.applyStarterTemplate(
                        sampleText = template.sampleText,
                        font = template.font,
                        startColor = template.primaryColor,
                        endColor = template.secondaryColor,
                        presetId = template.defaultPresetId
                    )
                    currentScreen = Screen.EDITOR
                },
                onDesignVerse = { verse: BibleVerse, lang: String ->
                    editorViewModel.applyBibleVerse(verse, lang)
                    currentScreen = Screen.EDITOR
                },
                onDeleteProject = { id: Long ->
                    coroutineScope.launch {
                        repository.deleteById(id)
                    }
                }
            )
        }
        Screen.EDITOR -> {
            EditorScreen(
                viewModel = editorViewModel,
                onNavigateBack = {
                    currentScreen = Screen.HOME
                }
            )
        }
    }
}
