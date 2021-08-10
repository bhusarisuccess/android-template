package io.bloco.template.domain.network

import io.bloco.template.BuildConfig
import io.bloco.template.domain.model.ContactList
import io.bloco.template.domain.model.starred.StarredModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ContactListService {

    @GET("/iie-service/v1/contacts")
    suspend fun getContactList(
        @Query("pageNumber") pageNumber: Int
    ): ContactList

    @POST("/iie-service/v1/star/{id}")
    suspend fun postStartContact(
        @Path("id") id: Int
    ): StarredModel

    @POST("/iie-service/v1/unstar/{id}")
    suspend fun postUnStartContact(
        @Path("id") id: Int
    ): StarredModel
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BuildConfig.BASE_URL)
    .client(okHttpClient)
    .build()

object ContactService {
    val contactService: ContactListService by lazy { retrofit.create(ContactListService::class.java) }
}

