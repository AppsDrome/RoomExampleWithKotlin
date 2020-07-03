package com.appdrome.roomdemo.db

class SuscriberRepository(private val dao: SubscriberDAO) {

    val subscribers = dao.getAllSubscriber()

    suspend fun Insert(subscriber: Subscriber): Long{
       return dao.insertSubscriber(subscriber)
    }

    suspend fun Update(subscriber: Subscriber): Int{
       return dao.updateSubscriber(subscriber)
    }

    suspend fun Delete(subscriber: Subscriber) : Int{
      return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(): Int{
       return dao.deleteAll()
    }
}