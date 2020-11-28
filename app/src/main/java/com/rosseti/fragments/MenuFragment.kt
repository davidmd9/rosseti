package com.rosseti.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.rosseti.MainActivity
import com.rosseti.R
import com.rosseti.base.BaseFragment
import com.rosseti.models.SuggestionRequest

class MenuFragment: BaseFragment() {

    lateinit var createSuggestion: ConstraintLayout
    lateinit var createApplication: ConstraintLayout
    lateinit var createExpert: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        bindView(view)

        return view
    }

    private fun bindView(view: View) {
        createSuggestion = view.findViewById(R.id.createSuggestion)
        createApplication = view.findViewById(R.id.createApplication)
        createExpert = view.findViewById(R.id.createExpert)

        createSuggestion.setOnClickListener {
            (activity as? MainActivity)?.pushFragment(CreateSuggestionTopicFragment(
                SuggestionRequest()
            ), true)
        }

        createApplication.setOnClickListener {
            (activity as? MainActivity)?.pushFragment(ProjectsFragment(), true)
        }

        createExpert.setOnClickListener {
//            (activity as? MainActivity)?.pushFragment()
            (activity as? MainActivity)?.pushFragment(ExpertFragment(), true)
        }
    }
}