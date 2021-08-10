package io.bloco.template.ui.contactlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.bloco.template.data.ContactRepository
import io.bloco.template.domain.model.ContactList
import io.bloco.template.domain.model.Content
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

enum class LoadingState { LOADING, ERROR, DONE }

class ContactListViewModel : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 7
        private const val ELEMENTS_PER_PAGE = 10
    }

    var pageNo = 1
    private var contentList: List<Content> = emptyList()

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val contactListRepository = ContactRepository()

    private val _contactData = MutableLiveData<ContactList>()
    val contactData: LiveData<ContactList>
        get() = _contactData

    private val _contentData = MutableLiveData<List<Content>>()
    val contentData: LiveData<List<Content>>
        get() = _contentData

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState


    fun initContactList() {
        pageNo = 1
        contentList = emptyList()
        _loadingState.value = LoadingState.LOADING
        getContactList()
    }

    private fun getContactList() {
        try {
            coroutineScope.launch {
                _loadingState.value = LoadingState.LOADING
                val contactList = contactListRepository.getContactList(pageNo)
                contentList = if (contentList.isNotEmpty()) {
                    val temp = contentList
                    contactList.content.plus(temp).plus(contactList.content)
                } else {
                    contactList.content.plus(contactList.content)
                }
                pageNo++
                Timber.d("Content List: $contentList")
                _contentData.value = contentList
                _loadingState.value = LoadingState.DONE
            }
        } catch (ex: Exception) {
            Timber.d("Exception ${ex.message}")
            _loadingState.value = LoadingState.ERROR
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItem: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItem + VISIBLE_THRESHOLD >= totalItemCount) {
            if (_loadingState.value !== LoadingState.LOADING) {
                getContactList()
            }
        }
    }

}