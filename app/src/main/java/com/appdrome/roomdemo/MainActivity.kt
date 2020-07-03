package com.appdrome.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appdrome.roomdemo.databinding.ActivityMainBinding
import com.appdrome.roomdemo.db.Subscriber
import com.appdrome.roomdemo.db.SubscriberDataBase
import com.appdrome.roomdemo.db.SuscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModelClass: SubscriberViewModelClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao= SubscriberDataBase.getInstance(application).subscriberDAO
        val repository = SuscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModelClass = ViewModelProvider(this,factory).get(SubscriberViewModelClass::class.java)
        binding.myViewModel = subscriberViewModelClass
        binding.lifecycleOwner = this

        initRecyclerView()

        subscriberViewModelClass.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscriberList()
    }

    private fun displaySubscriberList(){
        subscriberViewModelClass.subscribers.observe(this, Observer {
            Log.i("MyTAG",it.toString())
            binding.subscriberRecyclerView.adapter= MyRecyclerAdapter(it,{selectedItem:Subscriber->listItemClickEvents(selectedItem)})
        })
    }

    //For ClickEvents
    private fun listItemClickEvents(subscriber: Subscriber){
        //Toast.makeText(this,"Selected Item name is ${subscriber.name}",Toast.LENGTH_SHORT).show()
        subscriberViewModelClass.initUpdateAndDelete(subscriber)
    }
}