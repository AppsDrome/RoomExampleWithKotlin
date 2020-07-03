package com.appdrome.roomdemo

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anushka.roomdemo.Event
import com.appdrome.roomdemo.db.Subscriber
import com.appdrome.roomdemo.db.SuscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModelClass(private val repository: SuscriberRepository) :ViewModel(), Observable {
    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber
    @Bindable
    var inputName = MutableLiveData<String>()
    @Bindable
    var inputEmail= MutableLiveData<String>()
    @Bindable
    var saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    var clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value ="Save"
        clearAllOrDeleteButtonText.value ="ClearAll"
    }

    fun saveOrUpdate(){
        if(inputName.value==null){
            statusMessage.value = Event("Please insert subscriber name")
        }else if (inputEmail.value == null){
            statusMessage.value = Event("Please insert subscriber email")
        }else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            statusMessage.value = Event("Please Enter correct email address")
        }else {
            if (isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.Email = inputEmail.value!!
                update(subscriberToUpdateOrDelete)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insert(Subscriber(0, name, email))
                inputName.value = null
                inputEmail.value = null

            }
        }
    }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
        }else{
            clearAll()
        }

    }

    fun insert(subscriber: Subscriber)= viewModelScope.launch{
            val newRowId = repository.Insert(subscriber)
        if(newRowId>-1) {
            statusMessage.value = Event("Subscribers Inserted Successfully $newRowId")
        }else{
            statusMessage.value = Event("Error Occurred")
        }

        }

    fun update(subscriber: Subscriber)= viewModelScope.launch{
        val noOfRows = repository.Update(subscriber)
        if(noOfRows>0){
            inputName.value =null
            inputEmail.value= null
            isUpdateOrDelete = false

            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value= "ClearAll"

            statusMessage.value = Event("${noOfRows} Rows Updated Successfully")
        }else{
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun delete(subscriber: Subscriber)= viewModelScope.launch{
        val noOfRowsDelete = repository.Delete(subscriber)
        if (noOfRowsDelete>0){
            inputName.value =null
            inputEmail.value= null
            isUpdateOrDelete = false

            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value= "ClearAll"

            statusMessage.value = Event("${noOfRowsDelete} Rows Deleted Successfully")
        }else{
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun clearAll()=viewModelScope.launch {
        val noOfRowsDelete = repository.deleteAll()
        if (noOfRowsDelete>0){
            statusMessage.value = Event("${noOfRowsDelete} rows Deleted Successfully")
        }else{
            statusMessage.value = Event("Error")
        }

    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value =subscriber.name
        inputEmail.value= subscriber.Email
        isUpdateOrDelete =true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value= "Delete"

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}


