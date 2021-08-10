package io.bloco.template.data

import io.bloco.template.domain.model.ContactList
import io.bloco.template.domain.model.starred.StarredModel
import io.bloco.template.domain.network.ContactService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ContactRepository {
    suspend fun getContactList(pageNo: Int): ContactList {
        return withContext(Dispatchers.IO) {
            val data: ContactList = ContactService.contactService.getContactList(pageNo)
            Timber.d("Data: $data")
            data
        }
    }

    suspend fun setStarred(id: Int): StarredModel {
        return withContext(Dispatchers.IO) {
            val data = ContactService.contactService.postStartContact(id)
            data
        }
    }

    suspend fun setUnStarred(id: Int): StarredModel {
        return withContext(Dispatchers.IO) {
            val data = ContactService.contactService.postUnStartContact(id)
            data
        }
    }
}