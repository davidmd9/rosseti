package com.rosseti.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosseti.R
import com.rosseti.adapters.TopicsAdapter
import com.rosseti.base.BaseFragment
import com.rosseti.models.Topic

class TopicsFragment(
    private val topics: ArrayList<Topic>,
    private val delegate: TopicsAdapter.Delegate
    ): BaseFragment() {

    lateinit var rvTopics: RecyclerView
    override var title = "Направления деятельности"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_topics, container, false)
        bindView(view)

        return view
    }

    private fun bindView(view: View) {
        rvTopics = view.findViewById(R.id.rvTopics)
        val adapter = TopicsAdapter(topics)
        adapter.delegate = object : TopicsAdapter.Delegate {
            override fun selectTopic(topic: Topic) {
                delegate.selectTopic(topic)
                fragmentManager?.popBackStack()
            }
        }

        rvTopics.adapter = adapter
    }


}