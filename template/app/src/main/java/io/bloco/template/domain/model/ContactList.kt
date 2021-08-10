package io.bloco.template.domain.model

data class ContactList(
    val meta: Meta,
    var content: MutableList<Content>
)

data class Meta(
    val success: Boolean,
    val message: String,
    val pageNumber: Int,
    val pageSize: Int
)

data class Content(
    val id: Int,
    val name: String,
    val phone: String,
    val thumbnail: String,
    val email: String,
    val isStarred: Int
)