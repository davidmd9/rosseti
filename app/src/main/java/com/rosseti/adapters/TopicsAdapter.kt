package com.rosseti.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rosseti.R
import com.rosseti.models.Topic

class TopicsAdapter(var topics: ArrayList<Topic>): RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {

    interface Delegate {
        fun selectTopic(topic: Topic)
    }

    var delegate: Delegate? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(topics[position])
    }

    override fun getItemCount(): Int {
        return topics.size
    }



    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvValue: TextView = view.findViewById(R.id.tvValue)
        private var currTopic: Topic? = null

        init {
            tvValue.setOnClickListener {
                delegate?.selectTopic(currTopic!!)
            }
        }

        fun bind(topic: Topic) {
            currTopic = topic
            tvValue.text = topic.title
        }
    }
}