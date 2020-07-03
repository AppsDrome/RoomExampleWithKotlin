package com.appdrome.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber):Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber):Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber): Int

    @Query("DELETE from Subscriber_data_table")
    suspend fun deleteAll():Int

    @Query("Select * from Subscriber_data_table")
    fun getAllSubscriber() : LiveData<List<Subscriber>>
}