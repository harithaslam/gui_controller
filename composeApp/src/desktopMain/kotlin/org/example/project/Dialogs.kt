package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OpenDialogButton(action: () -> Unit) {
    // State to manage dialog visibility
    val openDialog = remember { mutableStateOf(false) }

    // Button to open the dialog
    Button(
        shape = RoundedCornerShape(25.dp),
        onClick = { openDialog.value = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
    ) {
        Text(text = "Reset Database", color = Color.White)
    }

    // Dialog
    if (openDialog.value)
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Reset Database", style = MaterialTheme.typography.h4) },
            text = { Text("This action will cause the database to be erased. Are you sure?") },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        action()
                    }, shape = RoundedCornerShape(25.dp)
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = { openDialog.value = false }, shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Cancel")
                }
            }
        )


}