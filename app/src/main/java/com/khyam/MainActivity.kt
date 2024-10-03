package com.khyam

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    companion object {
        val notes = mutableStateListOf<Note>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Add sample notes for testing
        if (notes.isEmpty()) {
            notes.add(Note("Sample Note 1", "This is the content of note 1"))
            notes.add(Note("Sample Note 2", "This is the content of note 2"))
        }

        setContent {
            HomeScreen(notes = notes, onAddNote = {
                startActivity(Intent(this, CreateNoteActivity::class.java))
            }, onNoteClick = { note ->
                val intent = Intent(this, EditNoteActivity::class.java)
                intent.putExtra("note_title", note.title)
                intent.putExtra("note_content", note.content)
                startActivity(intent)
            })
        }
    }
}

@Composable
fun HomeScreen(notes: List<Note>, onAddNote: () -> Unit, onNoteClick: (Note) -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddNote() }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(notes) { note ->
                    NoteCard(note = note, onClick = { onNoteClick(note) })
                }
            }
        }
    )
}

@Composable
fun NoteCard(note: Note, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = note.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = note.content.take(50) + "...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
