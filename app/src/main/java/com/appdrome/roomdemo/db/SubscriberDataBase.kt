package com.appdrome.roomdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class],version = 1)
abstract class SubscriberDataBase : RoomDatabase() {
    abstract val subscriberDAO: SubscriberDAO

    companion object{
        @Volatile
        private var INSTANCE : SubscriberDataBase? =null
        fun getInstance(context: Context):SubscriberDataBase{
            synchronized(this){
                var instantce : SubscriberDataBase? = INSTANCE
                if(instantce == null){
                    instantce = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDataBase::class.java,
                        "subscriber_data_database")
                        .build()
                }
                return instantce
            }

        }


    }
}