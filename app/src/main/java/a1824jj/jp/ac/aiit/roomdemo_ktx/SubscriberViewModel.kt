package a1824jj.jp.ac.aiit.roomdemo_ktx

import a1824jj.jp.ac.aiit.roomdemo_ktx.db.Subscriber
import a1824jj.jp.ac.aiit.roomdemo_ktx.db.SubscriberRepository
import android.util.MutableDouble
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(), Observable {

    var subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear ALL"
    }

    fun saveOrUpdate(){
        if(isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        }else{
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Subscriber(0, name, email))
            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
        }else{
            clearAll()
        }

    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    private fun insert(subscriber: Subscriber) =
        viewModelScope.launch {
            repository.insert(subscriber)
            statusMessage.value = Event("Subscriber Inserted Successfully")
        }

    private fun update(subscriber: Subscriber) =
        viewModelScope.launch {
            repository.update(subscriber)
            statusMessage.value = Event("Subscriber updated Successfully")
        }

    private fun delete(subscriber: Subscriber) =
        viewModelScope.launch {
            repository.delete(subscriber)
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("Subscriber deleted Successfully")
        }

    private fun clearAll() =
        viewModelScope.launch {
            repository.deleteAll()
            statusMessage.value = Event("Subscriber Deleted All Successfully")
        }



    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        //build error avoid
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        //build error avoid
    }
}