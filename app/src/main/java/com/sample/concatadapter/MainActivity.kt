package com.sample.concatadapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.sample.concatadapter.databinding.ActivityMainBinding
import com.sample.concatadapter.databinding.ListItem1Binding
import com.sample.concatadapter.databinding.ListItem2Binding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        var addedItemCount1 = 0
        var addedItemCount2 = 0

        val list1 = mutableListOf<String>()
        val list2 = mutableListOf<String>()

        val adapter1 = MyAdapter1()
        val adapter2 = MyAdapter2()
        val adapter = ConcatAdapter(adapter1, adapter2)

        binding.recyclerView.adapter = adapter

        binding.addButton1.setOnClickListener {
            addedItemCount1++
            list1.add("Item #$addedItemCount1")
            adapter1.submitList(list1.toList())
        }
        binding.removeButton1.setOnClickListener {
            if (list1.isNotEmpty()) {
                list1.removeAt(Random.nextInt(list1.size))
                adapter1.submitList(list1.toList())
            }
        }

        binding.addButton2.setOnClickListener {
            addedItemCount2++
            list2.add("Title #$addedItemCount2")
            adapter2.submitList(list2.toList())
        }
        binding.removeButton2.setOnClickListener {
            if (list2.isNotEmpty()) {
                list2.removeAt(Random.nextInt(list2.size))
                adapter2.submitList(list2.toList())
            }
        }
    }
}

private val stringItemCallback = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class MyAdapter1 : ListAdapter<String, ViewHolder1>(stringItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
        return ViewHolder1(ListItem1Binding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        val text = getItem(position)
        holder.bind(text)
//        holder.bind("$position, ${holder.absoluteAdapterPosition}, ${holder.bindingAdapterPosition}")
    }
}

class ViewHolder1(private val binding: ListItem1Binding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(text: String) {
        binding.textView.text = text
    }
}

class MyAdapter2 : ListAdapter<String, ViewHolder2>(stringItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        return ViewHolder2(ListItem2Binding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        val text = getItem(position)
        holder.bind(text)
//        holder.bind("$position, ${holder.absoluteAdapterPosition}, ${holder.bindingAdapterPosition}")
    }
}

class ViewHolder2(private val binding: ListItem2Binding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(text: String) {
        binding.textView.text = text
    }
}
