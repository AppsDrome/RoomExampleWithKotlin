package com.appdrome.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.appdrome.roomdemo.databinding.ListItemBinding
import com.appdrome.roomdemo.db.Subscriber

class MyRecyclerAdapter(private val subscribersList: List<Subscriber>,private val clickListener: (Subscriber)->Unit) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val  binding :ListItemBinding= DataBindingUtil.inflate(LayoutInflater,R.layout.list_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position],clickListener)
    }
}
class MyViewHolder( val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(subscriber: Subscriber, clickListener: (Subscriber)->Unit){
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.Email
        binding.listItemLayout.setOnClickListener{
            clickListener(subscriber)
        }
    }
}