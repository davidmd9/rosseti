package com.rosseti.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.rosseti.MainActivity
import com.rosseti.R
import com.rosseti.adapters.TopicsAdapter
import com.rosseti.base.BaseFragment
import com.rosseti.models.SuggestionRequest
import com.rosseti.models.Topic
import com.rosseti.models.TopicsResponse
import com.rosseti.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateSuggestionTopicFragment(private var model: SuggestionRequest): BaseFragment() {

    override var title = "Создать"
    var currentTopic: Topic? = null
    var topics: ArrayList<Topic> = ArrayList()

    lateinit var btnNext: RelativeLayout
    lateinit var etName: EditText
    lateinit var tvTopic: TextView
    lateinit var topic: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_suggesstion_topic, container, false)
        bindView(view)

        showProgress()
        ApiClient.getApi().topics.enqueue(object : Callback<TopicsResponse> {
            override fun onResponse(
                call: Call<TopicsResponse>,
                response: Response<TopicsResponse>
            ) {
                response.body()?.topics?.let { topics.clear(); topics.addAll(it) }
                hideProgress()
            }

            override fun onFailure(call: Call<TopicsResponse>, t: Throwable) {
                hideProgress()
            }

        })

        return view
    }



    private fun bindView(view: View) {
        btnNext = view.findViewById(R.id.btnNext)
        etName = view.findViewById(R.id.etName)
        tvTopic = view.findViewById(R.id.tvTopic)
        topic = view.findViewById(R.id.topic)


        btnNext.setOnClickListener {
            model.topicId = currentTopic?.id ?: 1
            model.title = etName.text.toString()

            (activity as? MainActivity)?.pushFragment(CreateSuggestionProblemFragment(model), true)
        }

        topic.setOnClickListener {
            val topicDelegate = object : TopicsAdapter.Delegate {
                override fun selectTopic(topic: Topic) {
                    currentTopic = topic
                    tvTopic.text = currentTopic?.title
                }
            }

            val fragment = TopicsFragment(topics, topicDelegate)
            (activity as? MainActivity)?.pushFragment(fragment, true)
        }

        tvTopic.text = currentTopic?.title ?: ""
    }
}