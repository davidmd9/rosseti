package com.rosseti.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.rosseti.MainActivity
import com.rosseti.R
import com.rosseti.base.BaseFragment

class ProfileFragment: BaseFragment() {


    override var title: String = "Профиль"
    lateinit var textViewSuggest: TextView
    lateinit var textViewCommitCount: TextView
    lateinit var textViewSucces: TextView
    lateinit var textViewTotal: TextView
    lateinit var textViewRating: TextView

    lateinit var textViewInfo: TextView
    lateinit var buttonNext: RelativeLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        bindView(view)

        return view
    }

    private fun bindView(view: View) {
        textViewSuggest = view.findViewById(R.id.text_view_suggest)
        textViewCommitCount = view.findViewById(R.id.text_view_comit)
        textViewSucces = view.findViewById(R.id.text_view_succes)
        textViewRating = view.findViewById(R.id.text_view_rating)
        textViewInfo = view.findViewById(R.id.text_view_info)
        textViewTotal = view.findViewById(R.id.text_view_total)

        buttonNext = view.findViewById(R.id.button_next)

        buttonNext.setOnClickListener {
            mainActivity?.supportFragmentManager?.popBackStack()
        }
    }
}