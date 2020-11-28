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
import com.rosseti.base.BaseFragment
import com.rosseti.models.SuggestionRequest

class CreateSuggestionTopicFragment(private var model: SuggestionRequest): BaseFragment() {

    lateinit var btnNext: RelativeLayout
    lateinit var etName: EditText
    lateinit var tvTopic: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_suggesstion_topic, container, false)
        bindView(view)

        return view
    }



    private fun bindView(view: View) {
        btnNext = view.findViewById(R.id.btnNext)
        etName = view.findViewById(R.id.etName)
        tvTopic = view.findViewById(R.id.tvTopic)


        btnNext.setOnClickListener {
            model.topicId = 1
            model.title = etName.text.toString()

            (activity as? MainActivity)?.pushFragment(CreateSuggestionProblemFragment(model), true)
        }
    }
}