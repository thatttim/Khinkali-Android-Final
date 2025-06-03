package ge.framework.khinkali.data.model

data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val description: String,
    val time: String,
    val level: String,
    val is_liked: Boolean,
    val likes_count: Int,
    val category_names: List<String>,
    val ingredients: List<Ingredient>?,
    val instructions: List<Instruction>?,
    val youtubeVideoID: String?,
    val author: Author?
)

data class Ingredient(
    val name: String,
    val ing_id: Int,
    val quantity: Double,
    val unit: String,
    val unit_id: Int
)

data class Instruction(
    val step: Int,
    val instruction: String
)

data class Author(
    val id: Int,
    val name: String,
    val surname: String,
    val avatar_url: String
)

data class UserProfile(
    val id: Int,
    val email: String?,
    val name: String?,
    val surname: String?,
    val avatar_url: String?,
    val score: Int?,
    val level: Int?,
    val todays_max_score: Int?
)

data class HomeResponse(
    val recipes_trending: List<Recipe>,
    val recipes_new: List<Recipe>
)

data class FavoritesResponse(
    val favorites: List<Recipe>
)

data class LoginResponse(
    val user_id: Int
)

data class RegisterResponse(
    val success: Boolean,
    val user_id: Int
)

data class ProfileEditResponse(
    val success: Boolean,
    val message: String
)

data class ToggleLikeResponse(
    val success: Boolean
) 