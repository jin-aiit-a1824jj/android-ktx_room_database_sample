package a1824jj.jp.ac.aiit.roomdemo_ktx

import a1824jj.jp.ac.aiit.roomdemo_ktx.db.Subscriber
import a1824jj.jp.ac.aiit.roomdemo_ktx.db.SubscriberRepository
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(), Observable {

    var subscribers = repository.subscribers

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear ALL"
    }

    fun saveOrUpdate(){
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0, name, email))

        inputName.value = null
        inputEmail.value = null
    }

    fun clearAllOrDelete(){
        clearAll()
    }

    fun insert(subscriber: Subscriber) =
        viewModelScope.launch { repository.insert(subscriber) }

    fun update(subscriber: Subscriber) =
        viewModelScope.launch { repository.update(subscriber) }

    fun delete(subscriber: Subscriber) =
        viewModelScope.launch { repository.delete(subscriber) }

    fun clearAll() =
        viewModelScope.launch { repository.deleteAll() }



    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        //build error avoid
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        //build error avoid
    }
}