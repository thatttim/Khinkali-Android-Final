package ge.framework.khinkali.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ge.framework.khinkali.data.model.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(
    recipe: Recipe,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isCompact: Boolean = false,
    cardHeight: Int = 220 // Default height for recent recipes
) {
    var showContextMenu by remember { mutableStateOf(false) }
    var contextMenuPosition by remember { mutableStateOf(Offset.Zero) }

    Card(
        onClick = { onRecipeClick(recipe.id) },
        modifier = modifier
            .height(cardHeight.dp)
            .clip(RoundedCornerShape(26.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { offset ->
                        contextMenuPosition = offset
                        showContextMenu = true
                    }
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = recipe.image,
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }

    // Context Menu
    DropdownMenu(
        expanded = showContextMenu,
        onDismissRequest = { showContextMenu = false }
    ) {
        DropdownMenuItem(
            text = { Text("გაზიარება") },
            leadingIcon = { Icon(Icons.Default.Share, contentDescription = null) },
            onClick = {
                // TODO: Implement share functionality
                showContextMenu = false
            }
        )
        DropdownMenuItem(
            text = { Text("ფავორიტებში დამატება") },
            leadingIcon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            onClick = {
                // TODO: Implement add to favorites functionality
                showContextMenu = false
            }
        )
    }
} 