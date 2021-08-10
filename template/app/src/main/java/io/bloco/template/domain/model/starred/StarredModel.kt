package io.bloco.template.domain.model.starred

data class StarredModel(
    val meta: Meta,
    val content: Content
)

data class Content(
    val id: Int,
    val name: String,
    val phone: String,
    val thumbnail: String,
    val email: String,
    val isStarred: Int
)

data class Meta(
    val success: Boolean,
    val message: String
)
