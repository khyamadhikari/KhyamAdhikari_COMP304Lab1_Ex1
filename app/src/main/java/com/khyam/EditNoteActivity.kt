package com.khyam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class EditNoteActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noteTitle = intent.getStringExtra("note_title") ?: ""
        val noteContent = intent.getStringExtra("note_content") ?: ""

        setContent {
            EditNoteScreen(note = Note(noteTitle, noteContent), onSaveNote = { updatedNote ->
                // Update the note in MainActivity's notes list
                val index = MainActivity.notes.indexOfFirst { it.title == noteTitle }
                if (index != -1) {
                    MainActivity.notes[index] = updatedNote
                }
                // Finish the activity and return to the previous screen
                finish()
            })
        }
    }
}

@Composable
fun EditNoteScreen(note: Note, onSaveNote: (Note) -> Unit) {
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Edit Note Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Edit Note Content") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onSaveNote(Note(title, content)) }) {
            Text("Save Changes")
        }
    }
}
