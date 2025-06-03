package ge.framework.khinkali.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ge.framework.khinkali.data.model.Recipe
import ge.framework.khinkali.data.model.Author
import ge.framework.khinkali.data.model.Ingredient
import ge.framework.khinkali.data.model.Instruction

@Composable
fun RecipeHeader(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = recipe.image,
        contentDescription = recipe.title,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(26.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun RecipeInfo(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = recipe.time + " წუთი",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = recipe.level,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = recipe.description,
            style = MaterialTheme.typography.bodyLarge
        )
        if (!recipe.youtubeVideoID.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* TODO: Open YouTube video */ }
            ) {
                Text("Watch on YouTube")
            }
        }
    }
}

@Composable
fun IngredientsList(
    ingredients: List<Ingredient>?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "ინგრედიენტები",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        ingredients?.forEach { ingredient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text("•", modifier = Modifier.padding(end = 8.dp))
                Text("${ingredient.quantity} ${ingredient.unit} ${ingredient.name}")
            }
        }
    }
}

@Composable
fun InstructionsList(
    instructions: List<Instruction>?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "ინსტრუქცია",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        instructions?.forEach { instruction ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "${instruction.step}.",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(instruction.instruction)
            }
        }
    }
}

@Composable
fun AuthorInfo(
    author: Author?,
    modifier: Modifier = Modifier
) {
    if (author == null) return
    
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = author.avatar_url,
            contentDescription = "${author.name} ${author.surname}",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp)
        )
        Column {
            Text(
                text = "${author.name} ${author.surname}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
} 