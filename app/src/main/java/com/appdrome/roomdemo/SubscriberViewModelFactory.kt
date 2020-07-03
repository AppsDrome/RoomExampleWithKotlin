package com.appdrome.roomdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appdrome.roomdemo.db.SuscriberRepository
import java.lang.IllegalArgumentException

class SubscriberViewModelFactory(val repository: SuscriberRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(SubscriberViewModelClass::class.java)){
           return SubscriberViewModelClass(repository) as T
       }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}