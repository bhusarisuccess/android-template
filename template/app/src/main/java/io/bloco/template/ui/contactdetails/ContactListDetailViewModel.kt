package io.bloco.template.ui.contactdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.bloco.template.data.ContactRepository
import io.bloco.template.ui.contactlist.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class ContactListDetailViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val contactListRepository = ContactRepository()

    private val _isStaredChange = MutableLiveData<Int>()
    val isStaredChange: LiveData<Int>
        get() = _isStaredChange

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    fun unStarred(id: Int) {
        try {
            coroutineScope.launch {
                _loadingState.value = LoadingState.LOADING
                val starData = contactListRepository.setUnStarred(id)
                Timber.d("StarData: $starData")
                starData?.run {
                    if (starData.content?.isStarred != null) {
                        _isStaredChange.value = starData.content.isStarred
                    }
                }
                _loadingState.value = LoadingState.DONE
            }
        } catch (ex: Exception) {
            Timber.d("Exception $ex")
            _loadingState.value = LoadingState.ERROR
        }
    }

    fun starred(id: Int) {
        try {
            coroutineScope.launch {
                _loadingState.value = LoadingState.LOADING
                val starData = contactListRepository.setStarred(id)
                Timber.d("StarredData $starData")
                starData?.run {
                    if (starData.content?.isStarred != null) {
                        _isStaredChange.value = starData.content.isStarred
                    }
                    _loadingState.value = LoadingState.DONE
                }
            }
        } catch (ex: Exception) {
            Timber.d("Exception $ex")
            _loadingState.value = LoadingState.ERROR
        }
    }

}